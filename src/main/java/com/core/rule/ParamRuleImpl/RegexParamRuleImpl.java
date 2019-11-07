package com.core.rule.ParamRuleImpl;

import com.core.rule.ParamRule;

import java.io.File;
import java.util.Map;
import java.util.regex.Pattern;

import static com.sun.deploy.util.SessionState.save;

/**
 * @author YangWenjun
 * @date 2019/11/7 19:48
 * @project MockFramework
 * @title: RegexParamRuleImpl
 * @description:
 */
public class RegexParamRuleImpl implements ParamRule {
    @Override
    public Map<String, Map<Object, Pattern>> loadParamRule(File ruleFile) {
        
        //前置校验
        
        readRuleFile();
        
        saveRule();

        compilePattern();
        
        synchCache();

        return null;
    }

    private void compilePattern() {
    }

    private void saveRule() {
    }

    private void synchCache() {
    }

    private void readRuleFile() {
    }

    @Override
    public Map<Object, Pattern> getParamRule(String className) {

        //cache中获取

        return null;
    }
}
