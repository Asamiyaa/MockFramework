package com.utils.convert;

import com.alibaba.druid.util.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * XML - 对象 拼装和解析  dom解析时，可以通过buildElement等操作添加元素....
 * 泛型  -  反射
 * TODO 1.查看:20190710_xml解析思考循环递归反射泛型及框架快速学习使用
 *        https://github.com/corPrgrm/VeteransLoveWars/blob/master/20190710_xml%E8%A7%A3%E6%9E%90%E6%80%9D%E8%80%83%E5%BE%AA%E7%8E%AF%E9%80%92%E5%BD%92%E5%8F%8D%E5%B0%84%E6%B3%9B%E5%9E%8B%E5%8F%8A%E6%A1%86%E6%9E%B6%E5%BF%AB%E9%80%9F%E5%AD%A6%E4%B9%A0%E4%BD%BF%E7%94%A8
 *
 *      2.dom解析递归实现有问题，卡在获得的element 不清楚，解析混乱
 *
 *      3.sax解析 完成情况为半截 需要继续 参考：https://blog.csdn.net/lp284558195/article/details/79136322
 *        明确此时扩展的是   框架中的那部分，该部分的设计模式，我们写代码充当那么流程，避免不起作用或者重复起作用。--sax流程图对照实现
 *
 */

public class    XMLConvertUtil {
    private static final List users = new ArrayList<User>();

    /**
     * add + tab键
     * 　　* @description: TODO
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author ${USER}
     * 　　* @date 2019/7/6 23:00
     *
     */

    // public static List unMarshallDom(String XMLPath ,Class<?> cls) throws ParserConfigurationException, IOException, SAXException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
    public static <T> List<T> unMarshallDom(String XMLPath, T t) throws ParserConfigurationException, IOException, SAXException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
                                                            //Class<T> cls
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(XMLPath);//javax.xml.parsers包

        NodeList rootList = document.getChildNodes(); //想象为树 - 分叉 -处理 for if叶子节点处理 elseif 有子节点继续
        parseXml(t, rootList);

        // return (T)new Object();
        return users;
    }

    private static <T> void parseXml(T t, NodeList first) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //换个角度分割
        //if( first.getLength() > 0  && !"users".equals(first.item(0).getNodeName()) && !(first.item(0).getNodeName().startsWith("user"))){ //叶子节点
        //if(first.getLength() == 0 ){
        Node node = first.item(0);
        //if(node.getNodeType() == node.TEXT_NODE){ //debug来归结，解决问题
        //if(node != null  && !(node.getNodeValue().getNodeName().trim().equals("users")) && !(node.getNodeName().trim().equals("user")) && !(StringUtils.isEmpty(node.getNodeValue().trim()))){
        if (node != null && !(node.getNodeName().trim().equals("users")) && !(node.getNodeName().trim().equals("user")) && !(StringUtils.isEmpty(node.getNodeValue().trim()))) {
            Class cls = t.getClass();
            Object obj = cls.newInstance();

            Method[] declaredMethods = cls.getDeclaredMethods();
            // Map<String , Class[]> methodMap = new HashMap<String , Class[]>(); //放到前面
            Map<String, Class[]> methodMap = new HashMap();
            for (Method method :
                    declaredMethods) {
                // String[] split = method.getName().split("."); //lastIndex()+split
                //String methodName = split[split.length-1];
                if (!method.getName().startsWith("set")) {
                    continue;
                }
                methodMap.put(method.getName(), method.getParameterTypes());
            }

            for (int x = 0; x < first.getLength(); x++) {
                if ("#text".equals(first.item(x).getNodeValue())) {
                    continue;
                }
                //System.out.println(third.item(x).getNodeName() +" = "+ third.item(x).getTextContent());
                // if(x == 0 ){user.set}
                String t1 = first.item(x).getNodeName().substring(0, 1).toUpperCase();  //#text 到底是什么
                String t2 = first.item(x).getNodeName().substring(1);
                String setM = "set" + t1 + t2; //完整类名 T对象的路径
                //String setM =  user.getClass().getName()+".set"+t1+t2;
                //反射方法调用
                //if(setM.equals("setName")){continue;} 测试
                //Method setM1 = cls.getDeclaredMethod( setM ,String.class);//将所有‘指定’的东西，泛化-从Class中获取

                Method setM1 = cls.getDeclaredMethod(setM, methodMap.get(setM));
                //TODO 不是固定的 - 获取all method上所有的参数列表 map + 基本类型
                setM1.invoke(obj, first.item(x).getTextContent());
                // users.add((T)obj);每一组才是一个完整obj，所以放到下面
            }
            users.add((T) obj); //泛型强转  反射强转可以获取类+()或者cast()
        } else {
            for (int i = 0; i < first.getLength(); i++) {  //doument下几个一级对象。可以有多个<users></users>--报错。这个方法是为了获得除正文意外的其他信息吗？
                //拿着它，解析得到它 ...
                if (first.item(i) instanceof Element) {
                    Node seconedD = first.item(i);         //相当于document
                    NodeList seconed = seconedD.getChildNodes();
                    parseXml(t, seconed);
                }
            }
        }
    }


    public static <T> List unMarshallDom4j(String filePath, T t) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException {
        try {
            File inputXml = new File(filePath);
            SAXReader saxReader = new SAXReader();

            org.dom4j.Document document = saxReader.read(inputXml);
            org.dom4j.Element root = document.getRootElement();

            parseXmlDom4j(root, t).remove(users.size()-1);

        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    static int ii = 0 ;
    private static <T> List parseXmlDom4j(org.dom4j.Element root, T t) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException, ParserConfigurationException, SAXException {

        if (!root.hasMixedContent()) {
            Class cls = t.getClass();
            if (users.size() == 0) {
                users.add(cls.newInstance());//添加初始值
            }

            Object obj = users.get(users.size() - 1);

            Method[] declaredMethods = cls.getDeclaredMethods();
            Map<String, Class[]> methodMap = new HashMap();
            for (Method method : declaredMethods) {
                if (!method.getName().startsWith("set")) {
                    continue;
                }
                methodMap.put(method.getName(), method.getParameterTypes());
            }

            String t1 = root.getName().substring(0, 1).toUpperCase();
            String t2 = root.getName().substring(1);
            String setM = "set" + t1 + t2;
            Method setM1 = cls.getDeclaredMethod(setM, methodMap.get(setM));
            setM1.invoke(obj, root.getText());

            ii++;
            if (ii == 3) {
                    ii = 0;
                    users.add(cls.newInstance());
            }
            // }else {
        } else {
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                org.dom4j.Element child = (org.dom4j.Element) i.next();
                parseXmlDom4j(child, t);
            }
             //return users ;
        }
        return users ;
    }

    public static <T> T unMarshallSAX(String beginNode ,Class<T> cls) throws ParserConfigurationException, SAXException, IOException {
        //在主体测试没有问题后，再去优化，现在连xml结构解析都有问题，引入反射 泛型 递归 导致问题更加复杂

        SAXParserFactory saxfac = SAXParserFactory.newInstance();
        SAXParser saxparser = saxfac.newSAXParser();
        InputStream is = new FileInputStream("src/main/java/com/utils/convertUtils/xmlTest.xml");
        saxparser.parse(is, new MySAXHandler(beginNode ,cls));


        return (T)new Object();
    }

/*

    public static <T> T unMarshallXPath(){
        //1.解析 2.反射映射

        return (T)new Object();
    }

    public static String unMarshallxx(){

        return null ;
    }
*/

    public static void main(String[] args) throws NoSuchMethodException, ParserConfigurationException, InstantiationException, SAXException, IllegalAccessException, InvocationTargetException, IOException {

            //1.为什么相对路径没有生效 xmlTest.xml  加载默认初始路径是：D:\Data\mySrc\hook\
            //2.绝对路径：/src/main/java/com/utils/convertUtils/xmlTest.xml 没有找到 这里的 / 指的是 ‘系统 而不是项目’ 系统找不到指定的路径。)

            //TODO : 1.解决获取9个list内容问题  2.如何 递归 将if/else分为递归和普通 这俩个封为一个method 才能实现递归。 而不是三次调用呢

            XMLConvertUtil.unMarshallSAX("",User.class);
           // List list = XMLConvertUtil.unMarshallDom4j("src/main/java/com/utils/convertUtils/xmlTest.xml", new User());
           // System.out.println(list.size());
           // System.out.println(list.get(0));
        }

    }

class   MySAXHandler<T> extends DefaultHandler { //模板方法
    boolean hasAttribute = false;
    Attributes attributes = null;
    String beginNode  ;
    Class<T> cls ;
   // List list  ;
    Map<String , Method> map ;
    String currentTag ;

    public MySAXHandler(String beginNode ,Class<T> cls) {  //根据需要构造：无需传入的不用。可能初始化init其他/或者方法调用
        this.beginNode = beginNode;
        this.cls = cls ;
    }

    public void startDocument() throws SAXException {
        // System.out.println("文档开始打印了");
        //log...
        //list = new ArrayList();
        map = new HashMap();
        Field[] declaredFields = cls.getDeclaredFields();
        for (Field field:
        declaredFields ) {
            try {
                map.put(field.getName(),getSetMethod(field.getName()));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private Method getSetMethod(String fieldName) throws NoSuchMethodException {
        String t1 = fieldName.substring(0, 1).toUpperCase();
        String t2 = fieldName.substring(1);
        String setM = "set" + t1 + t2;

        return cls.getDeclaredMethod(setM ,getParamArray(setM)) ;
       // Method setM1 = cls.getDeclaredMethod(setM, methodMap.get(setM));
      //  setM1.invoke(obj, root.getText());

    }

    private Class[] getParamArray(String param){
        Method[] declaredMethods = cls.getDeclaredMethods();
        Map<String, Class[]> methodMap = new HashMap();
        for (Method method : declaredMethods) {
            if (!method.getName().startsWith("set")) {
                continue;
            }
            methodMap.put(method.getName(), method.getParameterTypes());  //重新组织结构 保证一次可以将需要的参数获取全
        }
        return methodMap.get(param);
    }
    public void endDocument() throws SAXException {
        // System.out.println("文档打印结束了");
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        /*if (qName.equals("users")) {
            return;
        }
        if (qName.equals("user")) {
            return;
        }*/
    if(localName.equals(beginNode)) {
        /*if (attributes.getLength() > 0) {
            this.attributes = attributes;
            this.hasAttribute = true;
        }*/
        T t = null ;
        for (int i = 0; i < attributes.getLength(); i++) {
            //User user = new User();
            try {
                 t = cls.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //if("id".equals(attributes.getLocalName(i))){
            if(map.get(attributes.getLocalName(i)) != null){
                //user.setId(Long.parseLong(attributes.getValue(i)));
                try {
                    map.get(attributes.getLocalName(i)).invoke(t,getParamArray(attributes.getLocalName(i)));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

        }
        currentTag = localName; //框架中从一个方法到下一个方法参数传递。- 成员变量
    }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (hasAttribute && (attributes != null)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.print(attributes.getQName(0) + ":"
                        + attributes.getValue(0));
            }
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        //System.out.print(new String(ch, start, length));
        //类似start那样判断节点 并赋值
        super.characters(ch,start,length);
        String value = new String(ch , start ,length);
        if(map.get(currentTag) != null){
            //user.setId(Long.parseLong(attributes.getValue(i)));


        }
    }
}



