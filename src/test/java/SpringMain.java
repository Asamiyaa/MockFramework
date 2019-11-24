
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * @author YangWenjun
 * @date 2019/7/26 16:45
 * @project hook
 * @title: SpringTest
 * @description:
 *
 *
 *      1.spring - springboot - springCloud - spring家族体系架构
 *      2.-共性抽象（领域模型）
 *              复杂业务流程模型
 *              订单模型
 *              ..
 *          - 类之间关系( 继承-实现-依赖-关联-组合-聚合)
 *              -  六大原则(https://blog.csdn.net/fragmentalice/article/details/82460939)
 *
 *                      单一职责(-避免业务逻辑-)
 *                          实现高内聚、低耦合的指导方针，它是最简单但又最难运用的原则，需要设计人员发现类的不同职责并将其分离，
 *                          而发现类的多重职责需要设计人员具有较强的分析设计能力和相关实践经验。
 *                          包装对象 层级关系
 *
 *                      接口隔离（接口单一职责）
 *                          使用多个专门的接口，而不使用单一的总接口，即客户端不应该依赖那些它不需要的接口。
 *                          我们需要注意控制接口的粒度，接口不能太小，如果太小会导致系统中接口泛滥，不利于维护；接口也不能太大，太大的接口将违背接口隔离原则，灵活性较差，使用起来很不方便。
 *
 *                      开闭
 *                          一个软件实体应当对扩展开放，对修改关闭。即软件实体应尽量在不修改原有代码的情况下进行扩展。
 *                          代码发展过程中，需要在源代码基础上增加if .
 *                          xml和properties等格式的配置文件是纯文本文件，只涉及这些修改。是符合开闭的
 *                          成员 - 配置文件 - 数据库读取 、 类之间接口中定义具体实现哪个，由外部调用时，指定子类
 *                          修改已有代码陷阱 - 测试范围 隔离性降低
 *
 *                      迪米特
 *                          一个软件实体应当尽可能少地与其他实体发生相互作用。
 *                          迪米特法则要求限制软件实体之间通信的宽度和深度
 *                          应该尽量减少对象之间的交互，如果两个对象之间不必彼此直接通信，那么这两个对象就不应当发生任何直接的相互作用，
 *                          通过引入一个合理的第三者来降低现有对象之间的耦合度。
 *                          转发对象Mediator
 *
 *
 *                      依赖倒转
 *                          依赖倒转原则要求我们在程序代码中传递参数时或在关联关系中，尽量引用层次高的抽象层类，即使用接口和抽象类进行变量类型声明、参数类型声明、方法返回类型声明，以及数据类型的转换等
 *                          程序中尽量使用抽象层进行编程，而将具体类写在配置文件中，这样一来，如果系统行为发生变化，只需要对抽象层进行扩展，并修改配置文件，而无须修改原有系统的源代码，
 *
 *                      里氏替换
 *
 *                - 设计模式( 业务场景+微妙的对象间关系  | 多个组合在一起的 | 不是套用而是根据需求 )
 *                          设计模式之间的关系图 - spring中常用
 *                          设计模式 类实现      - 菜鸟教程 （类之间组合关系）
 *
 *
 *                  创建型：5
 *                  结构型：6+3
 *                  行为型：5+5
 *                  展现行：8
 *
 *
 *
 *       3.混合编程
 *              面向对象 + 面向过程(复杂大业务)
 *              java + sql（存储过程 视图..）+ shell + js ...
 *
 *
 *
 *
 *
 */


@Component
public class SpringMain extends springFathrer implements BeanFactoryPostProcessor,BeanNameAware,BeanFactoryAware,ApplicationContextAware,BeanPostProcessor
        ,InitializingBean,DisposableBean {

    private String str ;
    private static String str2 = "22";
    private  String str3 = "33";

    //真实项目中并不是将所有的对象都由spring管理，由的对象不再spring下 比如vo..
    //加载 而不是new   能否引用其他对象
    //java的static块执行时机:https://blog.csdn.net/berber78/article/details/46472789 静态属性方法调用也是会初始化
    //静态代码变量也是这个时候初始化

    static{
        System.out.println(str2+"--static do");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    {
        str2 = "223344";
        System.out.println(str+"--run do");
        System.out.println(str3+"--run do33");

    }

    public SpringMain() {
        System.out.println(str2+"----contructor do");

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("setBeanFactory do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext do ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * https://www.cnblogs.com/V1haoge/p/6106456.html --流程中的初始化，同样是和jdk一样，大概走了10步
     *
     *
     * BeanFactoryPostProcessor接口与BeanPostProcessor接口的作用范围是整个上下文环境中，使用方法是单独新增一
     * 个类来实现这些接口，那么在处理其他Bean的某些时刻就会回调响应的接口中的方法。所以说这里的bean再这两个接口上是为了
     * 容器做贡献了。所以后面不停的postProcessBeforeInitialization 是再初始化其他实例
     *
     * BeanNameAware、BeanFactoryAware、ApplicationContextAware的作用范围的Bean范围，即仅仅对实现了该接口的指定Bean有效，
     * 所有其使用方法是在要使用该功能的Bean自己来实现该接口。
     *
     * 1.那么如何选择合适的初始化时间 jdk or spring接口 / 分别场景是啥？
     * 2.引用过程中顺序问题，是否由空指针？
     * 3.https://blog.csdn.net/javaloveiphone/article/category/1275333/1?流水号
     * 4.其他点https://blog.csdn.net/EthanWhite/article/details/52445346
     *  http://blog.didispace.com/books/spring-framework-4-reference/III.%20Core%20Technologies/Container%20Extension%20Points.html
     *  https://blog.csdn.net/javaloveiphone/article/details/52143126
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization do ");
        try {
            // Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization do ");
        try {
            // Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean; //return null 导致一致执行

    }

    public void setStr(String str) {  //不会调用
        System.out.println("---setStr---");
        this.str = str;
    }

    /**
     * 各个扩展的使用场景 - bean 初始化/实例化
     *  1.哪些使用注入方式，哪些使用new  , 是否再顺序上有问题 。 如何选择static/构造中 vs spring相关接口中。对于使用来说是否没太大意向
     *  2.
     *  3.
     *
     *
     */

    /**
     * 集成其他模块/框架时扩展
     *  1.FactoryBean (配合策略)/InstantiationAwareBeanPostProcessor
     *  2.ContextLoaderListener - context  SessionAwareMessageListener - jms ...quartz ... 开关判断。switch -->数据库表状态 tunninyg./jvmnode ..
     *  3.
     *
     */
}


/**
 * 设计模式
 *      1.场景  - 不使用模式造成的混乱(必要性)
 *      2.默写代码  推进演变
 *
 *          1.类 属性  方法 修饰符对比  （interface enum annotation）
 *               类：   访问权限(public default )                         其他(final abstract )
 *               属性:  访问权限(public protect default  private)         其他(static final volatile transient)
 *               方法： 访问权限(public protect default  private)         其他(synchronized static final abstract native)
 *
 *
 *          2.对象实例化顺序
 *           Person p=new Person("zhangsan",20);

                 1.在栈内存中()，开辟main函数的空间，建立main函数的变量 p
                 所有的操作在方法中，也就是必须在栈中
                 栈来自于线程   一个进程多个线程   线程之间没有关系的可以多线程执行
                 2.加载类文件：因为new要用到Person.class,所以要先从硬盘中找到Person.class类文件，并加载到内存中。
                 加载类文件时，类的所有信息都会加载除非静态变量
                 记住：加载，是将类文件中的一行行内容存放到了内存当中，并不会执行任何语句。---->加载时期，即使有输出语句也不会执行。
                 静态成员变量（类变量）           ----->方法区的静态部分
                 静态方法                         ----->方法区的静态部分
                 非静态方法（包括构造函数）       ----->方法区的非静态部分
                 静态代码块                       ----->方法区的静态部分
                 构造代码块                       ----->方法区的静态部分

                 注意：在Person.class文件加载时，静态方法和非静态方法都会加载到方法区中，只不过要调用到非静态方法时需要先实例化一个对象，
                 对象才能调用非静态方法。如果让类中所有的非静态方法都随着对象的实例化而建立一次，那么会大量消耗内存资源，
                 所以才会让所有对象共享这些非静态方法，然后用this关键字指向调用非静态方法的对象。
                 （对象唯一的特征就是非静态变量，其他全部来自方法区的共享）
                 3.执行类中的静态代码块：如果有的话，对Person.class类进行初始化。（这里的初始化是什么意思？）用于给类初始化，类加载时就会被加载执行，只加载一次。

            --new --的影响---

                4.开辟空间：在堆内存中开辟空间，分配内存地址。
                 5.默认初始化：在堆内存中建立 对象的特有属性，并进行默认初始化。（null  / 0 等值）
                 6.super()
                 7.显示初始化：对属性进行显示初始化。 （定义类时int i = 1 ;）
                 8.构造代码块：执行类中的构造代码块，对对象进行构造代码块初始化。  方法之外的语句       结构：{  System.out.println("这里就构造代码块");}                                                                                                                    用于给对象初始化的。只要建立对象该部分就会被执行，且优先于构造函数。
                 9.构造函数初始化：对对象进行对应的构造函数初始化。(构造器)   给对应对象初始化的，建立对象时，选择相应的构造函数初始化对象。
                 10.将内存地址赋值给栈内存中的变量p。

            3.当考虑加载顺序时，如何和spring柔和，会报错  -> spring 初始化
                 1.
                 2.
 *
 */
/**********************************************************构造型**************************************************/

/**
 *  1.单例  内存占用/时间/安全
 */
class Testsingle{
    /**
     * 块并没有执行，是因为没有new吗?
     */
    static {
        System.out.println("静态块执行");//执行

    }
    {
        System.out.println("块执行");
    }

    public static void main(String[] args) throws CloneNotSupportedException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InstantiationException {

        /**
         * 1.多线程模拟恶劣环境 - 安全
         * 2.反射   当基于完整逻辑(正常+异常)，注意反射对代码安全性
         * 3.继承方法中super() 是否由需要 super(xx)  完全重写(Exception/spring) vs 加强(spring)
         */


        CyclicBarrier cb = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {

                    System.out.println("每个线程进来等待");
                    cb.await();
                    System.out.println("五个线程都到达时，所有的都开始执行");
                    Thread.sleep(1000);
                    //System.out.println("万箭齐发完成,对象地址是："+HungryLoad.getInstance());
                    //System.out.println("万箭齐发完成,对象地址是："+LazyLoad.getInstance());
                    //System.out.println("万箭齐发完成,对象地址是："+DCL.getInstance());
                    //System.out.println("万箭齐发完成,对象地址是："+RegOrInner.getInstance());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        //System.out.println(HungryLoad.getInstance());
        /**
         * 内部类可以存在哪些属性以及调用
         */
        TestPrototype  tp =  new TestPrototype();
        tp.setName("firstName");
        //TestPrototype prototype = tp.getPrototype();
        System.out.println(tp.getPrototype().getName());
        System.out.println(tp.getName());
        System.out.println(tp.getPrototype());
        System.out.println(tp);

        /**
         * builder
         */
        TestBuilder tb = new TestBuilder();
        System.out.println(tb.new Meal().addItem(tb.new Cola()).addItem(tb.new Ahurger()).getPrice());
        System.out.println("main线程完成");

        /**
         * proxy
         * 我们此刻是作为 客户端(调用参数规则)+构造者(真实对象和代理对象内部实现) ==> 对外提供时，代理
         */
        TestProxy testProxy = new TestProxy();
        testProxy.new ProxyObj(testProxy.new RealObj()).out();

        TestProxy.MyInvocation tm = new TestProxy().new MyInvocation(new TestProxy().new RealObj());
        TestProxy.I proxyClass = (TestProxy.I) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{TestProxy.I.class}, tm);
        //System.out.println(proxyClass.hashCode()+"------");proxyClass空是如何
        proxyClass.out();

        /**
         * adapter
         */
        TestAdapter.Bird bird = new TestAdapter().new Bird();
       // TestAdapter.Tiger tt = new TestAdapter().new Tiger(bird);
       // tt.kill();

        /**
         * filter
         */
        Person a = new Person("a",1);
        Person b = new Person("b",2);
        List list = new ArrayList<>();
        list.add(a);
        list.add(b);

        List  list1  = new ArrayList<>();
        list1.add("name");
        list1.add("equals");
        list1.add("a");
        List fList = new ArrayList();
        fList.add(list1);

        Rule rule = new Rule(list,fList);
        Filter filter = new Filter(list, rule);
        List list2 = filter.doFilter();
        System.out.println(list2.size());

        /**
         * template
         *     1,通过  上下文环境 或者  包规律获得路径 反射获取对象
         *     2.将实例获取放到工厂中
         *     3.根据不同的策略生成调用不同子类
         */
        sub o = (sub)Class.forName("com.sub").newInstance();
        o.play();
//        System.out.println(aClass);


        /**
         * listner
         */
        Obser A = new Obser("A");
        Obser B = new Obser("B");
        Obser C = new Obser("C");
        List l = new ArrayList();
        l.add(A);
        l.add(B);
        l.add(C);

        TestListner testListner = new TestListner(l,2);

        testListner.changeState(112233);
    }

    static class HungryLoad{
         /**
          * 1.内部类 static 外部类加载而加载  非static，需要创建外部类，在创建内部类
          * 2.非static内部类，不能有static变量。
          * 3.这里为了体现类加载，所以设置成static类
          */
        private static HungryLoad hl = new HungryLoad() ;
        private HungryLoad()  {
            //throw new IllegalAccessException("单例模式禁止客户创建实例");// 反射 - 如何判断反射进来的？
            /***
             * 思考这里的super(),没有的化，并没有创建对象。写不写都行，因为构造器特殊性，其他方法就不一样了
             */
            super();
            System.out.println("---HungryLoad---");
        }
        public static HungryLoad getInstance(){
            return hl;
        }
    }

    static class LazyLoad{

        private static LazyLoad ll;  //静态初始化和实例初始化都行，主要约束在方法调用访问。
        private LazyLoad() {}

        /**
         * 加锁   - 缩小锁的粒度(DCL)
         * @return
         */
        public static synchronized LazyLoad getInstance(){
            if(ll == null){
                ll = new LazyLoad();
            }
            return ll ;
        }
    }

    static class DCL{
        private static DCL ll;
        private DCL() {}
        static Object lock = new Object();
        static Object lock2 = new Object();


        /**
         * 加锁   - 缩小锁的粒度(DCL)  : 从最小最核心依次向上同步。先new不能完全控制住，在向上
         * @return
         *
         * 对比这种：
         *      private volatile static Singleton singleton;
                private Singleton (){}
                public static Singleton getSingleton() {
                    if (singleton == null) {
                    synchronized (Singleton.class) {
                    if (singleton == null) {
                    singleton = new Singleton();
                    }
                  }
                }
         */
       // public static synchronized LazyLoad getInstance(){
          public static  DCL getInstance(){
              /**
               * 静态-类变量 - 这里可以锁静态成员 - 若没有其他方法调用可以使用DCL.class 锁着整个类
               */
              synchronized (lock2) {
                  if (ll == null) {
                      /**
                       * 需要静态，只要保证是同一个就行，加载时间在这里无所谓的
                       */
                      synchronized (lock) {
                          ll = new DCL();
                      }
                  }
              }
            return ll ;
        }
    }

    /**
     * TODO :对比IOC 注册登记式
     */
    static class RegOrInner{
        //static  ConcurrentHashMap<Class,RegOrInner> syncMap = new ConcurrentHashMap();
        /**
         * 可以使用非线程安全，都是get操作
         */
        //static  HashMap<Class,RegOrInner> syncMap = new HashMap();
        /**
         * 因为没有new ,所以构造，赋值等非静态的是不会被调用的。--> 类.staticxx() 这里就会加载类。 区分开
         */
        /*使用内部类，懒加载
        static{
            syncMap.put(RegOrInner.class, new RegOrInner());
        }*/

        /**
         * 使用内部类来延迟加载
         */
        private static class SingleHolder{
            private static final RegOrInner ri = new RegOrInner();
        }
        private RegOrInner(){}

        public static RegOrInner getInstance(){
           //eturn syncMap.get(RegOrInner.class);
            /**
             * 每次调用都是类首次加载的信息，后面的调用也不会在多i执行static 成员，所以式同一个
             */
            return SingleHolder.ri;
        }
    }

    class EnumT{
        /**
         * 1.Enum和类定义一样，只是将实例对象写在enum的开头，并且可以enum.xx直接调用
         * 2.可以定义field method (对比)  注意enum的默认方法 重写了equal compare
         *
         *  RED,BLACK;
            private ....
         */
    }
}

/**
 * 原型：每次访问都需要'  新建 ' ， 新建对象间’存在关系‘ 。 如数据库连接
 * clone-安全性-地址  浅拷贝实现 Cloneable，重写  线程池技术，深拷贝是通过实现 Serializable 读取二进制流。
 * beanUtils(DTO / VO / MODEL ) 反射  --> 参考:XMLConvertUtil oxm / 模板模式回调   结合在一起使用的 / A_reflect(https://github.com/corPrgrm/blog/blob/master/TechPoint/1_Core%20Java/A_reflect)
 * 序列化
 */
 class TestPrototype implements Cloneable{
    private String name ;
    /**
     * 享元模式  父子节点
     */
    /**
     * java.lang.StackOverflowError
     * @return
     *  成员变量默认初始化时，形成循环
     *  模拟outOfMemory:创建字符串循环
     */
    //TestPrototype obj = new TestPrototype();

    /*public TestPrototype getPrototype() throws CloneNotSupportedException {
        return (TestPrototype)obj.clone();
    }*/

    /**
     * 保证每个线程都拿到不同的对象，可以加锁
     * @return
     * @throws CloneNotSupportedException
     */
    public synchronized TestPrototype getPrototype() throws CloneNotSupportedException {
        return (TestPrototype) this.clone();
    }

    /**
     * 浅拷贝：拷贝对象的应用来自被拷贝对象，所以他们的引用修改时联动的
       深拷贝：引用指向对象，拷贝对象间无关

      实现：
            1.每个引用都实现clone并重写clone().形成递归的clone
            2.序列化
                虽然层次调用clone方法可以实现深拷贝，但是显然代码量实在太大。特别对于属性数量比较多、层次比较深的类而言，每个类都要重写clone方法太过繁琐。
                将对象序列化为字节序列后，默认会将该对象的整个 ’ 对象图  ' 进行序列化，再通过反序列即可完美地实现深拷贝。
               1.每个对象图对象实现Serializable
               2. ByteArrayOutputStream bos=new ByteArrayOutputStream();
                 ObjectOutputStream oos=new ObjectOutputStream(bos);
                 oos.writeObject(stu1);
                 oos.flush();
                 ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
                 Student stu2=(Student)ois.readObject();

     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * factory  方法封装(无论内部，必须保证输出是正确的--各种控制，入参思考，异常-) --> 类 --> 工具类 --> 对象创建方法封装(工厂模式)
 * 场景：
 *             1、日志记录器：记录可能记录到本地硬盘、系统事件、远程服务器等，用户可以选择记录日志到什么地方。
 *             2、数据库访问，当用户不知道最后系统采用哪一类数据库，以及数据库可能有变化时。
 *             3、设计一个连接服务器的框架，需要三个协议，"POP3"、"IMAP"、"HTTP"，可以把这三个作为产品类，共同实现一个接口。
 *
 *
 *
 * TODO:springBeanFactory
 * TODO:工厂模式+策略-- 工厂本身也是单例  ---从设计模式关系图，可以扩展-----
 *
 */
class TestFactory{
    /**
     * 1.提供方法选择，而不是入参。client入参可能造成null  。 提供枚举控制
     * 2.扩展性
     *
     */
    public static A getA(){
       // return new A();
        // 其他细节
        return A.getInstance();
    }

    //单例实现
    static class A{
        //....
        public static A getInstance(){
            return new A();
        }
    }
}

/**
 * 超级工厂(生产实际使用的)
 *    提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。
 *    主要解决接口选择的问题。
 *    产品族 pc -> 鼠标+键盘+xx -> 各个厂商对应的实现。
 *    参考：https://www.runoob.com/design-pattern/abstract-factory-pattern.html    uml
 *
 *TODO:抽象类-->模板方法(也是定义相互间依赖并提供扩展)
 *TODO:      -->抽象工厂
 */
class TestAbstractFactory{

    abstract class AbstractFactory{
        /**
         * 其他业务逻辑
         */
        //bstract Shcpe getShcpe(ShcpeEnum shcpeEnum);
        abstract Color getColor();
        /**
         * 其他业务逻辑
         */
    }
    interface Shcpe{
        //Circle getCircle(); -错误的
        void draw();
    }
    interface Color{
        void fill();
    }

    /**
     * 普通工厂继承了抽象工厂
     */
    class ShcpeFactory extends AbstractFactory{
        /**
         *
         * @param shcpeEnum 使用枚举控制客户客户端输入，保证健壮性
         * @return
         */
        /*@Override
        Shcpe getShcpe(ShcpeEnum shcpeEnum) {
            *//**
             * 参考工厂模式和单例，原型模式内容
             *//*
            return null;
        }*/

        @Override
        Color getColor() {
            return null;
        }
    }
    class ColorFactory extends AbstractFactory{

        /*@Override
        Shcpe getShcpe(ShcpeEnum shcpeEnum) {
            return null;
        }*/

        @Override
        Color getColor() {
            //....
            return null ;
        }
    }

}

/**
 * builder
 *      错误理解：将同一个对象进行append操作，每次调用生成都是该对象
 *      正确理解:使用多个简单的对象一步一步构建成一个复杂的对象 , 同样的构建过程可以创建不同的表示。
 *               一些基本部件不会变，而其组合经常变化的时候。
        如何解决：将变与不变分离开。
        与工厂模式的区别是：建造者模式更加关注与零件装配的顺序。 TODO:结合工厂模式  策略模式 组合模式
        生活实例：1、去肯德基，汉堡、可乐、薯条、炸鸡翅等是不变的，而其组合是经常变化的，生成出所谓的"套餐
                   参考：https://www.runoob.com/design-pattern/builder-pattern.html

 TODO:写设计模式时，从最主要的类作为入口，比如这里的item / meal . 当到每个类时，再去精进。 创建接口 ，关联，默认实现defaultxx
 *
 */
class TestBuilder{

    /**
     * 每个对象共同点
     */
    interface  Item{
        String getName() ;
        Double getPrice();
        Packing getPack();
   }

    /**
     * 对象条目，并在抽象类中绑定固定
      */
    public abstract class hurger implements Item{
        @Override
        public String getName(){
            return "burger";
        }
        public Packing getPack(){
            return new Wrapper();
        }

        /**
         * 子类实现
         */
        public abstract  Double getPrice();
    }

    public class Ahurger extends  hurger{

        @Override
        public Double getPrice() {
            return Double.valueOf("123");
        }
    }
    // Bhurger

    public abstract class Drink implements Item{
        @Override
        public String getName(){
            return "Drink";
        }
        public Packing getPack(){
            return new Bottle();
        }

        /**
         * 子类实现
         */
        public abstract  Double getPrice();
    }

    public class Cola extends  Drink{

        @Override
        public Double getPrice() {
            return Double.valueOf("234");
        }
    }
    public class Milk extends  Drink{

        @Override
        public Double getPrice() {
            return Double.valueOf("1111s");
        }
    }


    interface Packing{
        String pack();
    }
    public class Wrapper implements Packing{

        @Override
        public String pack() {
            return "wrapper";
        }
    }

    public class Bottle implements Packing{

        @Override
        public String pack() {
            return "bottle";
        }
    }

    /**
     * ---理解从实际中抽象代码---
     * 自由组合套餐 ， 有客户端决定组合顺序，个数
     */
    class Meal{
        private List<Item> mealItem = new ArrayList<>();
        public Meal addItem(Item item){
            /**
             * 实际开发中，许多业务场景需要嵌套 ‘  已有数据结构 List’，并作适当加工，比如这里加入return this
             * 这里如果不返回，也不会影响，只是前面逻辑是否一致。
             */
            return mealItem.add(item) == true?this:null;
        }
        public Double getPrice(){
            Double totalPrice = 0.0;
            for (Item item :
                    mealItem) {
                /**
                 * bigDecimal
                 */
                totalPrice+=item.getPrice();
            }
            return totalPrice;
        }

        public void showItems(){
            for (Item item : mealItem) {
                System.out.print("Item : "+item.getName());
                System.out.print(", Packing : "+item.getPack());
                System.out.println(", Price : "+item.getPrice());
            }
        }
    }
}
/**********************************************************结构型**************************************************/

/**
 *proxy 控制对某个对象的访问（将‘ 整个需要的事情分为业务核心和非业务及aop ’） . 使得核心更加专注。使调用更方便
 *     实例：Windows 里面的快捷方式   火车票代理点    支票   spring aop(一个核心从不同角度可以有多个切面，代理 比如日志，事务..)   对外发布接口(当前业务流程+代理部分控制校验 入参 出参 )
 *           便于解耦 从提供和调用角度
 * TODO:spring中代理aop实现
 */
class TestProxy{

    /**
     * 代理对象持有真实对象引用，并在client办事情直接访问代理对象，
     */
    /**
     * 静态代理
     */
    class RealObj implements I{
        public void out(){
            System.out.println("RealObj ..");
        }
    }
    class ProxyObj implements I{
        //private RealObj robj;
        private I robj;

        public ProxyObj(I robj) {
            this.robj = robj;
        }

        public void out(){
            System.out.println("代理对象输出开始");
            robj.out();
            System.out.println("代理对象输出结束");
        }
    }
    /**
     * 倘若这里没有接口，客户都需要明白内部逻辑 ，并且“没有接口不易面向接口编程，后期增加实现改造增加“
     * 所以定义接口
     */
    interface I{
        void out();
    }


    /**
     * 1.如果接口增加一个方法，除了所有实现类需要实现这个方法外，所有代理类也需要实现此方法
     * 2.每个代理类只能为一个接口服务，这样程序开发中必然会产生许多的代理类
     *
     *  --向上抽象一层 - 反射
     *
     *  jdk动态代理(生成代理对象，方法...)
     *  Proxy - InvocationHandler -
     */

    class MyInvocation implements InvocationHandler{

        private I robj;

        public MyInvocation(I robj) {
            this.robj = robj;
        }

        /**
         * 进一步封装到代理类中，而无需client知道太多细节
         * @return
         */
        public  I getInstance() {
            return (I)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{TestProxy.I.class}, this);
        }

        /***
         * 这里是 jdk在执行定义好了模板，我们只需要按照提供的API进行实现就好，后面可以深入源码
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            /**
             * 从静态代理 需要在接口改变都需要提供代理相应实现，修改为这里的if判断，完成隔离
             * TODO:每个代理类是从一个方面进行控制的，所以每个‘方法’定义一个代理类（aop切面），所以这里的if没有必要。
             * TODO:spring在这个基础上，进行xml/annotation封装。切面 /切点 / 通知 /... 封装了对类接口(jdk代理)和cglib代理判断
             */
            if(method.getName().equalsIgnoreCase("out")){
                /**
                 * TODO:反射可以获取运行时的所有东西，所以这里sysout 是完全简化了。 还可以有更多扩展 。
                 */
                System.out.println("invocation proxy out");
                /**
                 * 这里的robj，属性作为依赖/关联...。 关联可以通过构造注入/set注入  构造比set更有必要型
                 */
                method.invoke(robj,args);
                System.out.println("invocation proxy out");
            }else if(false){

            }
            return null;
        }
    }




    /**
     * cglib代理
     * Enhance ...MethodInterceptor MethodProxy
     */

}

/**
 * decorator
 *      动态(开关)地给一个对象添加一些额外的职责。就增加功能来说，装饰器模式相比生成子类更为灵活。子类会很膨胀。
 *
 */
class TestDecorator{

}

/**
 * delegate 委派  对比于代理模式，委派从被代理者为核心出发。进行分发 比如dispatcher
 */
class TestDelegate{

}

/**
 * adapter
 *      1.作为两个不兼容的接口之间的桥梁
 *      解决问题：常常要将一些"  现存的对象  "放到‘ 新的环境 ’中(并且需要改对象)，而新环境要求的接口是现对象不能满足的，通过接口转换，将一个类插入另一个类系中。
 *                （比如老虎和飞禽，现在多了一个飞虎，在不增加实体的需求下，增加一个适配器，在里面包容一个虎对象，实现飞的接口。）
 *                相对于重构，适配器只是被迫选择(适配器不是在详细设计时添加的，而是解决正在服役的项目的问题。)
 *
 *                /****https://www.ibm.com/developerworks/cn/java/j-lo-adapter-pattern/index.html
 *
 *      1.通过在原接口中添加方法，新接口方法，所有子类修改实现
 *      2.通过对实现类添加新的接口实现扩展，需要将已经实现的代码都放到当前实现类中，不能使用已有的实现。比如tiger  / fly 接口原来都有实现，现在新融合飞虎，需要挪代码
 *      3.新增子类， 膨胀
 *      4.通过依赖，关联将新接口柔进另一个接口，实现中对原来接口内部判断，是否调用新接口实现。。但是这种方式需要修改‘ 原接口创建(关联 - new)?这里spring注入呢？、调用(依赖)
 *      5.spring管理对象和new对象 。new 情况下这里只能保证不关联 -- 不影响
 *        这里增加一个适配层，适配层封装原有接口和现有接口的关系，无需将新增的具体调用放到原有接口实现中。形成隔离，解耦 。 相对于关联接口更加层次分明
 *
 *                **/

class TestAdapter{


    /**
     * 猛兽
     */
    interface Master{
        void kill();
    }

    /**
     * 飞禽
     */
    interface Fly{
        void goToFly();
    }

    /**
     * 原对象是一个老虎
     */
    class Tiger implements Master{
        //private Fly fly ;
        private FlyAdapter fa ;

        /*public Tiger(Fly fly) {
            this.fly = fly;
        }*/
        public Tiger() {
        }
        @Override
        public void kill() {
            System.out.println("老虎是猛兽!");
            /**
             * 也有一下是相同功能方向的，比如playMP3 / playMp4... 增加if elseif ...
             */
         //   fly.goToFly();
            fa.kill();
        }
    }
    class Bird implements Fly{

        @Override
        public void goToFly() {
            System.out.println("该动物可以飞行");
        }
    }
    /**
     * 现在有了飞虎，需要改造原来老虎。
     * 1.关联 修改Tiger , 增加fly
     *       系统需要使用现有的类，而此类的接口不符合系统的需要。 2、想要建立一个可以重复使用的类，用于与一些彼此之间没有太大关联的一些类，
     *       包括一些可能在将来引进的类一起工作，这些源类不一定有一致的接口。 3、通过接口转换，将一个类插入另一个类系中。
     * 2.增加Adapter
     */

    class FlyAdapter implements Master{
        private Fly fly ;

        public FlyAdapter(Fly fly) {
            this.fly = fly;
        }

        @Override
        public void kill() {
            fly.goToFly();
        }
    }



}

/**
 * Filter
 *  list条件（规则） - 被操作对象 - 判断
 *  SQL condition ...组装  -- 抽象到 Rule
 *  TODO :    ------这种过滤逻辑，结构向上抽象到过滤器模式(多种模式柔和)，RULE 对象  ， 泛型T (Person - T), 反射 ---
 *  TODO:     ------写的过程中，向上抽象是好的，但是会‘ 迷失 ’，此时应该重新脑海中梳理 ‘类UML’
 *  通过List<xxx>传递形成链式调用 -
 *          1.定义接口 Criteria  将每个标准定义为一个类  返回list ,传入list  才是 关键      ----不再使用rule
 *          2.
 */
class Filter<T>{

    List<T> sourceList = new ArrayList<>();
    /**
     * 满足 / 不满足  / and /or / > ....
     */
    private Rule rule ;
    /**
     * 根据需要定义
     */
    /**
     * 会默认初始化，但人需要new
     */
    private List matchedList = new ArrayList();
    private List unMatchedList = new ArrayList();

    public Filter(List<T> personList, Rule rule) {
        this.sourceList = personList;
        this.rule = rule;
    }

    public List<T> doFilter() throws NoSuchFieldException, IllegalAccessException {
        for (T t:
                sourceList) {
            /**
             * 中断更能完整的表示逻辑
             */
            if(!rule.match()){
                continue;
            }
            matchedList.add(t);
            System.out.println("ABC");
        }
        return matchedList;
    }
}

class Person{
    String name ;
    int age ;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Rule<T>{
    /**
     * 被操作对象
     *
     * --数据结构使用list<Object[]> 这里的T 没有意义，因为没有地方需要这种隐含关系 . -->数组的类型只能相同的类型 --修改为list
         * 被操作属性
         * 规则  > < ...
         * 期望值
     */

    private T t ;
    private List<List> condition;

    /**
     * TODO:这里传入对象还是List好  对象好吧！！ 从客户端(调用角度 出参 入参  异常合理性)和服务端(内部逻辑)
     * @param t
     * @param condition
     */
    public Rule(T t, List<List> condition) {
        this.t = t;
        this.condition = condition;
    }

    public  boolean  match() throws NoSuchFieldException, IllegalAccessException {
        /**
         * 入参校验..
         */
        boolean isMatched = true ;  //或者使用计数器
        for (List<String> objs: condition) {
            //String method ="get"+ objs.get(0).substring(0, 1).toUpperCase() + objs.get(0).substring(1);
            Field field = ((List)t).get(0).getClass().getDeclaredField(objs.get(0));
            /**
             * field属性值是否可以获取到 or setAccess..
             */
            Object o = field.get(((List) t).get(0));
            /**
             * TODO:类型判断 string  int .. 其他引用 ,对后面的> < ....比较 目前只有string    and (将满足的list向下传递  matchedlist向下传递)/ or (并集)逻辑
             */
             Boolean b = o.toString().equalsIgnoreCase(objs.get(2)) == true? null : (isMatched = false) ;
             if(!isMatched) return false ;
        }
        return true ;
    }

    /**
     * --------------------------------------------不使用上面rule逻辑，重新整理链式----------------------------------------
     * 接口中出参入参一致才能形成链式调用
     * 将需要的多个实现定义，方法调用中形成指定调用  形不成递归
     * 相当于sql中的关键字实现
     */
    interface Criteria{
        List<Person> meetCriteria(List<Person> ls);
    }
    class NameMeet implements Criteria{

        List meetList = new ArrayList();
        @Override
        public List<Person> meetCriteria(List<Person> ls) {
            for (Person person :
                    ls) {
                if(person.name.equalsIgnoreCase("a")){
                    meetList.add(person);
                }
            }
            return meetList;
        }
    }
    class AgeMeet implements Criteria{

        List meetList = new ArrayList();
        @Override
        public List<Person> meetCriteria(List<Person> ls) {
            for (Person person :
                    ls) {
                if(person.age == 1){
                    meetList.add(person);
                }
            }
            return meetList;
        }
    }
    /**
     * --------关键---------
     */
    class AndMeet implements Criteria{
        /*List oneList = new ArrayList();
        List twoList = new ArrayList();*/
        private Criteria one ;
        private Criteria two;

        /**
         * 定义为list<Criteria>
         */

        public AndMeet(Criteria one, Criteria two) {
            this.one = one;
            this.two = two;
        }

        @Override
        public List<Person> meetCriteria(List<Person> ls) {
            /**
             * todo:-----------------------------------------------------------------------------------------------
             * for(i=0;i<list.size;I++){
             *     ls = list.get(i).meetCriteria(ls)
             * }
             * return ls ;
             */
            return  two.meetCriteria(one.meetCriteria(ls));
        }
    }
    class ORMeet implements Criteria{
        /*List oneList = new ArrayList();
        List twoList = new ArrayList();*/
        private Criteria one ;
        private Criteria two;

        public ORMeet(Criteria one, Criteria two) {
            this.one = one;
            this.two = two;
        }

        @Override
        public List<Person> meetCriteria(List<Person> ls) {
           //两个集合取并集
            return  null ;
        }
    }

}


/**
 * composite:树形  整体和个体  文件夹  node
 *           展现  - 递归调用
 */
class Node{

    private int idx ;
    private String nm ;
    private List<Node> lnode ;

    public Node(int idx, String nm , List l) {
        this.idx = idx;
        this.nm = nm;
        this.lnode = l;
    }

    //add  remove ... size ..

    /**
     * 递归展示该节点下所有
     */
    public void getAllNm() {
        /**
         * 临界问题 null表示叶子
         * 自己调用自己直到某个条件满足。if/else
         */
        if (lnode != null) {
            for (Node node :
                    lnode) {
                node.getAllNm();
            }
        }else{
            System.out.println("name is " + nm);
        }
    }
}


/**
 * flyWeight :共享池  hashmap
 */
class TestFlyWeight{


}

/**
 * facade :接口 /方法定义  封装具体实现
 */
class TestFacade{


}

/**
 * bridge
 */
class TestBridge{

}

/**********************************************************行为型*****特别关注对象之间的通信。*********************************************/

/**
 * Template+工厂+子类反射+策略。
 */
abstract class TestTemplate {
    //field
    //other method
     protected void play(){
        begin();
        call();
        callBack();
    }
    private void begin(){
        System.out.println("begin");
    };
    abstract void call();
    abstract void callBack();
}

class sub extends TestTemplate{
    @Override
    void call() {
        System.out.println("SUB CALL");
    }

    @Override
    void callBack() {
        System.out.println("SUB CALLback");
    }
}

/**
 * strategy if判断 / 子类 / 策略模式  定义一系列的 ' 算法 ',把它们一个个封装起来, 并且使它们可相互替换。
 *          (子类实现 vs 策略-参数一样，执行逻辑结构不同而已，加减乘除) / ftp sftp ....实现同一个事情策略
 */
class TestStrategy{


}

interface s {
    void doOper(int i, int j);
}

class Add implements s{

    @Override
    public void doOper(int i,int j) {
        int sum = i+j;
        System.out.println(sum);
    }
}

class sut implements s{

    @Override
    public void doOper(int i,int j) {
        int sut = i-j;
        System.out.println(sut);
    }
}


/**
 * observer/listner
 *      当一个对象被修改时，则会' 自动 '通知它的依赖对象 . 触发机制 。易用和低耦合，保证高度的协作  广播和通知
 *      TODO:观察者是否有必要关联被观察者呢？这样被观察者需要提供add方法供各个子类调用，状态获取也可以直接直接从被观察者这里get获取。参考:https://www.runoob.com/design-pattern/observer-pattern.html
 *      TODO:MQ如何封装这种思路的，又是如何进行各方面控制的
 */
class TestListner{
    private List<Obser> list;
    private int state;

    public TestListner(List<Obser> list, int state) {
        this.list = list;
        this.state = state;
    }

    public void changeState(int state){
        this.state=state;
        notifyObser();
    }

    private void notifyObser() {
        for (Obser o:
           list  ) {
            /**
             * 传递更新值
             *
             */
            o.update(state);
        }
    }

}

class Obser{
    private String name ;

    public Obser(String name) {
        this.name = name;
    }

    public void update(int state){
        System.out.println(name+"观察者执行更新为新状态"+state);
    }
}

/**
 * Chain of Responsibility
 *   抽象类一定是从子类公共部分抽象起来的。所以从抽象类往下看不容易. 每个类是有 级别(是否责任匹配)/下一个日志/写     拦截器
 *   参考：https://www.runoob.com/design-pattern/chain-of-responsibility-pattern.html
 */
class TestChainOfResp{





}

abstract class AbsLog{
    protected int level;
    /**
     * 柔和了  组合模式
     */
    private AbsLog nextLog;

    public AbsLog(){}
    public AbsLog(int level, AbsLog absLog) {
        this.level = level;
        this.nextLog = absLog;
    }

    /**
     * --------判断---------
     */
    public void writeLog(int level ,String msg){
        if(this.level <= level){
            write(msg);
        }
        if(this.nextLog != null){
            nextLog.writeLog(level, msg);
        }
    }

    /**
     * ----------不同子类不同实现----------
     */
    protected abstract void write(String msg);


}

class ConsoleLogger extends AbsLog {

    public ConsoleLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String message) {
        System.out.println("Standard Console::Logger: " + message);
    }
}

/**
 * state   状态机
 */
class TestState{

}

/**
 * 1.Mediator
 * 2.Command
 * 3.Iterator
 * 4.Memento(备忘录)
 * 5.Visitor
 */


/**
 * ***设计模式特别关注表示层****
 *   MVC 模式（MVC Pattern）
     业务代表模式（Business Delegate Pattern）
     组合实体模式（Composite Entity Pattern）
     数据访问对象模式（Data Access Object Pattern）
     前端控制器模式（Front Controller Pattern）
     拦截过滤器模式（Intercepting Filter Pattern）
     服务定位器模式（Service Locator Pattern）
     传输对象模式（Transfer Object Pattern）
 */
