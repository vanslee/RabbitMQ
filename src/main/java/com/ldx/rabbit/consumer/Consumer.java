package com.ldx.rabbit.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class Consumer {
    @RabbitListener(queues = "confirm_queue")
    public void getMessage(Message message) throws UnsupportedEncodingException {
        log.info("接收到的消息为:{}",new String(message.getBody(),"utf-8"));
    }
}
