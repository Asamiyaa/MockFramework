package com.pattern.h;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 * 1.异步：多线程 - 回调模式 -  监听器模式 - spring事件机制 - mq(多线程处理安全、一致性) (一定是关联性不强，可异步的，非实时的，非必要条件  比如：发积分.发确认邮件 通知。)
 *      1.多线程
 *          1.@EnableAsync @order 对多个异步的进行（SmartApplicationListener）
 *            在方法上,增加了@Async注解,表示这是一个异步方法,如果注解使用到类上,表示类下所有方法,都是异步的.
 *            在类上,我们增加了注解@EnableAsync 表示开启异步@Async注解,不使用@EnableAsync注解,@Async是不会生效的.
 *          2.***多线程代码编写注意****
 *
 *
 *
 *
 *
 *      2.回调
 *          1.回调函数（callback）是什么？https://www.zhihu.com/question/19801131 *** 回调就成了一个高层调用底层，底层再回过头来调用高层的过程 hook 钩子 使用框架实现某个接口...
 *              runable就是这样。runable底层是对线程等封装，实际执行的就是我们定义的run方法实现
 *          2.我们同样可以自定义一个函数。a.b(bbb()) b方法通过传入的方法进行自己逻辑处理  类似于模板方法，模板方法是对类的扩展传入类，而这里是传入方法
 *
 *       3.监听器
 *          1：观察者模式分离了观察者和被观察者自身的责任，让类各自维护自己的功能，提高了系统的可重用性；
            2：观察看上去是一个主动的行为，但是其实观察者不是主动调用自己的业务代码的，相反，是被观察者调用的。
 *
 *       4.事件(获取spring容器信息)  SpringEvent.java
 *          1.发布订阅模式需要有一个调度中心，而观察者模式不需要
 *          2.java 和 spring 中都拥有 Event 的抽象，分别代表了语言级别和三方框架级别对事件的支持。
              EventSourcing 这个概念就要关联到领域驱动设计，DDD 对事件驱动也是非常地青睐，领域对象的状态完全是由事件驱动来控制，由其衍生出了 CQRS 架构，
                    具体实现框架有 AxonFramework。
              Nginx 可以作为高性能的应用服务器（e.g. openResty），以及 Nodejs 事件驱动的特性，这些也是都是事件驱动的体现。
            3.spring中使用到的时间  spring初始化Cmain.java
                ContextRefreshedEvent,ContextStartedEvent,ContextStoppedEvent,ContextClosedEvent,RequestHandledEvent
 *
 *        5.mq 产品
 *           1. 将事件模型从抽象到具体，并处理了系统间异构、通信、高可用等知识...
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

public class Hmain {

    public static void main(String[] args) {
        System.out.println("main start");
// 1.同步
//        doA();
//        doB();
//2.多线程 //详见：ThreadMain.class
        ExecutorService executor = Executors.newFixedThreadPool(2); // 禁止使用Executor创建线程池




        executor.submit(new Runnable() {
            @Override
            public void run() {
                doA();
            }
        });
        executor.submit(new Runnable() {
            @Override
            public void run() {
                doB();
            }
        });

//2.多线程 - 返回值 //TODO:2.线程终止
        ExecutorService executor1 = Executors.newFixedThreadPool(2);
        Future<Object> future = executor1.submit(new Callable<Object>() {//TODO:3.线程安全性
            @Override
            public Object call() throws Exception {
                return doC();
            }
        });

        //主进程在子进程做时做自己的 - future.get() 阻塞这里得到数据在进行
        System.out.println("main do...");
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//3.监听器 解耦 而不是由主线程进行向外扩展   1.创建类listner1 listner2 并调用被监听者方法进行注册  2.

        regesiterFlush();
        //状态改变 或者 事件发生
        if(1==1){
//            1.fore  遍历处理每个监听者固定方法调用  -- 还是耦合的
//            2.创建线程处理每个监听者代码  如何将改变的东西传递给监听者呢？所以每个监听接口必须包含被监听对象的引用进行传递。这个关系应该是组合关系即类确定时就以成员
//                        传入，而不是参数   -- https://www.runoob.com/design-pattern/observer-pattern.html

        }

//4.事件  spring对上述模式的包装以及整个容器关联
//        SpringEvent.java  ApplicationEventMulticaster就是异步发布的过程


        System.out.println("main end");
    }

    private static void regesiterFlush() {
        List listeners  = new ArrayList();
        listeners.add("监听者1");
        listeners.add("监听者2");
    }


    private static Object doC() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "doC...";
    }

    private static void doB() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("doB....");
    }

    private static void doA() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("doA....");
    }


}
