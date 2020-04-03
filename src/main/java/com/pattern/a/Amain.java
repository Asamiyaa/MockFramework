package com.pattern.a;

import com.redis.Cache;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

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
 */


//@Component  启动注入
public class Amain {

    //常量
//    @Value(${"spring.cache.type"})  注入类型
    private String cacheType;
//不注入 因为需要策略模式判断后加载对应实例，注入时这里可能存在多个实现。定义Cache接口就是为了‘给整个项目缓存定义统一的要求，’
    private Cache cacheInstance;

//这里提供获取实例是作为manager对象可以统一方便对外服务。但是这里提供静态方法是有点问题。又不想从spring中取。这样独立的manager形式就不明显了。但是这里还是有spring管理
//    所以提供静态实例化方法，实例化后调用
//    public static CacheManager getInstance(){
//        return Context.getBean("cacheManager");
//    }

//从spring加载中可知，spring ApplicationRunner接口作为springContext完成后的扩展接口，所以在runner实现类中调用该方法，使其初始化
public void initCache(){
    if(cacheInstance == null){
        if(!doInitCacheInstance()){
            return ;
        }
    }
}

    private boolean doInitCacheInstance() {
  //如果支持多个也是可以直接判断，这里是策略模式。直接通过传入名字通过工厂(ioc)定位到实现类。这里不会通过名字if....new ...获取。省略。通过统一规则 直接获取。
//        就是策略模式中传入策略。初始化本地变量，直接调用 。这里通过redis名字+ioc初始化了cacheInstance.其他方法调用即可
        if(!CacheType.REDIS.toString().equalsIgnoreCase(cacheType)){
//            logger.warn("未指定实现");
            return  false ;
        }
//工厂模式new xximpl()...向ioc转变
//        cacheInstance = Context.getBean(cacheType + CACHE_SUFFIX);
        return  true;
    }
 //public void setCache(List<CacheBean cacheBean>);
//    public long getNextId(@NotNull Counter counter){return cacheInstance.getNextId(Counter)}   @NotNull进行调用时参数提示和判断

//manager作为门面肯定是封装了最常用的方法，所以全部底层需要提供获取接口比如jdbcTemplate中query和....接口进行高级开发
    public Object getRawOperation(){
//        return cacheInstance.getRawOperation();
        return null;
    }


}
