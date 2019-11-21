package com.core.constant;

/**
 * @author YangWenjun
 * @date 2019/11/21 18:27
 * @project MockFramework
 * @title: BooleanEnum
 * @description:
 */
public enum BooleanEnum implements BaseEnum {
    TRUE(1,"正确") , FALSE(0,"错误");

    private int code ;
    private String msg ;

    BooleanEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
