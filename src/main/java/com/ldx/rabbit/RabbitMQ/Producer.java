package com.ldx.rabbit.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Producer {
    // 队列名称
    public static final String QUEUE_NAME = "hello";
    // 发消息
    public static void main(String[] args) throws IOException, TimeoutException {

        //TODO 1.创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 工厂IP连接RabbitMQ的队列
        factory.setHost("139.9.70.25");
        // 用户名
        factory.setUsername("admin");
        // 密码
        factory.setPassword("zhou2001.");

        //TODO 2.创建连接
        Connection connection = factory.newConnection();
        //TODO 3.获取信道
        Channel channel = connection.createChannel();
        //TODO 4.设置交换机(项目简单时可以省略用默认)
        //TODO 5.生成一个队列
        /**
         * param1: 队列名称
         * param2: 队列里面的消息是否持久化 默认存储在内存中
         * param3: 该队列是否只供一个消费者进行消费(消息共享),TRUE表示只供一个消费者消费
         * param4: 是否自动删除 最后一个消费者断开连接以后,该队列最后一条是否自动删除
         * param5:其他参数:
         */
        channel.queueDeclare(QUEUE_NAME,false,false,true,null);
        // 发消息
        String message = "hello world";
        /**
         * param1: 发送到哪个交换机,使用默认时填空字符串即可
         * param2: 路由的key值是哪个,即队列的名称
         * param3: 其他参数信息
         * param4: 发送消息的消息
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
        System.out.printf("消息发送完毕");

    }
}
