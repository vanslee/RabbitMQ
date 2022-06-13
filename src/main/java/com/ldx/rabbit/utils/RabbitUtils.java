package com.ldx.rabbit.utils;


import com.ldx.rabbit.properties.RabbitProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 创建信道的工具类
 */
public class RabbitUtils {
    @Autowired
    private  RabbitProperties properties;
    public static Channel getChannel() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("139.9.70.25");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("zhou2001.");
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
            return connection.createChannel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
