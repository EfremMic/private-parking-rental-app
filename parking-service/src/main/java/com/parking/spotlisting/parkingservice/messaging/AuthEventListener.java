package com.parking.spotlisting.parkingservice.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthEventListener {

    // In-memory store to manage user authentication sessions
    private final Map<String, Boolean> userSessions = new HashMap<>();

    // Listen to messages on the specified queue and handle authentication events
    @RabbitListener(queues = "${user.queue.name}")
    public void handleUserAuthEvent(UserAuthEvent event) {
        // Process the event and store user authentication state
        if (event.isAuthenticated()) {
            userSessions.put(event.getUserId(), true);
        }
    }

    // Method to check if a user is authenticated based on user ID
    public boolean isUserAuthenticated(String userId) {
        return userSessions.getOrDefault(userId, false);
    }
}
