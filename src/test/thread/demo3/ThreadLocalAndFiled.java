package thread.demo3;

/**
 * @author YangWenjun
 * @date 2019/7/16 18:04
 * @project BaseJava
 * @title: ThreadLocal
 * @description:https://www.cnblogs.com/dolphin0520/p/3920407.html
 *               http://www.jasongj.com/java/threadlocal/
 */
public class ThreadLocalAndFiled {

    /**
     * 1.多线程      多例      静态  成员   局部(各是各的，不影响)
     *   。。。      单例      。。。。。多线程会共同作用
     *                         局部      多线程各自
     *             --静态和成员都是为在 ‘不同实例’‘不同方法’间共享。但这种共享在多线程是否需要？ 需要则多线程同步控制/不需要则threadLocal(各自线程自己)
     * 2.底层将线程信息：当前线程当前对象先set在get  threadLocalMap
     */

    /***
     * 版本一
     * 连接获取connection，session获取。成员-线程间共享没必要，并且有问题，一个关闭而另一个还在用。此时有问题 - threadLocal
     * * private static Connection connect = null;

     public static Connection openConnection() {
     if(connect == null){
     connect = DriverManager.getConnection();
     }
     return connect;
     }

     public static void closeConnection() {
     if(connect!=null)
     connect.close();
     */

    /**
     * 版本二
     * private static final ThreadLocal threadSession = new ThreadLocal();   //多了创建

     public static Session getSession() throws InfrastructureException {
     Session s = (Session) threadSession.get();     //多了获取
     try {
     if (s == null) {
     s = getSessionFactory().openSession();
     threadSession.set(s);                          //多了塞入
     }
     } catch (HibernateException ex) {
     throw new InfrastructureException(ex);
     }
     return s;
     }
     */


   /* ThreadLocal<Long>   longLocal = new ThreadLocal<Long>();     //每个变量想要threadlocal需要声明
    ThreadLocal<String> stringLocal = new ThreadLocal<String>(); //版本二：使用initialValue()

    public void set() {
        longLocal.set(Thread.currentThread().getId());
        stringLocal.set(Thread.currentThread().getName());
    }*/
    //版本二
   ThreadLocal<Long> longLocal = new ThreadLocal<Long>(){
        protected Long initialValue() {
            return Thread.currentThread().getId();
        };
    };

    ThreadLocal<String> stringLocal = new ThreadLocal<String>(){
        protected String initialValue() {
            return Thread.currentThread().getName();
        };
    };

    public long getLong() {
        return longLocal.get();
    }

    public String getString() {
        return stringLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final ThreadLocalAndFiled test = new ThreadLocalAndFiled();

        //test.set();                             //版本二：使用initialValue()
        System.out.println(test.getLong());
        System.out.println(test.getString());

        Thread thread1 = new Thread(){
            public void run() {
                //test.set();
                System.out.println(test.getLong());
                System.out.println(test.getString());
            };
        };
        thread1.start();
        thread1.join();

        System.out.println(test.getLong());
        System.out.println(test.getString());
    }

    //版本三  如何将线程中key value 保存到threadLocal中，线程中应该有许多key value
    // 同一线程追加问题：http://www.jasongj.com/java/threadlocal/
}
