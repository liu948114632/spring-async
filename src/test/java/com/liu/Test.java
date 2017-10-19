package com.liu;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/17
 */
public class Test {
    public static void main(String a[]) {

        Runnable run =new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Singleton.getInstance();
            }
        };
        Thread[] threads =new Thread[500];
        for (int i =0;i<threads.length;i++){
            threads[i] = new Thread(run);
        }
        for (int i =0;i<threads.length;i++){
            threads[i].start();
        }

        if(Thread.activeCount()>1){
            Thread.yield();
        }
        System.out.println("结束");
    }
}
