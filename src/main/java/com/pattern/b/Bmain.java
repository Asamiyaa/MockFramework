package com.pattern.b;

/**
 *  1.层设计
         mvc:https://draveness.me/mvx
         https://www.runoob.com/design-pattern/mvc-pattern.html
         对外接口封装层(https://www.v2ex.com/amp/t/539766)....没有什么是加一层解决不了的
             - 开放接口层：可直接封装 Service 方法暴露成 RPC 接口；通过 Web 封装成 http 接口；进行网关安全控制、流量控制等。
             - 终端显示层：各个端的模板渲染并执行显示的层。当前主要是 velocity 渲染，JS 渲染，JSP 渲染，移动端展示等。
             - Web 层：主要是对访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等。
             - Service 层：相对具体的业务逻辑服务层。
             - Manager 层：通用业务处理层，它有如下特征：
             <br>1 ） 对第三方平台封装的层，预处理返回结果及转化异常信息；
             <br>2 ） 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理；
             <br>3 ） 与 DAO 层交互，对多个 DAO 的组合复用。
             - DAO 层：数据访问层，与底层 MySQL、Oracle、Hbase 等进行数据交互。
             - 外部接口或第三方平台：包括其它部门 RPC 开放接口，基础平台，其它公司的 HTTP 接口。
             应当如何理解Manager层呢，特别是上文提到的第三点与 DAO 层交互，对多个 DAO 的组合复用？
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
