package com.core.rule.mybaits;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;


/**
 * 用于定义setting 全局配置  setting 是什么：查看MyabtisMain中的3.配置
 *
 */
public class Configure implements ConfigurationCustomizer {

    @Override
    public void customize(Configuration configuration) {

//        configuration.setAggressiveLazyLoading();

//        configuration.setCacheEnabled();

    }
}
