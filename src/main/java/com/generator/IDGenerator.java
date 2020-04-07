package com.generator;

/**

 * 1.全局唯一生成规则  服务器重启继续排、读库性能？多台服务器公用 redis?  大数据并发环境下呢？

     1.流水号(或各种系统序列号)生成问题？https://www.zhihu.com/question/35674057
     如果只要求不重复的话，就使用uuid的方式, 时间+机器+应用ID 。
     就不存在并发问题，而且可以在‘ 客户端直接生产 ’。
     如果需要特殊规则，比如+1递增（并发-多个用户/线程访问同一个公共数据/数据库/成员 导致不安全）。
     就需要一个‘ 独立的应用服务 ’在来生成。可以有这些方式：
     1.利用数据库，自增字段。oracle ,mysql 都有。前端使用web应用封装一下。
     2.自己写代码也简单。java atom 变量可以用。不过要考虑持久化和备份问题。
     3.zookeeper node ID,不过效率比较低

     2.https://juejin.im/entry/5aa214e551882555872321d9：通过时间戳+机器码+随机数
     SimpleDateFormat线程不安全及解决办法：https://blog.csdn.net/csdn_ds/article/details/72984646

     3.支持高并发，可配置，效率高的流水号生成器：https://blog.csdn.net/huangwenyi1010/article/details/51476515

     --综合结论实现
     1.redis实现自增部分 https://xli1224.github.io/2018/03/10/global-id-generation/
 */

public class IDGenerator {
}
