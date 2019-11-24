package thread.collection;

import java.util.Collections;
import java.util.Map;

/**
 * @author ywj
 * @date  20180129
 * @see   该包作为多线程情况下容器处理。容器的同步实现并不代表方法同步
 */

public class SynchronizedCollection {
	//复合操作，比如先检查再更新  
    //如下代码:若多个线程执行了if()操作，均为null,那么此时都会put至容器。
	 class EnhancedMap <K, V> {
	    Map<K, V> map;
	    
	    public EnhancedMap(Map<K,V> map){
	        this.map = Collections.synchronizedMap(map);    //***
	    }
	    
	    public V putIfAbsent(K key, V value){
	         V old = map.get(key);
	         if(old!=null){
	             return old;
	         }
	         map.put(key, value);
	         return null;
	     }
	    
	    public void put(K key, V value){
	        map.put(key, value);
	    }
	}
	 
	 //伪同步
	 //为上面putIfAbsent方法添加synchronize是否起到效果
	 //public synchronized V putIfAbsent(K key, V value){
     /*  public V putIfAbsent(K key, V value){
		      synchronized(map){                           //*********
		        V old = map.get(key);
		         if(old!=null){
		             return old;
		         }
		         map.put(key, value);
		         return null;    
		    }
		}*/	   
	 //问题:而其他方法(如代码中的put方法)使用的是Collections.synchronizedMap返回的对象map，两者是不同的对象。”在putIfAbsent中添加synchronize同步粒度不就是整个方法，即使put方法锁不一致不是也应该无所谓吗?
	 //解答:
	 
	 
	 //迭代
	 //详见http://www.cnblogs.com/swiftma/p/6399548.html


}
