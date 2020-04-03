package com.pattern.b;

/**
 *  1.层设计
         mvc:https://draveness.me/mvx
         https://www.runoob.com/design-pattern/mvc-pattern.html
         今日头条：对外接口封装层....没有什么是加一层解决不了的
         前后端分离 - 职责

    2.TODO:是否需要base类  - 接口是模块的。当需要从不同维度扩展功能时，定义接口扩展。base、继承是从实现类将公共逻辑抽到基类
            好处：统一整洁复用   便于统一逻辑修改（统一异常、统一校验、统一返回、分页逻辑... baseController\baseservice。。。）
            坏处：单继承  造成类臃肿  抽象不合理造成其他子类需要实例化无用的父类部分

 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class Bmain {
}
