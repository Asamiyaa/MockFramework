package myFramework.myMybatis.com.asamiya.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 递归扫描指定路径下符合条件的类或对象并返回list
 */
public class ScanUtils {

    public static List scan(String basePath){


        List xml = new ArrayList();
        xml.add(new File("myFramework/myMybatis/com/asamiya/userInfo/User.xml"));
        return xml;
//        return Collections.emptyList();


    }





}
