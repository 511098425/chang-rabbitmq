package com.mq.rabbit.sender;

import com.alibaba.fastjson.JSON;
import com.mq.config.TheadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 消息回调
 *
 * @author Mr.Chang
 * @create 2018-04-09 16:49
 **/
@Slf4j
@Component
public class RabbitAckSender implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送ACK消息
     * @param obj
     */
    public void send(Object obj){
        log.info("开始发送ACK消息:{}",obj);
        rabbitTemplate.setConfirmCallback(this);
        CorrelationData cld = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("amq.topic","chang-queue",obj,cld);
    }

    /**
     * 接收确认消息
     * @param cld
     * @param b
     * @param s
     */
    @Override
    public void confirm(CorrelationData cld, boolean b, String s) {
        log.info("接收确认信息：{}",cld.getId());
    }
}
