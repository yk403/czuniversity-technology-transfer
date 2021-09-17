package com.itts.technologytransactionservice.config;

import com.rabbitmq.client.AMQP;
import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Data
public class MqConfig {

    public static String eventExchange = "eventExchange";

    public static String orderQueue = "orderQueue";

    public static String routingKey = "routingKey";

    /**
     * 创建交换机
     */
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange(eventExchange, true, false);
    }
    /**
     * 创建队列
     */
    @Bean
    public Queue orderQueue() {

        return new Queue(orderQueue, true, false, false);
    }
    @Bean
    public Binding orderBiding() {

        return new Binding(orderQueue,Binding.DestinationType.QUEUE,eventExchange,routingKey,null);
    }
}
