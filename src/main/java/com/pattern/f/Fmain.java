package com.pattern.f;

import com.cache.ICache;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

/***
 *
 *资源获取
 *      从Emain中定位问题衍生而来。
 *
 *      1.建立全局统一的Context  ContextFactory
 *      2.  1.几种上下文以及获取Spring的ApplicationContext的几种方法 https://blog.csdn.net/u013147600/article/details/49616427  + 直接注入
 *          2.获取容器中的Bean  https://www.jianshu.com/p/5e97109b479f
 *
 *
 *
 *
 *
 */
public class Fmain {
    @Autowired
    private ApplicationContext applicationContext;

//    获取指定注解所有的 Bean
    Map<String,Object> objectMap = applicationContext.getBeansWithAnnotation(Service.class);
//    获取指定注解所有的 Bean 的名字
    String[] beanNames = applicationContext.getBeanNamesForAnnotation(Service.class);
//    获取容器中指定某类型、或实现某接口、或继承某父类所有的 Bean
    Map<String, Test> objectMap2= applicationContext.getBeansOfType(Test.class);
//    获取容器中指定某类型、或实现某接口、或继承某父类所有的 Bean 的名称
    String[] beanNames2 = applicationContext.getBeanNamesForType(Service.class);
    Test testbean = (Test)applicationContext.getBean("testbean");
//    获取指定名字、类型的 Bean，指定的类型可以是其父类或所实现的接口
    Object object = applicationContext.getBean("testbean", ICache.class);
//    获取指定类型、或接口、或某类的子类的 Bean
    Object object2 = applicationContext.getBean(ICache.class);
    int beanCount = applicationContext.getBeanDefinitionCount();
    String[] beanNames3 = applicationContext.getBeanDefinitionNames();


}
