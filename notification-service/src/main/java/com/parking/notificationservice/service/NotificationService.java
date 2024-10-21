package com.parking.notificationservice.service;

import com.parking.notificationservice.model.Notification;
import com.parking.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void processNotification(Notification notification) {
        // Example: Sending email (you could integrate with an email provider)
        logger.info("Processing notification for recipient: {}", notification.getRecipient());
        notification.setStatus(Notification.STATUS_SENT);

        // Save notification to DB
        notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
