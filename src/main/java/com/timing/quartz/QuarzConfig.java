package com.timing.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 1.spring dependency
 *      Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'quartzManage': Unsatisfied dependency expressed through field 'scheduler'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'scheduler' defined in class path resource [com/timing/quartz/QuarzConfig.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.quartz.Scheduler]: Factory method 'scheduler' threw exception; nested exception is java.lang.IllegalStateException: No Scheduler set
 *2.TODO:Caused by: org.quartz.ObjectAlreadyExistsException: Unable to store Job : 'DEFAULT.JOB_null', because one already exists with this identification.
 * 3.配置文件属性注掉了 先使用默认
 *
 *
 *
 *
 @Configuration
 public class QuarzConfig {

 @Bean(name = "scheduler")
 //    public Scheduler scheduler(/*SpringBeanJobFactory springBeanJobFactory*/
//    public Scheduler scheduler(QuartzAutoConfiguration quartzAutoConfiguration) throws Exception { //到底哪些需要boot自动，哪些是自己扩展的。修改的
/*public Scheduler scheduler(*//*SchedulerFactoryBean schedulerFactoryBean*//*) throws Exception { //
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        *//*schedulerFactoryBean.setJobFactory(quartzAutoConfiguration);
        schedulerFactoryBean.afterPropertiesSet();*//*
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
        return scheduler;
        }
        }*/

/**
 * 有很多入口，如何选择组合 - 只要大方向没错，不要看到一点小空难就退缩。网上找别人的另一种方式去做，应该解决问题。
 */
/**
 * 要配置哪些东西？和application.proprety配置关系是啥？　察看redisconfig 查看这里的配置后于在applicaiotn中配置的。不冲突，这里是代码级别补充，定义
 * 又要补充哪些东西呢？
 *
 *
 * 日志打印：默认值
 *   Scheduler class: 'org.quartz.core.QuartzScheduler' - running locally.
    NOT STARTED.
    Currently in standby mode.
    Number of jobs executed: 0
    Using thread pool 'org.quartz.simpl.SimpleThreadPool' - with 10 threads.
    Using job-store 'org.quartz.simpl.RAMJobStore' - which does not support persistence. and is not clustered.
 */
/*    @Configuration
    public class QuarzConfig implements SchedulerFactoryBeanCustomizer {

        @Override
        public void customize(SchedulerFactoryBean schedulerFactoryBean) {
            schedulerFactoryBean.setStartupDelay(2);
            schedulerFactoryBean.setAutoStartup(true);
            schedulerFactoryBean.setOverwriteExistingJobs(true);
        }

    }*/


/**
 **
   代码中没有明确使用到spring和quartz结合的地方，那么他们是如何结合的？
   哪里启动的？
   扫描到的配置文件信息是如何和这里进行代码处理结合的？

 判断一段逻辑是否走了  日志  - debug



 1.任务 日志 性能  切面  还是listner 进行判断前置条件是否满足
 2.加载顺序
 3.反射
 4.线程 线程池 安全

 **
 */