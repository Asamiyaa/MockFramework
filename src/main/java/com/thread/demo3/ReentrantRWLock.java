package com.thread.demo3;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author YangWenjun
 * @date 2019/7/17 10:27
 * @project BaseJava
 * @title: ReentrantRWLock
 * @description:
 */
public class ReentrantRWLock {
    /**
     * lock vs synchronized 前者功能更强大  https://www.cnblogs.com/dolphin0520/p/3923167.html
     * condition 就会涉及到wait /notify --> sleep / yeild ...
     */
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    static Mythred1 mt = new Mythred1(lock);
    public static void main(String[] args) {
        for(int i = 0 ; i < 2 ; i++){
            new Thread(mt).start();
        }
    }
    }

class Mythred1 implements  Runnable {
    private ReentrantReadWriteLock lock ;
    public Mythred1(ReentrantReadWriteLock lock ){
        this.lock = lock ;
    }

    //@Override

    public void run() {         //版本二  添加控制保证一个线程完成另一个在做
    //ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); //放到成员变量中 否则会报错
    //public  void run() {

        /**
         * 只要没有 Writer 线程，读取锁可以由多个 Reader 线程同时保持。也就说说，写锁是独占的，读锁是共享的。
         *如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁。
         　　如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁。

         读锁和没加锁的区别？读锁和写锁到底是不是同一个锁？
                1.两个线程执行的代码片段要实现同步互斥的效果，它们必须用同一个Lock对象。
                2. "读-读" 不互斥  "读-写" 互斥  "写-写" 互斥 他们任然使用同一个锁对象。同一个对象有写入有读取。不同线程。
                  在锁上更加明确，精细。更加的提高性能。来自不同方法 ，缓存写入和读取，显式锁

            因为在真实的业务场景中，一份数据，读取数据的操作次数通常高于写入数据的操作，而线程与线程间的读读操作是不涉及到线程安全的问题，
            没有必要加入互斥锁，只要在读-写，写-写期间上锁就行了。

         https://blog.csdn.net/training2007/article/details/78837096

         */
        //lock.readLock().lock();
        lock.writeLock().lock();

        try {
            for (int j = 0; j < 10; j++) {       //版本三  区分读写锁
                try {
                   // Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName()+"--"+j);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
                lock.writeLock().unlock();
        }
    }
}
