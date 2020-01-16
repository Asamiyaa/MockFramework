package com.log.annotation;

/**
 * @author YangWenjun
 * @date 2019/10/16 9:33
 * @project hook
 * @title: AnnotationUtils
 * @description:
 */

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *      0.反射整体https://www.processon.com/diagraming/5dc3eac4e4b0335f1e52d611
 *      1.反射基本 Class - Filed - Method - con..
 *
 *      2.反射 + 泛型   Type
 *           1.Java reflect Type类及其子类用法分析  : https://blog.csdn.net/fang_qiming/article/details/78155271
 *
 *      3.反射 + 注解  Annotation
 *           1.
 *
 *
 *
 *
 *
 *
 *
 *
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
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        /*Student student = new Student();
        student.setMoney(100);
        AnnotationUtils.volidate(student);*/

        //测试反射和泛型
        //new AnnotationUtils().testGenericAndRef();

        //测试反射和注解
        new AnnotationUtils().testAnnotationAndRef();

    }

    /**--------------------反射+泛型---------------------------------------**/
        public void testGenericAndRef() throws NoSuchFieldException {

            //------ParimeterizedType   list<T>
            Student1 student1 = new Student1() ;
            Field courses = student1.getClass().getDeclaredField("courses");
            Type genericType = courses.getGenericType(); //generic 通用 - 泛
            System.out.println(genericType.getTypeName()); //java.util.ArrayList<java.lang.String>
            if(genericType instanceof ParameterizedType){ //ParameterizedType 就是 List<String>
               //  (ParameterizedType)genericType  -- 强转     raw 原
                System.out.println(ParameterizedType.class.cast(genericType).getRawType());   //class java.util.ArrayList
                System.out.println(ParameterizedType.class.cast(genericType).getActualTypeArguments()[0]); //class java.lang.String


            //------genericArrayType  T[]
                Field tt = student1.getClass().getDeclaredField("tt");
                Type genericType1 = tt.getGenericType();
                String typeName = genericType1.getTypeName();
                System.out.println(typeName);              //T[]
                if(genericType1 instanceof GenericArrayType){
                    System.out.println(((GenericArrayType) genericType1).getGenericComponentType().getTypeName());// T
                }

            //------TypeVariable   T extends Map
                Field ll = student1.getClass().getDeclaredField("ll");
                Type genericType2 = ll.getGenericType();
                //TypeVariable tv = (TypeVariable)genericType2; 先获取到param..Type ,在获取参数
                ParameterizedType pt  = (ParameterizedType)genericType2;
                Type[] types=pt.getActualTypeArguments();
                TypeVariable typeVariable=(TypeVariable)types[0];
                Type[] types2=typeVariable.getBounds();
                for(Type type : types2){
                    System.out.print(type + " ");
                }

            //------WildcardType  ? extends Number / ? super Integer

            }
        }

    /**--------------------反射+注解---------------------------------------**/
    public void testAnnotationAndRef() throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        Student1 student1 = new Student1();
        System.out.println(student1.getClass().getAnnotations()[0].annotationType()); //interface com.log.annotation.foramt
        System.out.println(student1.getClass().getDeclaredField("ann").getDeclaredAnnotation(foramt.class)); //@com.log.annotation.foramt(name=b, length=1)

        foramt foramt = student1.getClass().getDeclaredField("ann").getDeclaredAnnotation(foramt.class);
        System.out.println(foramt.name()); //***获取对应的注解的值**8
        //foramt.annotationType()  获得该注解的class对象 ，之后使用 Class方法  --- 获取都是 注解对象/属性对象

        //遍历获取属性列表 -- 遍历注解方法 -- 获取对应值   和  当前对象的值进行对比  <-- 工具类 . 上面定义了每个注解具体的比对逻辑 反射获取
        System.out.println(foramt.annotationType().getDeclaredMethods()[0].getName());
        System.out.println(foramt.annotationType().getDeclaredMethods()[0].getDefaultValue()); //默认值/当前值

        Field[] fields = student1.getClass().getDeclaredFields();
        for (Field f :
                fields) {
            Annotation[] annotations = f.getAnnotations();
            if(annotations == null){ continue; }
            for (Annotation ann :
                    annotations) {
                //-----省略
                String typeName = ann.annotationType().getTypeName();
                if(typeName.equals("foramt")){
                    //doForamt(f.get(student1),((foramt)ann).name());
                    //doForamt(f.get(student1),ann);
                    //doForamt(f,ann);
                    //--通过反射获取对应的注解类，并获取相关方法。这样的可以去掉if.
                }else if(typeName.equals("format1")){
                    doForamt1();
                }
                //---  省略

                //放到缓存中 -- 提供对应的类 包装该内部结构并提供获取和对比方法

                String annName = AnnHolder.getAnnName(ann);
                //反射获取对应的注解中的doValidate()来 。 所以注解都应该实现一个接口
                Method mm = Class.forName(annName).getMethod("doValidate",null);
                Map ret = (Map)mm.invoke(this, "", "");//对应的对象

               // if(ret){} 判断是否进行下一个循环校验


            }
            
        }
    }

    private void doForamt1() {
    }
//将这个对比逻辑放到对应的注解中，定义方法。因为他们更近
    private void doForamt(Field f , Annotation ann) {
        
    }


}
@foramt(length = 1,name = "b")
class Student1<T extends Map >{

    private  String  name = "student1";
    private  ArrayList<String> courses ;
    private T[] tt ;
    private ArrayList<T> ll ;
    @foramt(length = 1,name = "b")
    @foramt2
    private String ann ;


}

@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)  //定义枚举 也是作为控制的一种
@interface foramt{
    int length() default 0 ;
    String name() default "a";
}

@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)  //定义枚举 也是作为控制的一种
@interface foramt2{
    int length() default 0 ;
    String name() default "a";
}

class AnnHolder {

    //不提供
    /*public ArrayList getAnnLiist() {
        return annLiist;
    }
*/
    //TODO :扫描所有的属性获得对应的注解 --> spring ??
   static  ArrayList<String> annLiist = new ArrayList(Arrays.asList(new String[]{"foramt", "format1"}));

    public static String getAnnName(Annotation annotation){
        for (String ann :
                annLiist) {
           if(ann.equals(annotation.annotationType().getName())){
                return  ann ;
           }
        }
        return null ;
    }

}