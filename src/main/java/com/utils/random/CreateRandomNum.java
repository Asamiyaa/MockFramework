package com.utils.random;

import java.util.regex.Pattern;

public class CreateRandomNum {
  /**
   * 1.random
   * 2.secureRandom
   *      http://www.what21.com/sys/view/java_java-secure_1456896065146.html
   *      https://www.jianshu.com/p/1ea4c7cb83f3
   *      
   *  Java 随机数 Random VS SecureRandom  
   *        https://www.jianshu.com/p/2f6acd169202
   */
	public static void main(String[] args) {
		  System.out.println(getRandomNum());  
		  //System.out.println(boolean2Str(null)); null只是作为 “引用类型”
		  System.out.println(str2boolean("0"));
		  System.out.println(str2boolean(null));
		  System.out.println( Double2double(new Double("5")));
		  System.out.println( Double2double(null));    //作为调用 肯定判断
		  System.out.println(Str2Double("1"));
		  System.out.println(Str2Double(null));         //parse() 参数可能后台也有？.xxx()所以也的判断
		 System.out.println(Str2Double("我") );         //必要的参数校验
	}
	  //1.Math.random()
	   public static String getRandomNum (){
	    	return String.valueOf(Math.random());    // Math.random()
	    	
	     }
	     
	  // 2.java.util.Random
	     
	  // 3.java.security.SecureRandom
	   
	   public static String boolean2Str(boolean flag){
		   return flag ? String.valueOf(Boolean.TRUE) :String.valueOf(Boolean.FALSE) ;
	   }
	   
	   public static boolean str2boolean(String  flag){  //注意这里返回的是基本类型而不是包装器类型
		   return flag == "1" ?  true : false  ;
	   }
	   
	   
	   
	   //注意 0 / 1 对应的false / true  数据库存的0/1    常量 LogicConst
	   
	   public static double Double2double(Double d){
		   return  d == null ? 0d : d.doubleValue();      
	   }
	   
	   
	   //注意这里的0d   判断是否为null
	   
	   public static double Str2Double(String s){
		    if(s == null )  return 0.0d ;   //0L   long  // 0 int 
		    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
		    if( !pattern.matcher(s).matches())  throw new IllegalArgumentException() ;    //java.lang.NumberFormatException: For input string: "我"虽然没有这句控制也会在实际使用中抛异常。但是手动抛出，也可以使客户端代码进行控制处理
		    return   Double.parseDouble(s);     
	   }
	   
	   //注意i对s值判断 即使这里没有明显的NullPointException 
	   //null可以通过正则
}
