
package thread.cooperation;


public  class Producer2 extends Thread {
    MyBlockingQueue2<String> queue;

    public Producer2(MyBlockingQueue2<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (true) {
                String task = String.valueOf(num);
                queue.put(task);
                System.out.println("produce task " + task);
                num++;
                Thread.sleep((int) (Math.random() * 100));
            }
        } catch (InterruptedException e) {
        }
    }
}