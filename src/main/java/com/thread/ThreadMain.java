package com.thread;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

/**
 *  1.不为了多线程而写多线程，多线程必定带来系统的不稳定性。只有在性能瓶颈下使用
 *  2.局部变量 0 成员变量  全局变量  应该尽量在无锁无多线程下开发，也就是说尽量使用局部变量。但当需要方法间传递也不一定要可以通过a(b()) 或者在方法内调用。
 *          共享时必须使用成员、读无所谓(表面是读，但是底层确实多步且有写操作比如simpleDateFormat)。写就会有问题
 *  3.无锁编程
 *
 * *1.背景   修改 -公共(公共数据/数据库/成员) - 安全控制
 *      1.同步代码块之后可以有代码吗？
 *        将耗时的安全代码置于同步块外，同步后再写代码用到同步的数据，就会不安全。应该在同步块内return值返回至上层方法调用至局部变量
 *        尽量使用局部变量 - 成员变量 - 静态变量。成员变量就要考虑线程安全问题
 *        public static String name = "Hello";   //静态变量，可能发生线程安全问题
          int i;  //实例变量，可能发生线程安全问题
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
          此时jvm中由线程形成栈，栈引用堆中成员变量，静态在类的公共区。方法、方法中局部变量都在各自线程对应的栈中所以安全
 *
 *      2.线程的中断、时间片和happen-before 硬件级别的规则
 *        happen-before强调多线程间的顺序性。
 *        正确的同步代码不会应为时间片而‘ 释放锁 ’，所以不会有安全问题
 *
 *      3.volitile作为底层的实现，如何通过volitale规则保证可见性代码的。---没有理解。这里时间片不是可以中断吗？
 *
 *      4.客户访问web容器其中的多线程和应用中多线程关系，controller中如何控制多线程 https://www.javatt.com/p/28066
 *              1.servlet--在服务器上都是单例的
 *              2.所以servlet-controller本身就是不安全的
 *              3.controler、service spring单例 都不写‘有状态’成员变量的。而是写无状态比如xxxservice...xxxservice...。并且不修改也是安全的
 *                所以出问题的地方就是我们自己系统里面的业务对象，所以我们一定要注意这些业务对象里面千万不能要独立成员变量.业务对象通过new xx()
 *                创建新个体完成
 *
                 1.Servlet是线程安全的吗？ https://www.cnblogs.com/chanshuyi/p/5052426.html
                 2.struts2 每次请求都来实例化一次action,会不会造成内存溢出   struts2 是类级别的，多例的  https://www.javatt.com/p/37967
                 3.springMVC 谨慎使用成员变量 https://blog.csdn.net/panda_In5/article/details/78528762
                 4.springmvc controller中注入service后为什么是线程安全的？ https://segmentfault.com/q/1010000014971659

        5. Spring对一些（如RequestContextHolder、TransactionSynchronizationManager、LocaleContextHolder等）中
 *         非线程安全状态的bean采用ThreadLocal进行处理，让它们也成为线程安全的状态，因此有状态的Bean就可以在多线程中共享了。
 *
 *      6.只有在并发环境中，共享资源不支持并发访问，或者说并发访问共享资源会导致系统错误的情况下，才需要使用锁。否则对于调试、死锁问题***
 *              出现死锁：1.未在finally中释放锁  2.尽量使用重入锁，因为不确定多层、底层调用后是否存在工农调用  3.多把锁 相互锁住  并且都无对应的死锁异常处理机制
 *              读写锁可以兼顾性能和安全性
 *
 *
         7.cas
             CAS自旋：缓解这个问题的一个方法是使用 Yield()， 大部分编程语言都支持 Yield() 这个系统调用，
             Yield() 的作用是，告诉操作系统，让出当前线程占用的 CPU 给其他线程使用。每次循环结
             束前调用一下 Yield() 方法，可以在一定程度上减少 CPU 的使用率，缓解这个问题。你也
             可以在每次循环结束之后，Sleep() 一小段时间，但是这样做的代价是，性能会严重下降。
             所以，这种方法它只适合于线程之间碰撞不太频繁，也就是说绝大部分情况下，执行 CAS
             原语不需要重试这样的场景。

             使用 CAS 原语的方法，它的适用范围更加广泛一些。类似于这样的逻辑：先读取数据，做计算，然后更新数
             据，无论这个计算是什么样的，都可以使用 CAS 原语来保护数据安全，但是 FAA(Fetch and Add) 原语，
             这个计算的逻辑只能局限于简单的加减法。所以，我们上面讲的这种使用 CAS 原语的方法 并不是没有意义的。

        8.tomcat下某一应用的功能使用多线程，而tomcat接收多个请求也是多线程的，两者有什么联系和区别？
            https://www.zhihu.com/tardis/sogou/qus/267138066

    2.使用

         1.创建和调用  ExecutorServiceHelper.java
                 1.创建方式  thread runnable Callable+Executor（可以和callable配合也可以和runnable配合）
         *              创建子类 / 内部类 / lambda
 *
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
                     <T> Future<T> submit(Callable<T> task); Callable和Future
                     <T> Future<T> submit(Runnable task, T result);
                     Future<?> submit(Runnable task);                       ---Hmain.java中的future.get..在其他线程处理过程中主线程可以完成其他事，直到需要赶回出调用get.阻塞，判断
                                                                               后继续
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
         *           3.fork-join框架  TODO http://ifeve.com/fork-join-2/   vs executors 和 callable...
         *              Fork/Join并行方式是获取良好的并行计算性能的一种最简单同时也是最有效的设计技术。Fork/Join并行算法是我们所熟悉的分治算法的并行版本
         *              Fork/Join算法，如同其他分治算法一样，总是会递归的、反复的划分子任务，直到这些子任务可以用足够简单的、短小的顺序方法来执行。
 *                      该框架有自己的线程池和task必须按照规则编写
 *                      介绍 ForkJoinPool 的适用场景，实现原理：https://blog.csdn.net/m0_37542889/article/details/92640903


      2.设计模式 (相对于类设计，这里多线程已经定了。直接先套用)

             在软件工程中，设计模式是针对某一类共同问题的解决方案。这种解决方案被多次使用，而且已经被证明是针对该类问题的
             最优解决方案。每当你需要解决这其中的某个问题，就可以使用它们来避免做重复工作。

         1.5.1　信号模式(samophare) (允许多个线程访问共享 vs 互斥锁只允许一个 )这种设计模式介绍了如何实现某一任务向另一任务通告某一事件的情形。实现这种设计模式最简单的方式是采用信号量或者互斥，使用Java语言中的ReentrantLock类或Semaphore类即可，甚至可以采用Object类中的wait()方法和notify()方法 sleep/yield/join。 请看下面的例子。 public void task1() { section1(); commonObject.notify(); } public void task2() { commonObject.wait(); section2(); } 在上述情况下，section2()方法总是在section1()方法之后执行。
         1.5.2　会合模式(countLatchDown) 这种设计模式是信号模式的推广。在这种情况下，第一个任务将等待第二个任务的某一事件，而第二个任务又在等待第一个任务的某一事件。其解决方案和信号模式非常相似，只不过在这种情况下，你必须使用两个对象而不是一个。 请看下面的例子。 public void task1() { section1_1(); commonObject1.notify(); commonObject2.wait(); section1_2(); } public void task2() { section2_1(); commonObject2.notify(); commonObject1.wait(); section2_2(); } 在上述情况下，section2_2()方法总是会在section1_1()方法之后执行，而section1_2()方法总是会在section2_1()方法之后执行。仔细想想就会发现，如果你将对wait()方法的调用放在对notify()方法的调用之前，那么就会出现死锁。
         1.5.3　互斥模式 互斥这种机制可以用来实现临界段，确保操作相互排斥。这就是说，一次只有一个任务可以执行由互斥机制保护的代码片段。在Java中，你可以使用synchronized关键字（这允许你保护一段代码或者一个完整的方法）、ReentrantLock类或者Semaphore类来实现一个临界段。 让我们看看下面的例子。 public void task() { preCriticalSection(); try { lockObject.lock() // 临界段开始 criticalSection(); } catch (Exception e) { } finally { lockObject.unlock(); // 临界段结束 postCriticalSection(); }
         1.5.4　多元复用模式 多元复用设计模式是互斥机制的推广。在这种情形下，规定数目的任务可以同时执行临界段。这很有用，例如，当你拥有某一资源的多个副本时。在Java中实现这种设计模式最简单的方式是使用Semaphore类，并且使用可同时执行临界段的任务数来初始化该类。 请看如下示例。 public void task() { preCriticalSection(); semaphoreObject.acquire(); criticalSection(); semaphoreObject.release(); postCriticalSection(); }
         1.5.5　栅栏模式(cycleBarry) 这种设计模式解释了如何在某一共同点上实现任务同步的情形。每个任务都必须等到所有任务都到达同步点后才能继续执行。Java并发API提供了CyclicBarrier类，它是这种设计模式的一个实现。 请看下面的例子。 public void task() { preSyncPoint(); barrierObject.await(); postSyncPoint(); }
                        线程间等待满足时多线程才开始并可以reset https://blog.csdn.net/qq_38293564/article/details/80558157  countDown...主等待其他
         1.5.6　双重检查锁定模式 当你获得某个锁之后要检查某项条件时，这种设计模式可以为解决该问题提供方案。如果该条件为假，你实际上也已经花费了获取到理想的锁所需的开销。对象的延迟初始化就是针对这种情形的例子。如果你有一个类实现了单例设计模式，那可能会有如下这样的代码。 public class Singleton{ private static Singleton reference; private static final Lock lock=new ReentrantLock(); public static Singleton getReference() { try { lock.lock(); if (reference==null) { reference=new Object(); } } catch (Exception e) { System.out.println(e); } finally { lock.unlock(); } return reference; } } 一种可能的解决方案就是在条件之中包含锁。 public class Singleton{ private Object reference; private Lock lock=new ReentrantLock(); public Object getReference() { if (reference==null) { lock.lock(); if (reference == null) { reference=new Object(); } lock.unlock(); } return reference; } } 该解决方案仍然存在问题。如果两个任务同时检查条件，你将要创建两个对象。解决这一问题的最佳方案就是不使用任何显式的同步机制。 public class Singleton { private static class LazySingleton { private static final Singleton INSTANCE = new Singleton(); } public static Singleton getSingleton() { return LazySingleton.INSTANCE; } }
         1.5.7　读写锁模式 当你使用锁来保护对某个共享变量的访问时，只有一个任务可以访问该变量，这和你将要对该变量实施的操作是相互独立的。有时，你的变量需要修改的次数很少，却需要读取很多次。这种情况下，锁的性能就会比较差了，因为所有读操作都可以并发进行而不会带来任何问题。为解决这样的问题，出现了读?写锁设计模式。这种模式定义了一种特殊的锁，它含有两个内部锁：一个用于读操作，而另一个用于写操作。该锁的行为特点如下所示。 ? 如果一个任务正在执行读操作而另一任务想要进行另一个读操作，那么另一任务可以进行该操作。 ? 						个任务正在执行读操作而另一任务想要进行写操作，那么另一任务将被阻塞，直到所有的读取方都完成操作为止。 ? 如果一个任务正在执行写操作而另一任务想要执行另一操作（读或者写），那么另一任务将被阻塞，直到写入方完成操作为止。 Java并发API中含有ReentrantReadWriteLock类，该类实现了这种设计模式。如果你想从头开始实现该设计模式，就必须非常注意读任务和写任务之间的优先级。如果有太多读任务存在，那么写任务等待的时间就会很长。
                        保证读写一致，读写、写写互斥。只支持降级不支持升级。 缓存刷
                        copyOnWrite 写时复制，支持读写，但是写写会有问题
         1.5.8　线程池模式 这种设计模式试图减少为执行每个任务而创建线程所引入的开销。该模式由一个线程集合和一个待执行的任务队列构成。线程集合通常具有固定大小。当一个线程完成了某个任务的执行时，它本身并不会结束执行，它要寻找队列中的另一个任务。如果存在另一个任务，那么它将执行该任务。如果不存在另一个任务，那么该线程将一直等待，直到有任务插入队列中为止，但是线程本身不会被终结。 Java并发API包含一些实现ExecutorService接口的类，该接口内部采用了一个线程池。
         1.5.9　线程局部存储模式 这种设计模式定义了如何使用局部从属于任务的全局变量或静态变量。当在某个类中有一个静态属性时，那么该类的所有对象都会访问该属性的同一存在。如果使用了线程局部存储，则每个线程都会访问该变量的一个不同实例。 Java并发API包含了ThreadLocal类，该类实现了这种设计模式。
 *
 *        TODO:    immutability 不变模式
 *                  copyOnWrite
 *                  本地存储  局部变量/threadLocal
 *                  多线程下if与执行：guarded suspension / balking
 *                  分工：thread-per-message
 *
 *      3.协作和同步 ：MainClass.java   无锁编程  局部变量话   threadLocal
                - JUC（Autoxxx）< - cas  只有单个变量 、简单场景   atomicIntegerArray atomicRefrence<User>
                - 并发属性修饰符 -ThreadLocal / ConcurrentHashMap CopyOnWriteArrayList concurrentLinkedList concurrentSkiplistMap  
                                                  ConcurrentSkipListMap 多线程下安全的TreeMap，底层实现是跳表 http://wsswdl.github.io/posts/ConcurrentSkipListMap.html
		                              queue 底层实现  消息模型-先进先出。mq底层
                - 锁(synchronized / lock - condition) | wait notify
                              ******锁分类，使用场景以及api对应的实现。优化中如何：美团不可不说的Java“锁”事：https://tech.meituan.com/2018/11/15/java-lock.html
                              1.死锁场景及解决  ： https://blog.csdn.net/lluozh2015/article/details/49097301 
                                                 https://blog.csdn.net/m0_37962600/article/details/78238998
                              2.lock 和 synchronized区别：
                                        1.synchronized 锁this/锁其他对象，表示不同线程间操作同一个对象。/锁类：synchronized后面括号里是类，如果线程进入，则线程在该类中所有操作不能进行，包括静态变量和静态方法，对于含有静态方法和静态变量的代码块的同步，通常使用这种方式。
                                        2.Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现；
                                                  synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，如果没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；
                                                  Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能够响应中断；
                                                  通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。
                                                  Lock可以提高多个线程进行读操作的效率。（可以通过readwritelock实现读写分离）
                                                  性能上来说，在资源竞争不激烈的情形下，Lock性能稍微比synchronized差点（编译程序通常会尽可能的进行优化synchronized）。但是当同步非常激烈的时候，synchronized的性能一下子能下降好几十倍。而ReentrantLock确还能维持常态。

                                                  支持condition
                - volitale(底层的、容易出错的) / aqs
                      1）对变量的写操作不依赖于当前值
            　　      2）该变量没有包含在具有其他变量的不变式
          4.传统executorservcie.submit...vs stream + CompletableFuture使用和feature区别 <--- 线程池不释放
                   
                   1.CompletableFuture使用和feature区别 *********
                      组合计算：
                              将多个异步计算的结果合并为一个
                              等待Future集合中的所有任务都完成
                              Future完成事件 (即，任务完成以后触发执行动作)

                              *****
                              https://liurio.github.io/2019/12/17/Future%E5%92%8CCompletableFuture%E7%9A%84%E5%8C%BA%E5%88%AB/
                              *****
                     2.线程池
                              线程池是否要每次新建，是否要使用完销毁 。既然作为池，那么使用第一种吧
                              1.不新建  spring启动后初始化  每次提交使用，用完放回去 
                              2.新建 --> 注意用完销毁 ***** 
          
                     3.stream vs ex..submit callable / runnable 
                             1. 当不能使用stream调用同一个方法的并发时，可以使用ExcutorService的invokeAll(list...) 利用（）-> {...} 可以
			  同时提交批次但又是不同的方法调用。类似于流程中 submit 
                                         1 submit 2.其实二者也是没有关系的。可以同时batch提交
		           https://segmentfault.com/a/1190000014940144
                               
                               
                             2.stream和多线程关系  ---- 结合脑图上stream的使用
                                   stream是高级的iterator,是数据结构并不保存数据，它是有关算法和计算的,意味着list和线程结合。从countdownloatch/cyclebary/ --fork jon - stream 都是从流程架构上提供解决方案 . 个数据大爆炸的时代，在数据来源多样化、数据海量化的今天，很多时候不得不脱离 RDBMS，或者以底层返回的数据为基础进行更上层的数据统计。函数式语言+多核时代综合影响
                                        0.特点:
                                                  1.保护数据源。对Stream中任何元素的修改都不会导致数据源被修改，比如过滤删除流中的一个元素，再次遍历该数据源依然可以获取该元素。
                                                  2.不要再这个过程中修改数据源 ，新创建new list承接 
                                                  3.并行流使用场景：parallelStream 	适用的场景是CPU密集型的，只是做到别浪费CPU，假如本身电脑CPU的负载很大，那还到处用并行流，那并不能起到作用；
                                                            I/O密集型 磁盘I/O、网络I/O都属于I/O操作，这部分操作是较少消耗CPU资源，一般并行流中不适用于I/O密集型的操作，就比如使用并流行进行大批量的消息推送，涉及到了大量I/O，使用并行流反而慢了很多
                                                  4.在使用并行流的时候是无法保证元素的顺序的，也就是即使你用了同步集合也只能保证元素都正确但无法保证其中的顺序；
                                                  5.lambda的执行并不是瞬间完成的，所有使用parallel stream的程序都有可能成为阻塞程序的源头，并且在执行过程中程序中的其他部分将无法访问这些workers，这意味着任何依赖parallel streams的程序在什么别的东西占用着common ForkJoinPool时将会变得不可预知并且暗藏危机。
                                                  6.parallelStream是创建一个并行的Stream,而且它的并行操作是不具备线程传播性的,所以是无法获取ThreadLocal创建的线程变量的值；
                                                  7.会带来不确定性，请确保每条处理无状态且没有关联；
                                                  8.不要在多线程中使用parallelStream，原因同上类似，大家都抢着CPU是没有提升效果，反而还会加大线程切换开销；

                                  1.stream如何处理阻塞，释放资源？
                                                            1.新建fork-join池：	https://www.jianshu.com/p/bd825cb89e00  统一使用统一fork-join池，要新建类似于new ThreadPoolExecutor(NUMBER_OF_CORES,
                                                        NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT,
                                                        mWorkQueue,mThreadFactory);
                                        2.任务阻塞操作都应该又对应的解决比如httpclient / db操作，不建议做io阻塞操作存在不确定性。做了对应代码也要保证可以从hung中解除
                                          这不是线程考虑的。而是实现代码中考虑的

                                  2.异常处理
				  	1.线程自己处理自己的异常，不往外抛出：https://blog.csdn.net/h2604396739/article/details/102632494  
					2.倘若希望将子线程中异常跑到父线程感知呢？
						futureTask.get() .. e.printStackTrace();
						  } catch (ExecutionException e) {
						    //4.处理捕获的线程异常
					
				 	      那么对于stream这种如何处理？  
					3.stream如何处理异常？
						Java 8 Stream 中异常处理的4种方式：https://blog.csdn.net/zl1zl2zl3/article/details/90175115
					
					

                                        1.多线程发展历史：https://www.raychase.net/698
                                        2.fork - join 原生写法：https://www.infoq.cn/article/forkjoin-to-parallel-streams
                                        2.性能对比：https://www.cnblogs.com/carpenterlee/p/6675568.html
                                        4.stream初始化：
                                                  3、Stream的生成方式
                                                            （1）从Collection和数组获得
                                                            Collection.stream() Collection.parallelStream() Arrays.stream(T array) or Stream.of()
                                                            （2）从BufferedReader获得  < ---- 文件读取 ------
                                                            java.io.BufferedReader.lines()    <===== 读取一行一行作为list的一个元素
                                                            Stream<Path> pathStream = Files.list(Paths.get("/"));

                                                            try (Stream<String> lines = Files.lines(Paths.get(path))) {

                                                  String content = lines.collect(Collectors.joining(System.lineSeparator()));

                                                          } catch (IOException e) {
                                                              e.printStackTrace();
                                                          }

                                                      }

                                                            （3）静态工厂
                                                            java.util.stream.IntStream.range() java.nio.file.Files.walk()
                                                            （4）自己构建
                                                            java.util.Spliterator
                                                            （5）其他
                                                            Random.ints() BitSet.stream() Pattern.splitAsStream(java.lang.CharSequence) ==> 切割生成 JarFile.stream()
                                                             (6)使用生成器
                                                             // 随机生成100个整数
                                                                      Random random = new Random();
                                                                      // 加上limit否则就是无限流了
                                                                      Stream<Integer> stream = Stream.generate(random::nextInt).limit(100);
                                                            （7）使用迭代器创建Stream实例
                                                                      // 生成100个奇数，加上limit否则就是无限流了
                                                                      Stream<Integer> stream = Stream.iterate(1, n -> n + 2).limit(100);
                                                                      stream.forEach(System.out::println);



                                        5.stream坑
                                                  1.comparing thenCompare :https://blog.csdn.net/u011381576/article/details/79422498 												场景：类似于数据库一样处理数据  
                                                  2.什么时候可以使用Person：：age 
                                                  3.stream中limit使用
                                                  4.多个parallelStream之间默认使用的是同一个线程池，所以IO操作尽量不要放进parallelStream中，否则会阻塞其他parallelStream。




          
          
          
                                             1.状态标记量
                                             volatile boolean flag = false;

                                             while(!flag){
                                             doSomething();
                                             }

                                             public void setFlag() {
                                             flag = true;
                                             }

                                             volatile boolean inited = false;
                                             //线程1:
                                             context = loadContext();
                                             inited = true;

                                             //线程2:
                                             while(!inited ){
                                             sleep()
                                             }
                                             doSomethingwithconfig(context);


                                             2.double check

                                             class Singleton{
                                             private volatile static Singleton instance = null;  //保证线程间可见

                                             private Singleton() {

                                             }

                                             public static Singleton getInstance() {
                                             if(instance==null) {   //为后续访问无需枷锁判断，提高性能
                                             synchronized (Singleton.class) {   //为首次判断创建唯一对象
                                             if(instance==null)
                                             instance = new Singleton();
                                             }
                                             }
                                             return instance;
                                             }
                                             }


                         lock vs synchronized  二者都是可重入锁  对象head中monitor
                            1.提供了灵活的lockInterruptibly() 中断  / newCondition() 条件 /tryLock(long time, TimeUnit unit) 超时
                              synchrozed 无法实现
                            2.lock提供了获取queue\count\fair\owner..
                            3.synchronized与wait()和nitofy()/notifyAll()方法相结合可以实现等待/通知模型，ReentrantLock同样可以，但是需要借助Condition，
                                且Condition有更好的灵活性，具体体现在：
                                     1、一个Lock里面可以创建多个Condition实例，实现多路通知
                                     2、notify()方法进行通知时，被通知的线程时Java虚拟机随机选择的，但是ReentrantLock结合Condition可以实现有选择性地通知，这是非常重要的
                                     而synchronized就相当于整个Lock对象中只有一个单一的Condition对象，所有的线程都注册在这个对象上。线程开始notifyAll时，需要通知所有的WAITING线程，没有选择权，会有相当大的效率问题。
                                     4.lock和condition配合 https://www.cnblogs.com/xiaoxi/p/7651360.html
                                     实现生产消费 / 顺序执行线程

 */

/*compareAndSwapObject，compareAndSwapInt和compareAndSwapLong，再看AtomicBoolean源码，发现其是先把Boolean转换成整型，再使用compareAndSwapInt进行CAS，
所以原子更新double也可以用类似的思路来实现。*/
public class ThreadMain {

    public static void main(String[] args) {
        new ThreadMain().testAtomicArray();
    }

    private AtomicInteger atomicInteger = new AtomicInteger();
    Random random = new Random();
    public void testAtomic(){
        for (int i = 0; i < 10; i++) {
            //内部类不会出现问题，因为是每个自己的runnable
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //虽然这里的，但是完全可以保证并发下，
                    // 1.核心的增长数是安全的
                    // 2.输出顺序可能是不同的
                    // 3.通过sysout来模拟后续业务代码造成atomic后的逻辑使用了同一个andIncreate - 不会发生，因为他是局部变量
                    int andIncrement = atomicInteger.getAndIncrement();
                    try {
                        //Thread.sleep(Long.valueOf(random.nextInt()).longValue());
                        Thread.sleep(1000l);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(andIncrement);
                    //下面的使用andIncrement是有问题的

                }
            }).start();
        }
    }

    public void testAtomic2(){
        MyThread myThread = new MyThread();
        for (int i = 0; i < 10; i++) {
            //局部变量使得这里即使访问同一个类的安全代码都不会有问题，所以代码，方法后续可以使用该变量进行自己的处理，不收atomicInteger影响
            new Thread(myThread).start();
        }
    }
    public void testAtomic3(){
        MyThread2 myThread = new MyThread2();
        for (int i = 0; i < 10; i++) {
            new Thread(myThread).start();
        }
    }

    //AtomicIntegerArray类需要注意的是，数组value通过构造方法传递进去，然后AtomicIntegerArray会将当前数组复制一份，
    // 所以当AtomicIntegerArray对内部的数组元素进行修改时，不会影响到传入的数组。
    int[] value = new int[] { 1, 2 };
    AtomicIntegerArray ai = new AtomicIntegerArray(value);
    public void testAtomicArray(){
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ai.getAndSet(0, 3);//addAndSet
                    System.out.println(ai.get(0));
                    System.out.println(value[0]);
                }
            }).start();
        }
    }
}
/***原子更新基本类型的AtomicInteger，只能更新一个变量，如果要原子的更新多个变量，就需要使用这个原子更新引用类型提供的类**/
class AtomicReferenceTest {

    public static AtomicReference<User> atomicUserRef = new AtomicReference<User>();

    public static void main(String[] args) {
        User user = new User("conan", 15);
        atomicUserRef.set(user);
        User updateUser = new User("Shinichi", 17);
        atomicUserRef.compareAndSet(user, updateUser);
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getOld());
    }

    static class User {
        private String name;
        private int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public int getOld() {
            return old;
        }
    }
}

//将 安全值 返回成 成员而不去控制会造成问题
class MyThread implements Runnable{
    private AtomicInteger atomicInteger = new AtomicInteger();
    Random random = new Random();
    //--有时被迫需要将局部变量提高成员，以便在同一进程中的多个方法间使用改值
    int andIncrement ;
    @Override
    public void run() {
        //把返回值放到成员变量就有问题了
        //int andIncrement = atomicInteger.getAndIncrement(); //正确的
         andIncrement = atomicInteger.getAndIncrement();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
        }
        System.out.println(andIncrement);
    }
}

//ThreadLocal
class MyThread2 implements Runnable{
    private AtomicInteger atomicInteger = new AtomicInteger();
    Random random = new Random();
    //修改为ThreadLocal ,<>中的内容就是要操作的
    //private ThreadLocal<Integer> atomic = new ThreadLocal<>();
    //为了扩展这种关系
    private ThreadLocal<Map<String,Integer>> atomic = new ThreadLocal();
    Map<String, Integer> map = new HashMap<>();
    //其实直接将这种map形式设计为Context
    @Override
    public void run() {
      // atomic.set((Map<String, Integer>) new HashMap<>().put(Thread.currentThread().getName(),atomicInteger.getAndIncrement())) ;
      // ThreadLocal.get: 获取ThreadLocal中当前线程共享变量的值。
        map.put(Thread.currentThread().getName(),atomicInteger.getAndIncrement());
        atomic.set(map);
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
        }
    //输出自己线程的东西
    //  System.out.println(atomic.get().get(Thread.currentThread().getName())); atomic.get() 空指针 - 分析 ThreadLocal中没有共享变量，应该讲map设为共享
        System.out.println(atomic.get().get(Thread.currentThread().getName()));
    }
}

/**
 * https://www.ibm.com/developerworks/cn/java/j-jtp07233/index.html
 * http://ifeve.com/concurrent-collections-1/
 ConcurrentHashMap
     一个高效的并发 HashMap，并且是线程安全的。
 CopyOnWriteArrayList
     一个 List，和 ArrayList 是一族的，在读多写少的场合，该 List 的性能非常好，远远好于 Vector。
 ConcurrentLinkedQueue
     高效的并发队列，使用链表实现，可看作一个线程安全的 LinkedList。
 ConcurrentSkipListMap
     一个 Map ，跳表实现，使用跳表的数据结构快速查询。
 */
class TestConcurrentHashMap{
    //static Map map = new HashMap(); 输出的size结果为1
    static ConcurrentHashMap map = new ConcurrentHashMap();

    public static void main(String[] args) throws InterruptedException {
        new TestConcurrentHashMap().test();
        Thread.sleep(5000l);
        System.out.println(map.keySet().size());
    }

    private void test() throws InterruptedException {
        //final int i ;
        for (int i = 0; i < 10; i++) {
            int j = 0 ;
            new Thread(new Runnable() {
                @Override
                public void run() {
                   // map.put(j++,j++);//相同key会覆盖
                }
            }).start();
        }


    }


}
