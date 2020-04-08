
package com.thread;


/**
 * @author YangWenjun
 * @date 2019/9/19 16:29
 * @project BaseJava
 * @title: MainClass
 * @description:
 */


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/***
    deadlock
         死锁
         1.死锁:多个进程在运行过程中因争夺资源而造成的一种僵局，当进程处于这种僵持状态时，若无外力作用，它们都将无法再向前推进。
         *     					 因此我们举个例子来描述，如果此时有一个线程A，按照先锁a再获得锁b的的顺序获得锁，而在此同时又有另外一个线程B，按照先锁b再锁a的顺序获得锁
         *  		  2.原因：a. 竞争资源
         系统中的资源可以分为两类：
         可剥夺资源，是指某进程在获得这类资源后，该资源可以再被其他进程或系统剥夺，CPU和主存均属于可剥夺性资源；
         另一类资源是不可剥夺资源，当系统把这类资源分配给某进程后，再不能强行收回，只能在进程用完后自行释放，如磁带机、打印机等。
         产生死锁中的竞争资源之一指的是竞争不可剥夺资源（例如：系统中只有一台打印机，可供进程P1使用，假定P1已占用了打印机，若P2继续要求打印机打印将阻塞）
         产生死锁中的竞争资源另外一种资源指的是竞争临时资源（临时资源包括硬件中断、信号、消息、缓冲区内的消息等），通常消息通信顺序进行不当，则会产生死锁

         b. 进程间推进顺序非法

         若P1保持了资源R1,P2保持了资源R2，系统处于不安全状态，因为这两个进程再向前推进，便可能发生死锁
         例如，当P1运行到P1：Request（R2）时，将因R2已被P2占用而阻塞；当P2运行到P2：Request（R1）时，也将因R1已被P1占用而阻塞，于是发生进程死锁
         3.必要条件
         互斥条件：进程要求对所分配的资源进行排它性控制，即在一段时间内某资源仅为一进程所占用。
         请求和保持条件：当进程因请求资源而阻塞时，对已获得的资源保持不放。
         不剥夺条件：进程已获得的资源在未使用完之前，不能剥夺，只能在使用完时由自己释放。
         环路等待条件：在发生死锁时，必然存在一个进程--资源的环形链。
         4.解决方案
         资源一次性分配：一次性分配所有资源，这样就不会再有请求了：（破坏请求条件）
         只要有一个资源得不到分配，也不给这个进程分配其他的资源：（破坏请保持条件）
         可剥夺资源：即当某进程获得了部分资源，但得不到其它资源，则释放已占有的资源（破坏不可剥夺条件）
         资源有序分配法：系统给每类资源赋予一个编号，每一个进程按编号递增的顺序请求资源，释放则相反（破坏环路等待条件）
         1、以确定的顺序获得锁
         ?那么死锁就永远不会发生。 针对两个特定的锁，开发者可以尝试按照锁对象的hashCode值大小的顺序，分别获得两个锁，这样锁总是会以特定的顺序获得锁，那么死锁也不会发生。问题变得更加复杂一些，如果此时有多个线程，都在竞争不同的锁，简单按照锁对象的hashCode进行排序（单纯按照hashCode顺序排序会出现“环路等待”），可能就无法满足要求了，这个时候开发者可以使用银行家算法，所有的锁都按照特定的顺序获取，同样可以防止死锁的发生，该算法在这里就不再赘述了，有兴趣的可以自行了解一下。

         2、超时放弃

         当使用synchronized关键词提供的内置锁时，只要线程没有获得锁，那么就会永远等待下去，然而Lock接口提供了boolean tryLock(long time, TimeUnit unit) throws InterruptedException方法，该方法可以按照固定时长等待锁，因此线程可以在获取锁超时以后，主动释放之前已经获得的所有的锁。通过这种方式，也可以很有效地避免死锁。
         jstack  jconsole

         当一个线程获取了锁之后，是不会被interrupt()方法中断的。

 */

//public class MainClass {

//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //for(int i = 0 ; i<10 ;i++){

        // new TestThread1().start();
        //  new Runnable()

        //匿名内部类实现

//new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("TestThread2");
//                }
//            }).start();

        //lambda允许把函数(有参，无参)作为一个方法的参数（函数作为参数传递进方法中）。
        //不需要声明参数类型，编译器可以统一识别参数值。

//new Thread(()->{
//                System.out.println("TestThread3");
//            }).start();



        // ExecutorService executorService = Executors.newFixedThreadPool(5); --创建多个线程池

//executorService.submit(()->{
//                //Callable
//
//            });


/*
 Future submit = executorService.submit(new a());
            System.out.println(submit.get());
*/


        // }

        //wait notify
//        List good = new ArrayList();
//        Object lock = new Object();
       // MainClass main = new MainClass();

/*WaitCondition wc = new WaitCondition(good,lock);
        NotifyCondition nc = new NotifyCondition(good,lock);*//*

        */
/*new Thread(wc).start();
        new Thread(nc).start();
*//*
      WaitCondition wc = new WaitCondition(good,lock);
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


    */
/**
     * create
     **//*

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

    */
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
     **//*

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
                    */
/*good.add(new String("a"));
                    System.out.println("添加一个a");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*//*

                    if (good.size() == 10) {
                        try {
                            System.out.println("wait()");
                            //this.wait();
                            lock.wait();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    */
/*try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*//*

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
                    */
/*if(good.size() > 0){                //存在漏洞的，如果notify 还是remove线程，那么这里就会一致报错，加了if一定要考虑else 走向
                        good.remove(0);
                    }*//*

                    */
/*if(good.size() == 0) {              // 执行先后顺序 wait()和前置条件 ，多线程必须考虑更细粒度控制
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //lock.notifyAll();
                    }*//*

                    */
/*try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("删除一个a");*//*

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

    */
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
     *//*

    private void testSemaphoreImpl(){
            Semaphore  fullCount = new Semaphore(0);    //不同维度解释同一个东西。可添加信号+可删除信号=总信号 所以这里的fullCount.release()会增加一个信号
            Semaphore  emptyCount = new Semaphore(10);  //控制数量
        */
/**
         *  共享锁和独占结合 控制访问资源操作数  也可以for 线程，对特定资源进行限制
         *  避免信号量和实际size不一致(信号未执行完插入操作 中断性)
         *  控制局部并发 可能大线程进去会有瓶颈，其他处理可以各自处理，但是公共资源访问部分使用信号量限制线程数
         *//*

            Semaphore  isUse = new Semaphore(1);
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
                    */
/**
                     * 等待5s ,10个Thread 同时输出
                     *//*

                    semaphore.acquire();
                    //可能大线程进去会有瓶颈，其他处理可以各自处理，但是公共资源访问部分使用信号量限制线程数
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
                */
/**放到线程的最后，这样才可以表示完成后释放**//*

                countdl.countDown();
            }).start();
        }
        countdl.await();
        System.out.println("this is main" + System.currentTimeMillis());
    }

    */
/**
     * 验证countDownLatch(ThreadCount) 和 总任务数 不一致
     * @throws InterruptedException
     *//*

    private void testCountDownLatch2() throws InterruptedException {

        //其他线程完成的任务

        //主线程在者期间完成的任务

        //等待前置条件完成后执行下面  提高并发
        int count = 10 ;
        CountDownLatch cdl = new CountDownLatch(count);
        //TODO:使用阿里手动创建ThreadPoolExcutor
        //TODO:核心线程大小和最大线程大小设置  https://blog.csdn.net/u011519624/article/details/69263460 + 最大线程数https://www.cnblogs.com/waytobestcoder/p/5323130.html
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //从已有的集合中取。集合是否安全 从别的地方获获取的数据
        // 不参与共享
        ArrayList datas = new ArrayList<String>(Arrays.asList(new String[]{"1","2","3",
                "4","5","6","7","8","9","10"}));
        //换成同步队列
        List synDatas = Collections.synchronizedList(datas);
        System.out.println(synDatas.size());
        try{
            */
/**
             * 从已有的集合中取。集合是否安全
             *//*


            // for (int i = 0; i < count; i++) {
            for (int i = 0; i < datas.size(); i++) {
                //for (int i = 0; i < synDatas.size(); i++) {

                //ExecutorService executorService = Executors.newFixedThreadPool(2);//pool-2-thread-1 二号线程池 一线程
                executorService.submit(()->{
                    System.out.println("this is " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    */
/**如果上面是耗时操作，一定需要完成才可以释放，比如--保存入库--通过返回值--否则容易造成重复录入**//*

                    cdl.countDown();
                });
            }
            cdl.await();
        }finally {
            */
/**
             * 释放
             * 异常计数 vs 抛出
             * 执行顺序
             **//*

            executorService.shutdown();
        }
        System.out.println("--main--");
    }

    */
/***
     * CountDownLatch 实现 “-闭锁-”
     *//*

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
        */
/**------开关-----   配置文件中读取 / spring 读取 / signal 线程 - 这些工具都是基于wait()/notify()**//*

        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        System.out.println("闭锁退出，共耗时" + (end-start));
    }

    */
/**
     * 它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。barrier 在释放等待线程后可以重用
     * 可以配合countDownLatch使用，二者从不同角度理解
     *
     * TODO:分组 - 线程/组关系间有依赖关系
     *//*

    private void testCyclicBarrier(){

                ExecutorService service = Executors.newCachedThreadPool();
                //final  CyclicBarrier cb = new CyclicBarrier(3);//创建CyclicBarrier对象并设置3个公共屏障点
                final  CyclicBarrier cb = new CyclicBarrier(3,new Runnable(){
                    @Override
                    public void run() {
                        System.out.println("********我最先执行***********");
                    }
                });
                for(int i=0;i<3;i++){
                    Runnable runnable = new Runnable(){
                        public void run(){
                            try {
                                Thread.sleep((long)(Math.random()*10000));
                                System.out.println("线程" + Thread.currentThread().getName() +
                                        "即将到达集合地点1，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
                                cb.await();//到此如果没有达到公共屏障点，则该线程处于等待状态，如果达到公共屏障点则所有处于等待的线程都继续往下运行

                                Thread.sleep((long)(Math.random()*10000));
                                System.out.println("线程" + Thread.currentThread().getName() +
                                        "即将到达集合地点2，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
                                cb.await();    //这里CyclicBarrier对象又可以重用
                                Thread.sleep((long)(Math.random()*10000));
                                System.out.println("线程" + Thread.currentThread().getName() +
                                        "即将到达集合地点3，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
                                cb.await();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    service.execute(runnable);
                }
                service.shutdown();
            }



    */
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
     *//*

    private void testAtomic(){
        */
/**
         * larmbda表达式使用规则：
         *//*

            //int i = 0 ;
            */
/*new Thread((i)->{
                //i++;

            });*//*

        MainClass.A aa = new MainClass().new A();
        for (int i = 0; i < 1000; i++) {
            */
/**
             * 1.同一对象 成员 共享   当new Thread中创建内部类时，会出现输出都是int=1
             * 2.不同对象 成员 不共享
             * 3.局部变量 - 方法 - 栈调用 - 线程(栈=线程)
             *//*

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
            */
/**
             * 模拟服务器压力，展示线程读写时差
             *//*

            */
/*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*//*

            System.out.println("线程是：" + Thread.currentThread().getName() + "--i值为--" + i.get());
        }
    }

    */
/***
     * concurrentXX
     *//*

    private void testConcurrent1() throws InterruptedException {
        MainClass.B bb = new MainClass().new B("111"); //创建不同对象了-注意
        new Thread(bb).start();
        */
/**
         * 思考这几条线程执行先后
         *//*

        Thread.sleep(2000);//为了顺序执行 ， 这样是不合理的 。 --结果：1---null 1---222  -->
        //去掉：null---222 null---222
        //---所以先要不用sleep并且顺序执行，修改并发容器-----
        bb.setS("222");
        new Thread(bb).start();
    }

    */
/**
     * 变量的引入 构造+set..
     *//*

    class B implements Runnable{
        //HashMap hm = new HashMap(); //由于可能存在两个线程访问 所以结果
        ConcurrentHashMap  hm = new ConcurrentHashMap(); //仍然可以，segment 区段控制  而不是所有的都不同访问容器，保证数据安全即可
        //所以我们测试修改数据值而不是put() -run()->中添加s = "111".使其put不成功
        //ThreadLocal<String> s ;
        String  s ;
        AtomicInteger integer = new AtomicInteger();

        */
/**
         * 提供构造和set
         * @param val
         *//*

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
            */
/**
             * 调整顺序 使得线程间的顺序更加明显
             *//*

            hm.put(s, s+Thread.currentThread().getName());
            System.out.println("s = " + s);

            System.out.println("当前s值是:" + hm.get("111"));
        }



        */
/**
         * 提供set
         *//*

        public void setS(String s) {
            this.s = s;
        }
    }


    */
/**
     * threadLocal
     *//*

    private void testThreadLocal(){
        MainClass.C mc = new MainClass().new C();
        new Thread(mc).start();
        new Thread(mc).start();
    }

    class C implements Runnable{
        //公共的成员  可能是1线程先执行，10 ，2线程在执行，20 .也可能交叉执行.也可能任意值，原因在:j.所以循环的可能是10-20任意值
        int j ;
        */
/**
         * 等价于将变量i / j 定义在 run()中，成员可以在多个方法间共享！
         *//*

        */
/*ThreadLocal<Integer> j = new ThreadLocal<>();//循环次数
        ThreadLocal<Integer> i = new ThreadLocal<>();//添加总值*//*


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


    */
/**
     * 是否这里的集合安全性？上面通过锁来控制(synchronized) 如果使用
     *      问题：1.没有中止条件为什么这里还是停止了？ break - continue  --> 死锁问题
     *            2.为什么自己写的多线程似乎线程间有规律执行？而不是乱序的？
     *      扩展：1.ConcurrentHashMap如何高效实现线程安全  https://blog.csdn.net/sinat_27143551/article/details/80780775
     *
     * 是否可以通过  condition对lock条件  进行统一抽象
     *//*

    */
/**
     为什么这里没有控制住总数？
     *//*

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
        */
/***
         * 3.----Lock
         * ReentrantReadWriteLock和ReentrantLock的一个相同点和不同点，相同的是使用了同一个关键实现AbstractQueuedSynchronizer，
         * 不同的是ReentrantReadWriteLock使用了两个锁分别实现了AQS，而且WriteLock和ReentrantLock一样，使用了独占锁。
         * 而ReadLock和Semaphore一样，使用了共享锁。
         * 参考：https://juejin.im/post/5b9df6015188255c8f06923a
         *
         * condition 根据不同的条件对wait / notify 进行 个性化 通信
         *//*

        ReentrantLock lock = new ReentrantLock();
        //扩展
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        @Override
        //1.----public synchronized void run() {
        public  void run() {
           */
/* try {
                //Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*//*

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

    */
/*compareAndSwapObject，compareAndSwapInt和compareAndSwapLong，再看AtomicBoolean源码，发现其是先把Boolean转换成整型，再使用compareAndSwapInt进行CAS，
    所以原子更新double也可以用类似的思路来实现。*//*

    class temp {

        public static void main(String[] args) {
            new temp().testAtomicArray();
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
    */
/***原子更新基本类型的AtomicInteger，只能更新一个变量，如果要原子的更新多个变量，就需要使用这个原子更新引用类型提供的类**//*

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

    */
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
     *//*

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


    */
