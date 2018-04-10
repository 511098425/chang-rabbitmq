package com.mq.rabbit.processor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 消息接收
 *
 * @author Mr.Chang
 * @create 2018-04-09 16:07
 **/
@Component
@Slf4j
@RabbitListener(queues = "chang-queue")
public class RabbitMqReceiver {

    /**
     * 接收消息
     * @param obj
     */
    @RabbitHandler
    public void receiveMessage(@Payload String obj){
        log.info("收到的消息为：{}", JSON.toJSONString(obj));
    }
}
