package com.core.Cache;

import com.core.rule.bean.CombinedRuler;

/**
 * @author YangWenjun
 * @date 2019/11/12 11:33
 * @project MockFramework
 * @title: ICache
 * @description: 位于core下的缓存单独部署，因为作为框架不要和应用的缓存混合，就类似于bean的...，并且这里的
 *                 缓存策略和应用肯定也是不同的
 *
 *                 访问量大且出入参是一个有限集合的业务更加适合缓存。
 *
 *                 cacheable - cacheput - cacheInvit
 *                 场景 - 编码解决方案 - 向上抽象(设计模式) - 向上抽象(xml - 注解)
 *
 *                 不能在同一个类中调用被注解缓存了的方法。也就是说缓存调用方法和缓存注解方法不能在一个类中出现。
 *                 是否意味着底层用到了代理呢？
 *
 *
 *
 */
public interface ICache111 {

    void synch(CombinedRuler combinedRuler);

    /**
     * 定义不同策略进行刷新
     */
    void refresh();

}
