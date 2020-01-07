package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author YangWenjun
 * @date 2020/1/7 14:45
 * @project MockFramework
 * @title: Main
 * @description:
 */
public class Main {

    public static  void main(String[] args) throws InterruptedException {
        new Main().testSemaphore2();
    }

    private void testSemaphore2(){
        //Semaphore semaphore = new Semaphore(1 );//共享锁变独享锁
        Semaphore semaphore = new Semaphore(5 );//控制局部并发
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    /**
                     * 等待5s ,10个Thread 同时输出
                     */
                    semaphore.acquire();
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
                //上面为控制局部并发情况，下面是仍然可以使用大一些的线程处理
                System.out.println("abc" + Thread.currentThread().getName());
            }).start();
        }

    }


}
