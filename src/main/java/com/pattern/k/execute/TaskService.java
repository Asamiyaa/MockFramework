package com.pattern.k.execute;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import sun.misc.IOUtils;

public class TaskService implements ApplicationContextAware{

    //还有一个调度模块将保存在库中的信息反序列化 进行调用处理


    //执行任务
    //保存任务

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//
    }
}
