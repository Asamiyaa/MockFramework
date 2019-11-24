package com.core.draft.tmpBBSP.dto;

/**
 * @author YangWenjun
 * @date 2019/9/29 14:20
 * @project hook
 * @title: FactoryEnum
 * @description:
 */
public enum ShcpeEnum {
    CIRCLE("circle"), Rec("rec");

    //private AbstractFactory af ;
    /**
     通过引用控制不好 ， 字面量
     */
    private String factoryName;

    ShcpeEnum(String factoryName) {
        this.factoryName = factoryName;
    }

    //其他方法
}
