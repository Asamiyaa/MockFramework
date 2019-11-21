package com.core.rule.bean.subRuler;

import com.core.constant.BooleanEnum;
import com.core.rule.bean.CheckResult;
import com.core.rule.bean.dataObj.RuleDo;

/**
 * @author YangWenjun
 * @date 2019/11/21 17:50
 * @project MockFramework
 * @title: AbstractValidator
 * @description:  构建责任链
 */
public abstract class AbstractValidator {

//    CheckResult checkResult = new CheckResult(BooleanEnum.TRUE);

    protected  CheckResult checkTrue = CheckResult.CheckTrue();
    protected AbstractValidator nextValidator ;


    //protected  void setNextValidator(AbstractValidator nextValidator) { 为什么不能传递给子类
    public AbstractValidator setNextValidator(AbstractValidator nextValidator) {
        this.nextValidator = nextValidator;
        return (AbstractValidator)this;
    }

    /**
     *
     * @param t         被校验属性的值
     * @param ruleDo    被校验属性的值对应的规则
     * @param <T>
     * @return
     *
     * 抽象到传入地方，因为这里抽象就不是单一职责了，这       这里就是为单个属性进行链的构建
     */
    public <T> CheckResult validateOfOneProperty(T t , RuleDo ruleDo){

        if(this.validate(t,ruleDo).getResult().getCode() == BooleanEnum.FALSE.getCode()){
            return checkTrue.checkFalse();
        }

        if(nextValidator != null){
            return nextValidator.validate(t,ruleDo);
        }

        return checkTrue;
    };

    //    abstract <T> CheckResult Validate(T t , Class<T> cls , String property) ;
    abstract <T> CheckResult validate(T t ,RuleDo ruleDo) ;
}
