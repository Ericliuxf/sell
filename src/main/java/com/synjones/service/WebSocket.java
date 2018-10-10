package com.synjones.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/8/3 15:22
 */
@Component
@ServerEndpoint(value = "/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        System.out.println(session.getId());
        webSocketSet.add(this);
        log.info("【建立连接】,连接数={}",webSocketSet.size());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("【断开连接】，连接总数={}",webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("【webSocket消息】，收到客户端发来的消息={}",message);
    }

    public void sendMessage(String message){
        for(WebSocket webSocket : webSocketSet){
            try{
                webSocket.session.getBasicRemote().sendText(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}

