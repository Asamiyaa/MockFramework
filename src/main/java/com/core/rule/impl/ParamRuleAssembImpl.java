package com.core.rule.impl;

import com.core.rule.IParamRuleAssemb;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author YangWenjun
 * @date 2019/11/7 19:48
 * @project MockFramework
 * @title: RegexParamRuleImpl
 * @description:  ruler chain
 */
public class ParamRuleAssembImpl implements IParamRuleAssemb {
    @Override
    public Map<String, Map<Object, Pattern>> assembParamRule(File ruleFile) {
        
        //前置校验
        if(isCached()){
            return new HashMap<>();  //todo 从缓存中取，不能全部放到缓存中，注意失效加载策略
        };

        //getParamRule() + 组装

        return null;
    }

    private boolean isCached() {
        return Boolean.TRUE;
    }


    @Override
    public Map<Object, Pattern> getParamRuleChain(String className) {

        //cache中获取

        return null;
    }
}
