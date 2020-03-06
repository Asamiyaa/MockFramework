package com.timing.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Repository
public class Student {

    public void study(){
        //假设执行时间大于调度时间呢？
        System.out.println("-------study------");
    }
}
