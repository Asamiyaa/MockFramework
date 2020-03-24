package com.thread.cooperation;

public class Racer4 extends Thread  {
	
	    FireFlag4 fireFlag;

	    public Racer4(FireFlag4 fireFlag) {
	        this.fireFlag = fireFlag;
	    }

	    @Override
	    public void run() {
	        try {
	        	//***这里的wait()是fireFlag4对象
	            this.fireFlag.waitForFire();
	            System.out.println("start run "
	                    + Thread.currentThread().getName());
	        } catch (InterruptedException e) {
	        }
	    }
	}

