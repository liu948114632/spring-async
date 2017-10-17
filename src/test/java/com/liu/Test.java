package com.liu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/17
 */
public class Test {
    public static void main(String a[]) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        //线程池里面只有4个线程。下面循环5次。还用4个线程，另外一个在等待中
        for (int j =0;j<5;j++){
            executorService.execute(() -> {
                for (int i =0;i<10;i++){
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
}
