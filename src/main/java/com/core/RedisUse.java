package com.core;

/*
@Author YangWenjun
@Date
@description:  https://zhuanlan.zhihu.com/p/29665317
@TODO
@Version 1.0

    1.redis官方文档：https://redis.io/documentation
      redisson:https://github.com/redisson/redisson
    2.环境搭建 - jedis使用 https://github.com/xetorthio/jedis -https://blog.csdn.net/fanbaodan/article/details/89047909 - https://www.jianshu.com/p/a1038eed6d44
            - client配置 -
            jedis中对键通用的操作 - jedis中 字符串的操作 - jedis中对整数和浮点数操作 - jedis中对列表（list）操作 -  jedis 集合set 操作 - jedis中 有序集合Zsort
            jedis中 哈希（Hash）操作  - 操作排序
                -- https://redis.io/commands

            实现 池(池中数量/指定数据库/Key命名规范) 、Pipelining 、 sub/pub /script-lua/- >
             **** expire/lur-config配置-查看人家底层实现设计/ cache /trandition / lock / asyn - db -rdb/aof /master-slave -cluster/ 嵌入脚本 ***
             * lock:http://ifeve.com/redis-lock/

     3.整合spring

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
        //https://redisbook.readthedocs.io/en/latest/feature/pubsub.html
        //https://blog.csdn.net/canot/article/details/51938955
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
