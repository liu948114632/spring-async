package com.liu.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/27
 */
@Service
public class RabbitProduser {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void publish(String msg){
        rabbitTemplate.convertAndSend("topic","topic",msg);
    }
}
