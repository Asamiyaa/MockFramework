package com.pattern.k.execute;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AsyncTask {

    int taskType() ;
    int exeInterval() ;//执行间隔
    int executeMode() default 1;//1.同步线程异步执行 立即  2.异步调度执行
    //其他策略
}
