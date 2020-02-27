package com.core.rule.bean.dataObj;

public class CompositDTO {

    private String draftno;

    private String property;


    public CompositDTO(String draftno, String property) {
        this.draftno = draftno;
        this.property = property;
    }

    public String getDraftno() {
        return draftno;
    }

    public void setDraftno(String draftno) {
        this.draftno = draftno;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
