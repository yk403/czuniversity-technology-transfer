package com.itts.paymentservice;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/10
 */
@Configuration
@Data
public class MqConfig {

    /**
     * 交换机
     */
    @org.springframework.beans.factory.annotation.Value("${mqconfig.order_event_exchange}")
    private String eventExchange;

    /**
     * 第一个队列  延迟队列
     */
    @org.springframework.beans.factory.annotation.Value("${mqconfig.order_release_delay_queue}")
    private String orderReleaseDelayQueue;

    /**
     * 第一个队列的路由key
     * 进入队列的路由key
     */
    @org.springframework.beans.factory.annotation.Value("${mqconfig.order_release_delay_routing_key}")
    private String orderReleaseDelayRoutingKey;


    /**
     * 第二个队列，被监听恢复库存的队列
     */
    @org.springframework.beans.factory.annotation.Value("${mqconfig.order_release_queue}")
    private String orderReleaseQueue;

    /**
     * 第二个队列的路由key
     * <p>
     * 即进入死信队列的路由key
     */
    @org.springframework.beans.factory.annotation.Value("${mqconfig.order_release_routing_key}")
    private String orderReleaseRoutingKey;

    /**
     * 过期时间
     */
    @Value("${mqconfig.ttl}")
    private Integer ttl;

    /**
     * 消息转换器
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 创建交换机
     */
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange(eventExchange, true, false);
    }

    /**
     * 创建延迟队列
     */
    @Bean
    public Queue orderReleaseDelayQueue() {

        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", ttl);
        args.put("x-dead-letter-exchange", eventExchange);
        args.put("x-dead-letter-routing-key", orderReleaseRoutingKey);

        return new Queue(orderReleaseDelayQueue, true, false, false, args);
    }

    /**
     * 创建死信队列
     */
    @Bean
    public Queue orderReleaseQueue() {

        return new Queue(orderReleaseQueue, true, false, false);
    }

    /**
     * 延迟队列绑定关系
     */
    @Bean
    public Binding orderReleaseDelayBiding() {

        return new Binding(orderReleaseDelayQueue, Binding.DestinationType.QUEUE, eventExchange, orderReleaseDelayRoutingKey, null);
    }

    /**
     * 死信队列绑定关系
     */
    @Bean
    public Binding orderReleaseBiding() {

        return new Binding(orderReleaseQueue, Binding.DestinationType.QUEUE, eventExchange, orderReleaseRoutingKey, null);
    }
}