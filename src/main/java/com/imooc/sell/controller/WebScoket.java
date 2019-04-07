package com.imooc.sell.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/22 12:41
 * @Version 1.0
 */
/*
@serverEndpoint是将当前类定义成一个websoket的服务器端。
注解的值是用于监听的终端访问的url的地址，客户端可以通过这个url连接到服务器端
 */
@ServerEndpoint("/websocket")
@Component
@Slf4j
public class WebScoket {
    //定义当前的连接诶数
    private static int onlineCount = 0;

    //concurrent包的线程安全的set，用来存放每个客户端对应的websocket对象，若要实现服务端的单一客户端通信的话，可以使用map来进行存储
    //是一个websocket的容器，存储session
    private static CopyOnWriteArraySet<WebScoket> websocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接成功调用的方法
     * @param session
     */

    @OnOpen
    public void onOpen(Session session)
    {
        this.session = session;
        websocketSet.add(this);
        addOnlineCount();
        log.info("【webscoket消息】连接数 = {}",websocketSet.size());

    }

    /**
     * 当接收客户端消息的时候的调用方法
     * @param message
     *
     */
    @OnMessage
    public void onMessage(String message)
    {
        log.error("【webscoket消息】接收到消息message = {}",message);
    }

    public void sendMessage(String message)
    {
        //广播消息
       for (WebScoket webScoket:websocketSet)
       {
           try {
               webScoket.session.getBasicRemote().sendText(message);
           }catch (Exception e)
           {
               log.error("【webScoket消息】发送消息错误");
           }
       }

    }

    @OnClose
    public void onClose(Session session)
    {
        websocketSet.remove(this);
        log.info("【websocket消息】关闭连接");
    }
    /**
     * 当发生错误的时候的处理方法
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session,Throwable error)
    {
        error.printStackTrace();
    }

    public static synchronized int getOnlineCount() {
       return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        onlineCount--;
    }
}
