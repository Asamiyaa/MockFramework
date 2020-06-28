package com.log;

/**
 * @author YangWenjun
 * @date 2020/6/24 10:31
 * @project MockFramework
 * @title: LogTraceIdFilter
 * @description:
 *         TODO:对于分布式传递traceId则是通过dubbo这种rpc rpcContext来完成
 *         1.日志框架本身 坑 重复  -- 极客时间  - slf4j和其他实现  桥接/门面/适配...
 *         2.日志路径、模块、粒度、滚动
 *         3.日志代码写在哪里  工具类 vs getLog vs 切面
 *           https://blog.csdn.net/hjx_1000/article/details/45149651
 *         4.traceId / 多线程
 *              https://www.duyidong.com/2019/12/21/spring-boot-log-trace/  --> TODO:全局参数传递 ****
 *              https://cloud.tencent.com/developer/article/1580354
 *              https://bbs.csdn.net/topics/390821847
 *              https://juejin.im/post/5e8d809d51882573cc56b156
 *              https://www.jianshu.com/p/e0774f965aa3
 *              ThreadLocal变量为什么用static修饰  https://www.jianshu.com/p/ee9e1d0247a6  => threadLocal作为抽取公共传递成员(上下文)的一个实现手段 无锁
 *              java中，创建子类对象时，父类对象会也被一起创建么？https://www.zhihu.com/question/51920553
 *          5.为了传递traceId。判断是由方法调用baseService.log方法还是requestMapping中创建。创建了Global全局
 *            为了区分，添加了渠道。.。所以整个传递从threadLocal - global - rpcContext（dubbo处理）
 *          6.修改删除逻辑，remove .不知道哪里log remove.那么就在初始化先将上一次数据清空。在赋值
 *            --业务流程逻辑修改--
 *          7.实战代码
                1.C:\YangWenjunData\mySrc\MockFramework11\src\LifeAndWorkTodo\photo\log1.png
 *                C:\YangWenjunData\mySrc\MockFramework11\src\LifeAndWorkTodo\photo\log切面...png
 *                https://www.cnblogs.com/penghq/p/11226822.html
 */
public class LogTraceIdFilter {






}
