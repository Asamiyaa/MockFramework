package thread.race;

public class TestMain {
	/**描述线程中竞态条件:当多个线程访问和操作同一个对象时，最终执行结果与执行时序有关，可能正确也可能不正确
	 *          代码:创建1000个线程，每个线程(run()内容)进行1000次循环 并未成员变量+1 并发   ------>导致两个线程同时读到一个变量相同值。造成两次循环增加了1
	 *       解决方案:{获取成员变量是不并发   不全面 应该是}  --> {counter++这个操作不是原子操作  其中包含    取counter的当前值    在当前值基础上加1    将新值重新赋值给counter}
	 *                                        ***** 即:可能两个线程获得同一个counter也有可能+1后其他线程立刻得到该值 *****
	 *                                        *****解决:使用synchronized关键字   | 使用显式锁   | 使用原子变量 
	 *                                        *****    1.synchronized:单独给count++加锁是错误的，外加给成员变量加锁还是不行  -->到底是如何使用呢？
	 */

	 public static void main(String[] args) throws InterruptedException {
	        int num = 1000;
	        Thread[] threads = new Thread[num];          //创建多个线程时用到了     “循环和数组”
	        for (int i = 0; i < num; i++) {
	            threads[i] = new RaceCondition();
	            threads[i].start();
	        }

	        for (int i = 0; i < num; i++) {               //为了不让main线程结束等待所有子线程完成    这里各个子线程之间无需等待    理解join() 并不是“调用”
	            threads[i].join();
	        }

	        System.out.println(new RaceCondition().getCounter());        //第一次:994696   第二次:994805     第三次:993952
	    }
}
