package com.itts.common.websocket.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description：服务端将数据推动到客户端
 * @Author：lym
 * @Date: 2021/4/1
 */
@Data
public class OutMessage {

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 拍品ID
     */
    private Long productId;

    /**
     * 拍品名称
     */
    private String productName;

    /**
     * 竞拍者ID
     */
    private Long userId;

    /**
     * 竞拍者用户名
     */
    private String userName;

    /**
     * 出价价格
     */
    private BigDecimal bidPrice;

    /**
     * 出价时间
     */
    private Date bidDate;
}