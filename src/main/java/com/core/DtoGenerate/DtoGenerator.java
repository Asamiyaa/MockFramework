package com.core.DtoGenerate;

import com.core.DtoGenerate.AsmImpl.AsmGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author YangWenjun
 * @date 2019/11/1 11:01
 * @project MockFramework
 * @title: DtoCreater
 * @description:文件中的配置属性生成对象
 * TODO:是否反序列化到本地
 * 反射：获得运行时数据进行操作。比如比较、赋值、判断  详见hook中自定义注解
 */
public class DtoGenerator implements IDtoGenerate {

    public Object createDto(){

        return doCreate(load());
    }
    //
    private Map load(){
        Properties properties = new Properties();

        try (InputStream  in = new FileInputStream("resources/ces0GBK")) { //配置化
            properties.load(in);
            return properties;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null ;
    }
    //强转为最后的类型
    private Object doCreate(Map keyValMap) { //修改名字
        //keyValMap.entrySet();

        //调用第三方创建类  不是反射  不是map - 不是数据结构（先准备好一个对象，按照结构存放）
        //根据配置文件动态生成JAVA类：https://www.iteye.com/blog/sheng-647661

        //具体实现参考GeneratorBeanMain.java

        Map classPath = GeneratorFactory.getInstance().Generator();

        try {
            Class<?> initObj = Class.forName((String) classPath.get("classPath"));
            //从前台接受值，赋值， 到后面的 bean - xml 映射
            return initObj.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        keyValMap.forEach((key, value) -> {

        });
        return null;
    }

}
