package thread.race;

public class RaceCondition extends Thread {
	
	    private static int counter = 0  ;       //1.2这里不能使用volatile关键字，否则每次从内部中读取都是初始值0 ，为什么没有刷新到内存呢?
        
	    @Override
	    public void run() {
	       //   synchronized(this){             //1.2平均990000
	        for (int i = 0; i < 1000; i++) {
	       //	synchronized(this){             //1.2平均 980000       新增synchronized关键字保证  “同一时间”只有“一个线程”获得该“对象的锁[执行机会]而不是代码 ”
	            counter++;
	            }                               //1.2问题出现再main()通过new RaceCondition获得线程，也就是现在RaceCondition是不同线程而不是target,但注意这里的
	                                            //成员变量是线程间共享的。-->改造实现runnable(RaceCondition2)
	                                            //获得锁之后也是当前的线程对象，无法控制多个线程。
	        }
	    

	   public  int getCounter(){                  //为了使主线程获得子线程变量，只能通过getxx()获得          相对于    内部类来说较复杂
		   return counter ;
	   }
	}

