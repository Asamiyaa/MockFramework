package thread.cooperation;


public class Worker5 extends Thread {
		//线程中有对该“信号量的引用”
	    MyLatch5 latch;

	    public Worker5(MyLatch5 latch) {
	        this.latch = latch;
	    }

	    @Override
	    public void run() {
	        try {
	            // simulate working on task    相当重要的。否则出现了  非串行现象。
	           Thread.sleep((int) (Math.random() * 1000));

	            this.latch.countDown();
	        } catch (Exception e) {
	        }
	    }
}
