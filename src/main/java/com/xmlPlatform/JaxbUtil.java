package com.xmlPlatform;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author YangWenjun
 * @date 2019/12/14 11:19
 * @project MockFramework
 * @title: jaxbUtil
 * @description:   回顾工具类编写归范  本章内容参考：https://www.iteye.com/blog/fengyilin-2344183
 */
public final  class JaxbUtil {

    private static final String DEFAULT_ENCORDING = "UTF-8";
    //TODO:定义一个工具类注意什么 ； 在多线程场景下呢？
    /***1.构造方法不可调用(有的方法中必须覆写可以抛出异常 父类) + final   vs 单例 单例模式的类是普通的类，它具有面向对象的特性，方便扩展
                对于有配置的工具类，可以轻松的创建多个不同配置的单例对象（想起我主导的另一个项目就存在5-6个redis数据源，如果使用静态类就是灾难）
     *  2.使用重载编写衍生函数组
     *  3.使用父类/接口 参数ArrayList - list - collection / string - charsequence
     *  4.定义自己的工具类，尽量不要在业务代码里面直接调用第三方的工具类
     *
     *  ***/

    private JaxbUtil(){}

    /**
     *  写完代码后再写注释，因为可能中间由参数添加等，但是大概的思路定好了
     * @param obj
     * @param encording
     * @return
     */
    public static String convertBeanToXML(Object object){
        return convertBeanToXML(object, DEFAULT_ENCORDING);
    }


    public static String convertBeanToXML(Object obj , String encording){

        try {
            //看到别人的代码，先看懂，建立流程图，在自己敲，其中可以学到许多，而不是直接对着抄

            //需要到obj还用什么clas呢！
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT , true);
            //有时候不知道如何工业级开发，就结合已有的api反向注意，自己定义的时候模仿
            marshaller.setProperty(Marshaller.JAXB_ENCODING , encording);
            StringWriter writer = new StringWriter();
            //可以先根据需要写出需要的变量、方法、idea会提供提示去建立本地变量还是参数...再去思考合理性
            marshaller.marshal(obj, writer);
            return writer.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        //这里返回什么合理呢？-- 详见下面 ，提前定义返回，统一，但着不影响分支中断逻辑 . 同样是进来就写return ret idea自动提示创建规则
        return "";
    }

    public static <T> T convertXmlToBean(String xml , Class cls){
      /*  T ret = null ;
        return ret ;*/

        T ret = null ;
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(cls);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ret = (T) unmarshaller.unmarshal(new StringReader(xml));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return ret ;
    }


    //TODO:schema校验
    public static boolean ValidatorFromSchema(File xmlFile , File schemaFile){

        boolean ret = Boolean.TRUE;


        return ret ;
    }

}
