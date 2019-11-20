package com.core.rule;

import com.core.rule.bean.CheckResult;
import com.exception.ServiceCheckException;

/**
 * @author YangWenjun
 * @date 2019/11/7 18:05
 * @project MockFramework
 * @title: ParamCheck
 * @description:
 */
public interface IParamCheck {


    /**
     * 返回值 - 方法名 - 参数(哪些、类型、命名) - 异常
     * @param o
     * @param objectType
     * @param value
     * @return
     * @throws ServiceCheckException
     */
    CheckResult check(Object o , Class<?> objectType) throws ServiceCheckException;

    /**
     * 反射 - 类名 - 关联调用
     * @return
     */
    String getName();
}
