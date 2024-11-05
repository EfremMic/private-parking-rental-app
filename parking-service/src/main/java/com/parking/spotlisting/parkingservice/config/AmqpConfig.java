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

    @Bean
    public TopicExchange userLoginExchange(@Value("${user.login.exchange.name}") String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public TopicExchange userCreatedExchange(@Value("${user.created.exchange.name}") String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue userLoginQueue(@Value("${user.login.queue.name}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Queue userCreatedQueue(@Value("${user.created.queue.name}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding loginBinding(Queue userLoginQueue, TopicExchange userLoginExchange,
                                @Value("${user.login.routing.key}") String routingKey) {
        return BindingBuilder.bind(userLoginQueue).to(userLoginExchange).with(routingKey);
    }

    @Bean
    public Binding createdBinding(Queue userCreatedQueue, TopicExchange userCreatedExchange,
                                  @Value("${user.created.routing.key}") String routingKey) {
        return BindingBuilder.bind(userCreatedQueue).to(userCreatedExchange).with(routingKey);
    }
}
