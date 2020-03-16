package com.redis;

/*
@description:
      面试题：https://zhuanlan.zhihu.com/p/29665317
              为什么说Redis是单线程的以及Redis为什么这么快！:https://blog.csdn.net/xlgen157387/article/details/79470556
              Kafka、RabbitMQ、Redis消息中间件对比:http://archwang.top/2018/07/03/%E6%B6%88%E6%81%AF%E4%B8%AD%E9%97%B4%E4%BB%B6%E5%AF%B9%E6%AF%94/
              Jedis和Lettuce:https://blog.csdn.net/catoop/article/details/93756295
      redis官方文档：https://redis.io/documentation   -- 图示:https://redisbook.readthedocs.io/en/latest/index.html


      1.从不同角度理解redis场景  缓存、锁、分布式... RedisUse
            1.缓存
                    采用 @Cacheable 注解的方式缓存的命中率如何？或者说怎样才能
                    提高缓存的命中率？缓存是否总能返回最新的数据？如果缓存返回了过期的数据该怎么办？
                1.一致性
                    1.读写缓存  程序写入pageCache-异步磁盘同步 系统问题操作系统保证写入，断电丢失  kafka通过多个副本解决这种丢失
                    2.读缓存    从磁盘获取数据写入缓存  高并发场景下：你是选择同步还是异步来更新缓存呢？如果是同步更新，更新磁盘成功了，但是更新缓存失
                                                 败了，你是不是要反复重试来保证更新成功？如果多次重试都失败，那这次更新是算成功还
                                                 是失败呢？如果是异步更新缓存，怎么保证更新的时序？

                                                 解决方案：分布式事务 vs 定时将磁盘数据同步(不及时) - 全量 vs 增量   vs 过期
                                                             转账(不适用缓存或者分布式强一致性)      后两者比如邮件、微信头像

                                                             一定要合适的场景使用，不要死扣强一致性  人生也是这样要审时度势
                2.在内存有限的情况下，要优先缓存哪些数据，让缓存的命中率最高  - 缓存穿透 - 缓存置换  - 策略（命中率最高的置换策略，一定是根据你的业务逻辑，定制化的策略 -  LRU 算法 - 位置权重）



    1.1.redis配置
            Redis-cli: Learn how to master the Redis command line interface, something you'll be using a lot in order to administer, troubleshoot and experiment with Redis.
            Configuration: How to configure redis.
            Replication: What you need to know in order to set up master-replicas replication.
            Persistence: Know your options when configuring Redis' durability.         -- rdb/aof
            Redis Administration: Selected administration topics.
            Security: An overview of Redis security.
            Encryption: How to encrypt Redis client-server communication.
            Signals Handling: How Redis handles signals.
            Connections Handling: How Redis handles clients connections.
            High Availability: Redis Sentinel is the official high availability solution for Redis.
            Latency monitoring: Redis integrated latency monitoring and reporting capabilities are helpful to tune Redis instances for low latency workloads.
            Benchmarks: See how fast Redis is in different platforms.
            Redis Releases: Redis development cycle and version numbering.

    1.2.jedis使用 https://github.com/xetorthio/jedis -https://blog.csdn.net/fanbaodan/article/details/89047909 - https://www.jianshu.com/p/a1038eed6d44
            - 1
                    - jedis中对键通用的操作
                    - jedis中 字符串的操作
                    - jedis中对整数和浮点数操作
                    - jedis中对列表（list）操作
                    - jedis 集合set 操作
                    - jedis中 有序集合Zsort
                    jedis中 哈希（Hash）操作  - 操作排序
                        -- https://redis.io/commands
            1.keys * /keys apple*  查看所有key  flushall   keyspace
            2.get databases 这里database是在select xx （默认0-15）后的，比如spring中的value="xx"这个就是选择的库  分组
            3.


            - 2
                  Pipelining
            - 3
                  sub/pub /  及时性高但对可靠低 vs mq
            - 4
                    script-lua/-Lua + Redis 解决高并发:https://www.cnblogs.com/selfchange/p/redis.html 相当于存储过程、shell一样
            - 5
                   Memory optimization:
            - 6
                    expire/lur-config
            -7
                    trandition  不支持回滚 少用
            -8
                    Partitioning:  类似关系型数据库也有该问题，查询哪个库。Client side partitioning、Proxy assisted partitioning 、Query routing
                                    关系数据库有没有先从redis获取客户存在哪个数据库在进行定库查询  ---> mycat

            -9      Distributed locks   redisson:https://github.com/redisson/redisson  vs zookeeper  lock:http://ifeve.com/redis-lock/

            -10     Redis keyspace notifications: Get notifications of keyspace events via Pub/Sub

            -11     Redis-cli  类似于shell一样提供许多工具

            -12     cluster


     2.spring-redis
            1.缓存抽象（Cache-Abstraction）：http://www.spring4all.com/article/19893
            2.阿里云Redis开发规范 ： https://yq.aliyun.com/articles/531067  ******** key设计...
            3.Redis实战——Redis的pub/Sub(订阅与发布)在java中的实现 :https://www.cnblogs.com/onlymate/p/9524960.html

*/

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import redis.clients.jedis.*;

import java.util.*;

public class RedisUse {


//    private Jedis jedis ;  -- 类似于rockemq一样，先不结合spring

    public static void main(String[] args) throws InterruptedException {
//初始化数据
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushDB();

        jedis.set("test", "test");
        jedis.set("test1", "test1");
        jedis.set("test1", "test2");
        //设置多个字符对
        jedis.mset("1", "1", "333","333");

        System.out.println(jedis.get("1")+"------------------------00000");
        System.out.println(Arrays.asList(jedis.mget("1")).toString()+"------------------------00000");
        System.out.println(Arrays.asList(jedis.mget("333")).toString()+"------------------------00000");


//几种数据结构和操作

        jedis.mset(new String[]{"key1", "val1", "val2", "val3"});
        allKeys();

        jedis.expire("key1", 5);
        Thread.sleep(1000);
        jedis.persist("key1");
        System.out.println("expire time is ssl  " + jedis.ttl("key1"));

        List<String> mget = jedis.mget(new String[]{"key1", "test1"});
        System.out.println(jedis.get("key1"));
        for (String ss :mget) {
            System.out.println(ss);
        }
//
        jedis.set("serial", String.valueOf(1));//内部保存都是String格式的吗？
        //jedis.set("serial", "ser");//ERR value is not an integer or out of range
        jedis.incr("serial");
        jedis.incr("serial");
        jedis.incr("serial");
        System.out.println(jedis.get("serial"));

        jedis.lpush("list", "list1", "list2", "list3");
        System.out.println(jedis.llen("list"));
        System.out.println(jedis.lindex("list", 1));
        //System.out.println(jedis.sort("list").toString()); //JedisDataException: ERR One or more scores can't be converted into double

        jedis.sadd("set", "set1", "set2","set2");
        jedis.sadd("set1", "set1", "set3");
        System.out.println(jedis.smembers("set"));
        System.out.println(jedis.scard("set"));
        System.out.println(jedis.sinter("set", "set1").toString());
        System.out.println(jedis.sunion("set", "set1").toString());
        System.out.println(jedis.sdiff("set", "set1").toString());

        Map m = new HashMap(); m.put("hash1","hashVal1");m.put("hash2","hashVal2");
        jedis.hmset("hash", m);
        System.out.println(jedis.hkeys("hash").toString());
        System.out.println(jedis.hvals("hash").toString());
        System.out.println(jedis.hlen("hash"));


        SortingParams  sortingParams =  new SortingParams();
//        System.out.println(jedis.sort("hash", sortingParams.alpha()).toString());

        System.out.println("--pipeline test begin--");
        pipelineTest();
        System.out.println("--pipeline test end--");


        transaction();
    }

    private static void distributeLockManager(){
        //理论：http://ifeve.com/redis-lock/
        //单机：https://www.cnblogs.com/zhili/p/redisdistributelock.html
        //集群：https://www.cnblogs.com/zhili/p/redLock_DistributedLock.html
        //set - setnx + 多线程判断控制这里
    }


    private static void cluter(){
        //http://ifeve.com/redis-cluster-tutorial/
        //一致性哈希，而是使用哈希槽
    }

    private static void transaction(){
        Jedis redis = new Jedis("127.0.0.1", 6379, 400000);
        Transaction multi = redis.multi();
        multi.set("t1", "tv1");
        multi.set("t2", "tv2");
        List<Object> exec = multi.exec();
        for (Object o :exec) {
            System.out.println(o.toString());
        }
        System.out.println(redis.get("t1"));
        System.out.println(redis.get("t2"));

    }
    private static void cacheLru(){
        //http://ifeve.com/redis-lru
        //https://www.cnblogs.com/chenpingzhao/p/5022467.html
    }

    private static void subPubTest(){
        //pubsub + spring  https://www.cnblogs.com/onlymate/p/9524960.html

        //https://redisbook.readthedocs.io/en/latest/feature/pubsub.html
        //https://blog.csdn.net/canot/article/details/51938955
        Jedis redis = new Jedis("127.0.0.1", 6379, 400000);
        //JedisPubSub jedisPubSub =
       // redis.subscribe("fi","firstChannel");
        redis.publish("firstChannel","valueChanel");


    }

    private static void pipelineTest(){
        Jedis redis = new Jedis("127.0.0.1", 6379, 400000);
        Map<String, String> data = new HashMap<String, String>();
        redis.select(8);//指定数据库 -
        redis.flushDB();
        // hmset
        long start = System.currentTimeMillis();
        // 直接hmset
        for (int i = 0; i < 100000; i++) {
            data.clear();
            data.put("k_" + i, "v_" + i);
            redis.hmset("key_" + i, data);
        }
        long end = System.currentTimeMillis();
        System.out.println("dbsize:[" + redis.dbSize() + "] .. ");
        System.out.println("hmset without pipeline used [" + (end-start)/1000 + "] seconds ..");
        redis.select(8);
        redis.flushDB();
        // 使用pipeline hmset
        Pipeline p = redis.pipelined();
        start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            data.clear();
            data.put("k_" + i, "v_" + i);
            p.hmset("key_" + i, data);
        }
        p.sync();
        end = System.currentTimeMillis();
        System.out.println("dbsize:[" + redis.dbSize() + "] .. ");
        System.out.println("hmset with pipeline used [" + (end-start)/1000 + "] seconds ..");
        // hmget
        Set<String> keys = redis.keys("*");
        // 直接使用Jedis hgetall
        start = System.currentTimeMillis();
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        for (String key : keys) {
            result.put(key, redis.hgetAll(key));
        }
        end = System.currentTimeMillis();
        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("hgetAll without pipeline used [" + (end-start)/1000 + "] seconds ..");
        // 使用pipeline hgetall - 包装成pipeling.response
        Map<String, Response<Map<String, String>>> responses =
                new HashMap<String, Response<Map<String, String>>>(
                        keys.size());
        result.clear();
        start = System.currentTimeMillis();
        for (String key : keys) {
            responses.put(key, p.hgetAll(key));
        }
        p.sync();
        for (String k : responses.keySet()) {
            result.put(k, responses.get(k).get());
        }
        end = System.currentTimeMillis();
        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("hgetAll with pipeline used [" + (end-start)/1000 + "] seconds ..");
        redis.disconnect();
    }

    private  static void allKeys(){
        Jedis jedis = new Jedis("127.0.0.1" ,6379);
        Set<String> ll =  jedis.keys("*");
        for (String sss : ll) {
            System.out.print(sss+"-");
        }
    }

}
