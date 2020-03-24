package com.thread.share;

public class MainTest {
	
	/**
	 *描述线程中的共享内存   哪些资源是可以在线程间共享的。如对象，成员变量 ，方法体
	 *模拟真实的问题
	 *    卖100张车票   假设顾客无数     完成售票操作需要1秒   sleep(1000) 
	 *    
	 *  1.为了充分避免单继承   使用implements Runnable 接口
	 *  2.理解线程模型的多个口其实是自定义的:比如一个大厅口，多个人售票    --> 多个大厅口，多个人售票    整个大厅就是缓冲池 (考虑进出) 
	 */
	public static void main(String[] args)  {
	  
		//Thread t = new Thread(new SellTicket());      //一人售票       单线程用时10162毫秒完成 
		
		//Thread t = new Thread(new SellTicket());      //三人售票        多线程每个用时都是10s多，问题:设计相当于三个人各卖票100张  --> 指定成员变量
		//Thread t1 = new Thread(new SellTicket());     //                            Thread中填入的runnable其实是target
		//Thread t2 = new Thread(new SellTicket());    
		
		SellTicket sellTicket  = new SellTicket();
		Thread t = new Thread(sellTicket);              //每个线程使用3417 3423 3510   问题:当最后1张还未执行完(未到达ticketNum--)，但另一个线程童谣到达
		Thread t1 = new Thread(sellTicket);             //其实没有必要知道每个线程执行时间，我们只需要知道最终完成时间。所以比较各个线程执行完的时间
		Thread t2= new Thread(sellTicket);
		t.start();
		t1.start();
		t2.start();
		//使主线程等待子线程完成    是t1等待t0..还是说主线程        ---> 结果是  t t1 t2之间没有关系，只有主线程等待这些线程完成
		try {
			t.join();
			t1.join();
			t2.join();
		} catch (InterruptedException e) {}
		System.out.println(sellTicket.getTime());        //3506
	}
}
