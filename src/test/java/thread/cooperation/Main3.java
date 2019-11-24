package thread.cooperation;

/**
 * @author ywj
 * @date 20180205
 * @see  同时开始，类似于运动员比赛，在听到比赛开始枪响后同时开始，下面，我们模拟下这个过程，这里，有一个主线程和N个子线程，每个子线程模拟一个运动员，主线程模拟裁判，
 *       1.协作的共享变量是一个"开始信号"。成员变量其实是不准确的,应该是“共享变量”。
 *       2.条件:未fire时，各个运动员wait() , fire时，notify()所有的运动员
 *
 */
public class Main3 {
	 
	
	public static void main(String[] args) {
		//模拟有20个racer
		Racer3 racer3 = new Racer3();
		int i ;
		for(i=0;i<20;i++){
			Thread racer = new Thread(racer3);
			racer.start();
			//此时都处于预备状态
		}
		/**主线程设置fire为true 如何传递呢?
		 *   1.构造方法(对象已经在前面创建保证是同一个对象吗?) 
		 *   2.创建共享的FireFlag对象     
		 *   3.创建对象设置属性 
		 *   4.定义方法并在方法调用内直接notify()*/ 
		
		/*racer3.fire = true ;
	    synchronized(this){
		notifyAll();           不可在静态方法中引用非静态方法。
		}*/
		
		racer3.setFire();     //自己是使用方法4，但是这其实违背了Java面向对象的思想；及每个racer都有fire属性 --> FireFlag4 老马将这种信号抽象为类。
	}

}
