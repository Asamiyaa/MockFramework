
package com.utils;


/**
 * @author YangWenjun
 * @date 2019/11/1 17:52
 * @project MockFramework
 * @title: A
 * @description: *****http://commons.apache.org/*****
 *                  1.无需判断null / empty
 *                  2.提供了常用的工具，这些自己也可以实现。使用别人的为了方便
 *                  3.泛型 接口（所以对于不认识的可以思考比如charSequence 也可以是String）  完成内部转换 集合和数组间..
 *                  4.及时查看api文档和使用工具ctrl+shift+空格查看
 *
 *
 *          1.流水号  线程安全  AtomicInteger + 生成逻辑
            apache - common  --->  https://blog.csdn.net/HaHa_Sir/article/details/79583627

            文件权限、路径、列表、可读可写、创建、递归、流释放

             组件	功能介绍

             Lang	Java基本对象方法的工具类包 如：StringUtils - / split,ArrayUtils,AnnotationUtils,
                    BasicThreadFactory,,Diff,RandomUtils,TypeUitls
                    Validate等等.

                    ClassUtils - MethodUtils - FieldUtils - TypeUtils

                    stringUtils.leftPad/compare/LastIndexOfAny/split
                    //无需判断是否为空直接截取
                    System.out.println(StringUtils.lastIndexOfIgnoreCase("abccde", "c"));
                    System.out.println(StringUtils.lastIndexOfIgnoreCase(null, "c")); // -1

                    System.out.println(StringUtils.substringAfter("abccde", "c"));//cde
                    System.out.println(StringUtils.appendIfMissing("abc", "tt", "gg"));//abctt

                    正则
                    String[] strings = StringUtils.splitPreserveAllTokens("ab.c.dd,  ", ".", 3);
                    for (String s :
                    strings) {
                    System.out.println(s); //ab  c dd, 一定要考虑边界问题 - 空白问题
                    }
                    //正则表达式需要转义的特殊字符  - https://blog.csdn.net/lvshubao1314/article/details/51222978
                    //* . ? + $ ^ [ ] ( ) { } | \ /
                    System.out.println(Arrays.toString("ab.c.dd".split(".")));//[]
                    System.out.println(Arrays.toString("ab.c.dd".split("\\.")));//[ab, c, dd]

                    FileUtils.readFileToString


Collections	java集合框架操作. 集合 -jdk Arrays/collections/CollectionUtils - >

             BeanUtils	提供了对于JavaBean进行各种操作，克隆对象,属性等等.
                    copyProperties(Object dest, Object orig)  the same. name
                    populate(Object bean, Map<String,? extends Object> properties)
                    Populate the JavaBeans properties of the specified bean, based on the specified name/value pairs.

             IO	io工具的封装. byteArrayInputSteam  ... stream - byte - String
                                                              readLine
                    closeQuietly(Closeable... closeables)
                    contentEquals(InputStream input1, InputStream input2)
                    copy(InputStream input, OutputStream output, int bufferSize)  - 代替那种while... read..
                    read(InputStream input, byte[] buffer)
                 将流转为string相关来处理
                    List<String> readLines(InputStream input, String encoding)
                    String	resourceToString(String name, Charset encoding)

             Codec	处理常用的编码方法的工具类包 例如AES、DES、SHA1、MD5、hash、Base64等.
                    base64不是安全领域下的加密解密算法。能起到安全作用的效果很差，而且很容易破解，
                    他核心作用应该是传输数据的正确性，有些网关或系统只能使用ASCII字符。
                    Base64就是用来将非ASCII字符的数据转换成ASCII字符的一种方法，而且base64特别适合在http，
                    mime协议下快速传输数据。

                    不合适的编码选择会造成字符丢失，所以在编码/解码时应该明确自己的charset.和string,byte[]关系紧密
                    上面不同的这时候不同的编码规则

                    高级对称非对称加解密：https://blog.csdn.net/wildandfly/article/details/21521857
                    KeyGenerator、Cipher....

             Compress	java提供文件打包 压缩类库.
                    ar, cpio, Unix dump, tar, zip,gzip, XZ, Pack200 and bzip2格式的文件，功能比较强大。

             FileUpload	提供文件上传功能.           由mvc代替  https://www.jianshu.com/p/39ea3c34aea7
             FtpClient  提供ftp文件，对socket封装
             HttpClient	提供HTTP客户端与服务器的各种通讯操作. 现在已改成HttpComponents
                    HTTP和浏览器有点像，但却不是浏览器。很多人觉得既然HttpClient是一个HTTP客户端编程工具，
                    很多人把他当做浏览器来理解，但是其实HttpClient不是浏览器，它是一个HTTP通信库，
                    因此它只提供一个通用浏览器应用程序所期望的功能子集，最根本的区别是HttpClient中没有用户界面，
                    浏览器需要一个渲染引擎来显示页面，并解释用户输入，例如鼠标点击显示页面上的某处，有一个布局引擎，
                    计算如何显示HTML页面，包括级联样式表和图像。
                    javascript解释器运行嵌入HTML页面或从HTML页面引用的javascript代码。
                    来自用户界面的事件被传递到javascript解释器进行处理。除此之外，还有用于插件的接口，
                    可以处理Applet，嵌入式媒体对象（如pdf文件，Quicktime电影和Flash动画）或ActiveX控件（可以执行任何操作）。
                    HttpClient只能以编程的方式通过其API用于传输和接受HTTP消息

                    https://blog.csdn.net/justry_deng/article/details/81042379
                    异常，参数，返回对比
                    TODO:如何对返回数据进行解析处理 ，目前只是抓取了
             Email	    java发送邮件 对javamail的封装.

             Betwixt	XML与Java对象之间相互转换.

             //DBCP	提供数据库连接池服务.                               --由druid代替
             //Configuration	一个java应用程序的配置管理类库.          --由spring代替
             //DbUtils	提供对jdbc 的操作封装来简化数据查询和记录读取操作.  -- 由orm代替
             //Logging	提供的是一个Java 的日志接口.                    --由logback、log4j代替
             //Validator	提供了客户端和服务器端的数据验证框架.          -- 由正则-注解-自定义校验框架代替

 */


import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import sun.misc.IOUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Map;

/**
 *      Google Guava官方教程
 *                          --->http://ifeve.com/google-guava/
 *                          --->https://wizardforcel.gitbooks.io/guava-tutorial/content/1.html
 *            Basic utilities
 *            Collections
 *            Caches
 *                  Guava Cache：本地缓存实现，支持多种缓存过期策略
 *            Functional idioms
 *            Concurrency
 *                  Service框架：抽象可开启和关闭的服务，帮助你维护服务的状态逻辑
 *            Strings
 *            Primitives
 *            Ranges
 *            io
 *            Hash
 *            EventBus
 *                  传统上，Java的进程内事件分发都是通过发布者和订阅者之间的显式注册实现的。
 *                  设计EventBus就是为了取代这种显示注册方式，使组件间有了更好的解耦。
 *                  EventBus不是通用型的发布-订阅实现，不适用于进程间通信。
 *            Math
 *            Reflection
 *
 */

public class ApacheCommonTest {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, EmailException, IllegalAccessException, NoSuchFieldException {

        //https://blog.csdn.net/lonely_fireworks/article/details/7962171
        String str=null;
        str=String.format("Hi,%s", "A");
        System.out.println(str);
        str=String.format("Hi,%s:%s.%s", "A","A","V");  //中文乱码 -> 字符集

        //leftPad  垫值到固定长度
        System.out.println(StringUtils.leftPad("a", 5 ,"1"));
        System.out.println(StringUtils.remove("abccbbdd", "b"));
        System.out.println((StringUtils.split("a,b,c,d,e", ",", 3))[2]); //c,d,e 将最后的一个放到最后数组中
        System.out.println(StringUtils.wrapIfMissing("ab", "cd"));  //cdabcd

        //split

        System.out.println(Arrays.deepEquals(new String[]{"a", "b"}, new String[]{"b", "a"})); //false

        HashMap<String,String> map = new HashMap(){{
            put("a","a");
            put("b","b");
            put("c","c");
        }};


//new HashMap(){{put("id", "001");}} map内部类初始化  是否允许使用

for (Map.Entry<String, String> entry:map.entrySet()){
            //这里由于上面初始化map时，泛型的位置应该在前面，才能map.entryset时返回对应的泛型参数，而不是object
        }//*


        for (Map.Entry<String, String> entry:map.entrySet()){
            System.out.println(entry.getKey() + "--" + entry.getValue());
        }

        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (int i = 0; i < entries.size(); i++) {
            //entries 底层不是数组，没有get方法和下标识
        }

        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next.getKey() + "--iii--" + next.getValue());
        }
        System.out.println("完成读取");

        //so  linkedList - set - map 都是使用iterator
        //首先，在老版本java中这是惟一遍历map的方式。另一个好处是，你可以在遍历时调用iterator.remove()来删除entries，
        // 另两个方法则不能。根据javadoc的说明，如果在for-each遍历中尝试使用此方法，结果是不可预测的。

        //流读入 ，写出
        //InputStream in = new BufferedInputStream(new FileInputStream(new File("d:\\a.txt")));//路径有问题吗？
        InputStream in = new BufferedInputStream(new FileInputStream(new File("d:/a.txt")));
        OutputStream out = new FileOutputStream("d:\\b.txt"); //是否存在？文件权限 目录..创建...

/*@Override
            public void write(int b) throws IOException {
                out.write(b);
            }*/

            //流和代码如何转换  输入输出， 这里为了验证 copy功能  -- 来定义一个数组,读到数组中
            int length ;
            byte[] bytes = new byte[1024];
//        System.out.println(Arrays.toString(bytes));
//        while ( (length = in.read())>-1){   都进去才有数据呀！
       while ( (length = in.read(bytes))>-1){
                //没有写入 输出读入的内容
//                String str1 = new String(bytes); 乱码 从文件中
                String str1 = new String(bytes,"gbk");//buffer
                System.out.println(str1);
                out.write(bytes,0,length);
            }
            //没有flush
        out.flush(); //或者说关闭
        out.close();
            //读取后字符集问题  idea中文乱码 ，写出文件乱码 ：https://www.cnblogs.com/vhua/p/idea_1.html
        System.out.println("完成读取");


        //IOutils copy 和 close
        InputStream in1 = null;
        OutputStream out1 = null ;
        try {
            in1 = new BufferedInputStream(new FileInputStream(new File("d:/a.txt")));
            out1 = new FileOutputStream("d:\\c.txt");
            org.apache.commons.io.IOUtils.copy(in1, out1);
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly(in1);
            org.apache.commons.io.IOUtils.closeQuietly(out1);
        }
//        org.apache.commons.io.IOUtils.closeQuietly(in); -- try..with.


        //编解码   使用编解规则进行处理
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytess = encoder.encode(new String("abcd").getBytes("utf-8"));
        System.out.println(bytess.toString());          //[B@568db2f2
        System.out.println(Arrays.toString(bytess));    //[89, 87, 74, 106]
        System.out.println(Arrays.toString(new String("abcd").getBytes("utf-8")));//[97, 98, 99]

        Base64.Decoder decoder = Base64.getDecoder();
        System.out.println(new String(decoder.decode(bytess)));

        System.out.println(Base64.getEncoder().encodeToString("abcd".getBytes("utf-8")));   //YWJjZA==
        System.out.println(new String(Base64.getDecoder().decode("YWJjZA=="),"utf-8")); //abcd

        //加解密  base64时最基本的，md5，sha1等才算加解密 + base64编解码
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        System.out.println(md5.digest("abcd".getBytes("utf-8")).toString());
        System.out.println(sha1.digest("abcd".getBytes("utf-8")).toString());

        //完整的
        System.out.println(Base64.getEncoder().encodeToString(md5.digest("abcd".getBytes("utf-8"))));//4vxxTEcn7pOV8yTNLn8zHw==
        String s = new String(Base64.getDecoder().decode("4vxxTEcn7pOV8yTNLn8zHw=="), "utf-8");
        System.out.println(s);
        //��qLG'�$�.3 不可逆的。 md5为了不可逆加密，base64为了ascii，所以组合。
        //不可逆可以用来密码验证，正向就好

        //压缩解压
        InputStream in2 = new BufferedInputStream(new FileInputStream(new File("d:/a.txt")));
        ZipOutputStream out2 = new ZipOutputStream(new BufferedOutputStream(
                                     new FileOutputStream(new File("d:\\dzip.zip"))));
        //org.apache.commons.io.IOUtils.copy(in2, out2);
        int j ; byte[] bytesss = new byte[1024];
        while((j = in2.read(bytesss)) > -1){
            ZipEntry zipEntry = new ZipEntry("a");
            out2.putNextEntry(zipEntry);
            out2.write(bytesss);
        }
        in2.close();out2.close();

        //Compress工具使用

        //httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("https://so.bban.fun/");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        System.out.println(httpResponse.getEntity().getContent());
        System.out.println(httpResponse.getEntity().getContentLength());

        //email
        SimpleEmail simpleEmail = new SimpleEmail();
        simpleEmail.setHostName("smtp.qq.com");
//        simpleEmail.setAuthentication("736996211@qq.com", "wolfjun736996");  smtp验证码不是密码
        simpleEmail.setAuthentication("736996211@qq.com", "vtdglkcaqbxbbddf");
        simpleEmail.setFrom("736996211@qq.com");
        simpleEmail.addTo("528919059@qq.com");
        simpleEmail.setSubject("mail learn");
        simpleEmail.setMsg(" this is a mail ");
        simpleEmail.send();
        System.out.println("--mail send end--");



//    private void testCommonUtils(){
/*private void testCommonUtils(){
       *//* System.out.println(ClassUtils.getCanonicalName(temp.class));//完整类名  com.temp
        System.out.println(ClassUtils.getName(temp.class));//完整类名  com.temp
        System.out.println(ClassUtils.getShortCanonicalName(temp.class));//temp
*//*
        //getFieldsListWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls)
        //使用工具类可以 ‘ 一步包含多个步骤 ’达到快速
        //将.annotation 包含
        List<Field> fieldsListWithAnnotation = FieldUtils.getFieldsListWithAnnotation(Student1.class, foramt.class);
        for (Field fi :
                fieldsListWithAnnotation) {
            System.out.println(fi.getName()); //tt ann
        }
        //将.accessable 包含
        Student1<HashMap> util = new Student1<>("util");
        System.out.println(FieldUtils.readDeclaredField(util, "name",true)+"-1-1-");
        System.out.println(util.getClass().getDeclaredField("name").get(util)+"2-2-2-");


    };*/

    }
}


@foramt(length = 1,name = "b")
class Student1<T extends Map>{

    private  String  name = "student1";
    private ArrayList<String> courses ;
    @foramt(length = 1,name = "b")
    private T[] tt ;
    private ArrayList<T> ll ;
    @foramt(length = 1,name = "b")
    @foramt2
    private String ann ;

    public Student1(String name) {
        this.name = name;
    }
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
