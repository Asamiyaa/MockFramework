package com.pattern.g;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 1.接口文档与信息结构转化、组织
 *      0.形式
 *             时序图
 *             报文结构图
 *
 *      1.结构组织
 *          1.xml:https://www.w3school.com.cn/xml/xml_intro.asp
 *              1.树结构
 *              2.元素、属性、值-语法结构
 *              3.验证  schema
 *              4.命名空间
 *              5.XML CDATA
 *              6.从数据库获取 XML
 *              7.xquery xpath
 *
 *          2.json
 *              1.JSON支持的数据类型 Number\String("xx")\Boolean\Array([element,element,element, ])\Object{ string : value, }\空白、null
 *              2.json schema
 *
 *              json类型	    Java类型
                object	    LinkedHashMap
                array	    ArrayList
                string	    String
                number	    Integer,Long,Double
                true|false	Boolean
                null	    null

 *
 *          3.xml vs json
 *              1.https://www.cnblogs.com/sanmaospace/p/3139186.html
 *
 *
 *      2.组装、解析转换方式(js-dom(createElement/addElement)、后台) --- 这些是对上面组织结构使用的方法封装
 *             1.Xml4Parse  - XMLConvertUtil(xml-bean映射T-递归-反射-...)对比产品xmlBean\...
 *             2.父子节点
 *
 *        绑定方式
 *             jaxb
 *             xstream
 *             xmlbeans
 *             castor
 *              jibx
 *
 *              JSON官方  --> 注解
                GSON
                FastJSON
                jackson:  当前类 https://blog.csdn.net/m0_37076574/article/details/81317403
                        1.bind
                        2.树模型  类似xml 树
                        3.流式API 类似xml sax

 *        查询方式
 *              xpath
 *
 *      3.运用api灵活解析(和pjs-xml 前后端分离：json)
 *          1.加签验签
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class Gmain {

    public static void main(String[] args) throws IOException {
        //json对象格式 vs string
        String s = "{\"id\": 1,\"name\": \"小明\",\"array\": [\"1\", \"2\"]}";
        ObjectMapper mapper = new ObjectMapper();
        //Json映射为对象
        Student student = mapper.readValue(s, Student.class);
        //对象转化为Json
        String json = mapper.writeValueAsString(student);
        System.out.println(json);
        System.out.println(student.toString());


    }


}
