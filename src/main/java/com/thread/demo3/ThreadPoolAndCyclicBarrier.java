package com.thread.demo3;

import java.util.concurrent.*;

class ThreadPoolAndCyclicBarrier {

    /**
     * 所有的数据通过线程到达栅栏 ，这里的线程可以new 也可以池
     * 无需引入6 ，这里理解有问题，等待的是线程数，而是不是数据数量 那么如何解决线程池问题
     * @param args
     */
    static volatile  CountDownLatch countDownLatch = new CountDownLatch(5);//成员变量每个方法共享 --> 线程共享 volatile

    public static void main(String[] args) throws InterruptedException {
        int N = 5; //保证和线程池一样吗  corepool 来了 之后放进去 也就是线程  这里并没有考虑队列，非核心线程。 *****等待的是线程数而不是数据量数
        //所以这里的代码会和线程池core/队列长度有关... 这个数也是for个数。
        //如果一股脑塞入线程池，是不是不考虑这些协同操作呢。
        /*CountDownLatch countDownLatch = new CountDownLatch(10);*/
       // CyclicBarrier barrier  = new CyclicBarrier(N);
        CyclicBarrier barrier  = new CyclicBarrier(N, new Runnable() { //2.满足栅栏后回调操作
            @Override
            public void run() {
                System.out.println("所有的都子线程完成后，主线程做的事情...");
                //5.引入线程池后，没有关闭线程池，这里没有执行 - 问题
                //不能再每个线程后取执行关闭，应该记录数据记录数，满足时关闭。引入6
            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i=0;i<N;i++)
            //new Writer(barrier).start();        //4.引入线程池 是否满足需要呢
        {
            //Future<Object> submit = (Future<Object>) Executors.newFixedThreadPool(5).submit(new Writer(barrier));
            // pool-4-thread-1 线程池放到循环内，导致创建太多线程池，而不是线程
            //executorService.execute(new Writer(barrier,countDownLatch)); 7 不引入传参，因为是变化的
            executorService.execute(new Writer(barrier));
        }
        System.out.println(countDownLatch.getCount());//作为传参进入后，每个线程拿到的都是改对象没有共有
        countDownLatch.await();//等待执行完成减为0
        System.out.println(countDownLatch.getCount());
        //6.关闭线程池，这里应该栅栏的回调函数
        executorService.shutdown();

        System.out.println("main thread 执行，引入CountDownLatch后保证主线程后执行"); //1.栅栏不会阻塞主线程 ，只是子线程间通信且满足栅栏条件
    }

    //static class Writer extends Thread{
    static class Writer implements Runnable{
        private CyclicBarrier cyclicBarrier;
        private CountDownLatch countDownLatch;
       // public Writer(CyclicBarrier cyclicBarrier ,CountDownLatch count) {
        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
           // this.countDownLatch =count ;
        }

        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }
            System.out.println("所有的都子线程完成后，子线程各自做的事情...");//3.所有的都子线程完成后，子线程做的事情
            countDownLatch.countDown();
        }
    }
}