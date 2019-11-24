package thread.share;

public class SellTicket implements Runnable{
	
	public int i ;
	public int ticketNum = 100; 
	Long t = (long) 0.0;

	/*这种在for中使用成员变量更本就没起到成员变量作用，随便一个i也可以
	 * @Override
	public void run() {
		*//**
		 * 模拟该总目标为售票100张
		 *//*
	   Long t1 = System.currentTimeMillis();
		for(ticketNum = 100 ; ticketNum >= 0 ;ticketNum --){
		      System.out.println("开始售票");	
		      try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		      System.out.println("结束售票");	
		}
		Long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1 + "完成");
		
	}*/
	
	public void run(){
		if(ticketNum > 0){
		 Long t1 = System.currentTimeMillis();
		 System.out.println(t1);               //线程不是每次执行都是从run(),而是进入run()之后   “沿着代码向下执行”  公共的约束就是成员变量2
			for(i = ticketNum ; i >= 0 ; i--){
			      System.out.println("开始售票");	
			      try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			        ticketNum-- ; 
			      System.out.println("结束售票");	
			}
			Long t2 = System.currentTimeMillis();
			if((t2-t1) > t){
			      t = t2 - t1 ;
			      }
	}
	}
	
	//不是每个线程都打印   所以不再run()中写sysout()
	public Long getTime(){
		return t ;
	}
}
