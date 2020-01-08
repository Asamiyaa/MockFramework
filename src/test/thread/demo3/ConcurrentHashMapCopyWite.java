package thread.demo3;

import java.util.Vector;

/**
 * @author YangWenjun
 * @date 2019/7/17 11:58
 * @project BaseJava
 * @title: ConcurrentHashMapCopyWite
 * @description:并发容器
 */
public class ConcurrentHashMapCopyWite {

    /**
     *   * 1.Vector、Stack、HashTable  同步容器 不安全
     *       synchronized (Test.class) {   //进行额外的同步
             for(int i=0;i<vector.size();i++)
             vector.remove(i);
             }
             同步容器将所有对容器状态的访问都串行化
             2.
     */

    public static void main(String[] args) {
        Vector vector = new Vector();
        vector.add("1");
        vector.add("2");
        vector.add("3");
        vector.add("4");
        vector.add("5");

        vector.add("6");
        vector.add("7");
        vector.add("8");
        System.out.println(vector.size());
        for(int i=0;i<vector.size();i++)
        {vector.remove(i);}
        System.out.println(vector);
    }

    }

