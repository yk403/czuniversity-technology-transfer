package com.itts.common.bean;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/2
 */
public class SessionPool {

    /**
     * 用户链接池
     */
    public static Map<String, Session> sessions = new ConcurrentHashMap<>();
    /**
     * 房间链接池
     */
    public static Map<String, Set> rooms = new ConcurrentHashMap(8);
    /**
     * 建立链接
     */
    public static void open(String sessionId, Session session) {

        sessions.put(sessionId, session);
    }

    /**
     * 关闭链接
     */
    public static void close(String sessionId) throws IOException {

       /* Session session = sessions.get(sessionId);
        if (session != null) {
            sessions.get(sessionId).close();
        }*/

        for(String userId : SessionPool.sessions.keySet()){

            Session session = SessionPool.sessions.get(userId);

            if(session.getId().equals(sessionId)){

                sessions.remove(userId);
                break;
            }
        }
    }

    /**
     * 发送消息 - 点对点
     */
    public static void sendMessage(String sessionId, String message) {
        sessions.get(sessionId).getAsyncRemote().sendText(message);
    }

    /**
     * 发送消息 - 广播
     */
    public static void sendMessage(String message) {

        for (String sessionId : sessions.keySet()) {
            sessions.get(sessionId).getAsyncRemote().sendText(message);
        }
    }

    /**
    *心跳检测
    */
    public static void heartbeatDetection(String sessionId) throws IOException {

        sessions.get(sessionId).getBasicRemote().sendText("pong");
    }
}