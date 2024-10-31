package com.parking.user;

import com.parking.user.event.UserEventPublisher;
import com.parking.user.model.User;
import com.parking.user.repository.UserRepository;
import com.parking.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceApplicationTests {
	private UserRepository userRepository;
	private UserEventPublisher userEventPublisher;
	private SimpMessagingTemplate simpMessagingTemplate; // Add SimpMessagingTemplate mock
	private UserService userService;

	@BeforeEach
	void setUp() {
		userRepository = Mockito.mock(UserRepository.class);
		userEventPublisher = Mockito.mock(UserEventPublisher.class);
		simpMessagingTemplate = Mockito.mock(SimpMessagingTemplate.class); // Mock the SimpMessagingTemplate
		userService = new UserService(userRepository, userEventPublisher, simpMessagingTemplate); // Pass the mock
	}

	@Test
	void testGetOrCreateUser_userAlreadyExists() {
		OidcUser oidcUser = mock(OidcUser.class);
		when(oidcUser.getEmail()).thenReturn("test@example.com");
		User existingUser = new User();
		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

		userService.getOrCreateUser(oidcUser);

		verify(userRepository, times(1)).findByEmail("test@example.com");
		verify(userRepository, never()).save(any(User.class)); // Ensure save is never called since the user exists
		verify(userEventPublisher, never()).publishUserCreatedEvent(any(User.class)); // No event published for existing user
	}

	@Test
	void testGetOrCreateUser_newUserIsCreated() {
		OidcUser oidcUser = mock(OidcUser.class);
		when(oidcUser.getEmail()).thenReturn("newuser@example.com");
		when(oidcUser.getFullName()).thenReturn("New User");
		when(oidcUser.getPicture()).thenReturn("https://example.com/profile.jpg");

		when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());

		User savedUser = new User();
		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		userService.getOrCreateUser(oidcUser);

		verify(userRepository, times(1)).findByEmail("newuser@example.com");
		verify(userRepository, times(1)).save(any(User.class)); // Ensure save is called for the new user
		verify(userEventPublisher, times(1)).publishUserCreatedEvent(any(User.class)); // Event should be published
	}
}
