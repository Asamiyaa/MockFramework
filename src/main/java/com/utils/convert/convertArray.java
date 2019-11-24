package com.utils.convert;


import java.util.ArrayList;
import java.util.List;

public class convertArray {

    public static <T> void  convert( T source , T  destination){ //2.泛型的声明必须使用<T> 3.这里的泛型不都是T 为什么可以接受不同类型的参数
        //check                     //4.可以声明多个泛型变量
        //securecopy
        System.out.println("abc" + source.toString()+destination.toString());
       //return  ;
    }
    //public static <T，E> void  convert1( T source , E  destination){ //5.这里要求的是类型参数，所以这样可能E进来值
    public static <T> void  convert1( T source , Class  desc){  //这样就控制了
        System.out.println("abc" + source.toString()+desc.getName());
        //return  ;
    }

    public static <T> void  convert2( T[] source , Class  desc){   //定义分割符String splitType 胡扯 数组没有分割符
        //source.                     //加入未知类型的状态形式 这种状态如x.class 如 x[] 他们同样还是对象还是Object T
        System.out.println("abc" + desc.getName());
        //return  ;
    }

    public static <T> void  convert3( T[] source , Class  desc) throws Exception{
          // 更具反射创建对应的对象
       // System.out.println(desc.newInstance());
        System.out.println(desc.getName());
        //desc.newInstance();  //Interger就不提供
        System.out.println(desc.getDeclaredConstructor(int.class).newInstance(1));//int.class指的是指定构造器固定参数  //int integer独立不可公用
        System.out.println("abc" );                 //注意有 参数                     //注意这里必须塞入值
        //return  ;
    }

    public static <T> void  convert4( T[] source , Class  desc) throws Exception{
       // System.out.println(desc.getDeclaredConstructor(int.class).newInstance(1));
       //更具反射创建对应的构造器和  --有没有无参构造器 比如String就有 Integer就没有
       // if(desc instanceof  Integer.class){
         if(desc == Integer.class){  //不是使用instatnceof？ 是否可以使用getType？？
        System.out.println("int.class");}

        else
        {System.out.println("else");}
    }
    public static <T>  T[]  convert5( T[] source , Class  desc) throws Exception{
        List  list  =  new ArrayList();
         if(desc == Integer.class){
            System.out.println("int.class");
             System.out.println(desc.getDeclaredConstructor(int.class).newInstance(Integer.parseInt("1122")));
            // List list = null ;空指针
            // List  list  =  new ArrayList();放大域
             for (T t:source) {
                 //desc.getDeclaredConstructor(int.class).newInstance(Integer.parseInt(t.toString())).getClass() 验证类型是否转化
                // ;  这里设置临时变量更复杂不如直接塞入
                 list.add(desc.getDeclaredConstructor(int.class).newInstance(Integer.parseInt(t.toString())));
             }
            // return (T[]) list.toArray();均为分支所以使用最后的return  每修改一节就测试一下
         }
        else
            {
            System.out.println("else");
        }
       // return (T[])new Object();  //为了不报错  注意这里的转换明白泛型和[] .class关系
        return (T[]) list.toArray();
    }


    public static <T>  T[]  convert6( T[] source , Class  desc) throws Exception{
        List  list  =  new ArrayList();

        if(desc == Integer.class){  //不提供无参构造器方法 byte-....Long ----包装器类型均不提供 从String才开始允许有无参构造器的。
            for (T t:source) {                                                          //String作为桥梁
                 list.add(desc.getDeclaredConstructor(int.class).newInstance(Integer.parseInt(t.toString())));
            }
        }else if(false){

        }
        else
        {
            System.out.println("else");
        }
        return (T[]) list.toArray(); //我们假设T[]就是一个String[] 增加判断操作，当然细致考虑特殊地点比如这里的构造方法
    }



    public static void main(String[] args) throws Exception {
        String[] a = new String[]{"1","2","3"};
        Integer[] b = new Integer[]{5,6,7};
        String c = "c";
      //  int d  = 1;
        Integer i = 1 ;
       // convert1( c , i ) ; //  1.String - int 有问题吗？ 不是 方法定义都是Object 两个参数必须类型相同吗？？都是object就不能代表不同值吗？
        //convert1( c , Integer[].class ) ;
        //convert2( a , Integer[].class ) ;
        //convert3( b , Integer.class ) ;  //Caused by: java.lang.NoSuchMethodException: [Ljava.lang.Integer;.<init>()是不是不可以初始化[] 因为他不是一个类型 --错误不是这个原因
                                        //将Integer改为String可以成功convert3( a , String.class ) ; 可以 --》为什么
                                        //因为newInsatance调用无参构造器，而interger没有提供。--coun https://blog.csdn.net/qq_28325291/article/details/79342334
        //所以修改conver3方法中反射的部分
        /**convert3( b , String.class ) ;  --》将int - > interger
         * Error:(43, 9) java: 无法将类 util.convertOfType.convertArray中的方法 convert3应用到给定类型;
         需要: T[],java.lang.Class
         找到: int[],java.lang.Class<java.lang.String>
         原因: 推论变量 T 具有不兼容的限制范围
         等式约束条件: int
         上限: java.lang.Object
         */
        //convert4( b , String.class ) ;
        //System.out.println(convert5(b, Integer.class));
        System.out.println(convert6(b, Integer.class));


    }
}
