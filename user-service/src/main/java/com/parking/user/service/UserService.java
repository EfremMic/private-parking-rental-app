package com.parking.user.service;

import com.parking.user.model.User;
import com.parking.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getOrCreateUser(OidcUser oidcUser) {
        String email = oidcUser.getEmail();
        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(oidcUser.getFullName());
            newUser.setProfileImageUrl(oidcUser.getPicture());
            return userRepository.save(newUser);
        });
    }
}
