package com.parking.user.service;

import com.parking.user.model.User;
import com.parking.user.repository.UserRepository;
import com.parking.user.event.UserEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserEventPublisher userEventPublisher;
    private final SimpMessagingTemplate messagingTemplate;

    public User getOrCreateUser(OidcUser oidcUser) {
        String email = oidcUser.getEmail();
        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(oidcUser.getFullName());
            newUser.setProfileImageUrl(oidcUser.getPicture());

            log.info("Saving new user with email: {}", email);
            User savedUser = userRepository.save(newUser);

            log.info("Publishing UserCreatedEvent for new user: {}", savedUser.getEmail());
            userEventPublisher.publishUserCreatedEvent(savedUser.getGoogleId(), savedUser.getEmail(), savedUser.getName(), savedUser.getProfileImageUrl());

            messagingTemplate.convertAndSend("/topic/user-logins", savedUser);

            return savedUser;
        });
    }

    public User createUser(User user) {
        log.info("Creating new user with email: {}", user.getEmail());
        return userRepository.save(user);
    }

    public User getOrCreateUser(String email, String name, String profileImageUrl) {
        userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setProfileImageUrl(profileImageUrl);

            User savedUser = userRepository.save(newUser);
            userEventPublisher.publishUserCreatedEvent(savedUser.getGoogleId(), email, name, profileImageUrl);

            return savedUser;
        });
        return null;
    }
}
