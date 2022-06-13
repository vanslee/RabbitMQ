package com.ldx.rabbit.RabbitMQ5.topic;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 消息发送
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "topic_logs";
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitUtils.getChannel();
        /**
         * 下图绑定关系如下
         * Q1-->绑定的是  中间带orange带3个单词的字符串(*.orange.*)
         * Q2-->绑定的是 最后一个单词是rabbit的3个单词(*.*.rabbit),第一个单词是lazy的多个单词(lazy.#)
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        Map<String,String > bindingKeyMap =  new HashMap<>();
        bindingKeyMap.put("quick.orange.rabbit","被队列Q1Q2接收到");
        bindingKeyMap.put("lazy.orange.elephant","被队列Q1Q2接收到");
        bindingKeyMap.put("quick.orange.fox","被队列Q1接收到");
        bindingKeyMap.put("lazy.brown.fox","被队列Q2接收到");
        bindingKeyMap.put("lazy.pink.rabbit","被队列Q2接收到");
        bindingKeyMap.put("quick.brown.fox","没队列接收");
        bindingKeyMap.put("quick.orange.male.rabbit","被队列Q2接收到");
        bindingKeyMap.put("lazy.orange.male.rabbit","被队列Q2接收到");
        for (Map.Entry<String, String> stringStringEntry : bindingKeyMap.entrySet()) {
            String routingKey = stringStringEntry.getKey();
            String message = stringStringEntry.getValue();
            channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息"+ message);

        }

    }
}
