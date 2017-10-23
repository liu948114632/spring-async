package com.liu.listener;

import com.liu.config.ThreadConfig;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/9/18
 */
@WebListener("MyListener")
@Component
public class MyListener implements HttpSessionListener ,ServletContextListener{
    //保证可见性
    private volatile  int onLineCount =0;

    /**
           * private List<HttpSession> list = new ArrayList<HttpSession>();
           * 这样写涉及到线程安全问题,SessionScanerListener对象在内存中只有一个
           * sessionCreated可能会被多个人同时调用，
           * 当有多个人并发访问站点时，服务器同时为这些并发访问的人创建session
           * 那么sessionCreated方法在某一时刻内会被几个线程同时调用，几个线程并发调用sessionCreated方法
           * sessionCreated方法的内部处理是往一个集合中添加创建好的session，那么在加session的时候就会
           * 涉及到几个Session同时抢夺集合中一个位置的情况，所以往集合中添加session时，一定要保证集合是线程安全的才行
           * 如何把一个集合做成线程安全的集合呢？
           * 可以使用使用 Collections.synchronizedList(List<T> list)方法将不是线程安全的list集合包装线程安全的list集合
           */
    /**
     * 线程安全的锁与同步一起使用原因。
     * synchronized (this)同步方法使用this对象，只是锁sessionCreated这个方法。也就是只有一个线程在操作该方法。
     * 但是有可能其他的线程在操作其他的方法。于是在使用onLineCount++的时候，方法拿到onLineCount的值，
     * 然后另外一个线程做了onLineCount--的操作。此时还是原来的旧数据，执行onLineCount++就出现问题。
     * 如果是线程安全的，那么就保证了可见性，当值发生变化时，会通知其他线程中的缓存数据失效。保证每次使用的是最新的值。
     * 可以这样理解：volatile关键字和线程安全的list。都是保证可见性，即数据改变后，线程中的缓存会更新主存中最新的数据。
     */
      //使用 Collections.synchronizedList(List<T> list)方法将ArrayList包装成一个线程安全的集合
    private  List list = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        //同步，保证原子性
        synchronized (this){
            this.onLineCount++;
            System.out.println("有人上线，当前在线人数："+onLineCount);
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        synchronized (this){
            this.onLineCount--;
            System.out.println("有人下线，当前人数："+onLineCount);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("**********项目启动**********");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
