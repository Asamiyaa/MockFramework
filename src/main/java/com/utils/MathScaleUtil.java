package com.utils;

import java.math.BigDecimal;

/**
 *  @author  YangWenjun
 *  @date   2018年10月4日
 *  @description         数字精度计算 
 *                                 1.是什么
 *                                                   1.涉及类：int 默认类型    long 数据库映射类型  手机号 ...  -  double  string  bigDecimal
 *                                                   2.bigDecimal:不可变的、任意精度的有符号十进制数。 BigDecimal 类使用户能完全控制舍入行为。
 *                                                  
 *                                 2.为什么    整形 - 浮点型(float / double)
 *                                                    对于浮点型数据加减乘除操作会造成精度损失
 *                                                    			public class Test{
																		public static void main(String args[]){
																		System.out.println(0.05+0.01);
																		System.out.println(1.0-0.42);
																		System.out.println(4.015*100);
																		System.out.println(123.3/100);
																			}
																};
																		你会看到结果是：
																		0.060000000000000005
																		0.5800000000000001
																		401.49999999999994
																		1.2329999999999999 
 *                                 3.如何做
 *                                 
 *                                 4.对比 原理  改进 
 *                                        注意1:不要用equals方法来比较BigDecimal对象，因为它的equals方法会比较scale，如果scale不一样，它会返回false；例如：
																			 BigDecimal a = new BigDecimal("2.00");
																			 BigDecimal b = new BigDecimal("2.0");
																			 print(a.equals(b)); // false
																			所以你应该使用compareTo()和signum()方法
																			 a.compareTo(b);  // returns (-1 if a < b), (0 if a == b), (1 if a > b)
									            2.使用BigDecimal的字符串构造函数，不要使用double参数的构造函数，否则的话会出现你不想要的结果。
													例如下面的代码分别使用double和String的构造函数，然后使用HALF_EVEN的round方法，但是输出结果不一样：
													     1>将基本类型转化为String   String.valueOf(基本类型)   / Integer.toString(基本类型)
													         将String转化为基本类型   Long.parseLong(string s); /  Long.valueOf(String s )         valueof返回基本类型   parsexxx返回包装器类型  都是静态方法
													     2>将基本类型转化为包装器类型：Long.valueOf(long l ) 
													         将包装器类型转化为基本类型：对象.doubleValue() 返回double值                             对象.doubleValue()
													     3>将String转化为包装器类型：new Long(Stirng s);
													         将包装器类型转化为String：Long对象.toStirng()
													             
													     String是至关重要的。作为中间类可以用来传递，类与类间转化。  其中value是object    if(vlaue != null &&  Double.vlaueOf(value.toStirng()) > an.maxNumner())
													
											    3.BigDecial是immutable的，就像String一样，它的所有操作都会生成一个新的对象，所以
													 amount.add( thisAmount );
													 是错误的；而应该是：
													 amount = amount.add( thisAmount );
											    4.浮点型存储结构 , 取值范围     Integer.MAX_VALUE   9XXX10位    long:19位  double范围
											    5.为什么会丢失精度？浮点转二进制会丢失
											    6.bigDecimal bigInt可以表示任意长度，任意经读的值
											    7.选择优先级：基本类型是为了每个简单的定义都去堆上建立数据，所以基本类型再栈 快，内存小 > 包装器 > bigxxx
 *  @todo  TODO
 */

/**
 * 以下内容是bbsp中数字精度转换方法 
 */
public class MathScaleUtil {
	
	private static final int DEF_DIV_SCALE = 10;
	 
	public static void main(String[] args) {
		  System.out.println(intCal(12,7));
	
	
	}
	/**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale){

        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    
    
	    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
	    /**
	     * 分转元
	     * @param fen 分
	     * @return 元
	     */
	    public static String fenToYuan(String fen){
	        // 第二个参数表示精度,就是保留小数点之后多少位；
	        // 第三个参数表示精确方法,进位和舍位的标志位
	        BigDecimal divide = new BigDecimal(fen).divide(ONE_HUNDRED, 2, 7);
	        return divide.toString();
	    }
	    /**
	     * 元转分
	     * @param yuan 元
	     * @return 分
	     */
	    public static String yuanToFen(String yuan){
	        BigDecimal multiply = new BigDecimal(yuan).multiply(ONE_HUNDRED);
	        return String.valueOf(multiply.intValue());          //为什么不直接转化？？？？？？？？
	    }
	    /**
	     * float - double 
	     */
	  
	    public  static  Double  intCal(int a , int b ){
	    	//  int a = 12 ;
			//  int b = 7 ;
			//  System.out.println(a/b);  //1
			 BigDecimal  bda = new BigDecimal(String.valueOf(a))  ;
			 BigDecimal  bdb = new BigDecimal(String.valueOf(b))  ;  //这样使用的化，参数是不是可以泛型了****** 返回也统一为String而不是去依赖于参数类型
			  
			  //BigDecimal  bda = new BigDecimal(Integer.toString(a))  ;
			  // BigDecimal  bdb = new BigDecimal(Integer.toString(b))  ;
			 // bda.divide(bdb)；
			  //System.out.println(bda.divide(bdb)); //2.4 //对象不可以直接 + / -  /* /  调用对应方法  ---> 但是当使用到12/7时则会报错，因为除不尽精度未指定
			  
		return bda.divide(bdb, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
	    }
	}
/**
 * 详见：bbsp - mathUtil
 * 1.参数为string 返回string
 * 2.精度 / 四舍五入规则 重载调用  两参调三参 --->.... 默认值
 * 3.round
 * 4.一分钱比较
 * 5.先进行舍入还是先计算  中间差值。
 * 
 * */
