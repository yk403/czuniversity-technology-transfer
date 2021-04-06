package com.itts.common.websocket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description：websocket客户端请求服务端数据
 * @Author：lym
 * @Date: 2021/4/1
 */
@Data
public class InMessage {

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 拍品ID
     */
    private Long productId;

    /**
     * 用户token
     */
    private String token;

    /**
     * 出价价格
     */
    private BigDecimal bidPrice;

    /**
     * 出价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bidDate;
}