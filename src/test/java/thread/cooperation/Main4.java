package thread.cooperation;

/**
 * @author 马俊昌
 * 对FireFlag进行了进一步封装，更接近于现实和扩展
 */
public class Main4 {
	 
	public static void main(String[] args) throws InterruptedException {
	    int num = 10;
	    FireFlag4 fireFlag = new FireFlag4();
	    Thread[] racers = new Thread[num];
	    for (int i = 0; i < num; i++) {
	        racers[i] = new Racer4(fireFlag);
	        racers[i].start();
	    }
	    Thread.sleep(1000);
	    fireFlag.fire();
	}
}
