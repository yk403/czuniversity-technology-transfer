package com.itts.technologytransactionservice.controller.test.bid;

import com.itts.common.bean.SessionPool;
import com.itts.common.config.EndpointConfig;
import com.itts.common.constant.MQConstant;
import com.itts.common.constant.SystemConstant;
import com.itts.technologytransactionservice.feign.userservice.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/1
 */
@ServerEndpoint(value = SystemConstant.WEBSOCKET_URL + "/itts/chat/{roomId}/{userId}", configurator = EndpointConfig.class)
@Slf4j
@Component
public class ChatController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * websocket建立链接
     */
    @OnOpen
    public void onOpen(@PathParam("roomId") String roomId,@PathParam("userId") String userId, Session session) {
        Set set = SessionPool.rooms.get(roomId);
        SessionPool.open(userId, session);
        // 如果是新的房间，则创建一个映射，如果房间已存在，则把用户放进去
        if (set == null) {
            set = new CopyOnWriteArraySet();
            set.add(session);
            SessionPool.rooms.put(roomId, set);
        } else {
            set.add(session);
        }
        System.out.println("open....");
    }

    /**
     * websocket断开链接
     */
    @OnClose
    public void onClose(@PathParam("roomId") String roomId,@PathParam("userId") String userId, Session session) throws IOException {

        System.out.println("close.....");
        //从用户map中移除相应的用户
        SessionPool.close(session.getId());
        // 如果某个用户离开了，就移除房间map中相应的信息
        if (SessionPool.rooms.containsKey(roomId)) {
            SessionPool.rooms.get(roomId).remove(session);
        }
        session.close();
    }

    /**
     * 接收消息并处理消息
     */
    @OnMessage
    public void onMessage(@PathParam("roomId") String roomId, Session session,String message) throws IOException {
        log.info("接受到用户{}的数据:{}", session.getId(), message);
        System.out.println(message);

        //检测心跳
        if (message.equalsIgnoreCase("ping")) {

            SessionPool.heartbeatDetection("1");
            return;
        }

        //redisTemplate.convertAndSend(MQConstant.TECHNOLOGY_TRANSACTION_BID_CHANNEL, message);

        amqpTemplate.convertAndSend(MQConstant.TECHNOLOGY_TRANSACTION_BID_EXCHANGE, MQConstant.TECHNOLOGY_TRANSACTION_BID_ROUTING_KEY, message);
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
   /* public void receiveMessage(String msg) throws IOException {

        System.out.println("mq中的消息：" + msg);

        sendMessage(msg);
    }*/

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = MQConstant.TECHNOLOGY_TRANSACTION_BID_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = MQConstant.TECHNOLOGY_TRANSACTION_BID_ROUTING_KEY
    ))
    public void receive(String msg) throws IOException {
        sendMessage(msg);
    }
}