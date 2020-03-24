package com.thread;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;


/*compareAndSwapObject，compareAndSwapInt和compareAndSwapLong，再看AtomicBoolean源码，发现其是先把Boolean转换成整型，再使用compareAndSwapInt进行CAS，
所以原子更新double也可以用类似的思路来实现。*/
public class ThreadMain {

    public static void main(String[] args) {
        new ThreadMain().testAtomicArray();
    }

    private AtomicInteger atomicInteger = new AtomicInteger();
    Random random = new Random();
    public void testAtomic(){
        for (int i = 0; i < 10; i++) {
            //内部类不会出现问题，因为是每个自己的runnable
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //虽然这里的，但是完全可以保证并发下，
                    // 1.核心的增长数是安全的
                    // 2.输出顺序可能是不同的
                    // 3.通过sysout来模拟后续业务代码造成atomic后的逻辑使用了同一个andIncreate - 不会发生，因为他是局部变量
                    int andIncrement = atomicInteger.getAndIncrement();
                    try {
                        //Thread.sleep(Long.valueOf(random.nextInt()).longValue());
                        Thread.sleep(1000l);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(andIncrement);
                    //下面的使用andIncrement是有问题的

                }
            }).start();
        }
    }

    public void testAtomic2(){
        MyThread myThread = new MyThread();
        for (int i = 0; i < 10; i++) {
            //局部变量使得这里即使访问同一个类的安全代码都不会有问题，所以代码，方法后续可以使用该变量进行自己的处理，不收atomicInteger影响
            new Thread(myThread).start();
        }
    }
    public void testAtomic3(){
        MyThread2 myThread = new MyThread2();
        for (int i = 0; i < 10; i++) {
            new Thread(myThread).start();
        }
    }

    //AtomicIntegerArray类需要注意的是，数组value通过构造方法传递进去，然后AtomicIntegerArray会将当前数组复制一份，
    // 所以当AtomicIntegerArray对内部的数组元素进行修改时，不会影响到传入的数组。
    int[] value = new int[] { 1, 2 };
    AtomicIntegerArray ai = new AtomicIntegerArray(value);
    public void testAtomicArray(){
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ai.getAndSet(0, 3);//addAndSet
                    System.out.println(ai.get(0));
                    System.out.println(value[0]);
                }
            }).start();
        }
    }
}
/***原子更新基本类型的AtomicInteger，只能更新一个变量，如果要原子的更新多个变量，就需要使用这个原子更新引用类型提供的类**/
class AtomicReferenceTest {

    public static AtomicReference<User> atomicUserRef = new AtomicReference<User>();

    public static void main(String[] args) {
        User user = new User("conan", 15);
        atomicUserRef.set(user);
        User updateUser = new User("Shinichi", 17);
        atomicUserRef.compareAndSet(user, updateUser);
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getOld());
    }

    static class User {
        private String name;
        private int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public int getOld() {
            return old;
        }
    }
}

//将 安全值 返回成 成员而不去控制会造成问题
class MyThread implements Runnable{
    private AtomicInteger atomicInteger = new AtomicInteger();
    Random random = new Random();
    //--有时被迫需要将局部变量提高成员，以便在同一进程中的多个方法间使用改值
    int andIncrement ;
    @Override
    public void run() {
        //把返回值放到成员变量就有问题了
        //int andIncrement = atomicInteger.getAndIncrement(); //正确的
         andIncrement = atomicInteger.getAndIncrement();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
        }
        System.out.println(andIncrement);
    }
}

//ThreadLocal
class MyThread2 implements Runnable{
    private AtomicInteger atomicInteger = new AtomicInteger();
    Random random = new Random();
    //修改为ThreadLocal ,<>中的内容就是要操作的
    //private ThreadLocal<Integer> atomic = new ThreadLocal<>();
    //为了扩展这种关系
    private ThreadLocal<Map<String,Integer>> atomic = new ThreadLocal();
    Map<String, Integer> map = new HashMap<>();
    //其实直接将这种map形式设计为Context
    @Override
    public void run() {
      // atomic.set((Map<String, Integer>) new HashMap<>().put(Thread.currentThread().getName(),atomicInteger.getAndIncrement())) ;
      // ThreadLocal.get: 获取ThreadLocal中当前线程共享变量的值。
        map.put(Thread.currentThread().getName(),atomicInteger.getAndIncrement());
        atomic.set(map);
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
        }
    //输出自己线程的东西
    //  System.out.println(atomic.get().get(Thread.currentThread().getName())); atomic.get() 空指针 - 分析 ThreadLocal中没有共享变量，应该讲map设为共享
        System.out.println(atomic.get().get(Thread.currentThread().getName()));
    }
}

/**
 * https://www.ibm.com/developerworks/cn/java/j-jtp07233/index.html
 * http://ifeve.com/concurrent-collections-1/
 ConcurrentHashMap
     一个高效的并发 HashMap，并且是线程安全的。
 CopyOnWriteArrayList
     一个 List，和 ArrayList 是一族的，在读多写少的场合，该 List 的性能非常好，远远好于 Vector。
 ConcurrentLinkedQueue
     高效的并发队列，使用链表实现，可看作一个线程安全的 LinkedList。
 ConcurrentSkipListMap
     一个 Map ，跳表实现，使用跳表的数据结构快速查询。
 */
class TestConcurrentHashMap{
    //static Map map = new HashMap(); 输出的size结果为1
    static ConcurrentHashMap map = new ConcurrentHashMap();

    public static void main(String[] args) throws InterruptedException {
        new TestConcurrentHashMap().test();
        Thread.sleep(5000l);
        System.out.println(map.keySet().size());
    }

    private void test() throws InterruptedException {
        //final int i ;
        for (int i = 0; i < 10; i++) {
            int j = 0 ;
            new Thread(new Runnable() {
                @Override
                public void run() {
                   // map.put(j++,j++);//相同key会覆盖
                }
            }).start();
        }


    }


}
