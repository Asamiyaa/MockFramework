package com.cache.impl;

import com.cache.ICache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author YangWenjun
 * @date 2019/8/22 17:56
 * @project hook
 * @title: mapCacheImpl
 * @description:    有时需要将本已实现的源码比如jdk \ spring 回调口子 、 父类 进行扩展
 *                    这里就是讲map中实现借鉴
 */
public class mapCacheImpl implements ICache {

    /**复合关系 ***/
    private Map mcc = new HashMap();
    /**形成新的体**/
    private String space ;

    @Override
    public void put(Object key, Object value) {

    }

    /**1.方法相对于属性可以更多的控制和转义等功能  2.这种包装可以避免add addAll 区别**/
    @Override
    public void remove(Object key) {
        mcc.remove(key);
    }

    @Override
    public void set(Object key, Object value) {

    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void DeleteInactivity() {

    }

    @Override
    public int memorySize() {
        return 0;
    }
}
