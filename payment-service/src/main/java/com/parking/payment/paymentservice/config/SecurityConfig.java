package com.parking.payment.paymentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(new HandlerMappingIntrospector());

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(mvcMatcherBuilder.servletPath("/").pattern("/h2-console/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.servletPath("/api").pattern("/payment/stripe-key")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.servletPath("/api").pattern("/actuator/**")).permitAll() // Expose /actuator/*
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
