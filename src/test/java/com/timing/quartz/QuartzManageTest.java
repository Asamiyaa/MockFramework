package com.timing.quartz;

import boot.SpringBootApplication;
import com.timing.quartz.dao.JobMapper;
import com.timing.quartz.entity.Job;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class)
public class QuartzManageTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Autowired
    private JobMapper jobMapper;
    @Test
    public void testDb() {
        Job job = new Job();
        job.setId(1);
        job.setBeanName("student");
        job.setMethodName("study");
        jobMapper.insert(job);
    }

    @Test
    public void buildJob() {
        //启动后因为springRunner容器初始化会将自定义的quattz数据初始化，所以应该可以执行 - 断点
        Job job = new Job();
        job.setId(2);
        job.setBeanName("student");
        job.setMethodName("study");
        jobMapper.insert(job);
    }
}