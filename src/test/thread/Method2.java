package thread;

public class Method2 extends Thread{

    int j ;
	
	@Override
	public void run(){
		//if | for都是结构在方法体内
		
		for(j=0;j<=100;j++){
			System.out.println("J is  " + j+"---"+Thread.currentThread());
		}
	}

	public static void main(String[] args) {
		new Method2().start();
		System.out.println("" + Thread.currentThread());
		//new Method2().run();
	}
}
