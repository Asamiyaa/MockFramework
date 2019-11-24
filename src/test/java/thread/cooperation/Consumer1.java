package thread.cooperation;

import java.util.List;

public class Consumer1 implements Runnable {
	
	List list ;
	Consumer1(List list ){
		this.list = list ;
	}
	
	@Override
	public void run() {
		//生产者和消费者各用个的"锁"
		while(true){
		  synchronized(this){
				while(list.size() == 0 ){
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
					//list.remove(o); 这里应该是队列而不是“先进先出”,这里的remove是有问题的
				/**
				 * 可能出现
				 * 现在“删除”队列中的对象是:java.lang.Object@135fe0e
                                                                  现在“放进”队列中的对象是:java.lang.Object@135fe0e
                                                                  现在“放进”队列中的对象是:java.lang.Object@46c7e75d
                                                                  现在“放进”队列中的对象是:java.lang.Object@42f86b5d
                                                                  现在“放进”队列中的对象是:java.lang.Object@7faed66
                                                                  现在“放进”队列中的对象是:java.lang.Object@4f3384dc
                                                                  现在“放进”队列中的对象是:java.lang.Object@36b76f68
                                                                  出现该种问题的原因是:remove(0),其实使用queue。poll()才是正确的。
				 */
				    //删除操作只执行了一次  是因为没有while循环
				    Object obj =  list.remove(0);  
				    System.out.println("现在“删除”队列中的对象是:" + obj);
				    notifyAll();
					
			}
		}
	}

}
