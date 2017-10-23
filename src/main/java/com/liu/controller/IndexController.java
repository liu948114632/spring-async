package com.liu.controller;

import com.liu.annotation.MyAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/9/18
 */
@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    public Task task;

    @Autowired
    public ExecutorService executorService;

    @MyAnnotation(value = "这个是index方法")
    @RequestMapping("/")
    public String index(HttpSession session){
        String  s=session.getId();
        return "hello";
    }

    @MyAnnotation("这个方法是测试线程池的")
    @RequestMapping("/fun")
    public void fun(){
        for(int j=0;j<10;j++){
            //用多个线程去做execute里面的工作。
            executorService.execute(()->{
                for(int i=0;i<10;i++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+":"+i);
                }
            });
        }

    }


    @MyAnnotation("这个方法是测试同步的")
    @RequestMapping("/hello")
    public void sync() throws Exception{
        long start = System.currentTimeMillis();
        Future<String> task1 = task.doTaskOne();
        Future<String> task2 = task.doTaskTwo();
        while(true) {
            if(task1.isDone() && task2.isDone() ) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }
        long end = System.currentTimeMillis();
        System.out.println("完成所用，耗时：" + (end - start) + "毫秒");
    }
}
