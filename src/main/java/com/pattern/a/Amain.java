package com.pattern.a;

/**
 *
 * 1.源码中接口和抽象类、类多重继承结构
     2.JdbcTemplate不是模板方法 实则是门面模式。session...query等就是实现逻辑接口
     3.源码中Abstarctxxx抽象类，模板方法
     4.当manager 静态create创建返回对象。这样就避免了spring和new.其实也可以spring比如jdbcTemplate.不会像
     工具类那样一个static xx就可以解决，因为manager是个子系统加载要素。RedisTeplate
     5.spring框架的cacheManager接口着重是解决不同的缓存是实现的。我们这里定义自己的门面保存 我们项目中
     对外提供的综合

     --二者有个cacheName - 服务定位模式  vs 反射  https://www.runoob.com/design-pattern/service-locator-pattern.html

     模板方法 abstract...定义默认固定实现

     1.策略 缓存名称-枚举。启动加载时指定缓存方式 redis....
     通过传入的context(strage xx)完成  https://www.runoob.com/design-pattern/strategy-pattern.html
     spring启动从配置文件读取


     1.工厂 vs ioc
     IOC的实现原理—反射与工厂模式:https://blog.csdn.net/fuzhongmin05/article/details/61614873


     1.层设计
     mvc:https://draveness.me/mvx
     https://www.runoob.com/design-pattern/mvc-pattern.html
     今日头条：对外接口封装层....没有什么是加一层解决不了的
     前后端分离 - 职责


     1.初始化资源 方式 对比
     spring
     静态成员、块
     map

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
 *
 *
 *
 *
 */



public class Amain {
}
