package com.liu.config;

import com.liu.service.StringRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/18
 */
@Configuration
public class RedisListener {
    @Autowired
    private StringRedisService stringRedisService;

    //开启redis监听，不同频道线程不同
//    @PostConstruct
    public void subscribe(){
        stringRedisService.subscribe("hello");
        stringRedisService.subscribe("hi");
    }
}
