package thread.race;

public class TestMain2 {
	/**
	 * TestMain2 + RaceCondition2是为了验证synchronized关键字
	 */	
	public static void main(String[] args) throws InterruptedException {
		
	        int num = 1000;
	        Thread[] threads = new Thread[num] ;                     //数组必须被初始化 
	        RaceCondition2  raceCondition2  = new RaceCondition2();  //如何对比   runnable和thread--> 多个线程操作的是同一个对象        1vs2 1就像是1000个线程对象作用于1000   2就像是1000个线程作用于统一target
	        for (int i = 0; i < num; i++) {
	            threads[i] = new Thread(raceCondition2);
	            threads[i].start();
	        }

	        for (int i = 0; i < num; i++) {               
	            threads[i].join();
	        }

	        //System.out.println(new RaceCondition2().getCounter());  //为什么同样可以获得该值而不是初始值   
	        System.out.println(raceCondition2.getCounter());        
	    }
}
