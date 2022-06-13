package com.ldx.rabbit.RabbitMQ5.Direct;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class ExchangeDirect2 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明一个临时队列
        channel.queueDeclare("disk", false, false, true, null);
        // 交队捆绑
        channel.queueBind("disk", EXCHANGE_NAME, "error");
        channel.basicConsume("disk", true,
                ((consumerTag, message) -> {
                    // 接收消息
                    System.out.println("ReceiveLogs02控制台打印接收到的消息: " + new String(message.getBody(), "utf-8"));
                }), (consumerTag -> {
//                    消费者取消消息时回调该接口
                }));
    }
}
