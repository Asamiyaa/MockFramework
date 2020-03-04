package com.timing.quartz;

import com.timing.JobBean;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author YangWenjun
 * @date 2020/2/19 9:36
 * @project MockFramework
 * @title: Main
 * @description:
 *
 * ****TODO:GITHUB 开源项目  分布式调度 https://github.com/xuxueli/xxl-job  ---设计角度 恰到好处--- **************
 *          1.quarz:http://www.quartz-scheduler.org/
 *                  https://www.w3cschool.cn/quartz_doc/
 *                 1.org.quartz.threadPool.threadCount 意味着是否需要线程?
 *                 2.参考官方代码 + https://www.cnblogs.com/jingmoxukong/p/5647869.html
 *
 *
 *                  Main Configuration (configuration of primary scheduler settings, transactions)
 *
 *                              org.quartz.scheduler.instanceName
                                Can be any string, and the value has no meaning to the scheduler itself - but rather serves as a mechanism for client code to distinguish schedulers when multiple instances are used within the same program. If you are using the clustering features, you must use the same name for every instance in the cluster that is ‘logically’ the same Scheduler.

                                org.quartz.scheduler.instanceId
                                Can be any string, but must be unique for all schedulers working as if they are the same ‘logical’ Scheduler within a cluster. You may use the value “AUTO” as the instanceId if you wish the Id to be generated for you. Or the value “SYS_PROP” if you want the value to come from the system property “org.quartz.scheduler.instanceId”.

                                org.quartz.scheduler.instanceIdGenerator.class
                                Only used if org.quartz.scheduler.instanceId is set to “AUTO”. Defaults to “org.quartz.simpl.SimpleInstanceIdGenerator”, which generates an instance id based upon host name and time stamp. Other IntanceIdGenerator implementations include SystemPropertyInstanceIdGenerator (which gets the instance id from the system property “org.quartz.scheduler.instanceId”, and HostnameInstanceIdGenerator which uses the local host name (InetAddress.getLocalHost().getHostName()). You can also implement the InstanceIdGenerator interface your self.

                                org.quartz.scheduler.threadName
                                Can be any String that is a valid name for a java thread. If this property is not specified, the thread will receive the scheduler’s name (“org.quartz.scheduler.instanceName”) plus an the appended string ‘_QuartzSchedulerThread’.

                                org.quartz.scheduler.makeSchedulerThreadDaemon
                                A boolean value (‘true’ or ‘false’) that specifies whether the main thread of the scheduler should be a daemon thread or not. See also the org.quartz.scheduler.makeSchedulerThreadDaemon property for tuning the SimpleThreadPool if that is the thread pool implementation you are using (which is most likely the case).

                                org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer
                                A boolean value (‘true’ or ‘false’) that specifies whether the threads spawned by Quartz will inherit the context ClassLoader of the initializing thread (thread that initializes the Quartz instance). This will affect Quartz main scheduling thread, JDBCJobStore’s misfire handling thread (if JDBCJobStore is used), cluster recovery thread (if clustering is used), and threads in SimpleThreadPool (if SimpleThreadPool is used). Setting this value to ‘true’ may help with class loading, JNDI look-ups, and other issues related to using Quartz within an application server.

                                org.quartz.scheduler.idleWaitTime
                                Is the amount of time in milliseconds that the scheduler will wait before re-queries for available triggers when the scheduler is otherwise idle. Normally you should not have to ‘tune’ this parameter, unless you’re using XA transactions, and are having problems with delayed firings of triggers that should fire immediately. Values less than 5000 ms are not recommended as it will cause excessive database querying. Values less than 1000 are not legal.

                                org.quartz.scheduler.dbFailureRetryInterval
                                Is the amount of time in milliseconds that the scheduler will wait between re-tries when it has detected a loss of connectivity within the JobStore (e.g. to the database). This parameter is obviously not very meaningful when using RamJobStore.

                                org.quartz.scheduler.classLoadHelper.class
                                Defaults to the most robust approach, which is to use the “org.quartz.simpl.CascadingClassLoadHelper” class - which in turn uses every other ClassLoadHelper class until one works. You should probably not find the need to specify any other class for this property, though strange things seem to happen within application servers. All of the current possible ClassLoadHelper implementation can be found in the org.quartz.simpl package.

                                org.quartz.scheduler.jobFactory.class
                                The class name of the JobFactory to use. A JobFatcory is responsible for producing instances of JobClasses. The default is ‘org.quartz.simpl.PropertySettingJobFactory’, which simply calls newInstance() on the class to produce a new instance each time execution is about to occur. PropertySettingJobFactory also reflectively sets the job’s bean properties using the contents of the SchedulerContext and Job and Trigger JobDataMaps.

                                org.quartz.context.key.SOME_KEY
                                Represent a name-value pair that will be placed into the “scheduler context” as strings. (see Scheduler.getContext()). So for example, the setting “org.quartz.context.key.MyKey = MyValue” would perform the equivalent of scheduler.getContext().put(“MyKey”, “MyValue”).
                                The Transaction-Related properties should be left out of the config file unless you are using JTA transactions.

                                org.quartz.scheduler.userTransactionURL
                                Should be set to the JNDI URL at which Quartz can locate the Application Server’s UserTransaction manager. The default value (if not specified) is “java:comp/UserTransaction” - which works for almost all Application Servers. Websphere users may need to set this property to “jta/usertransaction”. This is only used if Quartz is configured to use JobStoreCMT, and org.quartz.scheduler.wrapJobExecutionInUserTransaction is set to true.

                                org.quartz.scheduler.wrapJobExecutionInUserTransaction
                                Should be set to “true” if you want Quartz to start a UserTransaction before calling execute on your job. The Tx will commit after the job’s execute method completes, and after the JobDataMap is updated (if it is a StatefulJob). The default value is “false”. You may also be interested in using the @ExecuteInJTATransaction annotation on your job class, which lets you control for an individual job whether Quartz should start a JTA transaction - whereas this property causes it to occur for all jobs.

                                org.quartz.scheduler.skipUpdateCheck
                                Whether or not to skip running a quick web request to determine if there is an updated version of Quartz available for download. If the check runs, and an update is found, it will be reported as available in Quartz’s logs. You can also disable the update check with the system property “org.terracotta.quartz.skipUpdateCheck=true” (which you can set in your system environment or as a -D on the java command line). It is recommended that you disable the update check for production deployments.

                                org.quartz.scheduler.batchTriggerAcquisitionMaxCount
                                The maximum number of triggers that a scheduler node is allowed to acquire (for firing) at once. Default value is 1. The larger the number, the more efficient firing is (in situations where there are very many triggers needing to be fired all at once) - but at the cost of possible imbalanced load between cluster nodes. If the value of this property is set to > 1, and JDBC JobStore is used, then the property “org.quartz.jobStore.acquireTriggersWithinLock” must be set to “true” to avoid data corruption.

                                org.quartz.scheduler.batchTriggerAcquisitionFireAheadTimeWindow
                                The amount of time in milliseconds that a trigger is allowed to be acquired and fired ahead of its scheduled fire time.
                                Defaults to 0. The larger the number, the more likely batch acquisition of triggers to fire will be able to select and fire more than 1 trigger at a time - at the cost of trigger schedule not being honored precisely (triggers may fire this amount early). This may be useful (for performance’s sake) in situations where the scheduler has very large numbers of triggers that need to be fired at or near the same time.

                    Configuration of ThreadPool (tune resources for job execution)

                                org.quartz.threadPool.class
                                Is the name of the ThreadPool implementation you wish to use. The threadpool that ships with Quartz is “org.quartz.simpl.SimpleThreadPool”, and should meet the needs of nearly every user. It has very simple behavior and is very well tested. It provides a fixed-size pool of threads that ‘live’ the lifetime of the Scheduler.

                                org.quartz.threadPool.threadCount
                                Can be any positive integer, although you should realize that only numbers between 1 and 100 are very practical. This is the number of threads that are available for concurrent execution of jobs. If you only have a few jobs that fire a few times a day, then 1 thread is plenty! If you have tens of thousands of jobs, with many firing every minute, then you probably want a thread count more like 50 or 100 (this highly depends on the nature of the work that your jobs perform, and your systems resources!).

                                org.quartz.threadPool.threadPriority
                                Can be any int between Thread.MIN_PRIORITY (which is 1) and Thread.MAX_PRIORITY (which is 10). The default is Thread.NORM_PRIORITY (5).

                                org.quartz.threadPool.makeThreadsDaemons
                                Can be set to “true” to have the threads in the pool created as daemon threads. Default is “false”. See also the org.quartz.scheduler.makeSchedulerThreadDaemon property.

                                org.quartz.threadPool.threadsInheritGroupOfInitializingThread
                                Can be “true” or “false”, and defaults to true.

                                org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread
                                Can be “true” or “false”, and defaults to false.

                                org.quartz.threadPool.threadNamePrefix
                                The prefix for thread names in the worker pool - will be postpended with a number.

                    Configuration of Listeners (your application can receive notification of scheduled events)
                                Global listeners can be instantiated and configured by StdSchedulerFactory, or your application can do it itself at runtime, and then register the listeners with the scheduler. “Global” listeners listen to the events of every job/trigger rather than just the jobs/triggers that directly reference them.

                                Configuring a Global TriggerListener
                                Configuration of Plug-Ins (add functionality to your scheduler)
                                org.quartz.triggerListener.NAME.class = com.foo.MyListenerClass
                                org.quartz.triggerListener.NAME.propName = propValue
                                org.quartz.triggerListener.NAME.prop2Name = prop2Value

                                Configuring a Global JobListener
                                org.quartz.jobListener.NAME.class = com.foo.MyListenerClass
                                org.quartz.jobListener.NAME.propName = propValue
                                org.quartz.jobListener.NAME.prop2Name = prop2Value

            对比当前：.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())  --         defaultScheduler.scheduleJob(jobDetail,trigger);

                    Configure Scheduler Plugins
                                Like listeners configuring plugins through the configuration file consists of giving then a name, and then specifying the class name, and any other properties to be set on the instance. The class must have a no-arg constructor, and the properties are set reflectively. Only primitive data type values (including Strings) are supported.
                                Sample configuration of Logging Trigger History Plugin
                                Sample configuration of XML Scheduling Data Processor Plugin
                                Sample configuration of Shutdown Hook Plugin

                    Configuration of RMI Server and Client (use a Quartz instance from a remote process)

                    Configuration of RAMJobStore (store jobs and triggers in memory)
                                org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore
                                org.quartz.jobStore.misfireThreshold  下次保存
                    Configuration of JDBC-JobStoreTX (store jobs and triggers in a database via JDBC)
                                JobStoreTX manages all transactions itself by calling commit() (or rollback()) on the database connection after every action (such as the addition of a job). JDBCJobStore is appropriate if you are using Quartz in a stand-alone application, or within a servlet container if the application is not using JTA transactions.
                                org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
                                        org.quartz.jobStore.driverDelegateClass	yes	string	null
                                        org.quartz.jobStore.dataSource	yes	string	null
                                        org.quartz.jobStore.tablePrefix	no	string	"QRTZ_"
                                        org.quartz.jobStore.useProperties	no	boolean	false
                                        org.quartz.jobStore.misfireThreshold	no	int	60000
                                        org.quartz.jobStore.isClustered	no	boolean	false
                                        org.quartz.jobStore.clusterCheckinInterval	no	long	15000
                                        org.quartz.jobStore.maxMisfiresToHandleAtATime	no	int	20
                                        org.quartz.jobStore.dontSetAutoCommitFalse	no	boolean	false
                                        org.quartz.jobStore.selectWithLockSQL	no	string	"SELECT * FROM {0}LOCKS WHERE SCHED_NAME = {1} AND LOCK_NAME = ? FOR UPDATE"
                                        org.quartz.jobStore.txIsolationLevelSerializable	no	boolean	false
                                        org.quartz.jobStore.acquireTriggersWithinLock	no	boolean	false (or true - see doc below)
                                        org.quartz.jobStore.lockHandler.class	no	string	null
                                        org.quartz.jobStore.driverDelegateInitString	no	string	null
                    Configuration of JDBC-JobStoreCMT (JDBC with JTA container-managed transactions)

                    Configuration of DataSources (for use by the JDBC-JobStores)
                                    All pool properties specified in the quartz.properties file, so that Quartz can create the DataSource itself.
                                    The JNDI location of an application server managed Datasource can be specified, so that Quartz can use it.
                                    Custom defined org.quartz.utils.ConnectionProvider implementations.
                    Configuration of Database Clustering (achieve fail-over and load-balancing with JDBC-JobStore)
                    Configuration of TerracottaJobStore (Clustering without a database!)
 *                           Terracotta是一款由美国Terracotta公司开发的著名开源Java集群平台。它在JVM与Java应用之间实现了一个专门处理集群功能的抽象层，
 *                           以其特有的增量检测、智能定向传送、分布式协作、服务器镜像、分片等技术，允许用户在不改变现有系统代码的情况下实现单机Java应用向集群化应用
 *                           的无缝迁移。使得用户可以专注于商业逻辑的开发，由Terracotta负责实现高性能、高可用性、高稳定性的企业级Java集群。
 *
 *          2.spring-boot -> quarz  https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/html/spring-boot-features.html#boot-features-quartz
 *                  初始化 扩展类 接口
 *                  spring scheduled的动态线程池调度和任务进度的监控:https://blog.csdn.net/yyx1025988443/article/details/78698046
 *
 *
 *                  TriggerListeners和JobListeners：****https://www.w3cschool.cn/quartz_doc/quartz_doc-ikfm2d43.html
 *
 *          3.抽象代码设计 ..父类...接口...设计模式 ...
 *              https://www.cnblogs.com/youzhibing/p/10024558.html  -- 动态添加任务,前台触发
 *              https://blog.csdn.net/yyx1025988443/article/details/78698046  -- 反射 和service结合
 *
 *
 *              为什么既有Job，又有Trigger呢？很多任务调度器并不区分Job和Trigger。有些调度器只是简单地通过一个执行时间和一些job标识符来定义一个Job；其它的一些调度器将Quartz的Job和Trigger对象合二为一。在开发Quartz的时候，我们认为将调度和要调度的任务分离是合理的。在我们看来，这可以带来很多好处。

例如，Job被创建后，可以保存在Scheduler中，与Trigger是独立的，同一个Job可以有多个Trigger；这种松耦合的另一个好处是，当与Scheduler中的Job关联的trigger都过期时，可以配置Job稍后被重新调度，而不用重新定义Job；还有，可以修改或者替换Trigger，而不用重新定义与之关联的Job。

Key
将Job和Trigger注册到Scheduler时，可以为它们设置key，配置其身份属性。Job和Trigger的key（JobKey和TriggerKey）可以用于将Job和Trigger放到不同的分组（group）里，然后基于分组进行操作。同一个分组下的Job或Trigger的名称必须唯一，即一个Job或Trigger的key由名称（name）和分组（group）组成。

job必须有一个无参的构造函数（当使用默认的JobFactory时）；另一个后果是，在job类中，不应该定义有状态的数据属性，因为在job的多次执行中，这些属性的值不会保留。

那么如何给job实例增加属性或配置呢？如何在job的多次执行中，跟踪job的状态呢？答案就是:JobDataMap，JobDetail对象的一部分。

Job状态与并发
JobDataMap 传递值从jobDetail和job之间 -- 代码实现中是否需要线程池？

错过触发(misfire Instructions)

Trigger t = newTrigger()
.withIdentity("myTrigger")
.forJob("myJob")
.withSchedule(dailyAtHourAndMinute(9, 30)) // execute job daily at 9:30
.modifiedByCalendar("myHolidays") // but not on holidays
.build();
 *
 */
public class QurarzMain {

    public static void main(String[] args) throws SchedulerException, IOException {

//       Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
//        Scheduler defaultScheduler = new StdSchedulerFactory("classpath:com.timing.quartz.qurarz.property").getScheduler();
//        Scheduler defaultScheduler = new StdSchedulerFactory("classpath:myqurarz.property").getScheduler();
//        TODO:方式1
//        Scheduler defaultScheduler = new StdSchedulerFactory("src/main/resources/myqurarz.property").getScheduler();//ok
//        Scheduler defaultScheduler = new StdSchedulerFactory(classpath:myqurarz.property).getScheduler();//ok TODO:为什么这里取不到？  https://segmentfault.com/a/1190000015802324

        String path = Object.class.getResource("/").toString(); //path = file:/C:/YangWenjunData/mySrc/MockFramework11/target/classes/
        System.out.println("path = " + path);

//        InputStream resourceAsStream = Object.class.getResourceAsStream("classpath:myqurarz.property");//error  //TODO:这种classpath:形式是不是只能在配置文件中使用
        //TODO:读取classpath下内容(resource下直接到classpath下 中间没有空文件夹)   https://www.cnblogs.com/molashaonian/p/8569922.html
        //TODO:方式2  -- 这种读取方式默认从classpath开始的
        InputStream resourceAsStream = Object.class.getResourceAsStream("/myqurarz.property");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        Scheduler defaultScheduler = new StdSchedulerFactory(properties).getScheduler();//ok


        defaultScheduler.start();

        //build 方式
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "triggerGroup1").startNow()
//                .startAt(evenHourDate(null)) // get the next even-hour (minutes and seconds zero ("00:00"))
                .withSchedule(
                           SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever() //.withSchedule(cronSchedule("0 0/2 8-17 * * ?"))
                        //TODO:这里cron更加通用和明确
//                        .withMisfireHandlingInstructionNextWithExistingCount())  失火策略
                )
                //CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
//                .startAt(futureDate(5, IntervalUnit.MINUTE)) // use DateBuilder to create a date in the future
//                .endAt(dateOf(22, 0, 0))  立即触发，每个5分钟执行一次，直到22:00：
                .build();

        defaultScheduler.scheduleJob(jobDetail,trigger);

        //defaultScheduler.shutdown();

    }



}


