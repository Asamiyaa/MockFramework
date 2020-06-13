package myFramework.myMybatis.com.asamiya.parse.xml;

import myFramework.myMybatis.com.asamiya.userInfo.User;
import myFramework.myMybatis.com.asamiya.utils.ScanUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mapper xml解析器  - 将接口实现解析放到map中
 */
public class MapperParse {

    Map<String, MyMapperXml> namespaceAndmapperIml = new HashMap<>();

    private String XmlBasePath ;    //myFramework.myMybatis.com.asamiya.userInfo.ImyMapper
    private String MapperBasePath;  //myFramework.myMybatis.com.asamiya.userInfo.ImyMapper

    public Map parse(){

        //递归扫描执行路径下符合条件的类
        List<File> mapperXMLlist = ScanUtils.scan(XmlBasePath);
//        List mapperList = ScanUtils.scan(MapperBasePath);

        //遍历解析xml
        for (File xmlMapper : mapperXMLlist) {
             namespaceAndmapperIml = doParse(xmlMapper);//namespace就是mapper
        }
        return namespaceAndmapperIml;
    }

    private Map<String,MyMapperXml> doParse(File xmlMapper) {

        Map map = new HashMap();
        MyMapperXml<User> objectMyMapperXml = new MyMapperXml<>();
        objectMyMapperXml.setBaseRusult(User.class);
        MyXMLNode myXMLNode = new MyXMLNode();
        myXMLNode.setId("selectAll");
        myXMLNode.setSql("select * from user");
        myXMLNode.setReturnType("baseResult");
        List xmlnodes = new ArrayList();
        xmlnodes.add(myXMLNode);
        objectMyMapperXml.setXmlnodes(xmlnodes);
        map.put("myFramework.myMybatis.com.asamiya.userInfo.ImyMapper",objectMyMapperXml);
        return map ;
//        return Collections.emptyMap();

    }
}
