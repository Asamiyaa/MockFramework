package com.thread.cooperation;

public class MyLatch5 {
	/**
	 * 该类就是在前几个基础上，将“信号”抽象为“类” 并将wait() 和  notify()进行封装。
	 */
	public int count ;
	
	MyLatch5( int count){
		this.count = count ;
	}
	
	//wait()
	public synchronized void await() throws InterruptedException{
		while(count > 0 ){
			wait();//当前对象在wait(),使得调用该方法的对象也在wait() ，这里其实是不关注 “谁在wait()的”
		}//这里就是为了wait() ,将else()对应操作在调用对象中完成。
	}
	
	//notify()
	public synchronized void countDown(){
		count--;
		while(count <= 0 ){//?????为什么到不了-----------------------------------------------------------？？？？？？？？？？？？
			notifyAll();
		}
	}
}
