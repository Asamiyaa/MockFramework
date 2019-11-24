package com.cache;

import java.util.Collection;
import java.util.Set;

/**
 * @author YangWenjun
 * @date 2019/8/22 17:14
 * @project hook
 * @title: ICache  泛型
 * @description:   整个项目的缓存，区分core目录下作为缓存，二者可能需要分析存在场景及策略
 */
public interface ICache<K,V> {

   void put(K key, V value);

   void remove(K key);

   void set(K key, V value);

   V get(K key);

   boolean containsKey(K key);

   Set<K> keySet();

   boolean isEmpty();

   Collection<V> values();

   int size();

   void clear();

   void refresh();

   /**删除不活跃 --hotKey**/
   void DeleteInactivity();
    /**cache memeory*/
   int memorySize();

}
