package com.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String name() default "";  //TODO：是否也有默认值，从上下文中获取类名或者方法名字
    boolean intoDb() default false;
//    String description() default "";

}
