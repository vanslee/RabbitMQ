package com.ldx.rabbit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 发送延迟消息
 *
 */
@RestController
@RequestMapping("/ttl")
@Slf4j
public class SendMsgController1 {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //开始发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){
        log.info("当前时间:{},发送一条信息给两个TTL队列:{}",new Date().toString(),message);
        rabbitTemplate.convertAndSend("X","XA","消息来自TTL为10S的队列: "+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自TTL为40S的队列: "+message);
    }
    // 开始发消息
    @GetMapping("send/{msg}/{time}")
        public void sendMsg1(@PathVariable String msg ,@PathVariable String time){
        log.info("当前时间:{},发送一条时长为{}毫秒的信息给队列QC:{}",new Date(),time,msg);
        rabbitTemplate.convertAndSend("X","XC",msg,(message1 -> {
            message1.getMessageProperties().setExpiration(time);
            return message1;
        }));
        }
}
