package com.ldx.rabbit.RabbitMQ4;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.ldx.rabbit.RabbitMQ.Consumer.QUEUE_NAME;

public class Producer {
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitUtils.getChannel();
        // 开启发布确认
        channel.confirmSelect();
        // 声明队列
        //需要让Queue进行持久化
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        // 开启发布确认
        channel.confirmSelect();
        // 开始时间
        long BEGIN_TIME = System.currentTimeMillis();
        // 1.单个确认消息
//        for (int i = 0; i < 1000; i++) {
//            String message = i+"";
//            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
//            //单个消息马上进行发布确认
//            boolean flag = channel.waitForConfirms();
//            if(flag){
//                System.out.println("消息发送成功");
//            }
//        }
        // 2.批量确认消息
        int batchSize = 100;
        for (int i = 0; i < 1000; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            // 判断达到100条消息的时候批量确认一次
            if(i%batchSize == 0 ){
                // 发布确认
                channel.waitForConfirms();
            }
        }

        // 结束时间
        long END_TIME = System.currentTimeMillis();
        System.out.println("发布1000条耗时:" + (END_TIME-BEGIN_TIME)+"ms");
        // 从控制台输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String next = scanner.next();
            // 设置生产者发送消息为持久化消息(要求保存到磁盘上)保存在内存中

            System.out.println("生产者发出消息: "+next);
        }
    }
}
