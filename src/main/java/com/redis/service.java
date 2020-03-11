package com.redis;


import com.core.rule.bean.dataObj.DraftDo;
import com.core.rule.dao.DraftDoMapper;
import com.core.rule.dao.RuleDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class service {

    @Autowired
    private DraftDoMapper draftDoMapper;


    @Cacheable(value = "testRedis" /*, key"#id" */)  //有也是
    public DraftDo testRedis(long id){ //默认key是什么/  "testRedis::50044"  有参数的   值：json串
        DraftDo draftDo  = draftDoMapper.selectByPrimaryKey(id);
        System.out.println(draftDo);
        return draftDo;
    }

    @Cacheable(value = "testRedis" ) //"testRedis::SimpleKey []"  没有参数
    public DraftDo testRedis2(){
        DraftDo draftDo  = draftDoMapper.selectByPrimaryKey(50043);
        System.out.println(draftDo);
        return draftDo;
    }

    @Cacheable(value = "testRedis", keyGenerator="cacheKeyGenerator" )
    //"testRedis::service.testRedis3:0"  -- 过期时间 .....  通过 cacheManager - expire /cacheResolver  同步异步
    public DraftDo testRedis3(){
        DraftDo draftDo  = draftDoMapper.selectByPrimaryKey(50043);
        System.out.println(draftDo);
        return draftDo;
    }

//    @CachePut 的作用
//    主要针对方法配置，能够根据方法的请求参数对其结果进行缓存。和 @Cacheable 不同的是，它每次都会触发真实方法的调用，此注解常被用于更新缓存使用。

//    @CacheEvict 的作用
//    主要针对方法配置，能够根据一定的条件对缓存进行清空

    //TODO：如何在spring中使用redis其他高级特性  - HashOperations 以及RedisUse中的高级 -- 不能覆盖成redisTemplate,是否有必要
    //        redisTemplate.executePipelined()...



}
