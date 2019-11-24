package thread.deadLock;

/*
 * 死锁:使用synchronized或者其他锁，要注意死锁，所谓死锁就是类似这种现象，比如， 有a, b两个线程，a持有锁A，在等待锁B，而b持有锁B，在等待锁A，a,b陷入了互相等待，最后谁都执行不下去
 *    理解:代码DeadLock中以lockA和lockB为锁   为线程执行条件     只有 获得锁才能执行。所以从main方法看
 */
public class DeadLock{
    private static Object lockA = new Object();
    private static Object lockB = new Object();

    private static void startThreadA() {
        Thread aThread = new Thread() {        //这种在类或者接口(Runnable)后直接写继承或者实现的简化方式就像new String[]{"1","2","3"}
            @Override
            public void run() {
                synchronized (lockA) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    synchronized (lockB) {
                    }
                }
            }
        };
        aThread.start();
    }

    private static void startThreadB() {
        Thread bThread = new Thread() {
            @Override
            public void run() {
                synchronized (lockB) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    synchronized (lockA) {
                    }
                }
            }
        };
        bThread.start();
    }

    public static void main(String[] args) {
        startThreadA();
        startThreadB();
    }
}
