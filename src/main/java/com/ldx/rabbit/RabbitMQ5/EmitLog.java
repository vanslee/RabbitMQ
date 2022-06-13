package com.ldx.rabbit.RabbitMQ5;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 消息发送
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        Scanner scanner =  new Scanner(System.in);
        while (scanner.hasNext()){
            String next = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,next.getBytes(StandardCharsets.UTF_8));
            System.out.println("成功发射:  "+next);
        }
    }
}
