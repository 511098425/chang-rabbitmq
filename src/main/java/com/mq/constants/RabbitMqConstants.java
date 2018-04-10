package com.mq.constants;

/**
 * 消息常量类
 *
 * @author Mr.Chang
 * @create 2018-04-10 11:16
 **/
public class RabbitMqConstants {

    /**
     * exchange名称
     */
    public static final String DEFAULT_EXCHANGE = "chang-default";
    /**
     * 死信队列
     */
    public static final String DEFAULT_DEAD_LETTER_QUEUE_NAME = "chang.dead.letter.queue";
    /**
     * 死信转发队列
     */
    public static final String DEFAULT_REPEAT_TRADE_QUEUE_NAME = "chang.repeat.trade.queue";
    /**
     * 普通消息队列
     */
    public static final String NORMAL_QUEUE = "chang.normal.queue";

}
