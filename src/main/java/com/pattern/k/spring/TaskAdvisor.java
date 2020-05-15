package com.pattern.k.spring;


import com.alibaba.fastjson.JSON;
import com.pattern.k.execute.AsyncTask;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;


@Service("taskAdvisor")
public class TaskAdvisor implements org.aopalliance.intercept.MethodInterceptor{

    //注意引入的包
   /* @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }*/
   private static ThreadLocal<Boolean> enableFlag = new ThreadLocal<>();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if(!isEnable()){ //如果指定线程不启用拦截器效果，则直接放行，只能生效一次
            enable();
            return methodInvocation.proceed();
        }

        Method method =  methodInvocation.getMethod();//目标方法
        Object target = methodInvocation.getThis();//目标对象
        Object[] prams = methodInvocation.getArguments();//方法入参
        AsyncTask task = method.getAnnotation(AsyncTask.class);
        Service annotion = target.getClass().getAnnotation(Service.class);
        String value = annotion.value();
        if("".equals(value) || value == null){
            //如果没有指定就取驼峰命名
            value= getToUpperFirstString(target);
        }
        TaskPo taskPo = new TaskPo();
        //set值...
        taskPo.setExeData("A|"+value+"|"+method.getName()+ JSON.toJSONString(prams[0]));
        //taskService 执行插入操作

        //事务处理
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        if(currentTransactionName == null){
            //taskService 执行task
            return null;
        }
        //事务提交后控制
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){
            @Override
            public void afterCommit(){
                //taskService 执行task  解析db中保存的现场数据，通过method.invoke..
            }
        });

        return null;
    }

    private void enable() {
    }

    private String getToUpperFirstString(Object target) {
        return "";
    }

    private boolean isEnable(){
        if(enableFlag.get() == null){
            enableFlag.set(true);
        }else {
            enableFlag.set(false);
        }
        return enableFlag.get();
    }
}
