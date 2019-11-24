package thread.visiable;

/**
 * 在这个程序中，有一个共享的boolean变量shutdown，初始为false，HelloThread在shutdown不为true的情况下一直死循环，当shutdown为true时退出并输出"exit hello"，
 * main线程启动HelloThread后睡了一会，然后设置shutdown为true，最后输出"exit main"。
         期望的结果是两个线程都退出，但实际执行，很可能会发现HelloThread永远都不会退出，也就是说，在HelloThread执行流看来，shutdown永远为false，即使main线程已经更改为了true。
           原因:一是修改没有及时同步到内存，二是另一个线程根本就没从内存读。
 * @author ywj
 */
public class Visiable {
    private static boolean shutdown = false;
    
    static class HelloThread extends Thread {
        @Override
        public void run() {
            while(!shutdown){
                // do nothing
            }
            System.out.println("exit hello");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new HelloThread().start();
        Thread.sleep(1000);
        shutdown = true;
        System.out.println("exit main");
    }
}