package com.parking.notificationservice.config;

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

    // Declare the Topic Exchange
    @Bean
    public TopicExchange userExchange(@Value("${user.created.exchange.name}") String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    // Declare the Queue
    @Bean
    public Queue userCreatedQueue(@Value("${user.queue.name}") String queueName) {
        return new Queue(queueName, true);
    }

    // Bind the Queue to the Exchange with the routing key
    @Bean
    public Binding userBinding(Queue userCreatedQueue, TopicExchange userExchange, @Value("${user.created.routing.key}") String routingKey) {
        return BindingBuilder.bind(userCreatedQueue).to(userExchange).with(routingKey);
    }

    // JSON message converter
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
