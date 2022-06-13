package com.ldx.rabbit.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者 接收消息
 */
public class Consumer {
    // 对列的名称
    public static final String QUEUE_NAME = "hello";

    // 接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("139.9.70.25");
        factory.setUsername("admin");
        factory.setPassword("zhou2001.");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * 消费者消费消息
         * param1:消费哪个队列
         * param2:消费成功后是否要自动应答,true自动应答
         * param3:消费未成功时的回调函数
         * param4:消费者取消消费的回调
         */
        channel.basicConsume(
                QUEUE_NAME,
                true,
                (consumerTag, message) -> {
                    System.out.printf(new String(message.getBody()));
                },
                (consumerTag) -> {
                    System.out.println("消息被中断");
                });
    }

}
