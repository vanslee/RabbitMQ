package com.ldx.rabbit.RabbitMQ5.topic;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.Channel;

/**
 * 声明主题交换机 及相关队列
 */
public class ReceiveLogs2 {
    // 交换机的名称
    public static final String EXCHANGE_NAME = "topic_logs";
    // 声明队列
    public static final String QUEUE_NAME = "Q2";
    // 接收消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitUtils.getChannel();
        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "lazy.#");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*.*.rabbit");
        System.out.println("等待接收消息....");
        //接收消息
        channel.basicConsume(QUEUE_NAME, true,
                ((consumerTag, message) -> {
                    System.out.println("接收到的消息为: " + new String(message.getBody(), "utf-8"));
                    System.out.println("接收队列:  " + QUEUE_NAME + " 绑定键:  " + message.getEnvelope().getRoutingKey());
                }), ((consumerTag, sig) -> {

                }));
    }
}
