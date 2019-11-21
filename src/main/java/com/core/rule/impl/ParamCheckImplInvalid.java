package com.core.rule.impl;

import com.core.rule.IParamCheckInvalid;
import com.core.rule.bean.CheckResult;
import com.exception.ServiceCheckException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author YangWenjun
 * @date 2019/11/7 18:48
 * @project MockFramework
 * @title: RegexParamCheck
 * @description:
 */
public class ParamCheckImplInvalid implements IParamCheckInvalid {

//    private IParamRuleAssemb paramRule;

    @Override
    //public CheckResult check(Object o, Class<?> objectType) throws ServiceCheckException {
    public <T> CheckResult check(T t , Class<T> cls) throws ServiceCheckException {

        //todo 前置判断
        //根据报文编号获得责任链，责任链最终是一个对象，只是里面进行了嵌套包装

//  /      Map<Object, Pattern>  ruleMap = paramRule.getParamRuleChain(cls.getName());
        Map<Object, Pattern>  ruleMap = new HashMap<>();
        Map  fieldValueMap = new HashMap();
        //List fieldValueList = new ArrayList();

        //o instanceof (objectType.)? ((objectType.getName()) o) : null;
        if(StringUtils.isEmpty(t)){
                throw  new ServiceCheckException("");
        }

        Field[] fields = cls.getDeclaredFields();
        for (Field field :
                fields) {
            field.setAccessible(true);
            try {
                fieldValueMap.put(field.getName(), field.get(cls.cast(t)));
                //fieldValueList.add
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        //直接比对两个map的值  先对比key 比对value
        return doCheck(ruleMap , fieldValueMap);

    }

    private CheckResult doCheck(Map<Object, Pattern> ruleMap, Map fieldValueMap) {
        //  rule文件配置 ：  filed    是否必输   正则  --> 添加其他 参考挡板框架  这里是否需要创建对象 而不是map
        fieldValueMap.forEach((key, value) -> {
           /*if(StringUtils.isEmpty(value) && fieldValueMap.get(key)){

            }*/
            ruleMap.get(key).matcher(value.toString());
        });
        return  null ;

    }

    @Override
    public String getName() {
        return null;
    }
}
