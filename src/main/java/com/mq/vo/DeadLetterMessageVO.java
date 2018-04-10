package com.mq.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 死信消息实体类
 *
 * @author Mr.Chang
 * @create 2018-04-10 11:34
 **/
@Data
public class DeadLetterMessageVO implements Serializable {

    private static final long serialVersionUID = 1097289671679332852L;

    /**
     * exchange
     */
    private String exchange;
    /**
     * 对列名称
     */
    private String queue;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 延迟时间
     */
    private Long times;

    public DeadLetterMessageVO() {
        super();
    }
    public DeadLetterMessageVO(String queue, String content, long times) {
        super();
        this.queue = queue;
        this.content = content;
        this.times = times;
    }

    public DeadLetterMessageVO(String exchange, String queue, String content, long times) {
        super();
        this.exchange = exchange;
        this.queue = queue;
        this.content = content;
        this.times = times;
    }



}
