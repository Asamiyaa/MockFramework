package com.redis;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 这里的cache定义最基本的必须的。其他具体比如redisCache extends Cache 扩展接口  ，再去实现
 *
 * 既然打算暴露manager,还需要这么详细的接口吗？直接manager返回Opera...对象、set对象、自行操作。这样就不会大范围缩小redis本身的特性
 * CacheManager如何  策略   缓存实现类呢？
 *
 * @param <H>
 * @param <HK>
 * @param <HV>
 */


public interface Cache<H, HK, HV>  {

    boolean existsKey(String key);
    void renameKey(String oldKey, String newKey);
    boolean renameKeyNotExist(String oldKey, String newKey);
    void deleteKey(String key);
    void deleteKey(String... keys);
//    void deleteKey(Collection<String> keys);
    void expireKey(String key, long time, TimeUnit timeUnit);
    void expireKeyAt(String key, Date date);
    long getKeyExpire(String key, TimeUnit timeUnit);
    void persistKey(String key);

    //---------hash--------
    Long delete(H var1, Object... var2);
    Boolean hasKey(H var1, Object var2);
    @Nullable
    HV get(H var1, Object var2);
    List<HV> multiGet(H var1, Collection<HK> var2);
    Long increment(H var1, HK var2, long var3);
    Double increment(H var1, HK var2, double var3);
    Set<HK> keys(H var1);
    Long size(H var1);
    void putAll(H var1, Map<? extends HK, ? extends HV> var2);
    void put(H var1, HK var2, HV var3);
    Boolean putIfAbsent(H var1, HK var2, HV var3);
    List<HV> values(H var1);
    Map<HK, HV> entries(H var1);
    Cursor<Map.Entry<HK, HV>> scan(H var1, ScanOptions var2);
    RedisOperations<H, ?> getOperations();

    //......
}
