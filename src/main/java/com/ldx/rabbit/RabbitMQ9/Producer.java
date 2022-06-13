package com.ldx.rabbit.RabbitMQ9;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;

public class Producer {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitUtils.getChannel();
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority",10); //官方允许0-255,不要设置过大
        channel.queueDeclare("QUEUE_NAME",false,false,false,arguments);
        // 发消息
        for (int i = 0; i < 10; i++) {
            String message = "info" + i;
            if(i%5==0) {
                AMQP.BasicProperties  properties= new AMQP.BasicProperties().builder().priority(5).build();
                channel.basicPublish("","QUEUE_NAME",properties,message.getBytes());
            }else {
                channel.basicPublish("","QUEUE_NAME",null,message.getBytes());
            }
        }
            System.out.println("10条消息发送完毕");

    }
}
