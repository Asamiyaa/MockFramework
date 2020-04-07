package com.pattern.h;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * 1.同步代码
 *       @Autowired
         EmailService emailService;
         @Autowired
         ScoreService scoreService;
         @Autowired
         OtherService otherService;

         public void register(String name) {
         System.out.println("用户：" + name + "已注册！");
         emailService.sendEmail(name);
         scoreService.initScore(name);
         otherService.execute(name);
         }

    2.



 */
@SpringBootApplication(scanBasePackages ={"com.pattern.h"})
public class SpringEvent {

    public static void main(String[] args) {
        SpringApplication.run(boot.SpringBootApplication.class, args);

    }
}
//在同一个文件中创建测试类




//事件 、发布者、监听者
@Service
class Publish{

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ApplicationEventMulticaster applicationEventMulticaster; // 以多线程方式发布 异步 否则就是同步 - 底层自己创建线程

    public void register(String name) {
        System.out.println("user reg ：" + name + "go ！");
        applicationEventPublisher.publishEvent(new UserRegisterEvent(name));
    }

    public void register2(String name) {
        System.out.println("user reg :" + name + "go ！");
//        applicationEventMulticaster.
        SimpleApplicationEventMulticaster sm = new SimpleApplicationEventMulticaster();  //配置对应的executor
        sm.setTaskExecutor(new ForkJoinPool());
        sm.multicastEvent(new UserRegisterEvent(name));

        //验证上面的是否是异步的 - 成立
        System.out.println("regest2.doing....");
    }


}

class UserRegisterEvent extends ApplicationEvent {

    /** 自定义需要参数
         private String name;
         //消息参数
         private List<String> contentList;

         public OrderCreateEvent(Object source, String name, List<String> contentList) {
         super(source);
         this.name = name;
         this.contentList = contentList;

     在publisher中进行  OrderCreateEvent orderCreateEvent = new OrderCreateEvent(this, "订单创建", contentList); this就是源，指明当前发布事件者
     */

    public UserRegisterEvent(String name) { //name 即 source  为了简单起见，注册事件只传递了 name（可以复杂的对象，但注意要了解清楚序列化机制）。
        super(name);//注意这里将谁包装成了event
    }

}

@Service
class EmailService {

    @EventListener
    public void listenUserRegisterEvent(UserRegisterEvent userRegisterEvent) throws InterruptedException {//直接通过泛型指定监控类型
        Thread.sleep(5000);
        System.out.println("mail is send" + userRegisterEvent.getSource() + " ing...");
    }
}
