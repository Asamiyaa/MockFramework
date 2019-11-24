package com.utils.convert;

/**
 * @author YangWenjun
 * @date 2019/7/8 17:19
 * @project hook
 * @title: User
 * @description:
 */
public class User {
        private String name ;
        private String  age ;
        private String sex ;
        private long id ;

    public void setId(long id) {
        this.id = id;
    }

    public void setSex(String sex) {
            this.sex = sex;
        }

    public String getSex() {
        return sex;
    }




    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
