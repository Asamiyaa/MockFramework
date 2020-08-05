package com.dubbo;

/**
 *
 *   1.基本配置 @enableDubbo @service @refrence regist  protocal  20880 / 7001   API模块
     2.配置-覆盖顺序
         1.启动时检查
         2.超时
         3.重试
                幂等-查询、删除、更新
                轮询策略
         4.多版本 灰度发布
         5.本地存更 userServiceStub  参数校验后采去调用
         6.服务降级
         7.熔断；

      todo:手机chrome dubbo面试题



 *
 *
 *入门
 背景
 需求
 架构
 用法
 快速启动
 依赖
 成熟度
 配置
 XML配置
 属性配置
 API配置
 注解配置
 动态配置中心
 配置加载流程
 自动加载环境变量
 示例
 启动时检查
 集群容错
 负载均衡
 线程模型
 直连提供者
 只订阅
 只注册
 静态服务
 多协议
 多注册中心
 服务分组
 多版本
 分组聚合
 参数验证
 结果缓存
 泛化引用
 泛化实现
 回声测试
 上下文信息
 隐式参数
 Consumer异步调用
 Provider异步执行
 本地调用
 参数回调
 事件通知
 本地存根
 本地伪装
 延迟暴露
 并发控制
 连接控制
 延迟连接
 粘滞连接
 令牌验证
 路由规则
 配置规则
 服务降级
 优雅停机
 主机绑定
 日志适配
 访问日志
 服务容器
 Reference Config 缓存
 分布式事务
 线程栈自动dump
 Netty4
 Kryo和FST序列化
 简化注册中心URL
 API配置参考手册
 schema配置参考手册
 介绍
 dubbo:service
 dubbo:reference
 dubbo:protocol
 dubbo:registry
 dubbo:monitor
 dubbo:application
 dubbo:module
 dubbo:provider
 dubbo:consumer
 dubbo:method
 dubbo:argument
 dubbo:parameter
 dubbo:config-center
 协议参考手册
 介绍
 dubbo://
 rmi://
 hessian://
 http://
 webservice://
 thrift://
 memcached://
 redis://
 rest://
 注册中心参考手册
 介绍
 Multicast 注册中心
 Zookeeper 注册中心
 Nacos 注册中心
 Redis 注册中心
 Simple 注册中心
 元数据中心参考手册
 介绍
 Redis
 Zookeeper
 telnet命令参考手册
 在线运维命令-QOS
 maven插件参考手册
 服务化最佳实践
 推荐用法
 容量规划
 性能测试报告
 测试覆盖率报告
 版本与升级
 2.7.x升级步骤及注意事项
 Erlang 语言
 快速启动
 消费者配置
 提供者配置
 序列化配置
 开发者指南
 源码构建
 框架设计
 扩展点加载
 实现细节
 SPI 扩展实现
 协议扩展
 调用拦截扩展
 引用监听扩展
 暴露监听扩展
 集群扩展
 路由扩展
 负载均衡扩展
 合并结果扩展
 注册中心扩展
 监控中心扩展
 扩展点加载扩展
 动态代理扩展
 编译器扩展
 消息派发扩展
 线程池扩展
 序列化扩展
 网络传输扩展
 信息交换扩展
 组网扩展
 Telnet 命令扩展
 状态检查扩展
 容器扩展
 页面扩展
 缓存扩展
 验证扩展
 日志适配扩展
 配置中心
 公共契约
 编码约定
 设计原则
 魔鬼在细节
 一些设计上的基本常识
 谈谈扩充式扩展与增量式扩展
 配置设计
 设计实现的健壮性
 防痴呆设计
 扩展点重构
 版本管理
 贡献
 检查列表
 坏味道
 技术兼容性测试
 源码导读
 Dubbo SPI
 自适应拓展机制
 服务导出
 服务引入
 服务字典
 服务路由
 集群
 负载均衡
 服务调用过程
 运维管理
 控制台介绍
 服务搜索
 服务治理
 服务测试

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


public class DubboMain {



}
