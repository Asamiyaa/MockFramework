/*
package com.xmlPlatform;

import com.xmlPlatform.com.fengyilin.hr.schemas.EmployeeType;
import com.xmlPlatform.com.fengyilin.hr.schemas.HolidayRequest;
import com.xmlPlatform.com.fengyilin.hr.schemas.HolidayType;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

*/
/**
 * @author YangWenjun
 * @date 2019/12/14 11:44
 * @project MockFramework
 * @title: jaxbTest
 * @description:
 *//*

public class jaxbTest {

    public static void main(String[] args) {

        HolidayRequest request = new HolidayRequest();
        EmployeeType employee = new EmployeeType();
        employee.setFirstName("feng");
        employee.setLastName("yilin");

        request.setEmployee(employee);

        HolidayType holiday = new HolidayType();
        //需要手工替换生成的类型不符合情况
        holiday.setEndDate(new Date());
        holiday.setStartDate(new Date());

        request.setHoliday(holiday);

        System.out.println(JaxbUtil.convertBeanToXML(request));
    }

    */
/**
     * 通过反射轮询对应目录下的类就是该报文对应的类，--> 如何知道类之间的嵌套关系呢？
     *  1.解析xsd只要知道结构就好 - 根据schema生成一个xml形式的嵌套bean对象用于接受前台信息
     *          1.解析xml schema 而不是 绑定此时。就是为了清楚结构
     *          2.因为对应的类已经在上一步生成了，所以说创建对应属性。 生成xml 类 --> 类生成
     *      使用xpath实现 定位类似于 正则表达式 。组装自己的传值处理bean,但是这里无法简单的组装xml
     *      实现：开始于<xs:element name  中间可能存在多存嵌套
     *           结束于 xs:complexType name xs:element name type="xs:integer"
     *      先sysout 验证处理逻辑
     *
     *    generator生成对应的bean对象并加载
     *
     *
     *  2.配置文件解析 这样的话其实客户清晰了知道了xml结构，有这个必要吗？到这一步是不是都可以自己上传个报文模板了以及
     *    第一版的各种定义规则，而不是使用schema.
     *//*



    public String parseXsdToDO(File xsd){
        String ret = "";
        //后两个标识退了一层向上，可以定位当前父子关系
        List<String> xpaths = Arrays.asList(new String[]{"xs:element name","xs:complexType","/>","</xs:element>"});
        try {

            BufferedReader reader = new BufferedReader(new FileReader(xsd));
            String str , xpath ;
            while((str = reader.readLine()) != null){
                //判断是否符合xpath的条件行，)否则跳过
                if((xpath = picker(str,xpaths))!= null){
                    buildDO(str,xpath);
                }
            }

            System.out.println(objPropers);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret ;

    }

    //先创建一个map,查看是否可以解析到正确东西
    HashMap objPropers = new HashMap();
    HashMap nameValPair = new HashMap();
    AtomicInteger deep = new AtomicInteger();
    private void buildDO(String str, String xpath) {
        //map维护父子关系
        //先判断是否有父亲node +1 代表进了一层而不是平行一层
        if(xpath.equals("/>") || xpath.equals("</xs:element>")){
            deep.decrementAndGet();}else{deep.getAndIncrement();}

        //String proper = str.split("\"")[0];
        String[] ss = str.split("\"");
        if(ss.length > 1){
            //深度 字段类型和字段值(为了好映射)
            objPropers.put(deep, nameValPair.put(ss[1],ss[3].split(":")[1]));
        }else{
            //类名
            objPropers.put(deep,ss[1]);
        }
    }

    private String picker(String str ,List<String> xpaths) {
        String ret = null;
        for ( String xpath: xpaths) {
            //这里不是contains这么简单吧，是否需要xml解析的东西
            if(str.contains(xpath)){
                return ret = xpath;
            }
        }
        return ret ;
    }
}
*/
