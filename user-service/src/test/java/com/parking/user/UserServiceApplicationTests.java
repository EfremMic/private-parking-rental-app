package com.parking.user;

import com.parking.user.event.UserEventPublisher;
import com.parking.user.model.User;
import com.parking.user.repository.UserRepository;
import com.parking.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
public class UserServiceApplicationTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserEventPublisher userEventPublisher;

	@Mock
	private SimpMessagingTemplate messagingTemplate;

	@InjectMocks
	private UserService userService;

	private User testUser;

	@BeforeEach
	public void setUp() {
		testUser = new User();
		testUser.setId(1L);
		testUser.setEmail("test@example.com");
		testUser.setName("Test User");
		testUser.setProfileImageUrl("http://example.com/profile.jpg");
	}

	@Test
	public void testGetOrCreateUser_NewUser_CallsPublishUserCreatedEvent() {
		// Mock userRepository to return an empty Optional, indicating the user does not exist
		when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());

		// Mock saving a new user in the repository
		when(userRepository.save(any(User.class))).thenReturn(testUser);

		// Call the service method
		userService.getOrCreateUser(testUser.getEmail(), testUser.getName(), testUser.getProfileImageUrl());

		// Verify that publishUserCreatedEvent is called with the correct parameters
		verify(userEventPublisher).publishUserCreatedEvent(
				testUser.getGoogleId(),
				testUser.getEmail(),
				testUser.getName(),
				testUser.getProfileImageUrl()
		);
	}

	@Test
	public void testGetOrCreateUser_ExistingUser_DoesNotCallPublishUserCreatedEvent() {
		// Mock userRepository to return an existing user
		when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

		// Call the service method
		userService.getOrCreateUser(testUser.getEmail(), testUser.getName(), testUser.getProfileImageUrl());

		// Verify that publishUserCreatedEvent is NOT called for an existing user
		verify(userEventPublisher, never()).publishUserCreatedEvent(any(), any(), any(), any());
	}
}
