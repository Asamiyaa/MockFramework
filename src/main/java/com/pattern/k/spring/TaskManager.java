package com.pattern.k.spring;


import com.pattern.k.execute.AsyncTask;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TaskManager implements BeanFactoryPostProcessor,Ordered{

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        //getConfig
        List<String> configs = getConfig();
        //setAopinfo
        setAopInfo(configurableListableBeanFactory,configs);
        
    }

    private void setAopInfo(ConfigurableListableBeanFactory configurableListableBeanFactory, List<String> configs) {
        if(configurableListableBeanFactory instanceof BeanDefinitionRegistry){
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) configurableListableBeanFactory;
            for(String config : configs){
                //增强器
                RootBeanDefinition advisor = new RootBeanDefinition(DefaultBeanFactoryPointcutAdvisor.class);
                advisor.getPropertyValues().addPropertyValue("adviceBeanName",new RuntimeBeanNameReference("taskAdvisor"));

                //切点类
                RootBeanDefinition point = new RootBeanDefinition(AspectJExpressionPointcut.class);
                point.setScope(BeanDefinition.SCOPE_PROTOTYPE);
                point.setSynthetic(true);
                point.getPropertyValues().addPropertyValue("expression",config);

                advisor.getPropertyValues().addPropertyValue("pointcut",point);

                //注册到spring容器
                String beanName = BeanDefinitionReaderUtils.generateBeanName(advisor, beanDefinitionRegistry, false);
                beanDefinitionRegistry.registerBeanDefinition(beanName,advisor);
            }
        }

    }

    private List<String> getConfig() {
        //xml+annotation  扫描处理  asynTask注解臊面
        String path = "com.pattern.k.execute";//暂时写死
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(path)).setScanners(new MethodAnnotationsScanner()));
        Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(AsyncTask.class);
        List<String> cust = new ArrayList<>();
        for (Method method : methodsAnnotatedWith) {
            AsyncTask asyncTask = method.getAnnotation(AsyncTask.class);
            String classAndMethod = method.getDeclaringClass().getCanonicalName() + "." + method.getName();
            String express = "execution( * "+classAndMethod+"(..))";
            cust.add(express);
        }
        return cust;
    }

    /**
     * 保存现场 最外层
     * @return
     */
    @Override
    public int getOrder() {
        return 999;
    }
}
