package com.thread.cooperation;

public class FireFlag4 {

	    private volatile boolean fired = false;

	    public synchronized void waitForFire() throws InterruptedException {
	        while (!fired) {
	            wait();
	        }
	    }

	    public synchronized void fire() {
	        this.fired = true;
	        notifyAll();
	    }
	}


