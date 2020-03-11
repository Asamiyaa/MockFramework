package com.redis;

/*
@description:
      面试题：https://zhuanlan.zhihu.com/p/29665317
              为什么说Redis是单线程的以及Redis为什么这么快！:https://blog.csdn.net/xlgen157387/article/details/79470556
              Kafka、RabbitMQ、Redis消息中间件对比:http://archwang.top/2018/07/03/%E6%B6%88%E6%81%AF%E4%B8%AD%E9%97%B4%E4%BB%B6%E5%AF%B9%E6%AF%94/
              Jedis和Lettuce:https://blog.csdn.net/catoop/article/details/93756295
      redis官方文档：https://redis.io/documentation   -- 图示:https://redisbook.readthedocs.io/en/latest/index.html

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
