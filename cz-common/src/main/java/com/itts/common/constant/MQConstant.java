package com.itts.common.constant;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/6
 */
public class MQConstant {

    /**
     * 技术交易叫价消息队列
     */
    public static final String TECHNOLOGY_TRANSACTION_BID_QUEUE = "itts_technology_transaction_bid_queue";

    /**
     * 技术交易叫价消息交换机
     */
    public static final String TECHNOLOGY_TRANSACTION_BID_EXCHANGE = "itts_technology_transaction_bid_exchange";

    public static final String TECHNOLOGY_TRANSACTION_BID_ROUTING_KEY = "itts.technology.transaction.bid";
    /**
     * 技术交易聊天消息交换机
     */
    public static final String TECHNOLOGY_TRANSACTION_CHAT_EXCHANGE = "itts_technology_transaction_chat_exchange";

    public static final String TECHNOLOGY_TRANSACTION_CHAT_ROUTING_KEY = "itts.technology.transaction.chat";

    /**
     * 技术交易叫价消息信道
     */
    public static final String TECHNOLOGY_TRANSACTION_BID_CHANNEL = "itts_technology_transaction_bid_channel";
}