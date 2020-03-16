import com.core.constant.BaseResultTypeEnum;
import com.core.constant.xxConstant;
import com.exception.ServiceCheckException;
import com.utils.EnumHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * @author YangWenjun
 * @date 2019/11/8 10:01
 * @project MockFramework
 * @title: Main
 * @description:
 */
public class Main {

    static String s;
    public  static  Object obj ;

    public static <T> void main(String[] args) throws ServiceCheckException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        System.out.println((String) obj);//null可以强转

        //BigDecimal bigDecimal = new BigDecimal("1000000000000000.1234567");
        BigDecimal bigDecimal = new BigDecimal(Math.pow(0.2334444444444444444444444444444444444444444444
                , 13.4422222224444444444444444444444444));
        System.out.println(bigDecimal.toString());  //3.21353312349286154870071688361386763599369942312478087842464447021484375E-9
        System.out.println(bigDecimal.toPlainString());//0.00000000321353312349286154870071688361386763599369942312478087842464447021484375


        //枚举
        Class<?> enumCls = Class.forName("com.core.constant.BaseResultTypeEnum");
        enumCls.getEnumConstants();
        T[] t = (T[]) enumCls.getEnumConstants();
        for (T tt :
                t) {
            System.out.println("--------------------");
            System.out.println(t.length);  //通过限制T extends 来可以有api引入
            //t.
            System.out.println("-------中国人-------------");
        }


        //test annotation
        try {

            Class cls = Class.forName("com.core.Annotation.TestAnnotation");
            System.out.println(cls.getAnnotatedInterfaces()+"0000");
            for (AnnotatedType atype :cls.getAnnotatedInterfaces()) {
                System.out.println(atype+"---99---");

            }

            System.out.println(
                  cls.getName()+"--"+         //com.core.Annotation.TestAnnotation
                    cls.getSimpleName()+"--"+    //TestAnnotation    不能作为强转 因为不是class 而是string
                          cls.getCanonicalName()+"--777-"+
                    cls.getDeclaredFields()+"---"+
                            cls.getDeclaredMethods()+"---"

            );

            Field[] fields = cls.getDeclaredFields();
            for (Field f : fields) {

                System.out.println("name is " + f.getName());
                System.out.println("f is " + f);
//                f.get

            }

            Method[] methods = cls.getDeclaredMethods();
            for (Method m : methods) {

                System.out.println("name is " + m.getName()); //code
                System.out.println(" is " + m);               //public abstract int com.core.Annotation.TestAnnotation.code()
                System.out.println(m.getDefaultValue() + "--"); //方法有默认值  属性没有
                System.out.println(m.getClass());              //class java.lang.reflect.Method 区分开 名字code

                //获得固定值

            }
            //type是name的更高一级，name获得该对象类名（不是对象名），type获得是类型
            System.out.println(cls.getClass()); //class java.lang.Class
            System.out.println(cls.getTypeName());
            System.out.println(cls.isAnnotation()); //true
            System.out.println(cls.getAnnotations());

            for (Annotation an : cls.getAnnotations()) {   //得到它身上的注解
                System.out.println(an.annotationType()); //interface java.lang.annotation.Target
                //interface java.lang.annotation.Retention
                for (Annotation ann : an.getClass().getAnnotations()) { //嵌套 ，每个描述元素本身一个对象 有自己的内容
                    System.out.println(ann.annotationType() + "===");  //为什么没有jdk内部的实现注解呢？
                    //System.out.println(ann.annotationType() instanceof Annotation);
                    Class clas= an.annotationType();
                    Class cclas = an.getClass();
                }

            }


        } catch (ClassNotFoundException e) {


        }


        String[] str = {"1", "2"};


        /*System.out.println(EnumHelper.getMsgByCode(BaseResultTypeEnum.class, 0));

        System.out.println(xxConstant.STUDENT_NUM);
        System.out.println(BaseResultTypeEnum.PARAM_ERROR.getCode());
        System.out.println(BaseResultTypeEnum.PARAM_ERROR.getMsg());
        System.out.println(BaseResultTypeEnum.PARAM_ERROR.name());
        System.out.println(BaseResultTypeEnum.PARAM_ERROR.ordinal());
        for (BaseResultTypeEnum bt :BaseResultTypeEnum.values()) {
            System.out.println(bt);
        }
        System.out.println(BaseResultTypeEnum.PARAM_ERROR.getMsgByCode());*/

        System.out.println(getMiddle(new Integer[]{1, 2, 3, 4}));
        System.out.println(getMiddle(new String[]{"a", "b", "c"}));


        //throw new ServiceCheckException("核对不正确");
        /*try {
            s.toUpperCase();
        } catch (Exception e) {
            ServiceCheckException ex = new ServiceCheckException("核对不正确",e);
            throw  ex ;
        }*/


    }


    //泛型   --抽象某个公共规则   "返回该数组的中间对象"
    public static <T> T getMiddle(T... t) {
        return t[t.length / 2];
    }
}
