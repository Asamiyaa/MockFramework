package seckill.controller.viewObj;

public class UserVO {
    private Integer id;
    private String name ;
    private Boolean gender;
    private Integer age;
    private String telphone;
/*
前台不需要那种注册方法，第三方id,密码，这些地段都是后台进行校验，或者推送服务，或者进行控制的信息，对客户来说没有意义展示
    private String regMode;
    private String thirdPartyId;
    private String encrptPassword;
*/


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
