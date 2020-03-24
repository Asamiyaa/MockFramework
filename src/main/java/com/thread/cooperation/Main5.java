package com.thread.cooperation;

/**
 * @author ywj
 * @date 20180205
 * @see  等待结束    使用join方法让主线程等待子线程结束。或者说“等人到齐了才开饭”
 *       主线程与各个子线程协作的共享变量是一个数，这个数表示“未完成的线程个数”，初始值为子线程个数，主线程等待该值变为0，
 *       而每个子线程结束后都将该值减一，当减为0时调用notifyAll，我们用MyLatch来表示这个协作对象
 */
public class Main5 {
	public static void main(String[] args) throws InterruptedException {
	    int workerNum = 100;
	    MyLatch5 latch = new MyLatch5(workerNum);
	    Worker5[] workers = new Worker5[workerNum];
	    for (int i = 0; i < workerNum; i++) {
	        workers[i] = new Worker5(latch);
	        workers[i].start();
	    }
	    latch.await();

	    System.out.println("collect worker results");
	} 
	
}
