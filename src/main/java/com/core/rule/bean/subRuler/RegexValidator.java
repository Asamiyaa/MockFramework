package com.core.rule.bean.subRuler;

import com.core.rule.bean.CheckResult;
import com.core.rule.bean.dataObj.RuleDo;

/**
 * @author YangWenjun
 * @date 2019/11/14 16:34
 * @project MockFramework
 * @title: RegexValidator
 * @description:
 */
public class RegexValidator extends AbstractValidator {

    @Override
    <T> CheckResult validate(T t, RuleDo ruleDo) {

        return ruleDo.getIsregex() == 0? CheckResult.CheckTrue():jsParseValidate(t);
    }

    //TODO :是否需要重新设置js,正则存储字段
    public <T> CheckResult jsParseValidate(T t ) {
        return CheckResult.CheckTrue();
    }
}
