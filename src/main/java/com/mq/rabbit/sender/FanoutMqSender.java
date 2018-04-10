package com.mq.rabbit.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * fanout模式发送消息
 *
 * @author Mr.Chang
 * @create 2018-04-10 10:23
 **/
@Slf4j
@Component
public class FanoutMqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * fanout(即发布订阅模式)方式发送消息
     * @param obj
     */
    public void send(Object obj){
        log.info("发送fanout消息：{}",obj);
        rabbitTemplate.convertAndSend("amq.fanout","amq.fanout.message",obj);
    }
}
