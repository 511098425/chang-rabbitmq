package com.mq.controller;

import com.mq.config.TheadPoolConfig;
import com.mq.constants.RabbitMqConstants;
import com.mq.rabbit.IMessageService;
import com.mq.rabbit.sender.FanoutMqSender;
import com.mq.rabbit.sender.RabbitAckSender;
import com.mq.rabbit.sender.RabbitMqTopicSender;
import com.mq.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * 消息controller
 *
 * @author Mr.Chang
 * @create 2018-04-09 16:12
 **/
@RequestMapping("api")
@RestController
public class MessageController {

    @Autowired
    private RabbitMqTopicSender rabbitMqSender;

    @Autowired
    private RabbitAckSender callBackSender;

    @Autowired
    private FanoutMqSender fanoutMqSender;

    @Autowired
    private TheadPoolConfig theadPoolConfig;

    @Autowired
    private IMessageService messageService;

    /**
     * 发送普通消息
     * @return
     */
    @GetMapping("message")
    public String sendMessage(){
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(1);
        orderVO.setOrderNo("PA"+String.valueOf(System.currentTimeMillis()));
        orderVO.setPayType((byte)0);
        orderVO.setPayStatus((byte)1);
        rabbitMqSender.send(orderVO);
        return "success";
    }

    /**
     * 发送ACK消息
     * @return
     */
    @GetMapping("message/ack")
    public String sendAckMessage(){
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 1000; i++) {
            theadPoolConfig.myTaskAsyncPool().execute(new Runnable() {
                @Override
                public void run() {
                    if (latch.getCount() == 0){
                        return;
                    }
                    latch.countDown();
                    String message = "发送一个ACK消息"+latch.getCount();
                    callBackSender.send(message);
                }
            });
        }
        return "success";
    }

    /**
     * fanout模式发送消息
     * @return
     */
    @GetMapping("message/fanout")
    public String sendFanoutMessage(){
        String msg = "fanout消息";
        fanoutMqSender.send(msg);
        return "success";
    }

    /**
     * 死信队列
     * @return
     */
    @GetMapping("message/deadLetter")
    public String sendDeadLetterMessage(){
        String msg = "死信队列消息";
        messageService.sendDelayMessage(RabbitMqConstants.NORMAL_QUEUE,msg,6000L);
        return "success";
    }

}
