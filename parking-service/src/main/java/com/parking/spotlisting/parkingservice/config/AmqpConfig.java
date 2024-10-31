package com.parking.spotlisting.parkingservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    // Define a topic exchange for user events
    @Bean
    public TopicExchange userExchange(@Value("${user.exchange.name}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    // Define a queue for user creation events
    @Bean
    public Queue userCreatedQueue(@Value("${user.queue.name}") final String queueName) {
        return new Queue(queueName, true);
    }

    // Bind the user-created queue to the user exchange with routing key "user.created"
    @Bean
    public Binding binding(Queue userCreatedQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userCreatedQueue).to(userExchange).with("user.created");
    }

    // Set up a message converter to handle JSON serialization/deserialization
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
