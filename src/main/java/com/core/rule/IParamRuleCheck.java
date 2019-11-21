package com.core.rule;

import com.core.rule.bean.CheckResult;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author YangWenjun
 * @date 2019/11/7 18:52
 * @project MockFramework
 * @title: LoadParamRule
 * @description:
 */
public interface IParamRuleCheck{

//    Map<String ,Map<Object ,Pattern>> assembParamRule(File ruleFile);

//    <T> CheckResult getParamRuleChain(String draftNo ,T t);
       <T> CheckResult check(String draftNo ,T t);
}
