package asm;

/**
 * @author YangWenjun
 * @date 2019/11/1 14:06
 * @project MockFramework
 * @title: MyClassLoader
 * @description:
 */
public class MyClassLoader extends ClassLoader {

    public Class<?> defineClassPublic(String name, byte[] b, int off, int len) throws ClassFormatError {
        Class<?> clazz = defineClass(name, b, off, len);
        return clazz;
    }
}

