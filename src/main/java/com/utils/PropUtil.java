package com.utils;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author YangWenjun
 * @date 2019/8/22 11:54
 * @project hook
 * @title: PropUtil
 * @description:
 *      1.java加载properties文件的六种基本方式  https://blog.csdn.net/u011063151/article/details/51888640
 *              ResourceBundle  class变量的getResourceAsStream()  ClassLoader.getSystemResourceAsStream("

        2.spring resource / resourceLoader   https://blog.csdn.net/shadow_zed/article/details/72540927
                整合了不同的‘ 资源获取方式 ’  classpath-file-http-/

        3.缓存 (静态.--加载顺序.) - 分多个文件，合在一个resource.file中

        4.理解扩展接口，扩展后需要做哪些处理

 */
public class PropUtil {

    public static void main(String[] args) throws IOException {

        /*Properties p =  new Properties();
        *//***路径使用 从resources开始(右键 as reosurce root)，maven目录和原始目录区分开**//*
        InputStream in = ClassLoader.getSystemResourceAsStream("test.properties");
        p.load(in);
        System.out.println(p.get("abc"));*/

        /**
         * 1.java多种读取资源方式 读入缓存  --> qt:如果有修改，如何刷新缓存呢？哪些配置文件vs数据库vs本地
         *    缓存通过static / 块 / 构造 vs spring vs servlet tomcat 容器 加载
         * 2.spring对资源获取的封装处理  Resource接口和ResourceLoader
         * 3.封装项目中Resource处理接口
         */
    }


    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    public final static List<Resource> PROP_FILE_RESOUCE = readPropertiesFiles("resource.file");
    public final static Properties PROPERTIES = new Properties();

    static{
        loadProperties(PROPERTIES,PROP_FILE_RESOUCE);
    }

    private static void loadProperties(Properties properties, List<Resource> propFileResouce) {
        for(Resource resource:propFileResouce){
            InputStream is = null ;
            try{
                is=resource.getInputStream();
                properties.load(is);
            }catch(Exception e){}finally {
                org.apache.commons.io.IOUtils.closeQuietly(is);
            }
        }
    }


    private static List<Resource> readPropertiesFiles(String baseFile) {
        InputStream is = null ;
        ArrayList<Resource> resourcesPaths = new ArrayList<>();
        try{
            Resource baseResource = resourceLoader.getResource(baseFile);
            is = baseResource.getInputStream();
            List<String> list = org.apache.commons.io.IOUtils.readLines(is);//这里没有问题吗????
            for (String location :
                    list) {
                location = location.trim();
                if(location.length()==0)continue;
                if(location.startsWith("#"))continue;
                if(location.startsWith("-"))continue;
                Resource resource = getResource(location);
                if(resource != null){
                    resourcesPaths.add(resource);
                }
            }
        }catch(Exception e){}finally {
            org.apache.commons.io.IOUtils.closeQuietly(is);
        }
        return resourcesPaths;
    }

    private static Resource getResource(String name ) {
        Resource resource = null ;
        resource = resourceLoader.getResource(name);
        if(resource == null || !resource.exists()){return null;}
        return resource;
    }

}
