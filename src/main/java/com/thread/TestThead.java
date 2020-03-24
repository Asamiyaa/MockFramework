package com.thread;

public class TestThead {
		
	/**
	 * 定义类线程类后，在main()中创建对象并调用start()处于准备态
	 * @throws InterruptedException 
	 */
	public static void main1(String[] args) throws InterruptedException {
		System.out.println("main() start");
		
		/*为线程命名 
		 *Method1 method1 =  new Method1("小明");
		  method1.start();*/
		
		//System.out.println( method1.getName());//Thread-0 默认
		new Method2().start();
	    
		/*创建线程的两种方式
		 * new Thread(new ThreadR1()).start();
		new Thread(new ThreadR2()).start();*/
		
		/*Thread.sleep(3000);
		System.out.println("sleep");*/
		System.out.println("main() end");
	}
	
	
	
	
}
