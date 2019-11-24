package thread.cooperation;

import java.util.List;

/**
 * @author ywj
 * @date 20180205
 * @see 这是一种常见的协作模式，
 *             1.生产者线程和消费者线程通过  "共享队列" 进行协作，生产者将数据或任务放到队列上，而消费者从队列上取数据或任务，
 *             2.如果队列长度有限，在队列满的时候，生产者需要等待，而在队列为空的时候，消费者需要等待。[极限问题]
 */

public class Producer1 implements Runnable{
	//共享队列   -->如何达到共享呢?  new Thread(target) 但是这里是两个类呀。不向原来多个thread一样可以同时访问同一个“成员变量”-->外界传入队列对象(同一个)
	List list ;
	int limit ;
	int i ;
	Producer1(List list , int limit ){
		this.list = list ;
		this.limit = limit ;
	}

	@Override
	public void run() {
   while(true){
    //队列满就wait()
	  synchronized(this){
		while(list.size() == limit ){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	//否则就一直放  -->为了这里传入外界的e对象，所以包装add()
	//list.add(e);
	//将循环放到可以判断size()的位置。
	//for(i=0 ; i<=20 ; i++){
			Object obj = new Object();
			list.add(obj);                   
			//现在放进队列中的对象是:java.lang.Object@4800002d放進去之後沒有喚醒消費者，所以喚醒所有.而不仅仅是当前线程{这里可能出现其他线程的wait()情况的}
			System.out.println("现在“放进”队列中的对象是:" + obj);
			notifyAll();
			}
		}
	}
}
