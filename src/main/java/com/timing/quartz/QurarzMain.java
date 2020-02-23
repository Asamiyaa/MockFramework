package com.timing.quartz;

import com.timing.JobBean;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author YangWenjun
 * @date 2020/2/19 9:36
 * @project MockFramework
 * @title: Main
 * @description:
 *          1.quarz:http://www.quartz-scheduler.org/
 *                  https://www.w3cschool.cn/quartz_doc/
 *                 1.org.quartz.threadPool.threadCount 意味着是否需要线程?
 *                 2.参考官方代码 + https://www.cnblogs.com/jingmoxukong/p/5647869.html
 *               持久化 - 线程 - .....
 *
 *          2.spring-boot -> quarz  https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/html/spring-boot-features.html#boot-features-quartz
 *                  初始化 扩展类 接口
 *                  spring scheduled的动态线程池调度和任务进度的监控:https://blog.csdn.net/yyx1025988443/article/details/78698046
 *
 *          3.抽象代码设计 ..父类...接口...设计模式 ...
 *              https://www.cnblogs.com/youzhibing/p/10024558.html  -- 动态添加任务,前台触发
 *              https://blog.csdn.net/yyx1025988443/article/details/78698046  -- 反射 和service结合
 *
 */
public class QurarzMain {

    public static void main(String[] args) throws SchedulerException {

        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();

        defaultScheduler.start();

        //build 方式
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "triggerGroup1").startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                //CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
                .build();

        defaultScheduler.scheduleJob(jobDetail,trigger);

        //defaultScheduler.shutdown();

    }



}


