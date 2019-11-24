package com.cache.impl;

import com.cache.ICache;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author YangWenjun
 * @date 2019/8/22 18:00
 * @project hook
 * @title: DicParamCache
 * @description:1.全包名 当有多个重复类名时
 *               2.序列化
 *               3.定义double long 类型时，0L ,0D
 *               4.单例
 *               5.锁
 *               6.区分哪里使用 new  vs spring 管理对象
 *               7.单一职责
 */
public class DicParamCache implements Serializable{

    private static final long serialVersionUID = 1L ;

    private static DicParamCache instance  = null ;

    //private ICache dicCache = CacheFactory.getCache("1");//从枚举取
    private ICache dicCache = null ;

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock wl =  rwl.writeLock();
    private final Lock rl =  rwl.readLock();
    
    private DicParamCache(){
        init();
    }

    private void init() {
        //xxdao = ApplicationContext.getBean("xxdao");   //本地代码使用spring,获得信息
        //...
        //dicCache.put()
    }

    public void destroy(){
        if(instance != null){
            instance.dicCache.clear();
        }
        instance = null ;
    }

    public void refresh(){
        try {
            wl.lock();
            dicCache.clear();
            init();
        }finally {
            wl.unlock();
        }
    }

    public static DicParamCache getInstance(){
        if(instance == null){
            instance = new DicParamCache();
        }
        return instance;
    }

    /********加入了 代码逻辑判断  破坏了单一职责，这个类的职责就是  创建缓存实例 ***所以需要创建DicUtils去getInstance.getDI
     * cCache()...进行逻辑判断************/
    public String getColumnDetail(String column_name ,String column_value , String param_diff){

        return null ;
    }
    /*************如果是简单的无需判断直接就可以返回 ，就可以放在这里，*********/

    public String getDicValue(String column_name ){
        return (String) dicCache.get(column_name) ;  //这种简单无需判断的
    }

}
