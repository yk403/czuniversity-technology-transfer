package com.itts.userservice.config;

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
    @Value("${mqconfig.video_event_exchange}")
    private String eventExchange;

    /**
     * 第一个队列  延迟队列
     */
    @Value("${mqconfig.video_release_delay_queue}")
    private String videoReleaseDelayQueue;

    /**
     * 第一个队列的路由key
     * 进入队列的路由key
     */
    @Value("${mqconfig.video_release_delay_routing_key}")
    private String videoReleaseDelayRoutingKey;


    /**
     * 第二个队列，被监听恢复库存的队列
     */
    @Value("${mqconfig.video_release_queue}")
    private String videoReleaseQueue;

    /**
     * 第二个队列的路由key
     * <p>
     * 即进入死信队列的路由key
     */
    @Value("${mqconfig.video_release_routing_key}")
    private String videoReleaseRoutingKey;

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
    public Exchange videoEventExchange() {
        return new TopicExchange(eventExchange, true, false);
    }

    /**
     * 创建延迟队列
     */
    @Bean
    public Queue videoReleaseDelayQueue() {

        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", ttl);
        args.put("x-dead-letter-exchange", eventExchange);
        args.put("x-dead-letter-routing-key", videoReleaseRoutingKey);

        return new Queue(videoReleaseDelayQueue, true, false, false, args);
    }

    /**
     * 创建死信队列
     */
    @Bean
    public Queue videoReleaseQueue() {

        return new Queue(videoReleaseQueue, true, false, false);
    }

    /**
     * 延迟队列绑定关系
     */
    @Bean
    public Binding videoReleaseDelayBiding() {

        return new Binding(videoReleaseDelayQueue, Binding.DestinationType.QUEUE, eventExchange, videoReleaseDelayRoutingKey, null);
    }

    /**
     * 死信队列绑定关系
     */
    @Bean
    public Binding videoReleaseBiding() {

        return new Binding(videoReleaseQueue, Binding.DestinationType.QUEUE, eventExchange, videoReleaseRoutingKey, null);
    }
}