package com.mq.rabbit.processor;

import com.alibaba.fastjson.JSON;
import com.mq.constants.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 普通消息队列处理器
 *
 * @author Mr.Chang
 * @create 2018-04-10 14:19
 **/
@Slf4j
@Component
@RabbitListener(queues = RabbitMqConstants.NORMAL_QUEUE)
public class NormalProcessor {

    /**
     * 接收消息
     * @param obj
     */
    @RabbitHandler
    public void receiveMessage(String obj){
        log.info("普通队列收到的消息为：{}", obj);
    }
}
