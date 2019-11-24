package com.core.draft.tmpBBSP.parse;

import com.draft1.dto.RequestInfo;

/**
 * @author YangWenjun
 * @date 2019/8/14 17:40
 * @project hook
 * @title: AbstractDraftParse
 * @description: 模拟了Aop切面处理 抽象类 模板方法
 */
public abstract class AbstractDraftParse {

    public void execute(RequestInfo requestInfo){
        beforeDeal();
        deal();
        afterDeal();
    }

    protected abstract void deal();

    private void afterDeal() {

    }

    private void beforeDeal() {

    }
}
