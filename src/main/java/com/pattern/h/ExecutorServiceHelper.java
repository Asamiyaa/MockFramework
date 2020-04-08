package com.pattern.h;

import java.util.Locale;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServiceHelper {


    /******************演变过程***************************/


    /**线程增长全局唯一值  --> aotomic
     *      private static int threadInitNumber;
             private static synchronized int nextThreadNum() {
             return threadInitNumber++;
     }
     */
    static final String THREAD_SAVE_ADDR = "THREAD_SAVE_ADDR";
    /**
     * 线程池命名
     *
     */
    static ThreadPoolExecutor executorOne = new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
            new NamedThreadFactory("poketPool")/*ThreadPoolExecutor.AbortPolicy*/);
    /**
     * 线程池线程执行逻辑
     *    new LinkedBlockingQueue<> 设置大小 依据？？ 评估
     *      maximumPoolSize 推荐取值
             如果是 CPU 密集型任务，就需要尽量压榨CPU，参考值可以设为 NUMBER_OF_CORES + 1 或 NUMBER_OF_CORES + 2
             如果是 IO 密集型任务，参考值可以设置为 NUMBER_OF_CORES * 2
     *
     *   每次提交任务时，如果线程数还没达到coreSize就创建新线程并绑定该任务。
         所以第coreSize次提交任务后线程总数必达到coreSize，不会重用之前的空闲线程。
         在生产环境，为了避免首次调用超时，可以调用executor.prestartCoreThread()预创建所有core线程，避免来一个创一个带来首次调用慢的问题。

         线程数达到coreSize后，新增的任务就放到工作队列里，而线程池里的线程则努力的使用take()阻塞地从工作队列里拉活来干。

         如果队列是个有界队列，又如果线程池里的线程不能及时将任务取走，工作队列可能会满掉，插入任务就会失败，此时线程池就会紧急的再创建新的临时线程来补救。

         临时线程使用poll(keepAliveTime，timeUnit)来从工作队列拉活，如果时候到了仍然两手空空没拉到活，表明它太闲了，就会被解雇掉。

         如果core线程数＋临时线程数 >maxSize，则不能再创建新的临时线程了，转头执行RejectExecutionHanlder。默认的AbortPolicy抛RejectedExecutionException异常，
         其他选择包括静默放弃当前任务(Discard)，放弃工作队列里最老的任务(DisacardOldest)，或由主线程来直接执行(CallerRuns)，或你自己发挥想象力写的一个。
     */

    static ThreadPoolExecutor executorTwo = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());


    public static void main(String[] args) {

            //订单模块  "Thread-0"  -->THREAD_SAVE_ADDR
            Thread threadOne = new Thread(new Runnable() {
                public void run() {
                    System.out.println("save pokete ....");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    throw new NullPointerException();
                }
            },THREAD_SAVE_ADDR);
            //发货模块
            Thread threadTwo = new Thread(new Runnable() {
                public void run() {
                    System.out.println("save dizhi ...");
                }
            },"dizhi");

            threadOne.start();
            threadTwo.start();

        //----------线程池 pool-1-thread-1 --> poketPool-1-thread-1
        //
        // 代码（1）poolNumber是static的原子变量用来记录当前线程池的编号是应用级别的，所有线程池公用一个，比如创建第一个线程池时候线程池编号为1，
        //          创建第二个线程池时候线程池的编号为2，这里pool-1-thread-1里面的pool-1中的1就是这个值
        // 代码（2）threadNumber是线程池级别的，每个线程池有一个该变量用来记录该线程池中线程的编号，这里pool-1-thread-1里面的thread-1中的1就是这个值

        //接受用户链接模块
        executorOne.execute(new  Runnable() {
            public void run() {
                System.out.println("pool poket save");
                throw new NullPointerException();
            }
        });
        //具体处理用户请求模块
        executorTwo.execute(new  Runnable() {
            public void run() {
                System.out.println("pool dizhi save");
            }
        });

        executorOne.shutdown();
        executorTwo.shutdown();

    }

    // 命名线程工厂 - copy原生代码的内容进行改造
    static class NamedThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NamedThreadFactory(String name) {

            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            if (null == name || name.isEmpty()) {
                name = "pool";
            }

            namePrefix = name + "-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }





    /**********************工具类**************************/

}

class finalServiceHelper{

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private final static BlockingQueue<Runnable> mWorkQueue;
    private final static long KEEP_ALIVE_TIME = 3L;
    private final static TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private static ThreadFactory mThreadFactory;

    static {
        mWorkQueue = new LinkedBlockingQueue<Runnable>();
        //默认的工厂方法将新创建的线程命名为：pool-[虚拟机中线程池编号]-thread-[线程编号]
        //mThreadFactory= Executors.defaultThreadFactory();
        mThreadFactory = new NamedThreadFactory();
//        System.out.println("NUMBER_OF_CORES:"+NUMBER_OF_CORES);
    }

    public  static  void  execute(Runnable runnable){
        if (runnable==null){
            return;
        }
        /**
         * 1.当线程池小于 corePoolSize 时，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程。
         * 2.当线程池达到 corePoolSize 时，新提交任务将被放入 workQueue 中，等待线程池中任务调度执行
         * 3.当 workQueue 已满，且 maximumPoolSize > corePoolSize时，新提交任务会创建新线程执行任务
         * 4.当提交任务数超过 maximumPoolSize 时，新提交任务由 RejectedExecutionHandler 处理
         * 5.当线程池中超过 corePoolSize 线程，空闲时间达到 keepAliveTime 时，关闭空闲线程
         * 6.当设置 allowCoreThreadTimeOut(true) 时，线程池中 corePoolSize 线程空闲时间达到 keepAliveTime 也将关闭
         **/
        /**
         maximumPoolSize 推荐取值
         如果是 CPU 密集型任务，就需要尽量压榨CPU，参考值可以设为 NUMBER_OF_CORES + 1 或 NUMBER_OF_CORES + 2
         如果是 IO 密集型任务，参考值可以设置为 NUMBER_OF_CORES * 2
         */
        ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT,
                mWorkQueue,mThreadFactory);
        executorService.execute(runnable);
    }


    private static class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumberAtomicInteger = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread thread=  new Thread(r,String.format(Locale.CHINA,"%s%d","NamedThreadFactory",threadNumberAtomicInteger.getAndIncrement()));
            /* thread.setDaemon(true);//是否是守护线程
            thread.setPriority(Thread.NORM_PRIORITY);//设置优先级 1~10 有3个常量 默认 Thread.MIN_PRIORITY*/
            return thread;
        }
    }

}
