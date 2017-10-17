package com.liu.listener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/9/18
 */
@WebListener("MyListener")
@Component
public class MyListener implements HttpSessionListener ,ServletContextListener{
    private static int onLineCount =0;
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        this.onLineCount++;
        System.out.println("有人上线，当前在线人数："+onLineCount);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        this.onLineCount--;
        System.out.println("有人下线，当前人数："+onLineCount);
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("**********项目启动**********");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
