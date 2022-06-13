package com.ldx.rabbit.RabbitMQ9;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class Consumer {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitUtils.getChannel();
        // 声明一个交换机
        /**
         * 生产一个临时队列、对列的名称都是随机的
         * 消费者断开,队列自动删除
         */
        /**
         * 绑定交换机-队列
         */
//        channel.queueBind("QUEUE_NAME", "", "");
        // 消费者取消消息时回调接口
        channel.basicConsume("QUEUE_NAME", true,
                (consumerTag, message) -> {
                    // 接收消息
                    System.out.println("接收到的消息: ," + new String(message.getBody(), "utf-8") + "把收到的消息打印到屏幕上.........");

                }, (consumerTag -> {}));
    }
}
