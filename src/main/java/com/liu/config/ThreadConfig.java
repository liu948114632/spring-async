package com.liu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.concurrent.*;

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
        ArrayList list = new ArrayList<>();

        list.add("hello");
        list.add(1);
        list.add(new ThreadConfig());
        //这里使用的队列是没有限制的
//        return Executors.newFixedThreadPool(100);

//     这里跟上面的唯一区别是，使用的是100个队列。有更多的任务就会丢弃
        return new ThreadPoolExecutor(100, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100));
    }

    public static HttpServletRequest getCurrentRequest() throws IllegalStateException {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new IllegalStateException("当前线程中不存在 Request 上下文");
        }
        attrs.getResponse();
        return attrs.getRequest();
    }
}
