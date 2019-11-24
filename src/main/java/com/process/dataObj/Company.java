package com.process.dataObj;

import java.util.List;

public class Company {
    private Long compId;

    private String compName;

    private List<Employee> people;

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public List<Employee> getPeople() {
        return people;
    }

    public void setPeople(List<Employee> people) {
        this.people = people;
    }
}
