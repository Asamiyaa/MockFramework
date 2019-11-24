import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.理解泛型的位置 普通处理类   --  泛型（为约束同时提供灵活性，）  -- Object
 * 2.强制类型转换的场景 4
 * 3.反射 Class类相关操作 *****
 * 4.泛型中具体的约束和实现，泛型擦除等
 */
public class Test {
    public static void main(String[] args) throws Exception {
        //A a = new A();
        //a.setAge(12);
        //System.out.println((String)a.getAge()); //强制类型转换是在可以转换的前提下(instans of 父类指向子类)，才能执行

        //a.setName("yyy");
        //System.out.println((int)a.getName());  //1. 这种的不属于父子关系，在编译时就会报错。

        //a.setObj(ooo);
        //a.setObj("ooo");
        //System.out.println((int) a.getObj());   //2. 因为都是obj子类，所以编译不报错，运行时会报错，因为ooo不可能转为数字

        //A a1 = new B();
        //System.out.println(a1 instanceof B);      //3. TRUE
        //System.out.println((B) a1);               //   遵循父子关系，并且父类引用指向子类(向下转型) 才可以转

       C c =  new C();
       //c.method1("FirstTest");                   //class java.lang.String
       //c.method1(1);                             //class java.lang.Integer

        /*  for (Integer i :c.method2_1("1,2,3" ,Integer.class)
             ) {
            System.out.println("---"+i);
        }; */
        for (Long  i :c.method2_1("1,2,3" ,Long.class)
             ) {
            System.out.println("---"+i);
        };

    }
}

class C{
        /**
         * 泛型方法
         */
            <T> void method1 (T t){
                System.out.println(t.getClass());
            }

        /**
         * 返回值中返回泛型
         */
            <T> T method2(T t){
                return  t ;
            }
            //下面是示例
             <T extends Object> List<T> method2_1(String str , Class<T> cls) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
                    String[] strArray = str.split(",");
                   // List<T> ls = new ArrayList<T>(); --和后面的矛盾integer
                    List ls = new ArrayList();
                    System.out.println(cls.getName()+"---------");//cls.getName返回的String
                 for (String oj: strArray                        //向上转型 便于后面的向下转型  --》 然而并没有卵用
                      ){
                     //ls.add((T)oj)}; //  canot cast obj to T
                     // ls.add((cls.getName())oj);
                     // ls.add(cls.cast(oj));                   4.Cannot cast java.lang.String to java.lang.Integer  只是兼容，并没有真正将内容变为Object
                     // instanceOf判断  基本类型分类处理问题：这里只能分类吗，有没有可以直接的  抛异常
                     /*if(cls == Integer.class){
                            ls.add(Integer.parseInt(oj));//可以优化，因为他们调用公用的parseXX方法  --反射
                     }*/
                     String method  = "parse"+cls.getSimpleName().substring(0,1).toUpperCase()
                             + cls.getSimpleName().substring(1) ;       //parseInt
                     Method m  = cls.getDeclaredMethod(method , String.class);
                     ls.add(m.invoke(cls.getName(),oj));    //由类似Long.parseLong(str)来完成
                 }
                 return ls ;
             }

        /**
         * Class<T>传递  反射和类型转换
         */


        /**
         * T[]
         */
}


class A{
    private  Object obj ;
    private String name ;
    private Integer age ;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

class B extends  A{


}