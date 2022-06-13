package com.ldx.rabbit.RabbitMQ5.Direct;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Direct_logs {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitUtils.getChannel();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"warning",null ,next.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息: "+next);
        }

    }
}
