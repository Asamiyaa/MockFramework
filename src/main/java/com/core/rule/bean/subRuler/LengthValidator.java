package com.core.rule.bean.subRuler;

import com.core.constant.BooleanEnum;
import com.core.rule.bean.CheckResult;
import com.core.rule.bean.dataObj.RuleDo;

import java.lang.reflect.Field;

/**
 * @author YangWenjun
 * @date 2019/11/14 14:55
 * @project MockFramework
 * @title: LengthValidator
 * @description:
 */
public class LengthValidator extends AbstractValidator {

    /*  private String separator ;
        private Integer length   ;
    */

    @Override
    <T> CheckResult validate(T t ,RuleDo ruleDo) {
        /*if(ruleDo.getLength() == 0){
            return checkTrue;
        }*/

        return ruleDo.getLength() == 0 ? checkTrue : lengthValidate(t,ruleDo.getLength());
    }

//    public <T> CheckResult lengthValidate(T t, String property) {
//    public <T> CheckResult lengthValidate(Field field , String property) {
//    public <T> CheckResult lengthValidate(Field field) {  不能从field中直接get()获得，同样需要get(t)

    public <T> CheckResult lengthValidate(T t ,int length) {  //直接传入Object o = propertyField.get(t);效果
       // CheckResult checkTrue = CheckResult.CheckTrue(); 初始化到父类

           /*抽取到父类
            Field propertyField = t.getClass().getDeclaredField(property);
            propertyField.setAccessible(true);
            Object o = propertyField.get(t); //类型不定*/

      /*  if( !(t.toString().length() == length)){
            return checkTrue.checkFalse();
        }
*/
        return !(t.toString().length() == length) ? checkTrue.checkFalse():checkTrue;
    }



}
