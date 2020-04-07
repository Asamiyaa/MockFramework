package com.pattern.c;

import com.sun.btrace.BTraceUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Map;

/**
 *
 *1.初始化资源 方式 对比
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
 * TODO:从spring的初始化过程中找到扩展点 比如lister\runner\....
 *
 */
//这么写就是为了加载完spring bean后通过判断，处理一些加载信息  ---  spring 依赖关系
public class Cmain implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {

//下面的内容是重要的 并不是注释掉 她是可用的



        //加载缓存实例
//        Amain.getInstance.initCache();  这里通过这种方式而不是注入就是为了显示manager是一个独立的模块

        //通过spring获取该接口下所有实现类，循环、分组调用方法加载缓存
//       Map<String,StartUpInitCache> cache  =  Context.getApplicationContext().getBeanOfType(StartupInitCache.class);

//        List key = new Arrayl
        //循环分批加载
//        for(Map.Entry ....) {
//
//            StartupInitCache si = (StartupInitCache)entry.getValue();
//
//            boolean flag = true;
//            while (flag) {
//                si.getInitCacheBean(batch,capacity);
//                if(!BTraceUtils.Collections.isEmpty(initcachebeans)){
//                    initCache(init.beans..);
//                }
//                if(initBeans.size()<capacity){
//                    flag=false;
//                }
//                //循环一次就清空缓存，进行下次存储
//                key.clear();
//                batch++;
//            }
//        }

    }
}
