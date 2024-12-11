package com.parking.payment.paymentservice.config;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
@Configuration
public class AmqpConfig {

    @Bean
    public TopicExchange userExchange(@Value("${user.exchange.name}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Queue userCreatedQueue(@Value("${user.queue.name}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding binding(Queue userCreatedQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userCreatedQueue).to(userExchange).with("user.created");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
*/


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

    // User Exchange and Queue Configuration
    @Bean
    public TopicExchange userExchange(@Value("${user.exchange.name}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Queue userCreatedQueue(@Value("${user.queue.name}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding userBinding(Queue userCreatedQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userCreatedQueue).to(userExchange).with("user.created");
    }

    // Payment Exchange, Request Queue, and Response Queue Configuration
    @Bean
    public TopicExchange paymentExchange(@Value("${payment.exchange.name}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Queue paymentRequestQueue(@Value("${payment.request.queue}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Queue paymentResultQueue(@Value("${payment.result.queue}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding paymentRequestBinding(Queue paymentRequestQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentRequestQueue).to(paymentExchange).with("payment.request");
    }

    @Bean
    public Binding paymentResultBinding(Queue paymentResultQueue, TopicExchange paymentExchange) {
        return BindingBuilder.bind(paymentResultQueue).to(paymentExchange).with("payment.result");
    }

    // JSON Message Converter
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
