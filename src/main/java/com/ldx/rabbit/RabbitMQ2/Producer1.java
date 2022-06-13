package com.ldx.rabbit.RabbitMQ2;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.ldx.rabbit.RabbitMQ.Consumer.QUEUE_NAME;

public class Producer1 {
// 队列名称
public static void main(String[] args) {
    Channel channel = RabbitUtils.getChannel();
    try {
        channel.queueDeclare(QUEUE_NAME,false,false,true,null);
        // 控制台发送信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME, null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息完成: "+message);
        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}
}
