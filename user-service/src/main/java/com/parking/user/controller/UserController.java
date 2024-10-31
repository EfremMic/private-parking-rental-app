package com.parking.user.controller;

import com.parking.user.model.User;
import com.parking.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint to fetch or create a user based on the authenticated Google OAuth2 (OIDC) user.
     *
     * @param oidcUser The authenticated OIDC user object
     * @return The created or fetched User entity
     */
    @GetMapping("/me")
    public ResponseEntity<User> getOrCreateUser(@AuthenticationPrincipal OidcUser oidcUser) {
        // Check if OIDC user is null
        if (oidcUser == null) {
            log.warn("Unauthorized access attempt. No OIDC user found.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // Return 401 Unauthorized if no user is authenticated
        }

        // Extract user info from Google OAuth2 OIDC User
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String picture = oidcUser.getPicture();

        log.info("Authenticated user: {} ({})", name, email);

        // Create or retrieve the user in the database
        try {
            User user = userService.getOrCreateUser(email, name, picture);
            return ResponseEntity.ok(user);  // Return 200 OK with the user data
        } catch (Exception e) {
            log.error("Error creating or fetching user for email {}: {}", email, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Return 500 if something went wrong
        }
    }

    /**
     * Endpoint to create a user manually by sending a JSON payload.
     *
     * @param user The user data to create
     * @return The created User entity
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Creating user with email: {}", user.getEmail());

        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);  // Return 200 OK with the created user
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint to create a user using URL parameters.
     *
     * @param email The user's email address
     * @param name The user's name
     * @param profileImageUrl The user's profile image URL
     * @return The created or fetched User entity
     */
    @PostMapping("/createWithParams")
    public ResponseEntity<User> createUserWithParams(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String profileImageUrl
    ) {
        log.info("Creating user with params: email={}, name={}", email, name);

        try {
            User user = userService.getOrCreateUser(email, name, profileImageUrl);
            return ResponseEntity.ok(user);  // Return 200 OK with the user data
        } catch (Exception e) {
            log.error("Error creating user with params: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
