package com.ldx.rabbit.RabbitMQ4.asyn;

import com.ldx.rabbit.utils.RabbitUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static com.ldx.rabbit.RabbitMQ.Consumer.QUEUE_NAME;

public class ConfirmMessage {
    // 异步发布确认
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitUtils.getChannel();
        // 队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 开始时间
        // 开启发布确认
        channel.confirmSelect();
        /**
         * 线程安全有序的一个哈希表 适用于高并发的情况下
         * 特点:
         * 1. 轻松的将序号与消息进行关联
         * 2. 轻松的批量删除条目,只要给到需要
         * 3. 支持高并发
         */
        ConcurrentSkipListMap<Long,String> outStandingConfirms = new ConcurrentSkipListMap<>();
        long begin = System.currentTimeMillis();
        // 准备消息监听器,监听哪些消息成功了,哪些消息失败了
        // 1.监听成功消息
        // 2.监听失败消息
        /**
         * deliveryTag: 消息标记
         * multiple: 是否为批量确认
         */
        channel.addConfirmListener(
                ((deliveryTag, multiple) -> {
                    if(multiple){
                    // 1.删除已经确认的消息,剩下的就是未确认的消息
                        ConcurrentNavigableMap<Long, String> hasConfirmed = outStandingConfirms.headMap(deliveryTag);
                        hasConfirmed.clear();
                    }else {
                        outStandingConfirms.remove(deliveryTag);
                    }
                    // 消息确认成功回调方法
                    System.out.println("已经确认的消息: "+deliveryTag);
                }),((deliveryTag, multiple) -> {
                    // 2.未确认的消息都有哪些
                    String message = outStandingConfirms.get(deliveryTag);
                    // 消息确认失败回调函数
                    System.out.println("未确认的消息是: "+message+"     未确认的标记是: "+deliveryTag);
                })
        );
        // 批量确认消息大小
        // 批量发送消息 批量发布确认
        for (int i = 0; i < 1000; i++) {
            String message = i+"";
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            // 发布确认  (异步)
            // 1.记录下所有要发送的消息 消息的总数
            outStandingConfirms.put(channel.getNextPublishSeqNo()-1,message);
        }
        long end = System.currentTimeMillis();
        System.out.println("异步发送消息耗时: "+(end-begin)+"ms");
    }
}
