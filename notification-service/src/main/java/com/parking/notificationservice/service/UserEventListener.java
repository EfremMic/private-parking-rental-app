package com.parking.notificationservice.service;

import org.apache.catalina.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventListener {

    @RabbitListener(queues = "user.creatred.queue")
    public void handleUserCreatedEvent(User user) {
        System.out.println("User created event received: " + user.getName());
    }
}
