package com.liu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/17
 */
@Configuration
public class ThreadConfig {
    @Bean
    public ExecutorService getThread(){
        return Executors.newFixedThreadPool(100);
    }
}
