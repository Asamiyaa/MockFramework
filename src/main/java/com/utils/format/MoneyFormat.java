package com.utils.format;

import java.text.DecimalFormat;

public class MoneyFormat {
    //只有字符串才有格式  所以返回的也一定是字符串  
	//--> 当对象中需要表示格式的属性为非String，需要创建对应xxxString来单独作为展示属性 
	//但是这又是修改表结构的，所以建立  DTO对象  含有  对象+需要展示的string属性  在前台展示
	public static void main(String[] args) {
		System.out.println(method1(5000000.126d));
		System.out.println(method1(0.126d));
		System.out.println(method1(0));
		System.out.println(method2(Double.valueOf(1234.124)));
		System.out.println(method2(Double.valueOf(1234)));  // 1,234
		System.out.println(method2(null));
		System.out.println(method3(Double.valueOf(1234)));
	}
	
	public static String method1(double d){
	    DecimalFormat  df = new DecimalFormat("#,###.##");  //五舍六入
		return df.format(d) ;
   } 
	//这里只是进行格式控制，应该在scaleUtil中进行精度控制  及 四舍五入
	
	
	public static String method2(Double d){
	    DecimalFormat  df = new DecimalFormat("#,###.##");  //五舍六入
		return d == null ? "0.00" : df.format(d) ;
   } 
	
	public static String method3(Double d){
	    DecimalFormat  df = new DecimalFormat("#,##0.00");  //最常用的
		return d == null ? "0.00" : df.format(d) ;
   } 
	
}