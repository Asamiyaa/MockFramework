package com.core.rule;

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
public interface IParamRuleAssemb {

    Map<String ,Map<Object ,Pattern>> assembParamRule(File ruleFile);

    Map<Object ,Pattern> getParamRuleChain(String className);

}
