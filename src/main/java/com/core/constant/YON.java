package com.core.constant;

/**
 * @author YangWenjun
 * @date 2019/7/29 16:07
 * @project hook
 * @title: YON
 * @description:
 */
public enum YON {
    YES(1,"Y"),
    NO(0,"N");

    private Integer index ;
    private String  value ;

    YON(Integer index, String value) {
        this.index = index;
        this.value = value;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
