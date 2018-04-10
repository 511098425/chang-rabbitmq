package com.mq.rabbit.sender;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息发送类
 * @author Mr.Chang
 * @create 2018-04-09 15:34
 **/
@Slf4j
@Component
public class RabbitMqTopicSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 消息发送
     * @param obj
     */
    public void send(Object obj){
        log.info("开始发送消息········");
        synchronized (obj){
            rabbitTemplate.convertAndSend("amq.topic","chang-queue",obj);
        }
    }
}
