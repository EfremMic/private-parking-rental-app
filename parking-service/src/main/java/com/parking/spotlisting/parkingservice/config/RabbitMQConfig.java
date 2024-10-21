package com.parking.spotlisting.parkingservice.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PARKING_QUEUE = "parking.events";

    @Bean
    public Queue parkingQueue() {
        return new Queue(PARKING_QUEUE, true);
    }
}