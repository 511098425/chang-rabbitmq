package com.mq.rabbit.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mq.constants.RabbitMqConstants;
import com.mq.rabbit.IMessageService;
import com.mq.vo.DeadLetterMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 死信队列处理器
 *
 * @author Mr.Chang
 * @create 2018-04-10 11:42
 **/
@Slf4j
@Component
@RabbitListener(queues = RabbitMqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME)
public class TradeProcessor {

    @Autowired
    private IMessageService messageService;

    @RabbitHandler
    public void processDeadMessage(String content){
        log.info("死信队列收到消息：{}",content);
        DeadLetterMessageVO dlm = JSON.parseObject(content,new TypeReference<DeadLetterMessageVO>(){});
        messageService.sendMessage(dlm.getQueue(),dlm.getContent());
    }
}
