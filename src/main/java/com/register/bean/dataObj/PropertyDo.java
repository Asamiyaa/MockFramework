package com.register.bean.dataObj;

public class PropertyDo {
    private Long id;

    private String draftno;

    private String property;

    private String properdesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDraftno() {
        return draftno;
    }

    public void setDraftno(String draftno) {
        this.draftno = draftno == null ? null : draftno.trim();
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }

    public String getProperdesc() {
        return properdesc;
    }

    public void setProperdesc(String properdesc) {
        this.properdesc = properdesc == null ? null : properdesc.trim();
    }
}