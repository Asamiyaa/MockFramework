package com.secerity;

import com.log.annotation.AfterLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YangWenjun
 * @date 2019/8/29 11:56
 * @project hook
 * @title: VolidateFieldAnno
 * @description:该方式用于校验log/annotation..
 */
public class VolidateFieldAnno {

    public static Map<String,Object> validate(Object bean , String group) throws NoSuchMethodException {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("msg", "");
        result.put("result", true);
        Class<?> clazz = bean.getClass();

        try{
            Field[] fields = clazz.getDeclaredFields();
            for (Field fd :
                    fields) {
                fd.setAccessible(true);
                Object value = fd.get(bean);
                Annotation[] annotations = fd.getAnnotations();
                for (Annotation anno :
                    annotations) {
                    Class<? extends Annotation> aClass = anno.annotationType();
                    List list = Arrays.asList(getGroup(aClass,fd));
                       if(list != null && list.contains(group)){
                           Method[] medArr = aClass.getDeclaredMethods();
                           for (Method mtd:
                                medArr) {
                               String methodName = mtd.getName();
                               if("group".equals(methodName) || "filedName".equals(methodName)){
                                   continue;
                               }
                               //VolidateFieldAnno volidateFieldAnno = VolidateFieldAnno.class.newInstance();
                               /**注解的实际校验规则其实在----这里----**/
                               Object obj = VolidateFieldAnno.class.newInstance();
                               try{
                                   Method m = obj.getClass().getDeclaredMethod(methodName, Object.class,Field.class);
                                   result = (Map<String ,Object>)m.invoke(obj,value,fd );
                                   if(result.get("result").equals(false)){
                                       return result ;
                                   }
                               }catch (Exception e){}
                           }
                       }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
       return null ;
    }

    private static Object getGroup(Class<? extends Annotation> aClass, Field fd) {
        String[] group = {};
        if(AfterLog.class.getName().equals(aClass.getName())){
            AfterLog annotation = fd.getAnnotation(AfterLog.class);
            group = annotation.group();
        }
        //....
        return group ;
    }

    /***这才是真正的注解逻辑校验**/
    public static Map<String , Object> maxNumber(Object value , Field field){
        Map<String,Object>  result = new HashMap<>();
        result.put("msg", "验证通过");
        result.put("result", true);
        /**----下面内容是需要的----**/
        //MaxNumber an = field.getAnnotation(MaxNumber.class);
        //if(value != null && Double.valueOf(value.toString())>an.maxNumber()){
        /*  result.put("msg",..);
            result.put("result", false);*/
        //}
        return  null ;
    }
}
