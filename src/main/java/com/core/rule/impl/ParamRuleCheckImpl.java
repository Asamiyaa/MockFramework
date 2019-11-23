package com.core.rule.impl;

import com.core.constant.BooleanEnum;
import com.core.rule.IParamRuleCheck;
import com.core.rule.bean.CheckResult;
import com.core.rule.bean.dataObj.RuleDo;
import com.core.rule.bean.subRuler.*;
import com.core.rule.dao.RuleDoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author YangWenjun
 * @date 2019/11/7 19:48
 * @project MockFramework
 * @title: RegexParamRuleImpl
 * @description:  ruler chain
 */
@Service
public class ParamRuleCheckImpl implements IParamRuleCheck {

    private static final Logger log = LoggerFactory.getLogger(ParamRuleCheckImpl.class);
    @Autowired
    private RuleDoMapper ruleDoMapper;


    public Map<String, Map<Object, Pattern>> assembParamRule(File ruleFile) {
        
        //前置校验
        if(isCached()){
            return new HashMap<>();  //todo 从缓存中取，不能全部放到缓存中，注意失效加载策略
        };

        //getParamRule() + 组装

        return null;
    }


    private boolean isCached() {
        return Boolean.TRUE;
    }


    @Override
    public <T> CheckResult check(String draftNo ,T t) {
        //cache中获取
        //TODO:单个实现是为每个属性构建链，这里 则是多个属性的链。下面代码时获得对应的属性

        //直接和数据库对象交互 而无需中间多余的封装中间多余的封装.从前-后 从库向上，这里就是因为没有完全
        //接壤造成后续转化
        //CombinedRuler combinedRuler = new ParamRuleImpl().getParamRuleList(draftNo);
        //combinedRuler.get
        List<RuleDo> ruleDos = ruleDoMapper.selectRuleDoByNo(draftNo);
        for (RuleDo ruleDo :ruleDos) {
            //根据每个属性 获得该对象的属性值并传入
            Field propertyField = null;
            try {

                propertyField = t.getClass().getDeclaredField(ruleDo.getProperty());
                propertyField.setAccessible(true);
                T o = (T) propertyField.get(t);

                log.info("--开始进行property rule validate--",o);

                if(validateOfOneProperty(o,ruleDo).getResult().getCode()
                                == BooleanEnum.FALSE.getCode()){
                    return CheckResult.CheckTrue().checkFalse();
                };

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

       /* Field propertyField = t.getClass().getDeclaredField(property);
        propertyField.setAccessible(true);
        Object o = propertyField.get(t);*/

       //根据有ruleDao控制，所以这里的话volidator全部加入
        // 非链式写法
        // errorLogger.setNextLogger(fileLogger);
        // fileLogger.setNextLogger(consoleLogger);
        //


        return null;
    }

    private <T> CheckResult validateOfOneProperty(T t, RuleDo ruleDo){
        return new IsEmptyValidator().setNextValidator(
                new TypeValidator().setNextValidator(
                        new LengthValidator().setNextValidator(
                                new RegexValidator().setNextValidator(
                                        new JsEngineValidator()
                                )
                        )
                )
        ).validateOfOneProperty(t,ruleDo);}

    public void setRuleDoMapper(RuleDoMapper ruleDoMapper) {
        this.ruleDoMapper = ruleDoMapper;
    }

}
