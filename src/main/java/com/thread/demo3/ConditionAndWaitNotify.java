package com.thread.demo3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author YangWenjun
 * @date 2019/7/17 11:55
 * @project BaseJava
 * @title: ConditionAndWaitNotify
 * @description:https://www.cnblogs.com/dolphin0520/p/3920385.html  -- 消费者 生产者（加入死循环 加入判断空/满...）
 */
public class ConditionAndWaitNotify {

    /**
     * 1.前面主要是线程间竞争，锁的抢占，同步，现在时   协作 。当然协作时需要 锁的。wait notify..这个阻塞会到‘等待改对象锁队列’‘
     * 2.需要锁的协作方法：wait（释放锁）
     *                     sleep  yeild
     * 3.其实这个问题很简单，由于每个对象都拥有monitor（即锁），所以让当前线程等待某个对象的锁，当然应该通过这个对象来操作了。
     *  而不是用当前线程来操作，因为当前线程可能会等待多个线程的锁，如果通过线程来操作，就非常复杂了。
     *
     * 4.对内部类思考
     */

    public static Object object = new Object();
    private static Lock lock = new ReentrantLock(); //二
    static Condition condition = lock.newCondition();//其特定的名字，说明可以取多组
    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();

        thread1.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.start();
    }

    static class Thread1 extends Thread{
        @Override
        public void run() {
            //版本一
           // synchronized (object) {
            lock.lock();
                try {
                    //object.wait();    //版本二：使用condition 可以为等待资源分组，更灵活高效
                    condition.await();
                } catch (InterruptedException e) {
                }finally {
                    lock.unlock();
                System.out.println("线程"+Thread.currentThread().getName()+"获取到了锁");
            }
        }
    }

    static class Thread2 extends Thread{
        @Override
        public void run() {
           // synchronized (object) {
            lock.lock();
                //object.notify();
            try {
                condition.signalAll();//注意不时nodifyall
                System.out.println("线程"+Thread.currentThread().getName()+"调用了object.notify()");
            } finally {
                lock.unlock();
                System.out.println("线程"+Thread.currentThread().getName()+"释放了锁");
            }
        }
    }
}
