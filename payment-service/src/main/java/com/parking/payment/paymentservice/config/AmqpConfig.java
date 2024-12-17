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
@Configuration
public class AmqpConfig {

    public static final String USER_EXCHANGE = "user.exchange";
    public static final String USER_QUEUE = "user.events";
    public static final String PAYMENT_EXCHANGE = "payment.exchange";
    public static final String PAYMENT_REQUEST_QUEUE = "payment-request-queue";
    public static final String PAYMENT_RESULT_QUEUE = "payment-result-queue";

    @Bean
    public TopicExchange userExchange() {
        return ExchangeBuilder.topicExchange(USER_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue userCreatedQueue() {
        return new Queue(USER_QUEUE, true);
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder.bind(userCreatedQueue()).to(userExchange()).with("user.created");
    }

    @Bean
    public TopicExchange paymentExchange() {
        return ExchangeBuilder.topicExchange(PAYMENT_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue paymentRequestQueue() {
        return new Queue(PAYMENT_REQUEST_QUEUE, true);
    }

    @Bean
    public Queue paymentResultQueue() {
        return new Queue(PAYMENT_RESULT_QUEUE, true);
    }

    @Bean
    public Binding paymentRequestBinding() {
        return BindingBuilder.bind(paymentRequestQueue()).to(paymentExchange()).with("payment.request");
    }

    @Bean
    public Binding paymentResultBinding() {
        return BindingBuilder.bind(paymentResultQueue()).to(paymentExchange()).with("payment.result");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
