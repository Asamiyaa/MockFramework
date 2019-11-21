package com.core.rule.bean.subRuler;

import com.core.rule.bean.CheckResult;
import com.core.rule.bean.dataObj.RuleDo;

/**
 * @author YangWenjun
 * @date 2019/11/14 14:57
 * @project MockFramework
 * @title: TypeValidator
 * @description:TODo:提供了前台和正则两种，是否可以将前台转化为后台正则统一处理
 */
public class TypeValidator extends  AbstractValidator{

//    private String fullType ;
    @Override
    <T> CheckResult validate(T t, RuleDo ruleDo) {

        return Integer.valueOf(ruleDo.getType()) == 0 ? checkTrue:typeValidate(t,ruleDo.getType());
    }

    public <T> CheckResult typeValidate(T t ,String type) {

        //几种基本类型的判断，或者反射 。 那么从这里就可以看出传入Field更灵活
        return CheckResult.CheckTrue();
    }

}
