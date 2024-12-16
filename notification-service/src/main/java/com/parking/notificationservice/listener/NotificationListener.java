package com.parking.notificationservice.listener;

import com.parking.notificationservice.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Service
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);
    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${user.queue.name}")
    public void handleUserEvent(Map<String, Object> event) {
        String eventType = (String) event.get("eventType");
        logger.info("Received event: {}", event);  // Log entire event for debugging

        switch (eventType) {
            case "USER_CREATED":
                handleUserCreatedEvent(event);
                break;
            case "USER_LOGGED_IN":
                handleUserLoginEvent(event);
                break;
            default:
                logger.warn("Received unknown event type: {}", eventType);
                break;
        }
    }

    private void handleUserCreatedEvent(Map<String, Object> event) {
        String userEmail = (String) event.get("email");
        String userName = (String) event.get("name");

        logger.info("Processing USER_CREATED event for email: {}", userEmail);

        // Call the service to send the welcome email
        try {
            notificationService.sendWelcomeEmail(userEmail, userName);
            logger.info("Welcome email sent to {}", userEmail);
        } catch (Exception e) {
            logger.error("Failed to send welcome email to {}: {}", userEmail, e.getMessage());
        }
    }

    private void handleUserLoginEvent(Map<String, Object> event) {
        String userId = (String) event.get("userId");

        logger.info("Processing USER_LOGGED_IN event for user ID: {}", userId);

        // Additional actions for login events
        try {
            notificationService.sendLoginNotification(userId);
            logger.info("Login notification sent for user ID {}", userId);
        } catch (Exception e) {
            logger.error("Failed to send login notification for user ID {}: {}", userId, e.getMessage());
        }
    }
}