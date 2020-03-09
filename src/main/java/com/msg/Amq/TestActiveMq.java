package com.msg.Amq;


import boot.SpringBootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
public class TestActiveMq {

    /**
     * test代码执行完成后就没了，不会持续 ，比如quartz/mq这种连接就会断了
     */

    @Autowired
    private AmqSender amqSender;

    @Test
    public void test(){
        amqSender.send("this is the first send at 2020-03-08");
    }

























}
