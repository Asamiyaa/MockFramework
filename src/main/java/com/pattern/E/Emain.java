package com.pattern.E;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**服务定位  -- 哪些规则是强制、哪些是client可以扩展的、合理性、哪些需要直接定义成常量写死还是通过更高合理抽象维护其关联性
 * 0.本身之间有关联比如名字有某种约定，路径...无需映射直接写死“com.aa.bb”+msg.toString+"dd.cn"; - afcat builder
 * 1.map
 *     --自己思考的mapper和mapperObj本身生成时就是通过插件自定义的。所以这里没有固定而言。全限定类名。所以说通过代码是不可以实现的。那么只能去记录或者映射。方法1-4
 *     那么选择1map问题：何时map.1.客户自己在新增mapper时增加map 2.自动扫描
 * 2.反射
 *       1.全限定类名   2.类型.class   3.对象.getClass
 * 3.服务定位器模式        https://www.runoob.com/design-pattern/service-locator-pattern.html  - afcat process
 *          其中包括：缓存、定位器。用于分布系统，模块之间；不直接有代码关联。比如消息返回后从报文解析得到的名字和处理器子类实现关联
 * 4.dispatcher 委派模式  https://www.jianshu.com/p/38acf37b1e1f  屏蔽了调用方和实现方，中间有leader-dispatcher
 *
 *  路由表 - 跳表 算法
 *
 *
 * 定位技术点：
 *      1.xpath:
 *          https://www.cnblogs.com/hhh5460/p/5079465.html
 *          https://bbs.csdn.net/topics/390653373
 *          https://segmentfault.com/q/1010000016145426
 *
 *      2.扫描类 - 扫描注解(标识)....
 *
 * -
 */
//@Component
//@Order
public class Emain implements ApplicationRunner{


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //通过spring提前扫描加载到
    }



    //通过spring-applicationRunner order 加载mapper文件解析的xxMapper.xml中的 .提前指定mapper.xml跟路径  xpath解析查找
    // <mapper namespace="com.core.rule.dao.DraftDoMapper">
    //   <resultMap id="BaseResultMap" type="com.core.rule.bean.dataObj.DraftDo">

    private static final String BASE_MAPPER_PATH="src/main/resources/mapping/";//先写死
//    private static final String BASE_MAPPER_PATH="src.main.resources.mapping.";
//    private static final String BASE_MAPPER_PATH="com.asm";
    /*    public void getSelectXML(String query_sentences) throws XPathExpressionException {
            XPathFactory pathFactory = XPathFactory.newInstance();
            XPath path = pathFactory.newXPath();
            //该student.xml文件为于src目录下。
            InputStream in =Emain.class.getResourceAsStream("classpath:Student.xml");
            InputSource source = new InputSource( in ); //InputSource使用一次就会关闭，再次使用就用再new一次。
            //XPath语法的具体链接：http://www.w3school.com.cn/xpath/xpath_syntax.asp
            String query= "/students/student/[name='李小离']/age";
            //通过姓名为“李小离”查询他的年龄。
            //String evaluate(String expression,InputSource source);
            String age = (Node) path.evaluate(query, source);
            sysotem.out.println("李小离的年龄是： " + age);
        }*/
    private static Map<String, String> nameSpaceDtoPair = new HashMap<String, String>();//禁止回收 static 2.放到本地缓存而不是redis因为经常用
    public static void main(String args[]) throws IOException {

//        List<String> names = scanMapperBaseLocation(BASE_MAPPER_PATH);
//        System.out.println(names.toString());//[com.asm.GeneratorBeanMain, com.asm.MyClassLoader, com.asm.retv, com.asm.studentfature]

        try {
            //解析文档
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); // never forget this!
            DocumentBuilder builder = domFactory.newDocumentBuilder();

            List<String> mapperNames = scanMapperBaseLocation(BASE_MAPPER_PATH);
//            System.out.println(mapperNames.toString());
            //-------------------------
            for (int i = 0 ,len = mapperNames.size() ; i<len ;i++) {
//            Document doc = builder.parse("src/main/resources/Student.xml");//这里不可以使用unknown protocol: classpath
//                Document doc = builder.parse("src/main/resources/mapping/JobMapper.xml");
                Document doc = builder.parse(BASE_MAPPER_PATH+mapperNames.get(i));
                XPathFactory factory = XPathFactory.newInstance(); //创建 XPathFactory
                XPath xpath = factory.newXPath();//用这个工厂创建 XPath 对象


//            NodeList nodes = (NodeList) xpath.evaluate("location/property", doc, XPathConstants.NODESET);
                String  namespace = (String) xpath.evaluate("//mapper/@namespace", doc, XPathConstants.STRING);
                String  dto = (String) xpath.evaluate("//resultMap/@type", doc, XPathConstants.STRING);
                nameSpaceDtoPair.put(namespace, dto);

//                System.out.println( "this is namespace   "+namespace +" dto is  "+ dto);
            }


//            String name = "";
//            String value = "";
//            for (int i = 0; i < nodes.getLength(); i++) {
//                Node node = nodes.item(i);
//                name = (String) xpath.evaluate("name", node, XPathConstants.STRING);
//                value = (String) xpath.evaluate("value", node, XPathConstants.STRING);
//                System.out.println("name=" + name + ";value=" + value);
//            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(nameSpaceDtoPair.toString());

    }

    //扫描指定路径下的所有文件名
    private static List<String> scanMapperBaseLocation(String mapperBaseLocation) throws IOException {
//        return new ClasspathPackageScanner(mapperBaseLocation).getFullyQualifiedClassNameList(); //比人用来获取包下面所有类的方法
        return readFromDirectory(mapperBaseLocation);
    }

    private static List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();

        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }


}
