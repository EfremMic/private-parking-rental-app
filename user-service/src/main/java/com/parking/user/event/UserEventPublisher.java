package com.parking.user.event;

import com.parking.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserEventPublisher.class);

    @Value("${user.created.exchange.name}")
    private String exchangeName;

    @Value("${user.created.routing.key}")
    private String routingKey;

    @Value("${user.login.exchange.name}")
    private String userLoginExchange;

    @Value("${user.login.routing.key}")
    private String userLoginRoutingKey;

    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserCreatedEvent(String googleId, String email, String name, String profileImageUrl) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "USER_CREATED");
        event.put("googleId", googleId);
        event.put("email", email);
        event.put("name", name);
        event.put("profileImageUrl", profileImageUrl);

        log.info("Publishing USER_CREATED event to exchange: {} with routing key: {} and event: {}", exchangeName, routingKey, event);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, event);
    }

    public void publishLoginEvent(String userId) {
        Map<String, Object> event = new HashMap<>();
        event.put("userId", userId);
        event.put("eventType", "USER_LOGGED_IN");

        log.info("Publishing USER_LOGGED_IN event to exchange: {} with routing key: {}", userLoginExchange, userLoginRoutingKey);
        rabbitTemplate.convertAndSend(userLoginExchange, userLoginRoutingKey, event);
    }
}
