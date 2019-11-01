package boot;

import org.springframework.boot.SpringApplication;

/**
 * @author YangWenjun
 * @date 2019/10/30 11:38
 * @project MockFramework
 * @title: SpringBootApplication
 * @description:
 */

    @org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"com"})
    public class SpringBootApplication {

        public static void main(String[] args) {
            SpringApplication.run(SpringBootApplication.class, args);
        }
    }
