package com.core.rule.bean.subRuler;

/**
 * @author YangWenjun
 * @date 2019/11/14 14:55
 * @project MockFramework
 * @title: LengthValidator
 * @description:
 */
public class LengthValidator {

    private String separator ;
    private Integer length   ;

    public LengthValidator(String separator, Integer length) {
        this.separator = separator;
        this.length = length;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
