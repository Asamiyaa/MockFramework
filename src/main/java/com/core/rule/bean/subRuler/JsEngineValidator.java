package com.core.rule.bean.subRuler;

import com.core.rule.bean.CheckResult;
import com.core.rule.bean.dataObj.RuleDo;

/**
 * @author YangWenjun
 * @date 2019/11/21 17:17
 * @project MockFramework
 * @title: JsEngineValidator
 * @description:
 */
public class JsEngineValidator extends AbstractValidator {

    @Override
    <T> CheckResult validate(T t, RuleDo ruleDo) {

        return ruleDo.getIsjsengine() == 0? CheckResult.CheckTrue():jsParseValidate(t);
    }

    public <T> CheckResult jsParseValidate(T t ) { //TODO :是否需要重新设置js,正则存储字段
        return CheckResult.CheckTrue();
    }

}
