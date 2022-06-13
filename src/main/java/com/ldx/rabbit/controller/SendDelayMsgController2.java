package com.ldx.rabbit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.ldx.rabbit.config.DelayedQueueConfig.DELAYED_EXCHANGE_NAME;
import static com.ldx.rabbit.config.DelayedQueueConfig.DELAYED_ROUTING_KEY;

@RestController
@RequestMapping("plugs")
@Slf4j
public class SendDelayMsgController2 {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("send/{msg}/{time}")
    public void sendMsg(@PathVariable String msg,@PathVariable Integer time){
        log.info("当前时间:{},发送了一条消息:{},延时{}毫秒发送",new Date(),msg,time);
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME,DELAYED_ROUTING_KEY,msg,(message -> {
            // time(ms)
            message.getMessageProperties().setDelay(time);
            return message;
        }));
    }
}
