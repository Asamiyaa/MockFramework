package com.pattern.h;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@SpringBootTest(classes = SpringEvent.class)
@RunWith(SpringJUnit4ClassRunner.class)

public class SpringEventTest {

    @Autowired
    private Publish publish;

    @org.junit.Test
    public void test1(){
        publish.register("asamiya");
        System.out.println("test end");
        //验证异步
    }
    @org.junit.Test
    public void test2(){
        publish.register2("asamiya222 asyn");
        System.out.println("test end");//调用上述并不是异步，即使register中的调用是异步的。所以知道哪里开始异步的
    }
}

