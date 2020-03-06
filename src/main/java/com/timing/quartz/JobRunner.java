package com.timing.quartz;

import com.timing.quartz.dao.JobMapper;
import com.timing.quartz.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobRunner implements ApplicationRunner {


    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private QuartzManage quartzManage;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Job> jobs = jobMapper.selectAll();
        for (Job job : jobs) {
            quartzManage.buildJob(job);
        }
    }
}
