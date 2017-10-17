package com.liu.config;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/9/25
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * websocket配置类
 */
@Configuration
@EnableWebSocket
public class NewConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{

    @Autowired
    private WebSocketHandle socketHandler;

    @Override
//    注册处理类，是之前重写的WebSocketHandle。添加拦截器。在握手之前进行操作，将session中的数据放入到websocketsession中
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //注册处理拦截器,拦截url为socketServer的请求
        registry.addHandler(socketHandler, "/gs-guide-webSocket","hello").addInterceptors(new WebSocketInterceptor());

        //注册SockJs的处理拦截器,拦截url为/sockjs/socketServer的请求
        registry.addHandler(socketHandler, "/gs-guide-webSocket","hello").addInterceptors(new WebSocketInterceptor()).withSockJS();
    }


     class WebSocketInterceptor implements HandshakeInterceptor {
        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler handler, Exception exception) {

        }

        /**
         * @desp 将session中的对象放入webSocketSession中
         */
        @Override
        public boolean beforeHandshake(ServerHttpRequest request,
                                       ServerHttpResponse response, WebSocketHandler handler,
                                       Map<String, Object> map) throws Exception {
            if(request instanceof ServerHttpRequest){

                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                HttpSession session = servletRequest.getServletRequest().getSession();
                if(session!=null){
                    //区分socket连接以定向发送消息
                    map.put("user", session.getAttribute("user"));
                }
//                websocketsession中是没有之前在session放入的user对象。必须在拦截器中将session中的拦截器放入到websocketsession中。
            }
            return true;
        }

    }

}