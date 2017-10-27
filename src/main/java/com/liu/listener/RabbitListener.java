package com.liu.listener;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;


/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/27
 */
@Component
public class RabbitListener {

    @Component
    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues ={"hello"})
    class RabbitListener1{
        Logger logger = Logger.getLogger(RabbitListener1.class);
        @RabbitHandler
        public void subscribe(String msg){
            logger.info("收到消息:"+msg);

        }
    }

    @Component
    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues ={"chat"})
    class RabbitListener2{
        Logger logger = Logger.getLogger(RabbitListener2.class);
        @RabbitHandler
        public void subscribe(String msg){
            logger.info("收到消息:"+msg);
        }
    }

}
