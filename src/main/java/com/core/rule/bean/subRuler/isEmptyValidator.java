package com.core.rule.bean.subRuler;

import com.core.constant.BaseEnum;
import com.core.constant.BooleanEnum;
import com.core.rule.bean.CheckResult;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @author YangWenjun
 * @date 2019/11/14 14:52
 * @project MockFramework
 * @title: NoEmpty
 * @description:
 */
public class isEmptyValidator extends AbstractValidator{

    /*private boolean isEmpty ;


    public isEmptyValidator(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }*/

    /*
//       将set 返回 父类/接口 ，构建builder 是否合适？
         更改get/set为构造，必然性 --> 既然选择了该校验器，则一样是校验了，所以无需传入field
    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }*/

    private Object orgiObj ;
    private String property ;

    /**创建对象比起方法( 形参 )上，为了后面面向对象链，关系的维护**/
    public isEmptyValidator(Object orgiObj, String property) {
        this.orgiObj = orgiObj;
        this.property = property;
    }

    /*private CheckResult isEmpty(Object orgiObj ,String property){
        //因为Object类型退化了反射处理

    }*/

    //为了单独使用提供暴露
    public <T> CheckResult isEmpty(T t ,Class<T> cls ,String property)  { //这里有没有必要cls

        CheckResult checkResult = new CheckResult(BooleanEnum.TRUE);
        try {

            Field propertyField = t.getClass().getDeclaredField(property);
            propertyField.setAccessible(true);
            Object o = propertyField.get(t); //类型不定
            if(o == null){
                return checkResult.setResult(BooleanEnum.FALSE);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return checkResult;
    }

    //为了统一所以内部调用
    @Override
    <T> CheckResult Validate(T t ,Class<T> cls ,String property) {
        return isEmpty(t,cls,property);
    }
}
