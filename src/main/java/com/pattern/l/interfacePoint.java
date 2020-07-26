package com.pattern.l;

/**
 *  觉得100%完美的接口是不存在，只有适合才是最重要。就像类的粒度划分，同样是依赖对 ‘业务’ 深入理解
 *  1.分类
 *      1.对其他系统暴露 (rpc-dubbo参数/rest -前后台) > 对本系统其他模块 > 对同一个模块其他方法 methodPoint.java
 *           1、接口的命名。
             2、请求参数 - 个数/类型Class..T../是否合并成对象/是否需要多余参数 flag.../参数校验
                限定符/返回值-对象/是否抛出异常/适度过滤信息 分页分组排序
                - 是否使用枚举控制入参种类？参数是否@NotNull
             3、接口是否拆分。
             4、防重、防刷、幂等
                 安全兜底：极客：100例业务开发 - 28-  / 对账文件
             5、安全接口
                 1.黑白名单
                 2.加密、加签、时间戳、合法性  https://zhuanlan.zhihu.com/p/139000771
             6、服务治理
                 dubbo timeout retry  version group 是否存在单点-集群：负载、容错、降级、限流
                极客：100例业务开发 - 05-  /
                 https://blog.csdn.net/YZX2018/article/details/90230395
                 https://blog.csdn.net/tigerJGG/article/details/88707192
             7、TPS、QPS、并发数、响应时长。        -- 压测
             8、数据存储。DB选型、缓存选型。
             9、接口是否资源包、预加载还是内置。
             10、是否需要依赖于第三方。
             11、支持的协议。

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
