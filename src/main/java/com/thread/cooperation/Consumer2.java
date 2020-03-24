package com.thread.cooperation;

public class Consumer2 extends Thread {
    MyBlockingQueue2<String> queue;

    public Consumer2(MyBlockingQueue2<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String task = queue.take();
                System.out.println("handle task " + task);
                Thread.sleep((int)(Math.random()*100));
            }
        } catch (InterruptedException e) {
        }
    }
}