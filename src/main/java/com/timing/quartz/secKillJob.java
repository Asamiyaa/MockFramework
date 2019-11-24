package com.timing.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 */
//@Service("secKillJob")
    //错误: 找不到或无法加载主类 com.schedual.quartz.secKillJob???
public class secKillJob implements Job {

    //private Log log = LogFactory.getLog(this.getClass());log模块
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //log.info("this is first quratz excute !");
        System.out.println(("this is first quratz excute !"));
    }

    //使用MethodInvokingJobDetailFactoryBean无需实现job，自定义method
    public void call(){
        System.out.println("call schedual");
    }

    /**为了方便直接在这里测试 复杂模块到形成test目录**/
    public static void main(String[] args) {
       // ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:schedual/application-quartz.xml");
    }
}
