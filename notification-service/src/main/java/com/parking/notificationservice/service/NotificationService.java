package com.parking.notificationservice.service;

import com.parking.notificationservice.model.Notification;
import com.parking.notificationservice.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;

    public NotificationService(NotificationRepository notificationRepository, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }

    // Unified RabbitListener to handle all user events from a single queue
    @RabbitListener(queues = "${user.queue.name}")
    public void handleUserEvent(Map<String, Object> event) {
        String eventType = (String) event.get("eventType");

        switch (eventType) {
            case "USER_CREATED":
                handleUserCreatedEvent(event);
                break;
            case "USER_LOGGED_IN":
                handleUserLoginEvent(event);
                break;
            default:
                logger.warn("Unknown event type received: {}", eventType);
        }
    }

    private void handleUserCreatedEvent(Map<String, Object> event) {
        String userEmail = (String) event.get("email");
        String userName = (String) event.get("name");

        logger.info("Processing USER_CREATED event for email: {}", userEmail);
        sendWelcomeEmail(userEmail, userName);
    }

    private void handleUserLoginEvent(Map<String, Object> event) {
        String userId = (String) event.get("userId");

        logger.info("Processing USER_LOGGED_IN event for user ID: {}", userId);
        sendLoginNotification(userId);
    }

    public void sendWelcomeEmail(String email, String name) {
        logger.info("Preparing to send welcome email to {}", email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Welcome to ParkingApp!");
        message.setText("Hello " + name + ",\n\nWelcome to ParkingApp! We're thrilled to have you on board.");

        try {
            mailSender.send(message);
            logger.info("Welcome email successfully sent to {}", email);

            // Optionally, save the notification record in the database
            Notification notification = new Notification();
            notification.setRecipient(email);
            notification.setMessage("Welcome email sent to " + email);
            notification.setStatus(Notification.STATUS_SENT);
            notificationRepository.save(notification);

        } catch (Exception e) {
            logger.error("Failed to send welcome email to {}: {}", email, e.getMessage(), e);
        }
    }


    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public void sendLoginNotification(String userId) {
        logger.info("Sending login notification to user ID: {}", userId);
        // Additional login notification logic can be implemented here
    }

}
