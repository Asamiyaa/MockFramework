package thread;

/**
 * @author YangWenjun
 * @date 2019/9/19 16:29
 * @project BaseJava
 * @title: MainClass
 * @description:
 */

import thread.demo1.Main;

import javax.xml.soap.Node;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***
 * 1.哪些场景想到使用多线程：1.协作 (多个)    2.多个干相同的活(同步) 并且这些人是耗资源的(io / 网络)
 *              有一堆活，需要别人(一个线程)或者和一群人(多个线程)去干，
 *              他们之间可能需要两组 (通信 wait notify 模式) 或者 一组(同步模式)  - 主线程是否需要等待他们结果
 *              同步粒度:详见4
 *
 * 2.创建方式  thread runnable Callable+Executor（可以和callable配合也可以和runnable配合）
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
 * 3.返回值处理及主线程阻塞
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
 3.synchronized 块  lock ..  condition
 3.方法
 3.多线程相关设计模式

 5.多线程融合其他技术
 1.设计模式
 */
public class MainClass {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //for(int i = 0 ; i<10 ;i++){

        // new TestThread1().start();
        //  new Runnable()

        //匿名内部类实现
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("TestThread2");
                }
            }).start();*/

        //lambda允许把函数(有参，无参)作为一个方法的参数（函数作为参数传递进方法中）。
        //不需要声明参数类型，编译器可以统一识别参数值。
            /*new Thread(()->{
                System.out.println("TestThread3");
            }).start();

*/
        // ExecutorService executorService = Executors.newFixedThreadPool(5); --创建多个线程池
            /*executorService.submit(()->{
                //Callable

            });*/
           /* Future submit = executorService.submit(new a());
            System.out.println(submit.get());*/

        // }

        //wait notify
        List good = new ArrayList();
        Object lock = new Object();
       // MainClass main = new MainClass();
        /*WaitCondition wc = new WaitCondition(good,lock);
        NotifyCondition nc = new NotifyCondition(good,lock);*/
        /*new Thread(wc).start();
        new Thread(nc).start();
*/      WaitCondition wc = new WaitCondition(good,lock);
       // new Thread(wc).start();
        NotifyCondition nc = new NotifyCondition(good,lock);
       // new Thread(nc).start();

        //静态调用  -> 创建自己调用
        //new MainClass().testConcurrent();

        //Semaphore实现
        //new MainClass().testSemaphoreImpl();
        //new MainClass().testSemaphore2();

        //CountDownLatch
        //new MainClass().testCountDownLatch();
        //new MainClass().testCountDownLatch2();

        //Atomic
        //new MainClass().testAtomic();

        //concurrent
        //new MainClass().testConcurrent1();

        //threadLocal
        //new MainClass().testThreadLocal();

        //syncronized lock+conditon
        new MainClass().testSynchronized();

        System.out.println(Thread.currentThread() + "---main");
    }


    /**
     * create
     **/
    static class TestThread1 extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread() + "---TestThread1");
        }
    }

    static class a implements Callable {

        @Override
        public Object call() throws Exception {
            return Thread.currentThread() + "abc";
        }
    }

    //wait notify 通信

    /**
     * Product-consume   监听 while -> 监听器模式    依赖 /关联 ..公共成员控制  main中传入同一个list（是否安全容器）
     * /IllegalMonitorStateException wait notify 必须在锁前提下
     *          1.while中有锁，保证每次循环执行完释放锁 而不是获得锁不释放一直循环  形成交替
     *          2.IllegalMonitorStateException  抛出以表示线程已尝试在对象的监视器上等待或通知其他线程等待对象的监视器，而不拥有指定的监视器。
     *            so:lock.wait() ...而不是this
     *          3.ava.lang.IndexOutOfBoundsException: Index: 0, Size: 0 边缘问题  逻辑错误造成一个进程"夯住"
     *          4.不是一个进程只有wait() or notify() 而是 wait notify结合  只有满足临界时才需要wait() ,并且每次执行完后需要notify()  锁的最后执行
     *          5.为什么没有形成交替？  这里通过wait notify 通知形式  而不是 while（true）自旋模式  -- 问题：既然释放，即使有while也不应该有问题呀，是偏向锁了吗？
     *            去掉while ,通过唤醒通知控制无需监听
     *            --错误 --
     *            需要
     *          sleep不能放在同步代码块里面，因为sleep不会释放锁  当前线程会一直占有produce线程，直到达到容量，调用wait()方法主动释放锁 .
     *          问题:不会释放锁,sleep完成后不就释放了吗？
     *          6.notify位置
     *
     **/
    static class WaitCondition implements Runnable {

        //List good = Collections.EMPTY_LIST;
        List good;
        Object lock;

        public WaitCondition(List good, Object lock) {
            this.good = good;
            this.lock = lock;
        }

        @Override
        public void run() {

            while (true) {
                //
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (lock) {
                    //必须先进行判断 ，否则控制不住
                    /*good.add(new String("a"));
                    System.out.println("添加一个a");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    if (good.size() == 10) {
                        try {
                            System.out.println("wait()");
                            //this.wait();
                            lock.wait();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    /*try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    good.add(new String("a"));
                    System.out.println("添加一个a");
                    lock.notifyAll();
                }    //自动释放锁 正常执行完成  -- 错误
               //wait 都会释放锁，这里的notifyAll是为了唤醒其他统一锁的线程执行
            }

        }
    }

    static class NotifyCondition implements Runnable {

        List good;
        Object lock;

        public NotifyCondition(List good, Object lock) {
            this.good = good;
            this.lock = lock;
        }

        @Override
        public void run() {

            while (true) {
                try {
                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (lock) {
                    // good.remove(Math.abs(new Random().nextInt()%10));
                    // good.remove(Math.abs(new Random().nextInt()%(good.size())));  异常导致提前终止
                    //good.remove(0); // good.size()-1                         先进先出 vs  先进后出
                    /*if(good.size() > 0){                //存在漏洞的，如果notify 还是remove线程，那么这里就会一致报错，加了if一定要考虑else 走向
                        good.remove(0);
                    }*/
                    /*if(good.size() == 0) {              // 执行先后顺序 wait()和前置条件 ，多线程必须考虑更细粒度控制
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //lock.notifyAll();
                    }*/
                    /*try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("删除一个a");*/
                    if (good.size() == 0) {
                        System.out.println("remove()");
                        //this.notifyAll();  //notify vs notifyAll()
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    good.remove(0);
                    System.out.println(Thread.currentThread().getName()+"删除一个a");
                    lock.notifyAll();
                } //正常执行完 释放锁

            }
        }
    }

    /***
     * 1.Semaphore acquire()/release() 信号量 (共享锁)  -  CountDownLatch 闭锁 (共享锁 - 是否和synchronized结合？可以) - CyclicBarrier 栅栏
     *
     *     Semaphore
     *          1.--可以用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量。--
     *            跟锁机制存在一定的相似性，semaphore也是一种锁机制，所不同的是，reentrantLock是只允许一个线程获得锁，而信号量持有多个许可(permits)，
     *            允许多个线程获得许可并执行。
     *
     *          2.TODO:参考https://zhuanlan.zhihu.com/p/73442055 将线程完成抽象到容器(核心问题)
     *
     *     CountDownLatch
     *          1.允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行。
     *           CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。每当一个线程完成了自己的任务后，计数器的值就会减1。
     *           当计数器值到达0时，它表示所有的线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。
     *          2.当引入线程池，即线程执行和countDownLatch数不一致，即批量读取到内存，批量提交 100 - 10Thread - for
     *
     *     CyclicBarrier
     *          1.用于阻塞一组线程直到某个事件发生（--countDownLatch也可以 cdl.await() 后面跟着多线程--）。所有线程必须同时到达栅栏位置才能继续执行下一步操作，
     *            且能够被重置以达到重复利用。而闭锁是一次性对象，一旦进入终止状态，就不能被重置。
     *          2.
     *
     *
     * 2.Lock+condition(await()/signal())
     * 3.BlockingQueue
     * 4.PipedInputStream/ PipedOutputStream
     */
    private void testSemaphoreImpl(){
            Semaphore  fullCount = new Semaphore(0);    //不同维度解释同一个东西。可添加信号+可删除信号=总信号 所以这里的fullCount.release()会增加一个信号
            Semaphore  emptyCount = new Semaphore(10);  //控制数量
        /**
         *  共享锁和独占结合 控制访问资源操作数  也可以for 线程，对特定资源进行限制
         *  避免信号量和实际size不一致(信号未执行完插入操作 中断性)
         */
            Semaphore  isUse = new Semaphore(1  );
            List list  = new LinkedList();
        new Thread(()->{
            for(;;){
            try {
                emptyCount.acquire();
                isUse.acquire();
                list.add("a");
                System.out.println("插入a---"+System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                fullCount.release();
                isUse.release();
            }
        }}).start();
        //----删除----
        new Thread(()->{
            for(;;){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                fullCount.acquire();
                isUse.acquire();
                list.remove(0);
                System.out.println("删除a---"+System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                emptyCount.release();
                isUse.release();
            }
        }}).start();

    }

    private void testSemaphore2(){
        //Semaphore semaphore = new Semaphore(1 );//共享锁变独享锁
        Semaphore semaphore = new Semaphore(5 );//控制局部并发
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    /**
                     * 等待5s ,10个Thread 同时输出
                     */
                    semaphore.acquire();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
                //上面为控制局部并发情况，下面是仍然可以使用大一些的线程处理
                System.out.println("abc" + Thread.currentThread().getName());
            }).start();
        }

    }
    
    private void testCountDownLatch() throws InterruptedException {
        int ii = 0 ;
        CountDownLatch countdl = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                //countdl.countDown();
                System.out.println("this is " + Thread.currentThread().getName());
                //ii++; final类型 - 所以这里不可以使用变化值来确定
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**放到线程的最后，这样才可以表示完成后释放**/
                countdl.countDown();
            }).start();
        }
        countdl.await();
        System.out.println("this is main" + System.currentTimeMillis());
    }

    /**
     * 验证countDownLatch(ThreadCount) 和 总任务数 不一致
     * @throws InterruptedException
     */
    private void testCountDownLatch2() throws InterruptedException {

        //其他线程完成的任务

        //主线程在者期间完成的任务

        //等待前置条件完成后执行下面  提高并发
        int count = 10 ;
        CountDownLatch cdl = new CountDownLatch(count);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        try{
            for (int i = 0; i < count; i++) {
                //ExecutorService executorService = Executors.newFixedThreadPool(2);//pool-2-thread-1 二号线程池 一线程
                executorService.submit(()->{
                    System.out.println("this is " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /**如果上面是耗时操作，一定需要完成才可以释放，比如--保存入库--通过返回值--否则容易造成重复录入**/
                    cdl.countDown();
                });
            }
            cdl.await();
        }finally {
            /**
             * 释放
             * 异常计数 vs 抛出
             * 执行顺序
             **/
            executorService.shutdown();
        }
        System.out.println("--main--");
    }

    /***
     * CountDownLatch 实现 “-闭锁-”
     */
    public void timeTasks(int nThreads, final Runnable task) throws InterruptedException{
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for(int i = 0; i < nThreads; i++){
            Thread t = new Thread(){
                public void run(){
                    try{
                        startGate.await();
                        try{
                            task.run();
                        }finally{
                            endGate.countDown();
                        }
                    }catch(InterruptedException ignored){

                    }

                }
            };
            t.start();
        }

        long start = System.nanoTime();
        System.out.println("打开闭锁");
        /**------开关-----   配置文件中读取 / spring 读取 / signal 线程 - 这些工具都是基于wait()/notify()**/
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        System.out.println("闭锁退出，共耗时" + (end-start));
    }

    private void testCyclicBarrier(){

    }


    /***
     * 同步属性关键字控制
         1.Atomicxx
                1.这里的局部变量不也是受到多线程影响吗？和成员局部关系。和线程访问有关
                2.总的输出值没有问题，但是get()输出时对不上，这是因为getAndIncrement(）限制了获取添加，但get()该变量可能已经被别的线程访问。
                  解决：放到map中 key为线程名+时间( 错误 )
                        获取后直接set
         2.ConcurrentHashmap / concurrentLinkedDeque
                1.ConcurrentHashmap get/put都是原子的，但是分开就不是原子的。所以map+计数map.get("key").incrementAndGet()
                  参考https://bbs.csdn.net/topics/392448293?page=1 同一时刻，只有一个线程持有安全容器引用，并操作
                2.原理。segment https://blog.csdn.net/bill_xiang_/article/details/81122044 比如put,并不是说所有线程只能有一个持有CAS
                        hashmap - ConcurrentHashmap 红黑树
         3.ThreadLocal
                1.http://www.jasongj.com/java/threadlocal/
         4.volatile
     */
    private void testAtomic(){
        /**
         * larmbda表达式使用规则：
         */
            //int i = 0 ;
            /*new Thread((i)->{
                //i++;

            });*/
        MainClass.A aa = new MainClass().new A();
        for (int i = 0; i < 1000; i++) {
            /**
             * 1.同一对象 成员 共享   当new Thread中创建内部类时，会出现输出都是int=1
             * 2.不同对象 成员 不共享
             * 3.局部变量 - 方法 - 栈调用 - 线程(栈=线程)
             */
            new Thread(aa).start();
        }


    }

    class A implements Runnable{
        //多个线程可能读到同一个i,造成不一致
        //int i ;
        AtomicInteger i = new AtomicInteger();

        @Override
        public void run() {
            //i++;
            i.getAndIncrement();
            /**
             * 模拟服务器压力，展示线程读写时差
             */
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            System.out.println("线程是：" + Thread.currentThread().getName() + "--i值为--" + i.get());
        }
    }

    /***
     * concurrentXX
     */
    private void testConcurrent1() throws InterruptedException {
        MainClass.B bb = new MainClass().new B("111"); //创建不同对象了-注意
        new Thread(bb).start();
        /**
         * 思考这几条线程执行先后
         */
        Thread.sleep(2000);//为了顺序执行 ， 这样是不合理的 。 --结果：1---null 1---222  -->
        //去掉：null---222 null---222
        //---所以先要不用sleep并且顺序执行，修改并发容器-----
        bb.setS("222");
        new Thread(bb).start();
    }

    /**
     * 变量的引入 构造+set..
     */
    class B implements Runnable{
        //HashMap hm = new HashMap(); //由于可能存在两个线程访问 所以结果
        ConcurrentHashMap  hm = new ConcurrentHashMap(); //仍然可以，segment 区段控制  而不是所有的都不同访问容器，保证数据安全即可
        //所以我们测试修改数据值而不是put() -run()->中添加s = "111".使其put不成功
        //ThreadLocal<String> s ;
        String  s ;
        AtomicInteger integer = new AtomicInteger();

        /**
         * 提供构造和set
         * @param val
         */
        B(String val){
            this.s = val;
        }
        @Override
        public void run() {
            //测试安全容器
            System.out.println("Thredname+---" + Thread.currentThread().getName()+"--"+integer.getAndIncrement());
            s="111";

            try {
                Thread.sleep(5000); //为了模拟第一个线程为完成，值被修改 。 这些都可能存在，因为线程执行是不确定
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             * 调整顺序 使得线程间的顺序更加明显
             */
            hm.put(s, s+Thread.currentThread().getName());
            System.out.println("s = " + s);

            System.out.println("当前s值是:" + hm.get("111"));
        }



        /**
         * 提供set
         */
        public void setS(String s) {
            this.s = s;
        }
    }


    /**
     * threadLocal
     */
    private void testThreadLocal(){
        MainClass.C mc = new MainClass().new C();
        new Thread(mc).start();
        new Thread(mc).start();
    }

    class C implements Runnable{
        //公共的成员  可能是1线程先执行，10 ，2线程在执行，20 .也可能交叉执行.也可能任意值，原因在:j.所以循环的可能是10-20任意值
        int j ;
        /**
         * 等价于将变量i / j 定义在 run()中，成员可以在多个方法间共享！
         */
        /*ThreadLocal<Integer> j = new ThreadLocal<>();//循环次数
        ThreadLocal<Integer> i = new ThreadLocal<>();//添加总值*/

        //注意这里单单使用j不行，因为j的声明在for域中，即线程中，相当ThreadLocal
        int i = 0 ;
        @Override
        public void run() {
            //run()中的变量是否线程独有  j线程独有 i共有
            //int j ;

//            j.set(0);
//            i.set(0);

            for ( j = 0; j < 10; j++) {
            // for(j.get().intValue()<10;j.get().intValue()j.set(0);()++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               i++;
                System.out.println("当前线程是:" + Thread.currentThread().getName() + "  i值为：" + i);
            }
           // System.out.println("当前线程是:" + Thread.currentThread().getName() + "  i值为：" + i);
        }

    }


    /**
     * 是否这里的集合安全性？上面通过锁来控制(synchronized) 如果使用
     *      问题：1.没有中止条件为什么这里还是停止了？ break - continue  --> 死锁问题
     *            2.为什么自己写的多线程似乎线程间有规律执行？而不是乱序的？
     *      扩展：1.ConcurrentHashMap如何高效实现线程安全  https://blog.csdn.net/sinat_27143551/article/details/80780775
     *
     * 是否可以通过  condition对lock条件  进行统一抽象
     */
    /**
     为什么这里没有控制住总数？
     */
    private void testConcurrent() throws InterruptedException {
        ConcurrentLinkedDeque linkDq = new ConcurrentLinkedDeque();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                for(;;){    //自旋 即使获取到也是for
                    if(linkDq.size() == 10) continue;
                    //linkDq.add("a");
                    try {
                        linkDq.add("a");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("---添加a--"+linkDq.size()+Thread.currentThread().getName());
                }
            }).start();
        }

        Thread.sleep(3000);

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for(;;){
                    if(linkDq.size() == 0) continue;
                    //linkDq.pop();
                    try {
                        linkDq.pop();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("--删除a--"+linkDq.size()+Thread.currentThread().getName());
                }
            }).start();

        }

    }

    void testSynchronized() {
        MainClass.D md = new MainClass().new D();
        for (int i = 0; i < 300; i++) {
            new Thread(md).start();
        }
    }
    class D implements Runnable{
        int j ;
        Random random = new Random();
        /***
         * 3.----Lock
         * ReentrantReadWriteLock和ReentrantLock的一个相同点和不同点，相同的是使用了同一个关键实现AbstractQueuedSynchronizer，
         * 不同的是ReentrantReadWriteLock使用了两个锁分别实现了AQS，而且WriteLock和ReentrantLock一样，使用了独占锁。
         * 而ReadLock和Semaphore一样，使用了共享锁。
         * 参考：https://juejin.im/post/5b9df6015188255c8f06923a
         *
         * condition 根据不同的条件对wait / notify 进行 个性化 通信
         */
        ReentrantLock lock = new ReentrantLock();
        //扩展
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        @Override
        //1.----public synchronized void run() {
        public  void run() {
           /* try {
                //Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            //System.out.println("j值："+(j+1));  这里需要将值附回去
            //2. ----synchronized (this) {

            try {
                lock.lock();
                //ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
                System.out.println("j值：" + (j++));//存在中断
            }finally {
                lock.unlock();
            }

            //}
        }
    }

    /***
     * blockingQueue实现 AQS
     *
     *
     *
     *
     */

    /***
     * 代码优化
     *   1.更小粒度  代码属性修饰符
     */

    /***
     * 设计模式
     * https://zhuanlan.zhihu.com/p/27897587
     */
}
