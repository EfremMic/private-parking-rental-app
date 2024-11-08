package com.parking.user.config;

import com.parking.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .oauth2Login(oauth -> oauth
                        .loginPage("/oauth2/authorization/google")
                        .defaultSuccessUrl("http://localhost:3000/welcome", true))  // Redirect to frontend after login
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("http://localhost:3000"))  // Redirect after logout
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .build();
    }

    @Bean(name = "securityCorsConfigurer")
    public WebMvcConfigurer securityCorsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public OidcUserService customOidcUserService() {
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest oidcUserRequest) {
                OidcUser oidcUser = super.loadUser(oidcUserRequest);

                // Retrieve user's Google account details
                String email = oidcUser.getEmail();
                String name = oidcUser.getName();
                String profileImageUrl = oidcUser.getPicture();

                // Create or get the user in your application
                userService.getOrCreateUser(email, name, profileImageUrl);

                return oidcUser;
            }
        };
    }
}
