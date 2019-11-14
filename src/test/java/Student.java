/**
 * @author YangWenjun
 * @date 2019/11/14 15:02
 * @project MockFramework
 * @title: Student
 * @description: mock从前台输入对象
 */
public class Student {

    private String name ;
    private Integer age ;
    private String className;

    public Student(String name, Integer age, String className) {
        this.name = name;
        this.age = age;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
