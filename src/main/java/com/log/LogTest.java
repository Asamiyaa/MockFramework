package com.log;

import boot.SpringBootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
public class LogTest {

    @Autowired
    private LogMain logMain;

    @Test
    public void testLog(){

        logMain.doAspect("111", "asamiya");

    }



}
