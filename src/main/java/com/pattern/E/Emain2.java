package com.pattern.E;

import java.util.Collections;
import java.util.List;

/**
 *
 *
 * 对Emain中的5.2实现
 */


public class Emain2 implements StartUpInitCache {

    //实现方法并返回CacheBena
    public List getInitCacheBean(int batch, int capacity){

//        这里对自己要初始化缓存对象进行build成CacheBean,其中和表设计有关，是否生效等判断
    return Collections.emptyList();
    }
}


interface StartUpInitCache{
//    这里就是统一初始化注入缓存bean需要实现的接口  分批次加载到缓存，因为这个加载其实从本地缓存放到远程缓存的过程。所以分批
//    List<CacheBean> getInitCacheBean(int batch,int capacity);
}