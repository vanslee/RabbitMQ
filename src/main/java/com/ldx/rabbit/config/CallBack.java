package com.ldx.rabbit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * 回调接口
 */
@Slf4j
@Component
public class CallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
    // 交换机确认回调方法
    @Autowired
    private RabbitTemplate rabbitTemplate;
    // 将当前的类注入到
    // 注入
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }
    /**
     * 交换机确认回调方法
     * @param correlationData 保存回调消息ID及相关信息
     * @param b 是否应答,交换机收到消息 true
     * @param s 失败原因,发布成功是null
     * 发消息,交换机接收失败了 回调
     *          2.1 correlationData保存回调消息的ID及相关信息
     *          2.2 交换机收到的消息 b = false
     *          2.3 失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "";
            if(b) {
                log.info("交换机已经收到ID为:{}的消息",id);
            }else  {
                log.info("交换机还未收到ID为:{}的消息,原因是:{}",id,s);
            }
    }
    // 可以在当消息传递过程中不可达目的地时将消息返回给生产者
    // 消息只有不可达目的时,才进行回退

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        try {
            log.error("消息:{},被交换机:{}退回,退回原因:{},RoutingKey为:{}",new String (message.getBody(),"utf-8"),exchange,replyText,routingKey);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
