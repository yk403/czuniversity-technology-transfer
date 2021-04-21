package com.itts.technologytransactionservice.controller.test.bid;

import com.itts.common.bean.SessionPool;
import com.itts.common.config.EndpointConfig;
import com.itts.common.constant.MQConstant;
import com.itts.common.constant.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/1
 */
@ServerEndpoint(value = SystemConstant.WEBSOCKET_URL + "/itts/bid/{userId}", configurator = EndpointConfig.class)
@Slf4j
@Component
public class BidController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * websocket建立链接
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {

        SessionPool.open(userId, session);

        System.out.println("open....");
    }

    /**
     * websocket断开链接
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        System.out.println("close.....");

        SessionPool.close(session.getId());
        session.close();
    }

    /**
     * 接收消息并处理消息
     */
    @OnMessage
    public void onMessage(String message) throws IOException {

        System.out.println(message);

        //检测心跳
        if (message.equalsIgnoreCase("ping")) {

            SessionPool.heartbeatDetection("1");
            return;
        }

        redisTemplate.convertAndSend(MQConstant.TECHNOLOGY_TRANSACTION_BID_CHANNEL, message);
    }

    /**
     * 错误
     */
    @OnError
    public void onError(Throwable throwable) {

        log.error("WebSocket发生错误：" + throwable.getMessage());
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        SessionPool.sendMessage(message);
    }

    /**
     * 监听MQ消息，回复前端
     */
    //@RabbitListener(queues = "itts_technology_transaction_bid_queue")
    public void receiveMessage(String msg) throws IOException {

        System.out.println("mq中的消息：" + msg);

        sendMessage(msg);
    }
}