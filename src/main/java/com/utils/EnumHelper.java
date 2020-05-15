package com.utils;

import com.core.constant.BaseEnum;

/**
 * @author YangWenjun
 * @date 2019/11/8 10:56
 * @project MockFramework
 * @title: EnumHelper
 * @description:
 *          1.当入参 / 出参 / 内部逻辑类型间有关系 / 或者限制上下限 / 时，不要简单的使用object 而是使用T K V ..
 *                  1.容器  集合
 *                  2.Class<T>   Enum<T>
 *
 *          2.先思考单个实现，在这基础上将‘ 类 ’向上抽象 。‘ 逻辑 ’也向上抽象 。 所有的枚举都有code和msg
 *              //泛型   --抽象某个公共规则   "返回该数组的中间对象"
                public  static <T> T getMiddle(T... t){
                return  t[t.length/2];
                }
 *          3.重载
 *          4.既然是 T / object 那么哪些可以做泛型，哪些不能以及如何将希望类型进行绑定
 *             (T)o  - (T[])o - Class/ClassString/obj
 *             Class只能获得newInstance 以及周边
 *             ClassString 注意是string类型，注意哪里使用String不合适 区分开：Class.forName("xx")这是获得对象  可以得到
 *             obj  当前对象 o.getClass()
 *
 *             */
public class EnumHelper {
/*
    public  static EnumHelper enumHelper ;

    static {
        enumHelper = new EnumHelper();
    }*/
    private EnumHelper() {
    }

    public static <T extends Enum<?> & BaseEnum> String  getMsgByCode(Class<T> enumClass , int code) throws IllegalAccessException, InstantiationException {
        //这里的比较和当前对象是没有关系的，因为是常量，没有特性，所以直接newInstance而没有传入o -->enumClass.cast(o);
        /*T t = enumClass.newInstance();
        t.getCode();  -- 因为这里限制了接口 所以具有特性  */
        //枚举是特殊的，因为枚举中有对象
        T[] enumConst = enumClass.getEnumConstants();  //反射中的  boolean isEnum()当且仅当该类在源代码中被声明为枚举时才返回true。
        for (T t : enumConst) {
            if(t.getCode() ==  code){
                return t.getMsg();
            }
        }
        return null ;
    }


}
