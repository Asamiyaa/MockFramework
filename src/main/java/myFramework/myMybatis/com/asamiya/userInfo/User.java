package myFramework.myMybatis.com.asamiya.userInfo;

/**
 * 数据库映射对象
 */
public class User {

    private int id;
    private String name ;
    private String age ;


    public User(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
