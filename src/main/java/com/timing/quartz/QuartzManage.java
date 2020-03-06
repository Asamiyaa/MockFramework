package com.timing.quartz;

import com.timing.quartz.entity.Job;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class QuartzManage {


    private static final String JOB_PREFIX = "JOB_";
    private static final String TRIGGER_PREFIX = "TRIGGER_";
    @Autowired   //问题：这样直接注入spring识别吗？所有的框架都可以这样注入吗？
    private Scheduler scheduler;

    public void buildJob(Job job) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class)
                .withIdentity(JOB_PREFIX+job.getJobName(),job.getJobGroup())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TRIGGER_PREFIX+job.getTriggerName(),job.getTriggerGroup())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                .build();

        trigger.getJobDataMap().put("job",job);//可以获得吗？这里需要特定关系吗？其实接下来都是直接将所有datamap全部转化为调用反射，但是这里需要特定吗？

        scheduler.scheduleJob(jobDetail,trigger);

    }
}
