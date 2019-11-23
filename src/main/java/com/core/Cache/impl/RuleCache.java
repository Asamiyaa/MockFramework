package com.core.Cache.impl;

import com.core.Cache.ICache;
import com.core.rule.bean.CombinedRuler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author YangWenjun
 * @date 2019/11/12 11:33
 * @project MockFramework
 * @title: RuleCache
 * @description:
 */
@Service
public class RuleCache implements ICache{

    @Autowired
    private RedisTemplate redisTemplate ;

    @Override
    public void synch(CombinedRuler combinedRuler) {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(combinedRuler.getDraftNo(),combinedRuler,1,TimeUnit.MINUTES);
    }

    @Override
    public void refresh() {
        //redis 策略
    }

    /**
     * cache demo
     * @param i
     * @return
     */
    @Cacheable(value = "cache_test2")
    public String cacheFunctionForMemory(int i){
        try {
            long time = 2000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return "success" + i;
    }

    //@Cacheable(value = "cache_test1")
    public String cacheFunctionForRedis(int i){
        try {
            long time = 2000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        String val = "success" + i;

        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(i,val,1, TimeUnit.MINUTES );

        return val;
    }
}
