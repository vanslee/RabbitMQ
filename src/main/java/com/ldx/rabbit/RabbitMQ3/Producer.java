package com.ldx.rabbit.RabbitMQ3;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Producer {
    final static String  TASK_QUEUE_NAME = "ASK_QUEUE";
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitUtils.getChannel();
        //声明队列
        channel.queueDeclare(TASK_QUEUE_NAME,false,false,false,null);
        //从控制发出消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String next = scanner.next();
            // 设置生产者发送消息为持久化消息(要求保存到磁盘上),虽然不能保证一定会被持久化,但一般情况不会出现问题
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,next.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息: "+next);
        }
    }
}
