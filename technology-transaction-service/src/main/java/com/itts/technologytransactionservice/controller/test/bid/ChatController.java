package com.itts.technologytransactionservice.controller.test.bid;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.itts.common.bean.ChatSessionPool;
import com.itts.common.bean.LoginUser;
import com.itts.common.config.EndpointConfig;
import com.itts.common.constant.MQConstant;
import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.ServiceException;
import com.itts.technologytransactionservice.feign.userservice.UserInfoService;
import com.itts.technologytransactionservice.model.LyLt;
import com.itts.technologytransactionservice.model.LyLtDto;
import com.itts.technologytransactionservice.service.LyLtService;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/1
 */
//聊天室分布式websocket实现
@ServerEndpoint(value = SystemConstant.WEBSOCKET_URL + "/itts/chat/{roomId}/{userId}", configurator = EndpointConfig.class)
@Slf4j
@Component
public class ChatController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private LyLtService lyLtService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * websocket建立链接
     */
    @OnOpen
    public void onOpen(@PathParam("roomId") String roomId,@PathParam("userId") String userId, Session session) {
        Gson gson=new Gson();
        Map objectMap=new HashMap<String,Object>();
        objectMap.put("roomId",roomId);
        objectMap.put("userId",userId);
        objectMap.put("session",session);
        objectMap.put("type","0");
        String openString = gson.toJson(objectMap);
        amqpTemplate.convertAndSend(MQConstant.TECHNOLOGY_TRANSACTION_CHAT_EXCHANGE, MQConstant.TECHNOLOGY_TRANSACTION_CHAT_ROUTING_KEY, openString);
//        Set set = ChatSessionPool.rooms.get(roomId);
//        ChatSessionPool.open(userId, session);
//        // 如果是新的房间，则创建一个映射，如果房间已存在，则把用户放进去
//        if (set == null) {
//            set = new CopyOnWriteArraySet();
//            set.add(session);
//            ChatSessionPool.rooms.put(roomId, set);
//        } else {
//            set.add(session);
//        }
//        System.out.println("open....");
    }

    /**
     * websocket断开链接
     */
    @OnClose
    public void onClose(@PathParam("roomId") String roomId,@PathParam("userId") String userId, Session session) throws IOException {
        Gson gson=new Gson();
        Map objectMap=new HashMap<String,Object>();
        objectMap.put("roomId",roomId);
        objectMap.put("userId",userId);
        objectMap.put("session",session);
        objectMap.put("type","1");
        String openString = gson.toJson(objectMap);
        amqpTemplate.convertAndSend(MQConstant.TECHNOLOGY_TRANSACTION_CHAT_EXCHANGE, MQConstant.TECHNOLOGY_TRANSACTION_CHAT_ROUTING_KEY, openString);
/*        System.out.println("close.....");
        //从用户map中移除相应的用户
        ChatSessionPool.close(session.getId());
        // 如果某个用户离开了，就移除房间map中相应的信息
        if (ChatSessionPool.rooms.containsKey(roomId)) {
            ChatSessionPool.rooms.get(roomId).remove(session);
        }
        session.close();*/
    }

    /**
     * 接收消息并处理消息
     */
    @OnMessage
    public void onMessage(@PathParam("roomId") String roomId,@PathParam("userId") String userId, Session session,String message) throws IOException {
        Gson gson=new Gson();
        Map objectMap=new HashMap<String,Object>();
        objectMap.put("roomId",roomId);
        objectMap.put("userId",userId);
        objectMap.put("session",session);
        objectMap.put("message",message);
        objectMap.put("type","2");
        String openString = gson.toJson(objectMap);
        amqpTemplate.convertAndSend(MQConstant.TECHNOLOGY_TRANSACTION_CHAT_EXCHANGE, MQConstant.TECHNOLOGY_TRANSACTION_CHAT_ROUTING_KEY, openString);
        //redisTemplate.convertAndSend(MQConstant.TECHNOLOGY_TRANSACTION_BID_CHANNEL, message);

        //amqpTemplate.convertAndSend(MQConstant.TECHNOLOGY_TRANSACTION_CHAT_EXCHANGE, MQConstant.TECHNOLOGY_TRANSACTION_CHAT_ROUTING_KEY, message);

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
        ChatSessionPool.sendMessage(message);
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
            exchange = @Exchange(value = MQConstant.TECHNOLOGY_TRANSACTION_CHAT_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = MQConstant.TECHNOLOGY_TRANSACTION_CHAT_ROUTING_KEY
    ))
    public void receive(String msg) throws IOException {
        Gson gson = new Gson();
        Map<String,Object> map = gson.fromJson(msg,Map.class);
       Session session= (Session) map.get("session");
        //判断是连接
        if(map.get("type").toString().equals("0")){
            Set set = ChatSessionPool.rooms.get(map.get("roomId").toString());
            ChatSessionPool.open(map.get("userId").toString(), session);
            // 如果是新的房间，则创建一个映射，如果房间已存在，则把用户放进去
            if (set == null) {
                set = new CopyOnWriteArraySet();
                set.add(session);
                ChatSessionPool.rooms.put(map.get("roomId").toString(), set);
            } else {
                set.add(session);
            }
            System.out.println("open....");
        }else if(map.get("type").toString().equals("1")){
            System.out.println("close.....");
            //从用户map中移除相应的用户
            ChatSessionPool.close(session.getId());
            // 如果某个用户离开了，就移除房间map中相应的信息
            if (ChatSessionPool.rooms.containsKey(map.get("roomId").toString())) {
                ChatSessionPool.rooms.get(map.get("roomId").toString()).remove(session);
            }
            session.close();
        }else if(map.get("type").toString().equals("2")){
            log.info("接受到用户{}的数据:{}", session.getId(), map.get("message").toString());
            System.out.println(map.get("message").toString());
            //检测心跳
            if (map.get("message").toString().equalsIgnoreCase("ping")) {

                ChatSessionPool.heartbeatDetection("1");
                return;
            }
            //聊天记录存入数据库中(可以改造到redis中)
            LyLt lylt = new LyLt();
            lylt.setRoomId(Integer.parseInt(map.get("roomId").toString()));
            lylt.setContent(map.get("message").toString());
            lylt.setSendTime(LocalDateTime.now());
            lylt.setSendId(Integer.parseInt(map.get("userId").toString()));
            lyLtService.save(lylt);
            //获取当前用户信息
            LoginUser loginUser = threadLocal.get();
            LyLtDto lyLtDto=new LyLtDto();
            //返回给前端的完整对象信息lyLtDto
            BeanUtil.copyProperties(lylt,lyLtDto);
            BeanUtil.copyProperties(loginUser,lyLtDto);
            // 拼接一下用户信息
            //String msg = session.getId() + " : " + message;
            Set<Session> sessions = ChatSessionPool.rooms.get(map.get("roomId").toString());
            // 给房间内所有用户推送信息
            for (Session s : sessions) {
                if (!s.getId().equals(session.getId())) {
                    s.getBasicRemote().sendText(JSONUtil.toJsonStr(lyLtDto));
                }
            }
        }
    }

}