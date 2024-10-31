package com.parking.user.event;

import com.parking.user.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
@Service
public class UserEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    public UserEventListener(SimpMessagingTemplate messagingTemplate) {

        this.messagingTemplate = messagingTemplate;
    }

    // This method listens to the RabbitMQ queue for user creation events
    @RabbitListener(queues = "${user.queue.name}")
    public void handleUserCreatedEvent(User user) {
        // Forward the event to WebSocket subscribers under the topic "/topic/user-logins"
        messagingTemplate.convertAndSend("/topic/user-logins", user);
    }
}
