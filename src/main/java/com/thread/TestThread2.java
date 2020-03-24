package com.thread;

import java.util.concurrent.*;

public class TestThread2 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// Thread静态方法
		System.out.println(Thread.MAX_PRIORITY); // 10
		System.out.println(Thread.MIN_PRIORITY); // 1
		System.out.println(Thread.NORM_PRIORITY); // 5 分配给线程的默认优先级。
		System.out.println(Thread.activeCount()); // 1
		System.out.println(Thread.currentThread()); // Thread[main,5,main]
		// 返回对当前正在执行的线程对象的引用
		// Thread.dumpStack();
		System.out.println(Thread.getAllStackTraces()); // {Thread[Finalizer,8,system]=[Ljava.lang.
		// StackTraceElement;@5c647e05, Thread[Attach
		// Listener,5,system]=[Ljava.lang.StackTraceElement;@33909752,
		// Thread[Signal
		// Dispatcher,9,system]=[Ljava.lang.StackTraceElement;@55f96302,
		// Thread[Reference Handler,10,
		// system]=[Ljava.lang.StackTraceElement;@3d4eac69,
		// Thread[main,5,main]=[Ljava.lang.StackTraceElement;@42a57993}
		// 从输出可知除了main方法以外，Finalezer|Attach Listener|Signal Dispatcher|Reference
		// Handler均为系统级别
		//System.out.println(Thread.holdsLock(new TestArray())); // false
																// 当且仅当当前线程在指定的对象上保持监视器锁时，才返回
																// true。
		System.out.println(Thread.interrupted()); // false 测试当前线程是否已经中断。
		Thread.sleep(1); // 会有InterruptedException抛出
		System.out.println("sleep=========>");
		Thread.yield();
		System.out.println("yield=========>");
		System.out.println("=======================================================================");

		// 测试时循环是发生在|创建|start()|命名|run()
		/*
		 * 1.这只是创建十个线程，并不能对比线程执行顺序 for(int i = 0 ;i<10 ; i++){ myThread0
		 * threadMethod1 = new myThread0(); threadMethod1.start(); }
		 */

		// 1.通过命名区分两个线程(足够),//
		myThread0 threadMethod1 = new myThread0("A");
		myThread0 threadMethod2 = new myThread0("B");
		threadMethod2.start();
		threadMethod1.start();

		/*
		 * Thread threadMethod1 = new Thread(new Target("A")); Thread
		 * threadMethod2 = new Thread(new Target("B")); threadMethod2.start();
		 * threadMethod1.start();
		 */

		// 创建Callable对象
		/**
		 * @author ywj Callable 接口类似于 Runnable，两者都是为那些其实例可能被另一个线程执行的类设计的。但是
		 *         Runnable 不会返回结果，并且无法抛出经过检查的异常。
		 */
		Callable ca = new SomeCallable();
		// 创建FutureTask对象
		/**
		 * 可取消的异步计算。利用开始和取消计算的方法、查询计算是否完成的方法和获取计算结果的方法，此类提供了对 Future
		 * 的基本实现。仅在计算完成时才能获取结果；如果计算尚 未完成，则阻塞 get 方法。一旦计算完成，就不能再重新开始或取消计算。 可使用
		 * FutureTask 包装 Callable 或 Runnable 对象。因为 FutureTask 实现了 Runnable，所以可将
		 * FutureTask 提交给 Executor 执行。
		 */
		/*
		 * FutureTask ft = new FutureTask(ca); // System.out.println(ft.get());
		 * 写到这里会造成阻塞 因为创建后必须调用(start()才会有call方法的执行 | run()不是构造方法) Thread t = new
		 * Thread(ft); t.start();//必须才会启动线程调用 System.out.println(ft.get());
		 * //Callableyyywwwjjj System.out.println(ft.isDone()); // true
		 * 
		 */
		int taskSize = 5;
		/**
		 * Executors.newFixedThreadPool(int nThreads)
		 * newCachedThreadPool(ThreadFactory threadFactory)
		 */
		ExecutorService pool = Executors.newCachedThreadPool(); // 管理了new Thread
		// 执行任务并获取Future对象 相对于FutureTask来说结合了start方法
		Callable ca1 = new SomeCallable();
		Future f = pool.submit(ca1);
		System.out.println(f.get()); // Callableyyywwwjjj
		System.out.println(f.isDone()); // true
		// ===============如果不停止线程池，运行一直执行
		pool.shutdown();

	}
}
// 方法4

// 方法3

class SomeCallable implements Callable { // 返回结果并且可能抛出异常的任务。
	@Override
	public Object call() throws Exception {
		System.out.println("abcdefg");
		Thread.yield();
		String s = "Callableyyywwwjjj";
		return s;
	}

}

// 方法2
class Target implements Runnable {

	private String name;

	public Target(String name) {

		this.name = name;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			System.out.println(name + i + "正在运行");
		}

	}
}

// 为了方便测试，将Thread的其他相关都放在该类中 ， Thread的创建
// 方法1
class myThread0 extends Thread {
	// 3.需要变量来引入name,才可以在print时进行对比
	private String name;

	// 2.缺省构造器是继承自父类 非缺省重写
	public myThread0(String name) {
		// 4.内外联系 赋值方式
		this.name = name;
	}

	@Override
	public void run() {
		// 5.由于run中方法较简单，程序的执行顺序一定是先执行threadMethod1.start()再执行2，所以不容易看出
		// System.out.println(name +"1");--->重写run()时嵌入循环来延长执行时间来认证时间片用完再抢夺的原理
		for (int i = 0; i < 10; i++) {
			System.out.println(name + i + "正在运行"); // 结论：每次执行结果是不同的，次数越多越明显，少的话时间片可能一次执行完。
		}

	}

}
