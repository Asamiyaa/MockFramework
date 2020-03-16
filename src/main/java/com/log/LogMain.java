package com.log;

import com.log.annotation.AnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * Spring Boot 日志配置(超详细): https://blog.csdn.net/Inke88/article/details/75007649
 *
 *1.自定义注解  2.自定义注解 解析AnnotationUtils( 在校验中 对规则、多个注解遍历..根据规则取值进行判断，log只是输出，每个注解又有不同的属性)  3.切面知识
 *          现在的logAspect简单就简单在只是对其进行展示，描述，并没有将切面入参值修改传递给主方法，或者对返回值进行加工返回。这样的话，@before修饰方法就需要返回
 *          ---> 代理 TODO:继续织入会原逻辑代码
 *
 * 使用 Spring Boot AOP 实现 Web 日志处理和分布式锁：
 *          https://www.ibm.com/developerworks/cn/java/j-spring-boot-aop-web-log-processing-and-distributed-locking/index.html
 */

@Component
public class LogMain {

    private static final Logger LOG = LoggerFactory.getLogger(LogMain.class);

    @Bean
    public AnnotationUtils logMethod() {
        LOG.info("==========print log==========");
        return new AnnotationUtils();
    }

    //----------------logAspect---------------------
/*
    public String doAspect(String name ){

    }*/
//当这里不能注入时，说明在容器中没有对应的名称的数据，所以提前设计好
/*    static String id = "1";

    @Bean
    public AnnotationUtils doAspect(String id ,String name) {
        LOG.info("==========print log==========");
        return new AnnotationUtils();
    }*/

//    @Log(name = "doAspect")
    @Log
    public String doAspect(String id ,String name ){
        System.out.println("name is " + name + "--- id  =" + id);
//        System.out.println("name is " + name + "--- id  =" + id);
        return id+name+"ret";
    }



}
