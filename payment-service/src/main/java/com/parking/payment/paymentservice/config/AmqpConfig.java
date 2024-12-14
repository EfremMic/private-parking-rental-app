package com.parking.payment.paymentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class AmqpConfig {

    private static final Logger logger = LoggerFactory.getLogger(AmqpConfig.class);

    @Bean
    public TopicExchange userExchange(@Value("${user.exchange.name}") String exchangeName) {
        return buildExchange(exchangeName);
    }

    @Bean
    public Queue userCreatedQueue(@Value("${user.queue.name}") String queueName) {
        return buildQueue(queueName);
    }

    @Bean
    public Binding userBinding(Queue userCreatedQueue, TopicExchange userExchange, @Value("${user.created.routing.key}") String routingKey) {
        return buildBinding(userCreatedQueue, userExchange, routingKey);
    }

    @Bean
    public TopicExchange paymentExchange(@Value("${payment.exchange.name}") String exchangeName) {
        return buildExchange(exchangeName);
    }

    @Bean
    public Queue paymentRequestQueue(@Value("${payment.request.queue.name}") String queueName) {
        return buildQueue(queueName);
    }

    @Bean
    public Queue paymentResultQueue(@Value("${payment.result.queue.name}") String queueName) {
        return buildQueue(queueName);
    }

    @Bean
    public Binding paymentRequestBinding(Queue paymentRequestQueue, TopicExchange paymentExchange, @Value("${payment.request.routing.key}") String routingKey) {
        return buildBinding(paymentRequestQueue, paymentExchange, routingKey);
    }

    @Bean
    public Binding paymentResultBinding(Queue paymentResultQueue, TopicExchange paymentExchange, @Value("${payment.result.routing.key}") String routingKey) {
        return buildBinding(paymentResultQueue, paymentExchange, routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

    @PostConstruct
    public void logConfiguration() {
        logger.info("RabbitMQ Configuration Initialized");
    }

    private TopicExchange buildExchange(String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    private Queue buildQueue(String queueName) {
        return new Queue(queueName, true);
    }

    private Binding buildBinding(Queue queue, TopicExchange exchange, String routingKey) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
}
