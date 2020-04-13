package com.pattern.g;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * 1.接口文档与信息结构转化、组织  序列化和反序列化一些对象。
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
 *              7.xquery xpath  $bookstore//book/@q > 10  快速定位xml有用信息，无需全部加载解析(4种)以及绑定(2中组装解析转换)
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
 *              上述的问题是解决动态xml解析问题，如果仅仅需要对xml和对象绑定可以选择
 *             1.Xml4Parse  - XMLConvertUtil(xml-bean映射T-递归-反射-...)对比产品xmlBean\...
 *             2.父子节点
 *
 *        绑定方式：各种方式几乎都能完成，只是性能、时间、大小等方面有差别，同样是支持schema.namespace.属性..支持从xsd/xml到java对象
 *             jaxb      需要在bean上添加对应的注解进行控制。复杂类型需要定义解析器
 *             xstream
 *             xmlbeans  对于xsd,以及定义、复杂类型、标签生成对应类，更易转换操作  更高封装 通过递增的解除封送xml数据和高效的访问XML 模式内置数据类型的方法，XMLBeans交付了较好的性能。
 *             castor
 *             jibx
 *      JAXB与Apache XMLBeans：JAXB与Apache XMLBeans ：https://stackoverflow.com/questions/1362030/jaxb-vs-apache-xmlbeans  -- bbsp中注意每一步，拼接模块的复杂不仅仅是oxm还是业务逻辑交互
 *      交互又是另一个问题，所以解决完一个算一个
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
 *
 *          1.加签验签 - 两码-证书-签名验签服务器-加签验签操作
 *               签名，其实就是给报文做个摘要（哈希）。而且相同的签名算法得到的摘要是相同的，比如MD5，SH1，SH256等。
 *               https://blog.csdn.net/thekenofDIS/article/details/82344585
 *               https://blog.csdn.net/duweiqian/article/details/54090773
 *
 *          2.xml映射中除了body还是head组装 对比api文档中对每个字节描述，如何组装解析
 *          3.
 *
 */
public class Gmain {

    public static void main(String[] args) throws IOException, JAXBException {
        //json对象格式 vs string
        String s = "{\"id\": 1,\"name\": \"asamiya\",\"array\": [\"1\", \"2\"]}";
        ObjectMapper mapper = new ObjectMapper();
        //Json映射为对象
        Student student = mapper.readValue(s, Student.class);
        //对象转化为Json
        String json = mapper.writeValueAsString(student);
        System.out.println(json);
        System.out.println(student.toString());

        //xstream  https://www.jianshu.com/p/c6317346ba5c
        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(student);
        System.out.println(xml);
        Student student1 = (Student) xstream.fromXML(xml);
        System.out.println(student1);

        //jaxb  :https://www.javacodegeeks.com/2015/04/%E7%94%A8%E4%BA%8Ejava%E5%92%8Cxml%E7%BB%91%E5%AE%9A%E7%9A%84jaxb%E6%95%99%E7%A8%8B.html
        //在一个复杂的项目中，XML文件结构可能很复杂。这时，不需要手工编写对应的Java类文件，我们可以通过工具从xml schema文件获得java类文件，甚至可以从xml文件中转换得到java类文件。
//        http://www.blogways.net/blog/2015/05/05/jaxb-tutorial-1.html
        /* 初始化java对象 */
        Person person = new Person();
        person.setFirstName("net");
        person.setLastName("blogways");
        person.setCity("NanJing");
        person.setPostalCode(210000);
//        person.setBirthday(LocalDate.of(2013, 10, 11));  有点问题

        //初始化jaxb marshaler
        JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(person,new File("person.xml"));
        marshaller.marshal(person,System.out);
        System.out.println(marshaller.getSchema());//null

        //反编组
        File file = new File( "person.xml" );
        JAXBContext jaxbContext1 = JAXBContext.newInstance( Person.class );
        Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
        Person persons = (Person)jaxbUnmarshaller.unmarshal( file );
        System.out.println( persons );


        //xmlbeans ： https://www.oracle.com/technetwork/cn/topics/entarch/xmlbeans-2-083758-zhs.html
        // 1.下载对应xmlbeans项目  2.根据xsd生成类  3.打包到jar   4.放到classpath  5.根据文档进行解析组装操作








    }


}
