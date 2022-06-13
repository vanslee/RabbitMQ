package com.ldx.rabbit.RabbitMQ3;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.Channel;


import java.io.IOException;

import static com.ldx.rabbit.RabbitMQ3.Producer.TASK_QUEUE_NAME;

/**
 * 消息在手动应答时是不丢失,放回队列中重新消费
 */
public class AckByMyself {
    public static void main(String[] args) {
        Thread thread1 = new workThread1();
        Thread thread2 = new workThread2();
        thread1.start();
        thread2.start();
    }
}
class workThread1 extends Thread{
    @Override
    public void run() {
        String name = currentThread().getName();
        Channel channel = RabbitUtils.getChannel();
        System.out.println(name+"等待接收消息");
        // 关闭自动应答
        boolean autoAck = false;
        // 不公平分发
        int prefetchCount=1;
        try {
            channel.basicQos(prefetchCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            channel.basicConsume(TASK_QUEUE_NAME,autoAck,
                    ((consumerTag, message) -> {
                        String message1 = new String(message.getBody(),"utf-8");
                        // 模拟不同性能CPU处理消息所消耗的时长
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(name+":   接收到消息:"+message1);


                        /**
                         * param1: 消息的标记tag
                         * param2: false代表只应答接收到的那个传递的消息,true为应答所有消息包括传递过来的消息
                         */
                        channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                    }),(consumerTag -> {}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
class workThread2 extends Thread{
    @Override
    public void run() {
        String name = currentThread().getName();
        Channel channel = RabbitUtils.getChannel();
        System.out.println(name+"等待接收消息");
        // 不公平分发
        int prefetchCount=1;
        try {
            channel.basicQos(prefetchCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 关闭自动应答
        boolean autoAck = false;
        try {
            channel.basicConsume(TASK_QUEUE_NAME,autoAck,
                    ((consumerTag, message) -> {
                        String message1 = new String(message.getBody(),"utf-8");
                        // 模拟不同性能CPU处理消息所消耗的时长
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(name+":  接收到消息:"+message1);

                        /**
                         * param1: 消息标记tag
                         * param2: false代表只应答接收到的那个传递的消息,true为应答所有消息包括传递过来的消息
                         */
                        channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                    }),(consumerTag -> {}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }
