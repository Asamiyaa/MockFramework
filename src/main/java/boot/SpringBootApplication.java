package boot;

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
  /** 测试acttivemq*/ @org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"com.core.Cache"/*,"com.msg.Amq"*/},exclude = SecurityAutoConfiguration.class)
//为了测试quartz缩小了扫描范围
//    /*为了测试mybatis快速加载 配合MybatisMain*/@org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"com.core.rule"},exclude = SecurityAutoConfiguration.class)
    @MapperScan({"com.core.rule.dao","com.register.dao","com.timing.quartz.dao"}) //需要对应的mapperScan
    @EnableCaching
    public class SpringBootApplication {

        public static void main(String[] args) {
            SpringApplication.run(SpringBootApplication.class, args);
        }
    }
