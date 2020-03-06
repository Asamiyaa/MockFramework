package com.timing.quartz;

import org.apache.activemq.hooks.SpringContextHook;
import org.apache.commons.lang3.StringUtils;
import org.apache.el.util.ReflectionUtil;
import org.apache.xbean.spring.context.SpringApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@Component  -- 哪些需要注入，如何和spring结合
public class QuartzRunnable implements Runnable{

    /*@Autowired
    private ApplicationContext applicationContext;//从同一入口加载-工具类*/
    private Object target ;
    private Method method ;
    private String args ;
                        //不能自动注入因为找不到Stirng类型的beanName
    public QuartzRunnable(String beanName, String methodName, String params) throws NoSuchMethodException {
        target = SpringUtils.getBean(beanName);//反射获取对象？反射是去构建对象
        args = params;
        if(StringUtils.isNotBlank(args)){
            method = target.getClass().getDeclaredMethod(methodName, String.class);
        }else{
            method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public void run() {
        ReflectionUtils.makeAccessible(method);
        if(StringUtils.isNotBlank(args)){
            try {
                method.invoke(target, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }else{
            try {
                method.invoke(target);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
