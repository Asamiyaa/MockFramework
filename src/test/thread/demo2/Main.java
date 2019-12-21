package thread.demo2;

/**
 * @author YangWenjun
 * @date 2019/7/13 9:02
 * @project BaseJava
 * @title: Main
 * @description:
 */
public class Main {

    public static void main(String[] args) {
        //Bank bank = new Bank(100,"test", (double) 1000);
        for(int i = 0 ; i<100; i++) {

            /*new BankThread().transformThread(bank,i ,
                    (int)(Math.random()*5) ,(int)(Math.random()*1000));*/

            /**
             * 注意每一个 Bank对象有自己的ReentrantLock对象 。如果两个线程试图访问同一个
             Bank对象 ，那么锁以串行方式提供服务 。但是 ， 如果两个线程访问不同的 Bank对象 ， 每
             一个线程得到不同的锁对象，两个线程都不会发生阻塞 。本该如此 ，因为线程在操纵不同的
             Bank实例的时候 ，线程之间不会相互影响 。
             */
            new BankThread().transformThread(new Bank(100,"test",(double)1000),i , //执行速度加快没有走锁。不同对象。
                    (int)(Math.random()*5) ,(int)(Math.random()*1000));

        }
    }
}
