package com.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YangWenjun
 * @date 2019/10/16 9:26
 * @project hook
 * @title: MaxNumber
 * @description: 自定义注解
 *              自定义注解  - 注解+反射Filed / Filed值 / 注解的值 - AnnotationUtils自定义该方法的解析逻辑，进行判断
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxNumber {
    String name() default "";
    double maxNumber() default 99.9999;
}
