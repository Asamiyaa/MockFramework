package com.redis.impl;

import com.redis.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO:引入策略类
 *       将所有接口综合到cache,在redisCache实现 将底层屏蔽
 */


@Component
public class RedisCache implements Cache{
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private HashOperations hashOperations;


    /**
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;




    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     *
     * @param keys
     */
    public void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 删除Key的集合
     *
     * @param keys
     */
/*    public void deleteKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }*/

    /**
     * 设置key的生命周期
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     *
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

//-------------hash--------------

    @Override
    public Long delete(Object var1, Object... var2) {
        return null;
    }

    @Override
    public Boolean hasKey(Object var1, Object var2) {
        return null;
    }

    @Override
    public Object get(Object var1, Object var2) {
        return null;
    }

    @Override
    public List multiGet(Object var1, Collection var2) {
        return null;
    }

    @Override
    public Long increment(Object var1, Object var2, long var3) {
        return null;
    }

    @Override
    public Double increment(Object var1, Object var2, double var3) {
        return null;
    }

    @Override
    public Set keys(Object var1) {
        return null;
    }

    @Override
    public Long size(Object var1) {
        return null;
    }

    @Override
    public void putAll(Object var1, Map var2) {

    }

    @Override
    public void put(Object var1, Object var2, Object var3) {

    }

    @Override
    public Boolean putIfAbsent(Object var1, Object var2, Object var3) {
        return null;
    }

    @Override
    public List values(Object var1) {
        return null;
    }

    @Override
    public Map entries(Object var1) {
        return null;
    }

    @Override
    public Cursor<Map.Entry> scan(Object var1, ScanOptions var2) {
        return null;
    }

    @Override
    public RedisOperations getOperations() {
        return null;
    }


}