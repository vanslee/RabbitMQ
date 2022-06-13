package com.ldx.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class TTLQueueConfig {
    // 普通交换机名称
    public static final String X_EXCHANGE = "X";
    // 死信交换机名称
    public static final String Y_DEAD_LATER_EXCHANGE = "Y";
    // 普通队列名称
    public static  final String QUEUE_NAME_A = "QA";
    public static  final String QUEUE_NAME_B = "QB";
    public static  final String QUEUE_NAME_C = "QC";
    // 死信队列名称
    public static  final String QUEUE_DEAD_NAME_D = "QD";
    // 声明X交换机
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }
    // 声明Y交换机
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LATER_EXCHANGE);
    }
    // 声明队列,设置TTL为10s
    @Bean("queueA")
    public Queue   queueA(){
        HashMap<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LATER_EXCHANGE);
        // 设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        // 设置TTL 单位是ms
        arguments.put("x-message-ttl",10000);
        // 设置死信RoutingKey
        return QueueBuilder.durable(QUEUE_NAME_A).withArguments(arguments).build();
    }
    // 声明队列,设置TTL为10s
    @Bean("queueB")
    public Queue   queueB(){
        HashMap<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LATER_EXCHANGE);
        // 设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        // 设置TTL 单位是ms
        arguments.put("x-message-ttl",40000);
        // 设置死信RoutingKey
        return QueueBuilder.durable(QUEUE_NAME_B).withArguments(arguments).build();
    }
    // 通用时间队列
    @Bean("queueC")
    public Queue   queueC(){
        HashMap<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LATER_EXCHANGE);
        // 设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        // 设置TTL 单位是ms
//        arguments.put("x-message-ttl",40000);
        // 设置死信RoutingKey
        return QueueBuilder.durable(QUEUE_NAME_C).withArguments(arguments).build();
    }

    // 死信队列
    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(QUEUE_DEAD_NAME_D).build();
    }
    @Bean
    // 绑定 QA --> xExchange
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,@Qualifier("xExchange")DirectExchange exchange) {
        return BindingBuilder.bind(queueA).to(exchange).with("XA");
    }
    @Bean
    // 绑定 QB --> xExchange
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,@Qualifier("xExchange")DirectExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange).with("XB");
    }
    @Bean
    // 绑定 QB --> xExchange
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueB,@Qualifier("xExchange")DirectExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange).with("XC");
    }
    @Bean
    // 绑定QD --> yExchange
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,@Qualifier("yExchange")DirectExchange exchange) {
        return BindingBuilder.bind(queueD).to(exchange).with("YD");
    }
}
