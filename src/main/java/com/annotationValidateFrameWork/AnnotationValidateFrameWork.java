package com.annotationValidateFrameWork;

import org.apache.commons.lang3.StringUtils;

/**
 * 整合 注解 + 切面 形成aop
 *     TODO:     https://hibernate.org/validator/documentation/getting-started/ 结合 AnnotationUtils.java 结合proceeson
 *     TODO:     对枚举、注解、反射、接口进行再次梳理，如何选择、联通。---> 思考
 *
 *
 * 1.volidate 校验规则 -- hibernate=spring - 切面 - 校验框架
 * 2.
 */
public class AnnotationValidateFrameWork {


    public static void main(String[] args) {

    }

    //3.规则和业务逻辑仍在一起 - 切面（模块）
    //          1.注解
    //          2.切点 -inteceptor

    //2.使用idea进行extra重构


    /*1.简单的校验*/
    public void needVolidate(User user) throws IllegalAccessException {
        beforeValidate(user);
        //doSome
        System.out.println("doSome");
    }

    public void beforeValidate(User user) throws IllegalAccessException {
        if(StringUtils.isEmpty(user.getNaem())){
            throw new IllegalAccessException("name error");
        }
        if(user.getId() != null){
            throw new IllegalAccessException("id error");
        }
    }

}
