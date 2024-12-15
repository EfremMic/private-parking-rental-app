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

        log.info("Authenticated user: Name: {}, Email: {}", oidcUser.getClaim("name"), oidcUser.getEmail());

        try {
            User user = userService.getOrCreateUser(oidcUser);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Error creating or fetching user for email {}: {}", oidcUser.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
