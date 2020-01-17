package com;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author YangWenjun
 * @date 2020/1/7 14:45
 * @project MockFramework
 * @title: Main
 * @description:
 */
public class Main {

    public static  void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        //new Main().testSemaphore2();
        new Main().testSemaphore21();
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

    private void testSemaphore21(){



    }



}

