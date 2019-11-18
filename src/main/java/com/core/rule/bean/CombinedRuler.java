package com.core.rule.bean;

import java.util.List;
import java.util.Map;

/**
 * @author YangWenjun
 * @date 2019/11/12 10:38
 * @project MockFramework
 * @title: Ruler
 * @description:  标识每个报文名对应的list<字段>,每个字段对应的list<rule>。即用户规则输入对象
 */
public class CombinedRuler {

    private String draftNo ;
    private String draftDescribe;
    private Map<String,List<String>> propertyRule;


    public CombinedRuler(){}
    public CombinedRuler(String draftNo, String draftDescribe, Map<String, List<String>> propertyRule) {
        this.draftNo = draftNo;
        this.draftDescribe = draftDescribe;
        this.propertyRule = propertyRule;
    }

    public String getDraftNo() {
        return draftNo;
    }

    public void setDraftNo(String draftNo) {
        this.draftNo = draftNo;
    }

    public String getDraftDescribe() {
        return draftDescribe;
    }

    public void setDraftDescribe(String draftDescribe) {
        this.draftDescribe = draftDescribe;
    }

    public Map<String, List<String>> getPropertyRule() {
        return propertyRule;
    }

    public void setPropertyRule(Map<String, List<String>> propertyRule) {
        this.propertyRule = propertyRule;
    }

    @Override
    public String toString() {
        return "CombinedRuler{" +
                "draftNo='" + draftNo + '\'' +
                ", draftDescribe='" + draftDescribe + '\'' +
                ", propertyRule=" + propertyRule +
                '}';
    }
}
