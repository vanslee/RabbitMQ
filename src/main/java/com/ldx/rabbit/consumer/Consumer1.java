package com.ldx.rabbit.consumer;

import com.ldx.rabbit.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.channels.Channel;
import java.util.Date;

/**
 * 接收消息
 * 队列TTL队列 消费者
 */
@Component
@Slf4j
public class Consumer1 {
    @RabbitListener(queues = "QD")
    public void receiveD(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间:{}.收到死信队列的消息: {}",new Date().toString(),msg);
    }
}
