package com.redis;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import redis.clients.jedis.util.Hashing;


import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * 1.key重复 且无意义
 * 2.原子类型的数组，直接作为key使用也是不会生效的
 *
 *
        采用此方式后可以解决：多参数、原子类型数组、方法名识别 等问题


 从库角度拼接key
     public static String getKeyWithColumn(String tableName,String majorKey,String majorKeyValue,String column){
         StringBuffer buffer = new StringBuffer();
         buffer.append(tableName).append(":");
         buffer.append(majorKey).append(":");
         buffer.append(majorKeyValue).append(":");
         buffer.append(column);
         return buffer.toString();
 }
 */

@Component
public class CacheKeyGenerator implements KeyGenerator{
    // custom cache key
    public static final int NO_PARAM_KEY = 0;
    public static final int NULL_PARAM_KEY = 53;

    /**
     * TODO:这里有些参数是没哟意义的 - 后两个参数没有必要作为key部分
     *          @Cacheable(cacheNames="books", key="T(someType).hash(#isbn)")
                public Book findBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)
     * @param target
     * @param method
     * @param params
     * @return
     */

    @Override
    public Object generate(Object target, Method method, Object... params) {

        StringBuilder key = new StringBuilder();
        key.append(target.getClass().getSimpleName()).append(".").append(method.getName()).append(":");
        if (params.length == 0) {
            return key.append(NO_PARAM_KEY).toString();
        }
        for (Object param : params) {
            if (param == null) {
//                log.warn("input null param for Spring cache, use default key={}", NULL_PARAM_KEY);
                key.append(NULL_PARAM_KEY);
            } else if (ClassUtils.isPrimitiveArray(param.getClass())) {
                int length = Array.getLength(param);
                for (int i = 0; i < length; i++) {
                    key.append(Array.get(param, i));
                    key.append(',');
                }
            //------------------------------------------------------------------------------------------
            } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
                key.append(param);
            } else {
//                log.warn("Using an object as a cache key may lead to unexpected results. " +
//                        "Either use @Cacheable(key=..) or implement CacheKey. Method is " + target.getClass() + "#" + method.getName());
                key.append(param.hashCode());
            }
            key.append('-');
        }

        String finalKey = key.toString();
//        long cacheKeyHash = Hashing.murmur3_128().hashString(finalKey, Charset.defaultCharset()).asLong();
//        log.debug("using cache key={} hashCode={}", finalKey, cacheKeyHash);
        return key.toString();
    }

}
