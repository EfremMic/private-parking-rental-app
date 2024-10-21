package com.parking.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {

    public static final String User_QUEUE = "user.events";

    @Bean
    public Queue userQueue() {
        return new Queue(User_QUEUE, true);
    }
}
