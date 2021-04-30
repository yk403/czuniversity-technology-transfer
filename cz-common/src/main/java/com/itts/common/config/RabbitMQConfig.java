package com.itts.common.config;

import com.itts.common.constant.MQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/6
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 交换机
     *
     * @return
     */
    /*@Bean(name = "itts_technology_transaction_bid_exchange")
    public Exchange ittsTechnologyTransactionBidExchange() {
        return ExchangeBuilder.topicExchange(MQConstant.TECHNOLOGY_TRANSACTION_BID_EXCHANGE).durable(true).build();
    }*/

    /**
     * 队列
     *
     * @return
     */
   /* @Bean(name = "itts_technology_transaction_bid_queue")
    public Queue ittsTechnologyTransactionBidQueue() {
        return QueueBuilder.durable(MQConstant.TECHNOLOGY_TRANSACTION_BID_QUEUE).build();
    }*/

    /**
     * 交换机和队列绑定关系
     */
    /*@Bean(name = "itts_technology_transaction_binding")
    public Binding ittsTechnologyTransactionBinding() {

        return BindingBuilder.bind(ittsTechnologyTransactionBidQueue()).to(ittsTechnologyTransactionBidExchange()).with("itts.technology.transaction.#").noargs();
    }*/
}