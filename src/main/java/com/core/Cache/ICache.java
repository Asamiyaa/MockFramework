package com.core.Cache;

import com.core.rule.bean.CombinedRuler;

/**
 * @author YangWenjun
 * @date 2019/11/12 11:33
 * @project MockFramework
 * @title: ICache
 * @description: 位于core下的缓存单独部署，因为作为框架不要和应用的缓存混合，就类似于bean的...，并且这里的
 *                 缓存策略和应用肯定也是不同的
 */
public interface ICache {

    void synch(CombinedRuler combinedRuler);

    /**
     * 定义不同策略进行刷新
     */
    void refresh();

}
