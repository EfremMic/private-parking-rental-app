package com.parking.notificationservice.controller;

import com.parking.notificationservice.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class TestEmailController {
    private final EmailService emailService;

    public TestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping("/send-test-email")
    ResponseEntity<String> sendTestEmail() {
        try {
            emailService.sendTestEmail("jupiter.walker.2023@gmail.com", "Test Email", "This is a test email");
            return ResponseEntity.ok("Email sent");

        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email not sent" + e.getMessage());

        }
    }
}