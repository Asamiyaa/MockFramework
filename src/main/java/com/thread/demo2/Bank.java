package com.thread.demo2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author YangWenjun
 * @date 2019/7/13 9:00
 * @project BaseJava
 * @title: Account
 * @description:
 */
@SuppressWarnings("ALL")
public class Bank   {

    private Account[] accounts ;
    private Lock bankLock = new ReentrantLock();
    private Condition moneyEnoughCondition = bankLock.newCondition();

    public Bank() {

    }

    public Bank(int accNum , String name , Double accMoney) {
        accounts = new Account[accNum];
        for( int i = 0 ; i<accNum ; i++){
           accounts[i] = new Account(name , accMoney);
       }
    }

    public   void transform(int from , int to , Double transMoney) throws InterruptedException {
        bankLock.lock();

        try {
            /*if(accounts[from].getAccMoney().compareTo(transMoney)<0){
                System.out.println("余额不足");
                return ;}*/

            /**
             * 1.线程中可以return吗
             *
             * 通常 ，线程进人临界区 ，却发现在某一条件满足之后它才能执行 。要使用一个条件对
             象来管理那些已经获得了一个锁但是却不能做有用工作的线程
             * 2.引入多线程中条件变量概念。允许存在修改金额的线程，若满足条件，可以继续执行
             *
             * 现在 ，当账户中没有足够的余额时 ，应该做什么呢？ 等待直到另一个线程向账户中注入
             了资金 。但是 ，这一线程刚刚获得了对 bankLock的排它性访问，因此别的线程没有进行存
             款操作的机会 。这就是为什么我们需要条件对象的原因 。

             。 一个锁对象可以有一个或多个相关的条件对象 。 分组 而无需全部notifyAll all也是该组内的。
             */

            while(accounts[from].getAccMoney().compareTo(transMoney)<0){
                    //wait()
                moneyEnoughCondition.await();
                /**
                 * 当前线程现在被阻塞了 ，并放弃了锁 。
                 *
                 * 等待获得锁的线程和调用await方法的线程存在本质上的不同 。一旦一个线程调用awai
                 方法，它进人该条件的等待集 。当锁可用时 ，该线程不能马上解除阻塞 。相反 ，它处于阻塞
                 状态 直到另一个线程调用同一条件上的 signalAll方法时为止 。

                 suf ficientFunds,signalAll (); 不会释放锁 而是继续执行完成再释放。再去调度 可能下次执行也不是自己激活的组。时间片

                 它们再次成为可运行的 ，调度器将再次激活它们 。同时 ，它们将试图重新进人该对象 。一旦
                 锁成为可用的 ，它们中的某个将从 await调用返回，获得该锁并从被阻塞的地方继续执行 。 此时 ，线程应该再次测试该条件 。由于无法确保该条件被满足— —signalAll方法仅仅是
                 通知正在等待的线程 此时有可能已经满足条件 值得再次去检测该条件

                 番告 ：当一个线程拥有某个条件的锁时， 它仅仅可以在该条件上调用 await
                 、signalAll 或
                 signal 方法 。

                 */
            }
            double curMoneyFrom = accounts[from].getAccMoney();
            double dd = curMoneyFrom -= transMoney;
            Thread.sleep(2000);
            accounts[from].setAccMoney(dd);
            double curMoneyTo = accounts[to].getAccMoney();
            curMoneyTo = curMoneyTo + transMoney;
            Thread.sleep(2000);
            accounts[to].setAccMoney(curMoneyTo);

            System.out.println("账户from" + "----" + from + "---" + " to账户" + accounts[from].getAccMoney() + "----" +
                    to + "---" + accounts[to].getAccMoney()+"总额"+getTotalMoney());
            /**
             * 应该何时调用signalAll 呢？ 经验上讲 ，在对象的状态有利于等待线程的方向改变时调用
             gnalAll
             。例如 当一个账户余额发生改变时 等待的线程会应该有机会检查余额 。在例子

             这种模拟可能来自同一个线程，也可能来自其他线程。
             */
            moneyEnoughCondition.signalAll();

        } finally {
            bankLock.unlock();
        }
    }

    public double getTotalMoney(){
        double totalMoney = 0.0;
        for (Account acc :
                accounts) {
            totalMoney += acc.getAccMoney();
        }
        return totalMoney;
    }

    //other method
}
