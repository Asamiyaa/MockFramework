 package com.pattern.f;

import com.cache.ICache;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
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
 *      ----参考自：*** 为什么那么多框架都设计有Context?(https://www.zhihu.com/question/269905592?utm_source=wechat_session&utm_medium=social&utm_oi=820973752312545280) *******
 *          1.现场保存  cpu切换
 *          2.多模块间数据传递，交互( 模块解耦权衡，因为相互之间肯定有联系，如何联系呢？问题：倘若分布式部署，都不是一个spring容器如何处理呢？集群部署呢？ )
 *                  所以如何合理的选择局部变量、成员、静态、threadLocal..以及context...是对整个的考虑(尽量减少范围、尽量不适用多线程)，不建议用户代码对context处理，由框架完成
 *                  生命周期 lifestyle
 *                  哪些信息放到spring容器中呢?哪些又不放呢？
 *          3.上下文是依赖注入的使用  上帝模式
 *
 *     ----常见结构
 *          1.http head/body
 *                  https://blog.csdn.net/u010256388/article/details/68491509
 *          2.pjs xml
 *                  1-191 head / body head中第几个字节是代码什么...进行对应解析，body schema....映射 ‘ 对应对象 ’，因为head肯定是公用的，所以抽取出来
 *          3.统一返回
 *                  1.head - 成功标识、失败码、流水号、..../body
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

@EnableAsync
@Component
 class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext; // Spring应用上下文环境

    /*
     *
     * 实现了ApplicationContextAware 接口，必须实现该方法；
     *
     * 通过传递applicationContext参数初始化成员变量applicationContext
     *
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return (T) applicationContext.getBean(requiredType);
    }

    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }
}
