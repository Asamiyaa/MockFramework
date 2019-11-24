package com.log.annotation;

/**
 * @author YangWenjun
 * @date 2019/10/16 9:33
 * @project hook
 * @title: AnnotationUtils
 * @description:
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * spring 学习中 - 造轮子
 *          先定义流程  简写和脑海
 *          整洁
 *
 *          涉及：注解(定义-对应属性方法获取api)
 *                反射(真正理解高一层抽象 / 动态 获取当前调用对象的属性，方法，并对其做对应的操作逻辑 api--> proxy)
 *                     获取obj的值，或者注解，获取注解的值
 *
 *
 *           编写中的问题：1.没有思路 流程
 *                         2. 对api不熟悉导致 调试 (必要的return .. 初始化..参数 class<T> .[])
 *                         3.调试定位  sysout ...debug...
 */
public class AnnotationUtils {

    private  static Map<String,Object>  result = new HashMap<>();

    {
        result.put("result", true);
    }

    public static Map<String,Object> volidate(Object bean) throws IllegalAccessException, InstantiationException {
        //遍历对象属性 or  方法
        Field[] declaredFields = bean.getClass().getDeclaredFields();

        for (Field field:declaredFields) {

            Annotation[] annotations = field.getAnnotations();
            //善于写中断 return 也是  不要强调return 同一个值，这样就导致后面很多不必要的判断 ，可能还涉及数据加载
            if( annotations == null ){ continue; }
            //想到于一个类来处理
            for (Annotation annotation:annotations) {
                //当不确定api时，通过输出进行论证
               /* System.out.println(annotation); //@com.log.annotation.MaxNumber(name=money, maxNumber=99.99)
                System.out.println(annotation.annotationType());//interface com.log.annotation.MaxNumber
                System.out.println(annotation.annotationType().getSimpleName());*/
                /**
                 * 去掉对具体方法的判断，有设计模式、反射、重新数据结构算法
                 */
               // if("MaxNumber".equalsIgnoreCase(annotation.annotationType().getSimpleName())){
                    //annotation.annotationType()类似于获得了xx.class ，xx指的就是具体的对象
                    //注解的内容时方法
                    //System.out.println(annotation.annotationType().getDeclaredMethods().length);
                    Method[] declaredMethods = annotation.annotationType().getDeclaredMethods();
                    for (Method method :declaredMethods) {
                        if("name".equalsIgnoreCase(method.getName())){
                            continue;
                        }
                        //判断是否有注解修饰 ， 获取注解，获取该注解的值，获取该注解修饰的属性值  - 反射  运行时数据获取和处理

                        AnnotationUtils annotationUtils = AnnotationUtils.class.newInstance();
                        try {
                            /*Method maxNumber = annotationUtils.getClass().getDeclaredMethod("maxNumber",bean  Field.class);
                            Object invoke = maxNumber.invoke(annotationUtils, field);*/

                            //Method m = annotationUtils.getClass().getDeclaredMethod(method,new Class[]{Object.class,Field.class});
                            Method m = annotationUtils.getClass().getDeclaredMethod(method.getName(),Object.class,Field.class);
                            result =(Map<String,Object>)m.invoke(annotationUtils, bean ,field);
                            if(result.get("result").equals(false)){
                                System.out.println("xxxxresutl");
                                return result;
                            }

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
               // }else if(false){}
            }


        }
        //

        //TODO :在主流程中调用解析方法 - spring refresh()

        return null ;
    }

    //在该类中实现判断逻辑即对比注解规则和当前field值
    public Map<String,Object> maxNumber(Object obj ,Field field) throws IllegalAccessException {
        MaxNumber maxNum = field.getAnnotation(MaxNumber.class);
        /**
         * 访问权限
         */
        field.setAccessible(true);
        System.out.println("----->");

        Object o = field.get(obj);
       // if(Double.valueOf((String)
        // o).compareTo(maxNum.maxNumber())!= 0){
        if(Double.valueOf(o.toString()).compareTo(maxNum.maxNumber())!= 0){
            //map 记录msg 和 result true /false 返回
            result.put("result", false);
            result.put("msg", "wrong cause");
            //这里如果没有走if,则外面需要else . 所以可以通过初始化，得到一个值。不用再每个else去添加，只要修改就好。获取了无需判断
            //初始化成员直接初始化进去
            return  result;
        }
        return null ;
    }
    /**
     * util测试 直接main
     * @param args
     */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Student student = new Student();
        student.setMoney(100);
        AnnotationUtils.volidate(student);
    }



}
