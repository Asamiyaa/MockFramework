package com.timing.quartz;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 1.spring-bean获取方式4种  https://blog.csdn.net/liuxiao723846/article/details/81557735
 */
@Component //这里可以作为同一入口进行管理，而不是落到各个地方导致循环混乱
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;//为了方法间值得传递

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> baseType) {
        return applicationContext.getBeansOfType(baseType);

    }
}