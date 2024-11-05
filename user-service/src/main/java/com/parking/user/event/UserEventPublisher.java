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

    private final RabbitTemplate rabbitTemplate;
    private final String userLoginExchange;
    private final String userLoginRoutingKey;
    private final String userCreatedExchange;
    private final String userCreatedRoutingKey;

    @Autowired
    public UserEventPublisher(RabbitTemplate rabbitTemplate,
                              @Value("${user.login.exchange.name}") String userLoginExchange,
                              @Value("${user.login.routing.key}") String userLoginRoutingKey,
                              @Value("${user.created.exchange.name}") String userCreatedExchange,
                              @Value("${user.created.routing.key}") String userCreatedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.userLoginExchange = userLoginExchange;
        this.userLoginRoutingKey = userLoginRoutingKey;
        this.userCreatedExchange = userCreatedExchange;
        this.userCreatedRoutingKey = userCreatedRoutingKey;
    }

    public void publishLoginEvent(String userId) {
        Map<String, Object> event = new HashMap<>();
        event.put("userId", userId);
        event.put("eventType", "USER_LOGGED_IN");
        rabbitTemplate.convertAndSend(userLoginExchange, userLoginRoutingKey, event);
    }

    public void publishUserCreatedEvent(User user) {
        publishLoginEvent(user.getId().toString()); // Also publish a login event upon user creation
        rabbitTemplate.convertAndSend(userCreatedExchange, userCreatedRoutingKey, user);
    }
}
