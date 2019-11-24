/**
 * 
 */
package thread;

/**
 * @author ywj
 *
 */
public class ThreadR1 implements Runnable {
	int j ;

   @Override
	public void run() {
		for(j=0;j<=100;j++){
			System.out.println("j is  " + j);
		}
	}

}
