package com.pattern.l;

/**
 *  觉得100%完美的接口是不存在，只有适合才是最重要。就像类的粒度划分，同样是依赖对 ‘业务’ 深入理解
 *  1.分类
 *      1.对其他系统暴露 (rpc-dubbo参数/rest -前后台) > 对本系统其他模块 > 对同一个模块其他方法 methodPoint.java
 *           1、接口的命名。
             2、请求参数。
             3、支持的协议。
             4、TPS、并发数、响应时长。
             5、数据存储。DB选型、缓存选型。
             6、是否需要依赖于第三方。
             7、接口是否拆分。
             8、接口是否需要幂等。
             9、防刷。
             10、接口限流、降级。
             11、负载均衡器支持。
             12、如何部署。
             13、是否需要服务治理。
             14、是否存在单点。
             15、接口是否资源包、预加载还是内置。
             16、是否需要本地缓存。
             17、是否需要分布式缓存、缓存穿透怎么办。
             18、是否需要白名单。
          restful API
              1.
              2.统一返回结构体

 *      2.取舍
 *      3.类 --- 接口设计
         https://blog.csdn.net/qq_35860138/article/details/88783476
         https://blog.csdn.net/weixin_34414196/article/details/92105613
         https://www.jianshu.com/p/101bfdc28be6
         https://www.cnblogs.com/wangjiming/p/10256546.html
         https://www.jianshu.com/p/5592551c007d
         https://zhuanlan.zhihu.com/p/136203933
         https://zhuanlan.zhihu.com/p/130357540
         https://zhuanlan.zhihu.com/p/144810155  - 接口优化
         https://juejin.im/post/5dfe2e72518825125f39a2de#heading-1 - 代码优化
         https://zhuanlan.zhihu.com/p/139000771
 */


public class interfacePoint {




}
