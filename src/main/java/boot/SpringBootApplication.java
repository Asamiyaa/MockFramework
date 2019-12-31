package boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author YangWenjun
 * @date 2019/10/30 11:38
 * @project MockFramework
 * @title: SpringBootApplication
 * @description:
 */

    @org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"com"})
    @MapperScan({"com.core.rule.dao","com.register.dao"}) //需要对应的mapperScan
    @EnableCaching
    public class SpringBootApplication {

        public static void main(String[] args) {
            SpringApplication.run(SpringBootApplication.class, args);
        }
    }
