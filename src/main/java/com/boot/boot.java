package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//springmvc ---> controller启动
@SpringBootApplication(scanBasePackages = {"com.annotationValidateFrameWork"})
class boot {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}

