package com.ldx.rabbit.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

import static com.ldx.rabbit.config.BackConfig.*;

@Component
@Slf4j
public class BackUpConsumer {
    @RabbitListener(queues = CONFIRM_QUEUE)
    private void getMessage(Message message) throws UnsupportedEncodingException {
        log.info("接收到"+CONFIRM_QUEUE+"队列的消息为:{}",new String(message.getBody(),"utf-8"));
    }
    @RabbitListener(queues = BACKUP_QUEUE)
    private void getMessage1(Message message) throws UnsupportedEncodingException {
        log.info("接收到"+BACKUP_QUEUE+"队列的消息为:{}",new String(message.getBody(),"utf-8"));
    }
    @RabbitListener(queues = WARNING_QUEUE)
    private void getMessage2(Message message) throws UnsupportedEncodingException {
        log.info("接收到"+WARNING_QUEUE+"队列的消息为:{}",new String(message.getBody(),"utf-8"));
    }
}
