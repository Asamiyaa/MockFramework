package com.annotationValidateFrameWork;


import javax.validation.constraints.NotNull;

public class  User {

    @NotNull(message = "id is not null")
    private String id;
    @NotNull(message = "naem is not null")
    private String naem ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaem() {
        return naem;
    }

    public void setNaem(String naem) {
        this.naem = naem;
    }
}
