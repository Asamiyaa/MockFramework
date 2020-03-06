package com.timing.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutionJob implements Job {

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        com.timing.quartz.entity.Job job = (com.timing.quartz.entity.Job)jobExecutionContext.getMergedJobDataMap().get("job");
        QuartzRunnable quartzRunnable = null;
        try {
            quartzRunnable = new QuartzRunnable(job.getBeanName(),job.getMethodName(),job.getParams());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Future<?> submit = executorService.submit(quartzRunnable);
        try {

            submit.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
