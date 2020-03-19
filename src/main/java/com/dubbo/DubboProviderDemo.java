package com.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * dubbo 启动类
 * 问题：这里不类似spring有扫描吗？那么配置文件中的dubbo开头的如何识别？
 */

@EnableAutoConfiguration
public class DubboProviderDemo {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderDemo.class,args);
    }
}
