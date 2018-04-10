package com.mq.rabbit.impl;

import com.alibaba.fastjson.JSON;
import com.mq.constants.RabbitMqConstants;
import com.mq.rabbit.IMessageService;
import com.mq.vo.DeadLetterMessageVO;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息队列发送实现
 * @author Mr.Chang
 * @create 2018-04-10 11:13
 **/
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String queue, String message) {
        rabbitTemplate.convertAndSend(RabbitMqConstants.DEFAULT_EXCHANGE,queue,message);
    }

    @Override
    public void sendDelayMessage(String queue, String message, long times) {
        DeadLetterMessageVO dlm = new DeadLetterMessageVO(queue,message,times);
        MessagePostProcessor mpp = msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(times));
            return msg;
        };
        dlm.setExchange(RabbitMqConstants.DEFAULT_EXCHANGE);
        rabbitTemplate.convertAndSend(RabbitMqConstants.DEFAULT_EXCHANGE,RabbitMqConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME,JSON.toJSONString(dlm),mpp);
    }
}
