package com.parking.user.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class AmqpConfig {

    // Declare the Topic Exchange
    @Bean
    public TopicExchange userExchange(@Value("${user.exchange.name}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    // Declare the Queue for user-related messages
    @Bean
    public Queue userQueue(@Value("${user.queue.name}") final String queueName) {
        return new Queue(queueName, true);  // true makes the queue durable
    }

    // Bind the Queue to the Exchange using the routing key
    @Bean
    public Binding binding(Queue userQueue, TopicExchange userExchange, @Value("${user.created.routing.key}") final String routingKey) {
        return BindingBuilder.bind(userQueue).to(userExchange).with(routingKey);
    }

    // Use Jackson2JsonMessageConverter for serializing messages to JSON
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
