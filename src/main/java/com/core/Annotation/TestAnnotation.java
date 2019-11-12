package com.core.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YangWenjun
 * @date 2019/11/11 9:34
 * @project MockFramework
 * @title: TestAnnotation
 * @description: 就像反射一样从切面处理
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {

    public String name() default "";
    public int code() default 0;

}
