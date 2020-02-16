package com.product;

import com.utils.convert.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**上面的run - configure -editConfigure  -- 这是修改已有的，
 * 该文件右键 - create ..main... - configure
 * 1.设置对应参数
 * 2.
 */
public class OutOfmemory {
    //-Xmx32M -Xms32M
    static List<User> luser = new ArrayList<User>();//Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

    //-XX:MetaspaceSize=32M -XX:MaxMetaspaceSize=32M
    //static List<String> strs = new ArrayList<>();   问题：1.静态属性不在metaspace吗？ 2.string常量池不在吗？

    //引入asm动态生成class文件
    //static List<Class> classes = new ArrayList<>(); //Exception in thread "main" java.lang.OutOfMemoryError: Metaspace
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        while(true){
            luser.add(new User());
//            strs.add("12");
//            classes.addAll(Metaspace.createClasses());
        }
    }
}
