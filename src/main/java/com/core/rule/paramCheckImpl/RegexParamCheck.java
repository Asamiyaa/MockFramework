package com.core.rule.paramCheckImpl;

import com.core.rule.ParamCheck;
import com.core.rule.ParamRule;
import com.core.rule.bean.CheckResult;
import com.exception.ServiceCheckException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * @author YangWenjun
 * @date 2019/11/7 18:48
 * @project MockFramework
 * @title: RegexParamCheck
 * @description:
 */
public class RegexParamCheck implements ParamCheck{

    private ParamRule paramRule;

    @Override
    public CheckResult check(Object o, Class<?> objectType) throws ServiceCheckException {

        //todo 前置判断
        Map<Object, Pattern>  ruleMap = paramRule.getParamRule(objectType.getName());
        Map  fieldValueMap = new HashMap();
        //List fieldValueList = new ArrayList();

        //o instanceof (objectType.)? ((objectType.getName()) o) : null;
        if(StringUtils.isEmpty(o)){
                throw  new ServiceCheckException("");
        }

        Field[] fields = objectType.getDeclaredFields();
        for (Field field :
                fields) {
            field.setAccessible(true);
            try {
                fieldValueMap.put(field.getName(), field.get(objectType.cast(o)));
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
