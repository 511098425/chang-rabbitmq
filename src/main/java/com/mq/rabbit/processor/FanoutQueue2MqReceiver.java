package com.mq.rabbit.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Chang
 * @create 2018-04-10 10:33
 **/
@Slf4j
@Component
@RabbitListener(queues = "chang-queue-2")
public class FanoutQueue2MqReceiver {

    @RabbitHandler
    public void receive(String msg){
        log.info("chang-queue-2接收消息：{}",msg);
    }
}
