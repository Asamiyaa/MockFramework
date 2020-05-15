package com.Reflect;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author YangWenjun
 * @date 2020/5/13 11:22
 * @project MockFramework
 * @title: GenericUtils
 * @description: 通过反射获取泛型实际类型并判断
 *
 *
 *      Type[] getGenericInterfaces()
        返回 Type表示通过由该对象所表示的类或接口直接实现的接口秒。
        Type getGenericSuperclass()
        返回 Type表示此所表示的实体（类，接口，基本类型或void）的直接超类 类 。

        Type API  type 子类 https://blog.csdn.net/fang_qiming/article/details/78155271

        从Field 、method 获取对应泛型

        TODO:泛型工具类

 */
public class GenericUtils<Integer> extends GT2<Integer> implements ABC<String> {

        public static void main(String[] args) {


            /**
             * public class GenericUtils<Integer> ......
             * Exception in thread "main" java.lang.ClassCastException: java.lang.Class cannot be cast to java.lang.reflect.ParameterizedType
             */
            System.out.println(((ParameterizedType)new GenericUtils().getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

            /**
             * GenericUtils<Integer> extends GT2<Integer>
             * 输出：Integer
             * 父类-泛型-实际类型
             */


            /**
             * class GenericUtils<Integer> extends GT2<Integer> implements ABC<String> 接口
             * 输出：class java.lang.String
             */
            System.out.println(((ParameterizedType)new GenericUtils().getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0]);


            /**
             * 获取方法上的泛型
             *      class is class java.util.ArrayList
                    is empty true
             */

            doSomeThing(new ArrayList());




        }

    /**
     * 通过extends super控制范围。并且使用对应api逻辑
     */
        public static <T extends Collection> void doSomeThing(T t){
            /**
             * if判断 cast
             */
            System.out.println("class is "+ t.getClass());
            System.out.println(" is empty "+t.isEmpty());
        }




}
