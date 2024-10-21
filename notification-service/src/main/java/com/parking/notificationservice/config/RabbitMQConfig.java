package com.parking.notificationservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_EVENTS_QUEUE = "user.events";
    public static final String PARKING_EVENTS_QUEUE = "parking.events";

    @Bean
    public Queue userEventsQueue() {
        return new Queue(USER_EVENTS_QUEUE, true);
    }

    @Bean
    public Queue parkingEventsQueue() {
        return new Queue(PARKING_EVENTS_QUEUE, true);
    }
}
