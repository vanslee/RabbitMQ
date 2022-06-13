package com.ldx.rabbit.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.ldx.rabbit.config.DelayedQueueConfig.DELAYED_QUEUE_NAME;

//消费基于插件的延迟消息
@Component
@Slf4j
public class ConsumerDelayMsg2 {
    // 监听消息
    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiverDelayMsg(Message message) {
        String s = new String(message.getBody());
        log.info("当前时间:{},收到消息:{}",new Date(),s);
    }

}
