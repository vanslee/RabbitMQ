package com.ldx.rabbit.RabbitMQ2;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;

import java.io.IOException;

import static com.ldx.rabbit.RabbitMQ.Consumer.QUEUE_NAME;

/**
 * 这是一个工作线程(相当于之前的消费者)
 */
public class WorkThread {
    // 队列名称
    public static void main(String[] args) throws IOException {
        Thread thread1 = new WorkThread1();
        Thread thread2 = new WorkThread2();
        Thread thread3 = new WorkThread3();
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class WorkThread1 extends Thread {
    @SneakyThrows
    @Override
    public void run() {
        String name = currentThread().getName();
        System.out.println(name+"正在等待接收消息");
        Channel channel = RabbitUtils.getChannel();
        // 消息的接收
        channel.basicConsume(
                QUEUE_NAME,
                true,
                (consumerTag, message) -> {
                    System.out.println("接收到的消息" + message.getBody()+"        "+name);
                },
                (consumerTag) -> {
                    System.out.println("消息取消消费接口的回调逻辑" + consumerTag+"      "+name);
                });
    }
}

class WorkThread2 extends Thread {
    @SneakyThrows
    @Override
    public void run() {
        String name = currentThread().getName();
        System.out.println(name+"正在等待接收消息");
        Channel channel = RabbitUtils.getChannel();
        // 消息的接收
        channel.basicConsume(
                QUEUE_NAME,
                true,
                (consumerTag, message) -> {
                    System.out.println("接收到的消息" + message.getBody()+"        "+name);
                },
                (consumerTag) -> {
                    System.out.println("消息取消消费接口的回调逻辑" + consumerTag+"      "+name);
                });
    }
}

class WorkThread3 extends Thread {
    @SneakyThrows
    @Override

    public void run() {
        String name = currentThread().getName();
        System.out.println(name+"正在等待接收消息");
        Channel channel = RabbitUtils.getChannel();
        // 消息的接收
        channel.basicConsume(
                QUEUE_NAME,
                true,
                (consumerTag, message) -> {
                    System.out.println("接收到的消息" + message.getBody()+"        "+name);
                },
                (consumerTag) -> {
                    System.out.println("消息取消消费接口的回调逻辑" + consumerTag+"      "+name);
                });
    }
}
