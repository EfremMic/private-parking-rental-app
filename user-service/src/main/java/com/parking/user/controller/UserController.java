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

    @GetMapping("/me")
    public ResponseEntity<User> getOrCreateUser(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null) {
            log.warn("Unauthorized access attempt. No OIDC user found.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("Authenticated user: {} ({})", oidcUser.getFullName(), oidcUser.getEmail());

        try {
            User user = userService.getOrCreateUser(oidcUser);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Error creating or fetching user for email {}: {}", oidcUser.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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

