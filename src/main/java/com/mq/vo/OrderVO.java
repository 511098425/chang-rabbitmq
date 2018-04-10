package com.mq.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单vo
 *
 * @author Mr.Chang
 * @create 2018-04-09 16:37
 **/
@Data
public class OrderVO implements Serializable {

    private Integer orderId;
    private String orderNo;
    private Byte payType;
    private Byte payStatus;

}
