package com.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author YangWenjun
 * @date 2019/11/23 8:54
 * @project MockFramework
 * @title: RedisCacheManager
 * @description:
 *          Spring Boot会在侦测到存在Redis的依赖并且Redis的配置是可用的情况下，
 *          使用RedisCacheManager 初始化CacheManager。
 *
 *          手动在代码中填写redisTemplate操作 - 不行
 *
 *          查看test 发现无法序列化造成在客户端查不到数据 so
 *
 *          注意这里的@Bean 是否会影响到spring中的类的数据？
 *
 *
 *          @Bean
public RedisCacheManagerBuilderCustomizer myRedisCacheManagerBuilderCustomizer() {
return (builder) -> builder
.withCacheConfiguration("cache1",
RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10)))
.withCacheConfiguration("cache2",
RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)));

spring.cache.cache-names=cache1,cache2
spring.cache.redis.time-to-live=600000

}
 */
@Configuration
public class RedisCacheConfiguer {

    @Bean
    public RedisCacheManager cacheManager(RedisTemplate redisTemplate){
        //获得缓存管理类
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));//设置序列化
        //设置默认超过期时间是30秒
        redisCacheConfiguration.entryTtl(Duration.ofSeconds(30));
//        redisCacheConfiguration.
        //初始化RedisCacheManager
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);

/*      不同版本api
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
       开启使用缓存名称做为key前缀(这样所有同名缓存会整理在一起比较容易查找)
        //这里可以设置一个默认的过期时间 单位是秒
        redisCacheManager.setDefaultExpiration(600L);
        // 设置缓存的过期时间 单位是秒
        Map<String, Long> expires = new HashMap<>();
        expires.put("pub.imlichao.CacheTest.cacheFunction", 100L);
        redisCacheManager.setExpires(expires);*/

        return redisCacheManager ;
    }

    /**
     * retemplate相关配置
     * @param factory
     * @return
     */
    @Bean                                              //自动完成无需${}一个个获取 配置 ，spring上下文此时已经有了该类型bean,所以可以注入
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();//TODO:这里可以注入吗？？？？
        // 配置连接工厂
        template.setConnectionFactory(factory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        // 值采用json序列化
        template.setValueSerializer(jacksonSeial);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();

        return template;
    }

    /**
     * 对hash类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }


  /*  *
     * 对redis字符串类型数据操作
     *
     * @param redisTemplate
     * @return*/

    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

/*
    *
     * 对链表类型的数据操作
     *
     * @param redisTemplate
     * @return
*/

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {

        return redisTemplate.opsForList();
    }

/*    *
     * 对无序集合类型的数据操作
     *
     * @param redisTemplate
     * @return*/

   /* @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    *//**
     * 对有序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
*//*
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }*/
}
