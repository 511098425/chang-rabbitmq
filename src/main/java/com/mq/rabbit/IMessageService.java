package com.mq.rabbit;

/**
 * 消息队列接口
 *
 * @author Mr.Chang
 * @create 2018-04-10 11:12
 **/
public interface IMessageService {

    /**
     * 发送消息到队列
     * @param queue
     * @param message
     * @author Mr.Chang
     */
    void sendMessage(String queue,String message);
    /**
     * 延迟发送消息
     * @param queue
     * @param message
     * @param times
     * @author Mr.Chang
     */
    void sendDelayMessage(String queue,String message,long times);
}
