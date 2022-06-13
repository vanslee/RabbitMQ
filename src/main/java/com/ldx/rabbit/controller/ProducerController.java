package com.ldx.rabbit.controller;

import com.ldx.rabbit.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.ldx.rabbit.config.ConfirmConfig.CONFIRM_ROUTING_KEY;

/**
 * 开始发消息测试确认
 */
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    // 发消息
    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, CONFIRM_ROUTING_KEY,message,correlationData);
        log.info(CONFIRM_ROUTING_KEY+"发送消息内容为: {}",message+1);
        System.out.println("------------------------------------------------------");
        CorrelationData correlationData2 = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, CONFIRM_ROUTING_KEY+"abc",message,correlationData2);
        log.info(CONFIRM_ROUTING_KEY+"abc"+"发送消息内容为: {}",message+2);
    }
}
