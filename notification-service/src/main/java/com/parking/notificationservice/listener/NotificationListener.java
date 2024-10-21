package com.parking.notificationservice.listener;

import com.parking.notificationservice.service.NotificationService;
import com.parking.notificationservice.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);
    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "user.events")
    public void handleUserEvent(Notification notification) {
        logger.info("Received user event: {}", notification.getMessage());
        notificationService.processNotification(notification);
    }

    @RabbitListener(queues = "parking.events")
    public void handleParkingEvent(Notification notification) {
        logger.info("Received parking event: {}", notification.getMessage());
        notificationService.processNotification(notification);
    }
}
