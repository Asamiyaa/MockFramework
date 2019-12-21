package thread.cooperation;

public class Racer3 implements Runnable {
  //既然是racer 即线程    这里thread和Runnable应该都可以。 -->Runnable
	
	public boolean fire = false ;
	@Override
	public void run() {
		synchronized(this){
        //判断信号量,如果未点火,则
		while(!fire){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//System.out.println();不应该在“锁”内
	 }System.out.println(Thread.currentThread().getName() + "正在跑");
  } 
	
	public synchronized void setFire(){
		fire = true ;
		notifyAll();
	}
}
