package myFramework.myMybatis.com.asamiya.parse.xml;

import java.util.List;

/**
 * 对于复杂组合对象，需要使用list这种自己才知道的映射时，定义对象，使其面向对象传递和管理
 */
public class MyMapperXml<T> {

    private Class<T> baseRusult;
    private List<MyXMLNode> xmlnodes;










    public Class<T> getBaseRusult() {
        return baseRusult;
    }

    public void setBaseRusult(Class<T> baseRusult) {
        this.baseRusult = baseRusult;
    }

    public List<MyXMLNode> getXmlnodes() {
        return xmlnodes;
    }

    public void setXmlnodes(List<MyXMLNode> xmlnodes) {
        this.xmlnodes = xmlnodes;
    }
}
