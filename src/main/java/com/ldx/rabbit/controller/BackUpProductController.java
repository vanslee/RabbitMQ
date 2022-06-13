package com.ldx.rabbit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ldx.rabbit.config.BackConfig.CONFIRM_EXCHANGE;

@RestController
@Slf4j
@RequestMapping("backup")
public class BackUpProductController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @GetMapping("1/{message}")
    public void sendMsg(@PathVariable String message){
        CorrelationData correlationData= new CorrelationData("1");
        log.info("经过交换机:{},发出消息:{}",CONFIRM_EXCHANGE,message);
        rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE,"",message,message1 -> message1,correlationData);
        CorrelationData correlationData1= new CorrelationData("2");
        log.info("经过交换机:{},发出消息:{}",CONFIRM_EXCHANGE,message);
        rabbitTemplate.convertAndSend(CONFIRM_EXCHANGE,"",message,message1 -> message1,correlationData1);
    }
}
