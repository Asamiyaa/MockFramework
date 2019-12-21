package thread.demo3;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author YangWenjun
 * @date 2019/7/16 16:18
 * @project BaseJava
 * @title: VolatileVSsynchronized
 * @description:volatile关键字解析:https://www.cnblogs.com/dolphin0520/p/3920373.html
 */
public class VolatileVSsynchronized {

    //static  int count = 0 ;
    static volatile int count = 0 ; //第四版
    public static void main(String[] args) {

        MyThread  mt = new MyThread(count);
        for (int i = 0 ;i < 10 ;i++){

            /**第一版**/
                                                             //创建了不同的内部类对象，导致获得锁不是一个
           /* Executors.newFixedThreadPool(1).execute(new Runnable() { //这里的excutor用法错误，创建了10个线程池
                @Override
                public synchronized  void run() { //竞争VolatileVSsynchronized.class锁，所有的线程。不管是否同一线程池 --错误
                    try {

                        Thread.sleep(3000); //时间加大异常越来越明显
                        System.out.println(count++);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });*/
            /**第二版**/
            Executors.newFixedThreadPool(5).execute(mt);//不要瞎new内部类导致锁的对象不同
        }
       // System.out.println(count);
    }

}

//内部类

class MyThread implements Runnable{
    //private int count ;
    //private volatile int count ; //不保证原子性
    private AtomicInteger count ;
    public MyThread(int count){
        this.count = new AtomicInteger(count);
    }

    public MyThread() {

    }
    /**第二版**/
    /*@Override
    public synchronized void run() {
        try {
            Thread.sleep(3000); //时间加大异常越来越明显
            System.out.println(count++);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/

    /**第三版**/
    /*@Override
    public  void run() {
        try {

            Thread.sleep(3000);
            synchronized ("a"){                 //缩小粒度 速度快了许多
                System.out.println(count++);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/
    /**第四版**/
    @Override
    public  void run() {
        try {

            Thread.sleep(3000);
            //System.out.println(count++);       //volatile 使用关键字 有问题 - 这里的是对象myThread中属性的特性所以
            System.out.println(count.getAndIncrement());
            /**
             * 1.1.方法体内部定义的局部变量不共享。
               这是因为方法内部定义的变量是在运行时动态生成的。每个线程都有一个自己的堆栈，用于保存运行时的数据。
               最容易理解的就是递归调用时候，每次的入栈出栈操作。如下，每次调用时，变量a都是在运行时堆栈上保存的，方法结束变量也就释放了。

             1.2 .成员变量需要看变量指向的是否为同一个对象。

             cpu缓存 / 时间片 都会造成不一致
             */

            /**
             * 自增操作不是原子性操作，而且volatile也无法保证对变量的任何操作都是原子性的。 所以单独将成员变量volatile后不正确
             * so 将count++ 改为
             * 那么volatile什么时候单独用？
             *
             * synchronized关键字是防止多个线程同时执行一段代码，那么就会很影响程序执行效率，
             * 而volatile关键字在某些情况下性能要优于synchronized，但是要注意volatile关键字是无法替代synchronized关键字的，
             * 因为volatile关键字无法保证操作的原子性。
             *
             */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
