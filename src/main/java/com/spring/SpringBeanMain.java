package com.spring;

import boot.SpringBootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author YangWenjun
 * @date 2020/1/20 15:27
 * @project MockFramework
 * @title: main2
 * @description: 对象实例化和springbean的初始化流程顺序

 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
public class SpringBeanMain
{
    @Autowired
    private SpringMain SpringMain ;
    @Autowired
    private SpringMain2 SpringMain2 ;
    @Test
    public void testProperty(){
        SpringMain.setStr("nnn");
    }
}

/***
 *  22--static do
 null--run do
 33--run do33
 223344----contructor do
 setBeanName do
 setBeanFactory do
 setApplicationContext do
 afterPropertiesSet do
 postProcessBeanFactory do
 postProcessBeforeInitialization do
 postProcessAfterInitialization do
 */
@Component
class SpringMain  implements BeanFactoryPostProcessor,BeanNameAware,BeanFactoryAware,ApplicationContextAware,BeanPostProcessor
        ,InitializingBean,DisposableBean {

    private String str;
    private static String str2 = "22";
    private String str3 = "33";

    static {
        System.out.println(str2 + "--static do");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    {
        str2 = "223344";
        System.out.println(str + "--run do");
        System.out.println(str3 + "--run do33");

    }

    public SpringMain() {
        System.out.println(str2 + "----contructor do");

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization do ");
        try {
            // Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization do ");
        try {
            // Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean; //return null 导致一致执行

    }

    public void setStr(String str) {  //不会调用
        System.out.println("---setStr---");
        this.str = str;
    }
}

@Component
class SpringMain2  implements BeanFactoryPostProcessor,BeanNameAware,BeanFactoryAware,ApplicationContextAware,BeanPostProcessor
        ,InitializingBean,DisposableBean {

    private String str;
    private static String str2 = "22";
    private String str3 = "33";

    static {
        System.out.println(str2 + "--static do");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    {
        str2 = "223344";
        System.out.println(str + "--run do");
        System.out.println(str3 + "--run do33");

    }

    public SpringMain2() {
        System.out.println(str2 + "----contructor do");

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization do ");
        try {
            // Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization do ");
        try {
            // Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean; //return null 导致一致执行

    }

    public void setStr(String str) {  //不会调用
        System.out.println("---setStr---");
        this.str = str;
    }
}