/**
 * 
 */
package thread;

/**
 * @author ywj
 *
 */
public class ThreadR2 implements Runnable {

	int i;
	@Override
	public void run() {
		for(i=0;i<=100;i++){
			System.out.println("i is  " + i);
		}
	}

}
