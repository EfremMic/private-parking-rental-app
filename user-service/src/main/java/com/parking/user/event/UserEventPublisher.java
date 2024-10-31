package com.parking.user.event;

import com.parking.user.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishLoginEvent(String userId) {
        Map<String, Object> event = new HashMap<>();
        event.put("userId", userId);
        event.put("eventType", "USER_LOGGED_IN");
        rabbitTemplate.convertAndSend("user-login-exchange", "user.login", event);
    }

    public void publishUserCreatedEvent(User user) {
        publishLoginEvent(user.getId().toString());
        rabbitTemplate.convertAndSend("user-exchange-1", "user.created", user);
    }

}

