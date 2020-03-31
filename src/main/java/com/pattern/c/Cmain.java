package com.pattern.c;

/**
 *
 *1.初始化资源 方式 对比(本地保存)
 *               https://www.toutiao.com/i6809077726981390861/?tt_from=weixin&utm_campaign=client_share&wxshare_count=1&timestamp=1585649038&app=news_article&utm_source=weixin&utm_medium=toutiao_android&req_id=202003311803580101300371363C04C8C0&group_id=6809077726981390861
 *               https://www.cnblogs.com/dennyzhangdd/p/8028950.html
 *
    spring    加载顺序，保证自定义初始化逻辑中对象正常加载，特别是循环依赖的地方
                 CommandLineRunner和ApplicationRunner
                 @PostConstruct
                 InitializingBean
                 ApplicationListener

    静态成员、块
    map       new HashMap()直接通过初始化而不是在调用时，通常一些固定数据这么弄

 在平时的业务模块开发过程中，难免会需要做一些全局的任务、缓存、线程等等的初始化工作，那么如何解决这个问题呢？方法有多种，但具体又要怎么选择呢？
 *
 *
 */

public class Cmain {
}
