package com.liu.config;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/9/25
 */
@Service
public class WebSocketHandle implements WebSocketHandler {

    private static final List<WebSocketSession> users1 =new ArrayList<>();
    private static final List<WebSocketSession> user_hello =new ArrayList<>();

    //建立连接
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
//        表示路径中有hello，即建立的是hello的websocket。放入到user_hello中
        if(webSocketSession.getUri().getPath().indexOf("hello")!= -1){
            user_hello.add(webSocketSession);
        }else {
            users1.add(webSocketSession);
        }
        Object username = webSocketSession.getAttributes().get("user");
        if(username!=null){
            webSocketSession.sendMessage(new TextMessage("我们已经成功建立soket通信了"));
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        if(webSocketSession.getUri().getPath().indexOf("hello")!= -1){
            user_hello.remove(webSocketSession);
        }else {
            users1.remove(webSocketSession);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        if(webSocketSession.getUri().getPath().indexOf("hello")!= -1){
            user_hello.remove(webSocketSession);
        }else {
            users1.remove(webSocketSession);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     *
     * 群发
     *
     * @param message
     */
    public void sendAll(String message, String flag){
        if(flag.equals("hello")){
            for (WebSocketSession user: user_hello){
                try {
                    user.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            for (WebSocketSession user: users1){
                try {
                    user.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 私聊
     *
     * @param userName
     * @param message
     */
    public void sendToOne(String userName, String message, String flag){
        if(flag.equals("hello")) {
            for (WebSocketSession user : user_hello) {
                send(user,userName,message);
            }
        }else {
            for (WebSocketSession user : users1) {
                send(user,userName,message);
            }
        }
    }
    private void send(WebSocketSession user ,String userName ,String message){
        if (user.getAttributes().get("user").equals(userName)) {
            try {
                user.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
