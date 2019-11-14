package com.core.rule.bean.subRuler;

/**
 * @author YangWenjun
 * @date 2019/11/14 14:57
 * @project MockFramework
 * @title: TypeValidator
 * @description:
 */
public class TypeValidator {

    private String fullType ;

    public TypeValidator(String fullType) {
        this.fullType = fullType;
    }

    public String getFullType() {
        return fullType;
    }

    public void setFullType(String fullType) {
        this.fullType = fullType;
    }
}
