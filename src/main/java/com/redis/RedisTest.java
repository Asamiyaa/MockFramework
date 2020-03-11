package com.redis;


import boot.SpringBootApplication;
import com.core.rule.bean.dataObj.DraftDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
public class RedisTest {

    @Autowired
    private service service;

    @Test
    public void test(){
        DraftDo draftDo = service.testRedis(50044);
        System.out.println("---"+draftDo);
    }

    @Test
    public void test2(){
        DraftDo draftDo = service.testRedis2();
        System.out.println("---"+draftDo);
    }

    @Test
    public void testKey(){
        DraftDo draftDo = service.testRedis3();
        System.out.println("---"+draftDo);
    }

}
