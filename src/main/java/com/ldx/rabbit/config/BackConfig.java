package com.ldx.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackConfig {
    public static final String CONFIRM_QUEUE = "confirm.queue";
    public static final String CONFIRM_EXCHANGE = "confirm.exchange";
    public static final String ROUTING_KEY = "key1";
    public static final String BACKUP_EXCHANGE = "backup.exchange";
    public static final String BACKUP_QUEUE = "backup.queue";
    public static final String WARNING_QUEUE = "warning.queue";
    @Bean(CONFIRM_QUEUE)
    public Queue confirmQueue() {
        return new Queue(CONFIRM_QUEUE);
    }
    @Bean(BACKUP_QUEUE)
    public Queue backQueue() {
        return new Queue(BACKUP_QUEUE);
    }
    @Bean(WARNING_QUEUE)
    public Queue warningQueue() {
        return new Queue(WARNING_QUEUE);
    }
    @Bean(BACKUP_EXCHANGE)
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE,false,true);
    }
    @Bean(CONFIRM_EXCHANGE)
    public DirectExchange directExchange() {
        return  new DirectExchange(CONFIRM_EXCHANGE,false,true);
    }
    @Bean
    public Binding directExchangeToConfirmQueue(@Qualifier(CONFIRM_EXCHANGE)DirectExchange exchange,@Qualifier(CONFIRM_QUEUE)Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
    @Bean
    public Binding directExchangeToBackExchange(@Qualifier(CONFIRM_EXCHANGE)DirectExchange directExchange,@Qualifier(BACKUP_EXCHANGE)FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutExchange).to(directExchange).with("");
    }
    @Bean
    public Binding BackExchangeToBackQueue(@Qualifier(BACKUP_EXCHANGE)FanoutExchange exchange,@Qualifier(BACKUP_QUEUE)Queue queue) {
        return BindingBuilder.bind(queue).to(exchange);
    }
    @Bean
    public Binding BackExchangeToWarningBackQueue(@Qualifier(BACKUP_EXCHANGE)FanoutExchange exchange,@Qualifier(WARNING_QUEUE)Queue queue) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}
