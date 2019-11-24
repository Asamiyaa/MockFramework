package com.utils.convert.XStream;

import com.thoughtworks.xstream.XStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author YangWenjun
 * @date 2019/8/15 17:48
 * @project hook
 * @title: XMLUtils
 * @description:https://www.jianshu.com/p/9e31eeccc485
 */
public class XMLUtils {
        public static Object toBean(Class<?> clazz, String xml) {
            Object xmlObject = null;
            XStream xstream = new XStream();
            xstream.processAnnotations(clazz);
            xstream.autodetectAnnotations(true);
            xmlObject= xstream.fromXML(xml);
            return xmlObject;
        }

    public static void main(String[] args) throws IOException {
        Resource resource = new ClassPathResource("citylist.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) !=null) {
            buffer.append(line);
        }
        br.close();
        // XML转为Java对象
         CityList cityList = (CityList)XMLUtils.toBean(CityList.class, buffer.toString());
        //return cityList.getCityList();
        //for (City city :
//                cityList.getCityList()) {  //使用main...不行 lockbok不识别  注解不识别
           // System.out.println(city);

        //}
    }
}
