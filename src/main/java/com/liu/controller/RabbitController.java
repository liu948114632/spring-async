package com.liu.controller;

import com.liu.service.RabbitProduser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/27
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {
    @Autowired
    RabbitProduser rabbitProduser;

    @RequestMapping("/publish")

    public void publish(String msg){
        rabbitProduser.publish(msg);
    }
}
