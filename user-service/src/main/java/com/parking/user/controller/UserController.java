package com.parking.user.controller;

import com.parking.user.model.User;
import com.parking.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public User getUser(Principal principal) {
        OidcUser oidcUser = (OidcUser) principal;
        return userService.getOrCreateUser(oidcUser);
    }


}
