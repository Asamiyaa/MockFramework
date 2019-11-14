package com.core.rule.bean.subRuler;

/**
 * @author YangWenjun
 * @date 2019/11/14 14:52
 * @project MockFramework
 * @title: NoEmpty
 * @description:
 */
public class isEmptyValidator {

    private boolean isEmpty ;

    public isEmptyValidator(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
