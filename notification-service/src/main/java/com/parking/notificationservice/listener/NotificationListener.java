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

        if ("USER_CREATED".equals(eventType)) {
            handleUserCreatedEvent(event);
        } else if ("USER_LOGGED_IN".equals(eventType)) {
            handleUserLoginEvent(event);
        } else {
            logger.warn("Received unknown event type: {}", eventType);
        }
    }


    private void handleUserCreatedEvent(Map<String, Object> event) {
        // Extracting user email and name from the event to send the welcome email
        String userEmail = (String) event.get("email");
        String userName = (String) event.get("name");

        logger.info("Processing USER_CREATED event for email: {}", userEmail);

        // Calling the service to send the welcome email
        notificationService.sendWelcomeEmail(userEmail, userName);
    }

    private void handleUserLoginEvent(Map<String, Object> event) {
        String userId = (String) event.get("userId");

        logger.info("Processing USER_LOGGED_IN event for user ID: {}", userId);

        // You could implement additional actions for the login event if needed
        notificationService.sendLoginNotification(userId);
    }
}
