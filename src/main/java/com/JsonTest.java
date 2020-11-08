package com;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * 2020-11-08
 *
 *  1.各种格式化-转化 http://www.bejson.com/
 */
public class JsonTest {


    static String jsonstr = "{\n" +
            "  \"teacherName\": \"crystall\",\n" +
            "  \"teacherAge\": 27,\n" +
            "  \"course\": {\n" +
            "    \"courseName\": \"english\",\n" +
            "    \"code\": 1270\n" +
            "  },\n" +
            "  \"students\": [\n" +
            "    {\n" +
            "      \"studentName\": \"lily\",\n" +
            "      \"studentAge\": 12\n" +
            "    },\n" +
            "    {\n" +
            "      \"studentName\": \"lucy\",\n" +
            "      \"studentAge\": 15\n" +
            "    }\n" +
            "  ]\n" +
            "}";

        //转义字符是为了在“”中有了“”相当于 ‘’,所以可以贴到工具里面直接去掉。妨碍阅读
       static String JsonArr = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";
    public static void main(String[] args) {
        JSONObject jsonObject = JSONObject.parseObject(jsonstr);
        System.out.println(jsonObject);

        System.out.println(JSONObject.toJSONString(jsonObject)); //二者没有区别，因为这里的都是输出，所以都调用了toString .对于代码中则完全不同，二者类型不同、方式不同

        JSONArray objects = JSONArray.parseArray(JsonArr);
        System.out.println(objects);
        for (Object o : objects) {      //这里也可以通过index获取指定下标的值
//            o.ge 使用obj无法输出
            JSONObject o1 = (JSONObject) o;
            System.out.println(o1.getString("studentName"));
            System.out.println(o1.getInteger("studentAge")); //符合了转换操作，但必须了解其类型，都这导致转换异常
            System.out.println(o1.toJSONString());

        }
        System.out.println(objects.get(1).toString());
//        objects.toJavaList()  tojsva{T ....} 强转操作

        Student student = JSONObject.parseObject(objects.get(1).toString(), Student.class);
        System.out.println(student);

        String s = JSONObject.toJSONString(student);

        //------------------对象 json对象转换
        Student student1 = JSONObject.parseObject(((JSONObject) objects.get(1)).toJSONString(), Student.class);
        System.out.println(student1);

        //----------对象-jsonobj-----------
        //方式一
        String jsonString = JSONObject.toJSONString(student);
        JSONObject jsonObject22= JSONObject.parseObject(jsonString);
        System.out.println(jsonObject);

        //方式二 ***
        JSONObject jsonObject1 = (JSONObject) JSONObject.toJSON(student);
        System.out.println(jsonObject1);


        System.out.println(jsonObject22.entrySet());
        System.out.println(jsonObject22.containsValue("xx"));
        Map<String, Object> innerMap = jsonObject22.getInnerMap();
//        jsonObject22.merge()
//        jsonObject22.invoke()
//        jsonObject22.compute()
        System.out.println(jsonObject22.values());
        jsonObject22.putIfAbsent("xxx","fdfdf");  //replace remove
        jsonObject22.fluentPut("dfdf","fdfdf"); //myObj.fluentPut("key1",value1).fluentPut("key2",value2); builder模式
        jsonObject22.fluentRemove("dfdf");

        //jsonarray
//        objects.parallelStream()
//        objects.fluentAdd()
//        objects.toJavaList()
//        objects.toArray()
//        objects.forEach();
//        objects.subList()

    }
















}


class Student{
    private String studentName;
    private int studentAge;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    @Override
    public String toString() {
        return "Student111{" +
                "studentName111='" + studentName + '\'' +
                ", studentAge111=" + studentAge +
                '}';
    }
}



