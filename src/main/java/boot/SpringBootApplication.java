package boot;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author YangWenjun
 * @date 2019/10/30 11:38
 * @project MockFramework
 * @title: SpringBootApplication
 * @description:
 */

//  /** 全部的加载*/ @org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"com.timing.quartz"},exclude = SecurityAutoConfiguration.class)
//  /** 测试acttivemq*/ @org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages ={"com.dubbo"/*"com.log"*//*"com.redis"*/,"com.core.rule.dao","com.pattern.k"} /*{*//*"com.core.Cache",*//**//*"com.msg.Amq"*//*}*/,exclude = SecurityAutoConfiguration.class)
//为了测试quartz缩小了扫描范围
//    /*为了测试mybatis快速加载 配合MybatisMain*/@org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"com.core.rule"},exclude = SecurityAutoConfiguration.class)
//@org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"com.annotationValidateFrameWork"}) //controller验证 springmvc源码入口
@org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"com.core.rule"})//验证事务作用
    @MapperScan({"com.core.rule.dao","com.core.rule.dao","com.register.dao","com.timing.quartz.dao"}) //需要对应的mapperScan 入口RuleDaoMapperTest
//    @EnableCaching //启动缓存
//  @EnableDubbo
//  @EnableDubboConfig
    public class SpringBootApplication {

        public static void main(String[] args) {
            SpringApplication.run(SpringBootApplication.class, args);
        }
    }
/**\
 *
 * 这个类从哪个 jar 包加载的？为什么会报各种类相关的 Exception？

 我改的代码为什么没有执行到？难道是我没 commit？分支搞错了？

 遇到问题无法在线上 debug，难道只能通过加日志再重新发布吗？

 线上遇到某个用户的数据处理有问题，但线上同样无法 debug，线下无法重现！

 是否有一个全局视角来查看系统的运行状况？

 有什么办法可以监控到JVM的实时运行状态？
 ***/