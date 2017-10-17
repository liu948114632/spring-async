package com.liu.controller;

import com.liu.config.WebSocketHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/9/25
 */
@Controller
public class WebSocketController {
    @Autowired
    private WebSocketHandle webSocketHandle;

    @MessageMapping("/getMessage")
    @SendTo("/topic/hello")
    public String message(String msg){
        return msg;
    }
    @RequestMapping("/")
    public String index(){
        return "index.html";
    }

    @RequestMapping(value="/login")
    @ResponseBody
    public String login(HttpSession session ,@RequestParam("name") String name){
        if("".equals(name)) {
            return "error";
        }
        session.setAttribute("user", name);
        return "ok";
    }


    @RequestMapping("/sendMessage")
    @ResponseBody
//    这种方式是，记录websocketsession.然后主动给里面的用户发送消息。自己管理用户连接。这样服务端，接收或者发送消息都便于控制。
    public void sendMessage(@RequestParam("message") String message, HttpSession session ){
        if(message.startsWith("@")){
            String s[] = message.split(":");
            webSocketHandle.sendToOne(s[0].replace("@",""),session.getAttribute("user")+":"+s[1],"guide");
        }else {
            webSocketHandle.sendAll(session.getAttribute("user")+":"+message,"guide");
        }
    }
    @RequestMapping("/sendMessage_hello")
    @ResponseBody
//    这种方式是，记录websocketsession.然后主动给里面的用户发送消息。自己管理用户连接。这样服务端，接收或者发送消息都便于控制。
    public void sendMessage_hello(@RequestParam("message") String message, HttpSession session ){
        if(message.startsWith("@")){
            String s[] = message.split(":");
            webSocketHandle.sendToOne(s[0].replace("@",""),session.getAttribute("user")+":"+s[1],"hello");
        }else {
            webSocketHandle.sendAll(session.getAttribute("user")+":"+message,"hello");
        }
    }
}
