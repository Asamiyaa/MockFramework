package com.thread.race;

public class RaceCondition2 implements Runnable {
	
	    private static int counter = 0 ;       
        
	    @Override
	    public void run() {
	        for (int i = 0; i < 1000; i++) {
	        	  synchronized(this){ 	
		            counter++;
		            }                        
	        }
	    } 

	   public  int getCounter(){                  
		   return counter ;
	   }
	}

