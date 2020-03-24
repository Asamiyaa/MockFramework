package com.thread.cooperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ywj
 * @date 20180205
 * 问题:1.线程有的还未添加进去就remove() 实质已经添加了只是sysout会慢      -->老马那个也会出现这种情况
 *     2.自定义的consumer会在producer生产完之后一直处于wait(),如何停止
 *     3.可能因为remove(0)指定删除对象，而数据不是在0位置 ,只能使用queue中的poll()
 */
public class Main1 {
    
	public static void main(String[] args) {
		//传入对应的队列
		int limit = 5 ;
		List list = new ArrayList(limit);
		
		Producer1 pro1 = new  Producer1(list, limit);
		Consumer1 con1 = new  Consumer1(list);
		
		Thread pro = new Thread(pro1);
		Thread con = new Thread(con1);
		pro.start();
		con.start();
		
		//对象也是共享的 但是对象的创建应该有生产者来完成
		/*int i  ;
		for(i=0 ; i<=20 ; i++){
		Object obj = new Object();
		}*/
	}
}
