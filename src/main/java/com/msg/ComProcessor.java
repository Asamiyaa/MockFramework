package com.msg;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author YangWenjun
 * @date 2019/7/29 16:25
 * @project hook
 * @title: comProcessor
 * @description:
 */
public class ComProcessor implements BeanFactoryPostProcessor, DisposableBean {

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
