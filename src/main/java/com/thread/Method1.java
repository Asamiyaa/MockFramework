package com.thread;

public class Method1 extends Thread {
	  /**
	   * 1.Class extends Thread Class and Override  run()  run() contains "method1 vs method2 not care " 
	   */
	int i;
	
	public Method1(){
		
	}
    public Method1(String s){
		//默认调用super() 底层中并未指定该线程名
    	super(s);  //起作用
	}
	@Override
	public void run(){
		//if | for都是结构在方法体内
		
		for(i=0;i<=100;i++){
			System.out.println("i is  " + i);
		}
	}

}
