package com.ldx.rabbit.RabbitMQ5;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * 消息接收
 */
public class ReceiveLog1 {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        /**
         * 生产一个临时队列、对列的名称都是随机的
         * 消费者断开,队列自动删除
         */
        String queue = channel.queueDeclare().getQueue();
        /**
         * 绑定交换机-队列
         */
        channel.queueBind(queue, EXCHANGE_NAME, "");
        System.out.println("1111等待接收消息,把收到的消息打印到屏幕上.........");
        // 消费者取消消息时回调接口
        channel.basicConsume(queue, true,
                (consumerTag, message) -> {
                    // 接收消息
                    System.out.println("接收到的消息: ," + new String(message.getBody(), "utf-8") + "把收到的消息打印到屏幕上.........");

                }, (consumerTag -> {}));
    }
}
