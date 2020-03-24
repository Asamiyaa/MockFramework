package com.thread.cooperation;

/**
 * @author ywj
 * @date 20180205
 * @see  cooperation包作为对thread中除“资源竞争”外的另个版块进行分析即“协作”。(wait / notify使用)
 *    问题:1.都有哪些场景需要协作？如何实现各种典型的协作场景？
 *           1.生产者/消费者协作模式
             2.同时开始
             3.等待结束
             4.异步结果
             5.集合点
 *        2.wait/notify是什么？如何使用？实现原理是什么？协作的核心是什么？
 *           1.Object类方法，区别于Thread类的 static void  sleep()  及     void join()
 *           2.除了用于"锁的等待队列"，每个对象还有另一个等待队列，表示"条件队列"，该队列用于线程间的协作。[存在另一个干不同事情的线程来notify它][锁机制则是干同样事情的线程之间]
 *           3.调用wait就会把“当前线程”放到“条件队列上并阻塞”，表示当前线程执行不下去了，它需要等待一个条件，“这个条件它自己改变不了，需要其他线程改变”。
 *             当其他线程改变了条件后，应该调用Object的notify方法
 *             
 *             默认情况下的wait哪些条件:i/o完成  、锁释放  、
 *
 */
public class WaitThread0 extends Thread {
    //注意类中不仅仅是方法，同样有属性
	private boolean fire = false ;//尽量使用volatile, 避免读不到内存“已修改的数据”             协作变量
	
	@Override
	public void run(){
		while(!fire){
			synchronized(this){
			try {
				wait();
				System.out.println("fired");
			} catch (InterruptedException e) {
				e.printStackTrace();          //未在wait()和 notify()前写synchronized 则  java.lang.IllegalMonitorStateExcepti
//原因分析:该方法只能在"同步方法或同步块内部调用"。即“当前线程必须持有对象锁”。如果当前线程不是对象所得持有者，该方法抛出一个java.lang.IllegalMonitorStateException 异常”,所以我们现在就可以明确错误的原因了
			}
		}
		}}
	
	public synchronized void fire(){
		this.fire = true ;
		notify();
		System.out.println("123456");
	}
	
	public static void main(String[] args) throws InterruptedException {
		WaitThread0 waitThread = new WaitThread0();//对象创建
		waitThread.start();//线程启动
		Thread.sleep(10000);//让出cpu使得run()线程更有可能获得
		System.out.println("fire");
		waitThread.fire(); //设置属性，并唤醒等待队列线程              这里的true的设置只是伪代码，事实应该是“该线程满足某个条件之后才会设置fire”
		
		/**
		 * 问题2:waitThread.fire();既然run()中占用锁，这里怎么可以调用成功呢?
		 * 我们需要进一步理解wait的内部过程，虽然是在synchronzied方法内，但调用wait时，线程会释放对象锁，
		 *    wait的具体过程是：
                 1.把当前线程放入“条件等待队列”，释放对象锁，阻塞等待，线程状态变为WAITING或TIMED_WAITING
                 2.等待时间到或被其他线程调用notify/notifyAll从条件队列中移除，这时，要重新竞争对象锁
                 3.如果能够获得锁，线程状态变为RUNNABLE，并从wait调用中返回,(栈的调用顺序)
                                                                  否则，该线程加入对象“锁等待队列”，线程状态变为“BLOCKED”，只有在获得锁后才会从wait调用中返回
                                                                    线程从wait调用中返回后，不代表其等待的条件就一定成立了，它需要重新检查其等待的条件，
              notify具体过程:
                                                                    调用notify会把在条件队列中等待的线程唤醒并从队列中移除，但它不会释放对象锁，也就是说，只有在包含notify的synchronzied代码块执行完后，等待的线程才会从wait调用中返回。
                                                                   这就决定了在调用notify()的方法中是否还有其他处理逻辑
		 */
		
		
		//我们在设计多线程协作时，需要想清楚   "协作的共享变量和条件是什么" ，这是协作的核心。 -->Producer1和Consumer1
	}
}








