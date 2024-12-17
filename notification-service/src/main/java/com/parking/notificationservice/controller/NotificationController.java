package com.parking.notificationservice.controller;

import com.parking.notificationservice.service.NotificationService;
import com.parking.notificationservice.model.Notification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping("/list")
    public List<Notification> listNotifications() {
        return notificationService.getAllNotifications();
    }
}