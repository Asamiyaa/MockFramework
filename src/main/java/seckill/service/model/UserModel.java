package seckill.service.model;

/**
 * 属性为该对象的‘ 真实反映 ’，通常由 ‘数据模型层’整合而来，数据模型层从表角度思考，model则从业务角度思考
 */
public class UserModel {
    private Integer id;
    private String name ;
    private Boolean gender;
    private Integer age;
    private String telphone;
    private String regMode;
    private String thirdPartyId;

    private String encrptPassword;

    //alt+insert

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

    public String getRegMode() {
        return regMode;
    }

    public void setRegMode(String regMode) {
        this.regMode = regMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }
}
