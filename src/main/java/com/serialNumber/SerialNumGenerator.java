package com.serialNumber;

/**
 * @author YangWenjun
 * @date 2019/12/27 11:06
 * @project MockFramework
 * @title: serialNumGenerator
 * @description:
 *
 * 0.问题
 *      1.同步代码块之后可以有代码吗？
 *        将耗时的安全代码置于同步块外，同步后再写代码用到同步的数据，就会不安全。应该在同步块内return值返回至上层方法调用至局部变量
 *      2.线程的中断、时间片和happen-before
 *        happen-before强调多线程间的顺序性。
 *        正确的同步代码不会应为时间片而‘ 释放锁 ’，所以不会有安全问题
 *      3.volitile作为底层的实现，如何通过volitale规则保证可见性代码的。---没有理解。这里时间片不是可以中断吗？
 *
 *
 *
 *      1.readme中查看系代码时，哪些地方需要注意多线程、哪些不需要。
     *      多个客户/线程访问中间件，中间件线程池通过mapping找到对应的servlet.当出现两个以上时就会有安全问题
     *   --servlet--在服务器上都是单例的
            public static String name = "Hello";   //静态变量，可能发生线程安全问题
            int i;  //实例变量，可能发生线程安全问题
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         此时jvm中由线程形成栈，栈引用堆中成员变量，静态在类的公共区。方法、方法中局部变量都在各自线程对应的栈中所以安全
         所以servlet-controller本身就是不安全的。但service为什么就可以大方的写成员呢？
         因为此时已经是一个栈里面了。调用从前面开始。

         第二种线程是我们希望自己栈内创建线程来使得程序处理加速。这是需要思考多线程实现代码类中的成员

         ***为什么在spring项目的servcie/dao中可以不考虑并发问题呢？**** https://www.javatt.com/p/28066 ***
            无状态的，即实现中无成员变量，即使有也是像servie/dao等spring管理的不可变 。
            那些的在Dao中的xxxDao,或controller中的xxxService，这些对象都是单例那么就会出现线程同步的问题。
            但是话又说回来了，这些对象虽然会被多个进程并发访问，可我们访问的是他们里面的方法，
            这些类里面通常不会含有成员变量，那个Dao里面的ibatisDao是框架里面封装好的，已经被测试，不会出现线程同步问题了。
         ***所以出问题的地方就是我们自己系统里面的业务对象，所以我们一定要注意这些业务对象里面千万不能要独立成员变量，
 *          否则会出错。方法调用处理的数据来自依赖，方法的注入而不是成员，所以层层不会出问题。这些bean在jvm堆中只有方法
 *       ***查看bbsp的代码确实，原来没有注意****
 *
 *       Spring对一些（如RequestContextHolder、TransactionSynchronizationManager、LocaleContextHolder等）中
 *       非线程安全状态的bean采用ThreadLocal进行处理，让它们也成为线程安全的状态，因此有状态的Bean就可以在多线程中共享了。
 *
 *

        多线程的应用场景(1.io/cpu耗时 2.异步(无需当时同步方法必须的 mq就是扩展  后台任务/自动作业) 3.有状态)
            https://blog.csdn.net/hll814/article/details/50816268
            自己定义的多线程测试类，是否需要交由spring管理。即又在哪些场景下会写出包含成员变量的类呢？


       2.设计模式 (相对于类设计，这里多线程已经定了。直接先套用)
            并发型模式和线程池模式
            在软件工程中，设计模式是针对某一类共同问题的解决方案。这种解决方案被多次使用，而且已经被证明是针对该类问题的
            最优解决方案。每当你需要解决这其中的某个问题，就可以使用它们来避免做重复工作。
            其中，单例模式（Singleton）和工厂模式（Factory）是几乎每个应用程序中都要用到的通用设计模式。
            并发处理也有其自己的设计模式。本节，我们将介绍一些最常用的并发设计模式，以及它们的Java语言实现
            /samophare/countLatchDown/cycleBarrier
            1.5.1　信号模式 这种设计模式介绍了如何实现某一任务向另一任务通告某一事件的情形。实现这种设计模式最简单的方式是采用信号量或者互斥，使用Java语言中的ReentrantLock类或Semaphore类即可，甚至可以采用Object类中的wait()方法和notify()方法 sleep/yield/join。 请看下面的例子。 public void task1() { section1(); commonObject.notify(); } public void task2() { commonObject.wait(); section2(); } 在上述情况下，section2()方法总是在section1()方法之后执行。
            1.5.2　会合模式 这种设计模式是信号模式的推广。在这种情况下，第一个任务将等待第二个任务的某一事件，而第二个任务又在等待第一个任务的某一事件。其解决方案和信号模式非常相似，只不过在这种情况下，你必须使用两个对象而不是一个。 请看下面的例子。 public void task1() { section1_1(); commonObject1.notify(); commonObject2.wait(); section1_2(); } public void task2() { section2_1(); commonObject2.notify(); commonObject1.wait(); section2_2(); } 在上述情况下，section2_2()方法总是会在section1_1()方法之后执行，而section1_2()方法总是会在section2_1()方法之后执行。仔细想想就会发现，如果你将对wait()方法的调用放在对notify()方法的调用之前，那么就会出现死锁。
            1.5.3　互斥模式 互斥这种机制可以用来实现临界段，确保操作相互排斥。这就是说，一次只有一个任务可以执行由互斥机制保护的代码片段。在Java中，你可以使用synchronized关键字（这允许你保护一段代码或者一个完整的方法）、ReentrantLock类或者Semaphore类来实现一个临界段。 让我们看看下面的例子。 public void task() { preCriticalSection(); try { lockObject.lock() // 临界段开始 criticalSection(); } catch (Exception e) { } finally { lockObject.unlock(); // 临界段结束 postCriticalSection(); }
            1.5.4　多元复用模式 多元复用设计模式是互斥机制的推广。在这种情形下，规定数目的任务可以同时执行临界段。这很有用，例如，当你拥有某一资源的多个副本时。在Java中实现这种设计模式最简单的方式是使用Semaphore类，并且使用可同时执行临界段的任务数来初始化该类。 请看如下示例。 public void task() { preCriticalSection(); semaphoreObject.acquire(); criticalSection(); semaphoreObject.release(); postCriticalSection(); }
            1.5.5　栅栏模式 这种设计模式解释了如何在某一共同点上实现任务同步的情形。每个任务都必须等到所有任务都到达同步点后才能继续执行。Java并发API提供了CyclicBarrier类，它是这种设计模式的一个实现。 请看下面的例子。 public void task() { preSyncPoint(); barrierObject.await(); postSyncPoint(); }
            1.5.6　双重检查锁定模式 当你获得某个锁之后要检查某项条件时，这种设计模式可以为解决该问题提供方案。如果该条件为假，你实际上也已经花费了获取到理想的锁所需的开销。对象的延迟初始化就是针对这种情形的例子。如果你有一个类实现了单例设计模式，那可能会有如下这样的代码。 public class Singleton{ private static Singleton reference; private static final Lock lock=new ReentrantLock(); public static Singleton getReference() { try { lock.lock(); if (reference==null) { reference=new Object(); } } catch (Exception e) { System.out.println(e); } finally { lock.unlock(); } return reference; } } 一种可能的解决方案就是在条件之中包含锁。 public class Singleton{ private Object reference; private Lock lock=new ReentrantLock(); public Object getReference() { if (reference==null) { lock.lock(); if (reference == null) { reference=new Object(); } lock.unlock(); } return reference; } } 该解决方案仍然存在问题。如果两个任务同时检查条件，你将要创建两个对象。解决这一问题的最佳方案就是不使用任何显式的同步机制。 public class Singleton { private static class LazySingleton { private static final Singleton INSTANCE = new Singleton(); } public static Singleton getSingleton() { return LazySingleton.INSTANCE; } }
            1.5.7　读写锁模式 当你使用锁来保护对某个共享变量的访问时，只有一个任务可以访问该变量，这和你将要对该变量实施的操作是相互独立的。有时，你的变量需要修改的次数很少，却需要读取很多次。这种情况下，锁的性能就会比较差了，因为所有读操作都可以并发进行而不会带来任何问题。为解决这样的问题，出现了读?写锁设计模式。这种模式定义了一种特殊的锁，它含有两个内部锁：一个用于读操作，而另一个用于写操作。该锁的行为特点如下所示。 ? 如果一个任务正在执行读操作而另一任务想要进行另一个读操作，那么另一任务可以进行该操作。 ? 						个任务正在执行读操作而另一任务想要进行写操作，那么另一任务将被阻塞，直到所有的读取方都完成操作为止。 ? 如果一个任务正在执行写操作而另一任务想要执行另一操作（读或者写），那么另一任务将被阻塞，直到写入方完成操作为止。 Java并发API中含有ReentrantReadWriteLock类，该类实现了这种设计模式。如果你想从头开始实现该设计模式，就必须非常注意读任务和写任务之间的优先级。如果有太多读任务存在，那么写任务等待的时间就会很长。
            1.5.8　线程池模式 这种设计模式试图减少为执行每个任务而创建线程所引入的开销。该模式由一个线程集合和一个待执行的任务队列构成。线程集合通常具有固定大小。当一个线程完成了某个任务的执行时，它本身并不会结束执行，它要寻找队列中的另一个任务。如果存在另一个任务，那么它将执行该任务。如果不存在另一个任务，那么该线程将一直等待，直到有任务插入队列中为止，但是线程本身不会被终结。 Java并发API包含一些实现ExecutorService接口的类，该接口内部采用了一个线程池。
            1.5.9　线程局部存储模式 这种设计模式定义了如何使用局部从属于任务的全局变量或静态变量。当在某个类中有一个静态属性时，那么该类的所有对象都会访问该属性的同一存在。如果使用了线程局部存储，则每个线程都会访问该变量的一个不同实例。 Java并发API包含了ThreadLocal类，该类实现了这种设计模式。
 *
 *         极客时间：immutability 不变模式
 *                  copyOnWrite
 *                  本地存储  局部变量/threadLocal
 *                  多线程下if与执行：guarded suspension / balking
 *                  分工：thread-per-message
 *
 *      3.协作和同步 ：D:\Data\mySrc\MockFramework\src\test\thread\MainClass.java
     *      - JUC（Autoxxx）
     *      - 并发(属性修饰符 -ThreadLocal / ConcurrentHashMap CopyOnWriteArrayList:/volitile.
            - 锁(synchronized / lock - reentrantLock / ReadWriteLock) | wait notify condition
                      lock vs synchronized  二者都是可重入锁  对象head中monitor
                            1.提供了灵活的lockInterruptibly() 中断  / newCondition() 条件 /tryLock(long time, TimeUnit unit) 超时
                                synchrozed 无法实现
                            2.lock提供了获取queue\count\fair\owner..
                            3.synchronized与wait()和nitofy()/notifyAll()方法相结合可以实现等待/通知模型，ReentrantLock同样可以，但是需要借助Condition，且Condition有更好的灵活性，具体体现在：
                                1、一个Lock里面可以创建多个Condition实例，实现多路通知
                                2、notify()方法进行通知时，被通知的线程时Java虚拟机随机选择的，但是ReentrantLock结合Condition可以实现有选择性地通知，这是非常重要的
                                    而synchronized就相当于整个Lock对象中只有一个单一的Condition对象，所有的线程都注册在这个对象上。线程开始notifyAll时，需要通知所有的WAITING线程，没有选择权，会有相当大的效率问题。
                            4.lock和condition配合 https://www.cnblogs.com/xiaoxi/p/7651360.html
                               实现生产消费 / 顺序执行线程  TODO: lock的高级应用 ---> ?
            - volitale / aqs

        4.创建和调用
            1.创建方式  thread runnable Callable+Executor（可以和callable配合也可以和runnable配合）
 *              callable https://www.cnblogs.com/syp172654682/p/9788051.html
 *              每个用户一个线程，web容器分配，那么该线程就是该用户的主线程，当执行到多线程模块时，
 *              判断此时使用的子线程是使用runnable/callable 是否需要get   vs  countdownloach
 *
 *              创建子类 / 内部类 / lambda
 *
 *              线程池(构造)
 *              ExecutorService executorService = Executors.newSingleThreadExecutor()
 *              /newFixedThreadPool(5);/newCachedThreadPool() /newScheduledThreadPool(int corePoolSize)
 *              执行逻辑：https://www.jianshu.com/p/059710b6247a
 *              使用ThreadPoolExecutor构造参数设置 vs Executors :https://blog.csdn.net/a837199685/article/details/50619311
 *              参数设置准则：http://ifeve.com/how-to-calculate-threadpool-size/
 *                           tps: n+1/2n+1  多线程使用：io网络耗时操作  cpu耗时：单线程 避免上下文切换和锁
 *
 *             Future result =  executorService.submit(Callable<T> task) / submit(Runnable task)
 *                              execute  无返回值
 *                              shutdown()启动有序关闭，其中先前提交的任务将被执行，但不会接受任何新任务。
 *
             2.返回值处理及主线程阻塞
                <T> Future<T> submit(Callable<T> task);
                <T> Future<T> submit(Runnable task, T result);
                Future<?> submit(Runnable task);
                1.callable 可接受返回值    可以抛出异常
                2.Future api   get() / isDone() / isCancle() ..
                3.get() 阻塞：该future线程的值返回
                注意多个future无关 -- 多线程和for是肯定的，否则无法确定几个任务，也就是几个总个目标
                此时for循环中存在线程池的提交返回future 那么future获得的是哪个线程 - 只要有一个获得主线程就向下
                那为什么还开线程？get()前  - 线程提交后可以继续其他操作 比如其他io ... 派别人干事
                https://www.cnblogs.com/syp172654682/p/9788051.html
                 *
                 * 4.runnable / callable 具体多线程实现 - 多线程注意点
                 *                  （思考哪些是成员，哪些是局部；多个线程进来同步部分 ；思考底层 原子性/一致性/有序性 jmm cpu指令 咆沽视频）
                 *              1.安全  同步vswait+nofity通信
                 *                      IllegalMonitorStateException:
                 *              2.先后考虑 ：1.信号量 / 栅栏 / 门闩 / 队列
                 *                           2.小粒度  成员变量修饰符  AtomicInteger.. 并发容器vector -->concurrentHashamap /阻塞队列 linkedBlockQueue 协作  threadLocal  volatile  reetrentLock 读写锁  死锁  超时

        5.多线程融合其他技术 - netty/tomcat框架
            1.java 设计模式


        1.并发-多个用户/线程访问同一个公共数据/数据库/成员，并且修改它 导致不安全  --> 并发代码思考
        2.线程
        1.多个客户访问服务器，对代码controller 成员 以及 其他代码中的公共  -- 每个用户一个独立线程吗？servlet是线程安全的吗 ？
        2.宏观下 - 比如数据库、服务器、业务级判断 处理同一个数据 -->到底哪些代码是共享的？
        mvc-controller中
         @Autowired
         private UserService userService ; 因为该成员变量是不变化的，单例。就像工具类一样
         struts中的
         struts2 是类级别的，多例的。所以可以写 list xx / string intId ...

         1.Servlet是线程安全的吗？ https://www.cnblogs.com/chanshuyi/p/5052426.html
         2.struts2 每次请求都来实例化一次action,会不会造成内存溢出  https://www.javatt.com/p/37967
         3.springMVC 谨慎使用成员变量 https://blog.csdn.net/panda_In5/article/details/78528762
         4.springmvc controller中注入service后为什么是线程安全的？ https://segmentfault.com/q/1010000014971659

         答：你没搞清楚线程安全是什么意思。userService本身并不是线程安全的，你在userController里修改userService吗？
         只是调用userService里的方法吧？方法都是线程安全的，多线程调用一个实例的方法，会在内存中复制变量，
         所以只要你不在userConstroller里修改userService这个实例就没问题。-- 我去new xxservice() /..init /.属性

         手动初始化，不使用spring 就会造成线程安全问题吧

         3.一个客户 代码中使用多线程 手动Thread  <--- 会发生线程安全(多个用户访问/new Thread)问题，什么场景下使用多线程，
         解决方案:
         无锁编程  局部变量话 / threadLocal
         锁

 *
 */


/**
 *      1.全局唯一生成规则  服务器重启继续排、读库性能？多台服务器公用 redis?  大数据并发环境下呢？

         1.流水号(或各种系统序列号)生成问题？https://www.zhihu.com/question/35674057
         如果只要求不重复的话，就使用uuid的方式, 时间+机器+应用ID 。
         就不存在并发问题，而且可以在‘ 客户端直接生产 ’。
         如果需要特殊规则，比如+1递增（并发-多个用户/线程访问同一个公共数据/数据库/成员 导致不安全）。
         就需要一个‘ 独立的应用服务 ’在来生成。可以有这些方式：
         1.利用数据库，自增字段。oracle ,mysql 都有。前端使用web应用封装一下。
         2.自己写代码也简单。java atom 变量可以用。不过要考虑持久化和备份问题。
         3.zookeeper node ID,不过效率比较低

         2.https://juejin.im/entry/5aa214e551882555872321d9：通过时间戳+机器码+随机数
         SimpleDateFormat线程不安全及解决办法：https://blog.csdn.net/csdn_ds/article/details/72984646

         3.支持高并发，可配置，效率高的流水号生成器：https://blog.csdn.net/huangwenyi1010/article/details/51476515

         --综合结论实现
         1.redis实现自增部分 https://xli1224.github.io/2018/03/10/global-id-generation/
 */
public class SerialNumGenerator {



}
