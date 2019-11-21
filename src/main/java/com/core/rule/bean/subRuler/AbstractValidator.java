package com.core.rule.bean.subRuler;

import com.core.rule.bean.CheckResult;

/**
 * @author YangWenjun
 * @date 2019/11/21 17:50
 * @project MockFramework
 * @title: AbstractValidator
 * @description:  构建责任链
 */
public abstract class AbstractValidator {




    abstract <T> CheckResult Validate(T t , Class<T> cls , String property) ;
}
