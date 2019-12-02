package com.process.activityDemo.dataObj;

public class Employee {

    private Long personId;

    private String personName;

    private Company comp;

    public Employee(String personName) {
        this.personName = personName;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Company getComp() {
        return comp;
    }

    public void setComp(Company comp) {
        this.comp = comp;
    }
}
