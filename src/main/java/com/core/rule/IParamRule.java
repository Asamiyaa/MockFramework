package com.core.rule;

import com.core.rule.bean.CombinedRuler;

/**
 * @author YangWenjun
 * @date 2019/11/12 10:16
 * @project MockFramework
 * @title: IParamRule
 * @description:  参数规则业务类
 */
public interface IParamRule {

    /*void saveParamRule(CombinedRuler combinedRuler);

    void deleteParamRule(CombinedRuler combinedRuler);*/

    /**
     * 更新报文规则列表
     * @param combinedRuler 报文编号、各字段规则组合对象
     * @return
     */
    Integer updateParamRule(CombinedRuler combinedRuler);

    /**
     * 获取报文规则列表
     * @param draftNo  报文编号
     * @return
     */
    CombinedRuler getParamRuleList(String draftNo);

}
