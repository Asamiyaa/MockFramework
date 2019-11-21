package com.core.rule.bean.subRuler;

import com.core.constant.BooleanEnum;
import com.core.rule.bean.CheckResult;
import com.core.rule.bean.dataObj.RuleDo;

/**
 * @author YangWenjun
 * @date 2019/11/14 14:52
 * @project MockFramework
 * @title: NoEmpty
 * @description:
 */
public class IsEmptyValidator extends AbstractValidator{

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

    /*private Object orgiObj ;
    private String property ;

    *//**创建对象比起方法( 形参 )上，为了后面面向对象链，关系的维护**//*
    public isEmptyValidator(Object orgiObj, String property) {
        this.orgiObj = orgiObj;
        this.property = property;
    }*/

    /*private CheckResult isEmpty(Object orgiObj ,String property){
        //因为Object类型退化了反射处理

    }*/

    //为了单独使用提供暴露
    //public <T> CheckResult isEmpty(T t ,Class<T> cls ,String property)  { //这里有没有必要cls
//    public <T> CheckResult isEmpty(T t  ,String property)  { //这里有没有必要cls
    public <T> CheckResult isEmpty(T t) {

    //    CheckResult checkResult = new CheckResult(BooleanEnum.TRUE);//面向接口-父类编程
        //
//            Field propertyField = t.getClass().getDeclaredField(property);
//            propertyField.setAccessible(true);
//            Object o = propertyField.get(t); //类型不定
      /*  if(t == null){
            return checkResult.setResult(BooleanEnum.FALSE);
        }
*/
        return t == null ? checkTrue.checkFalse():checkTrue;
    }

    //为了统一所以内部调用
    //将这里的参数放到成员构造注入还是方法注入  1.形成链 -初始化到内存-来了输入对象调用，如果构造传入
    //的话，需要将每一个对象在输入后再去构造，可能第一次校验就不通过，还构造这么多。所以采用传入模式。


    @Override
//    <T> CheckResult validate(T t, String property) {
    <T> CheckResult validate(T t, RuleDo ruleDo) {

        //构造链时无需判断，全部按照顺序进入，由ruleDo在每个validator中判断是否需要改判断
        /*if(ruleDo.getIsempty() == 0){ //无需判空
            return checkTrue;
        }*/
        return ruleDo.getIsempty() == 0 ? checkTrue : isEmpty(t);
    }
}
