package myFramework.myMybatis.com.asamiya.executor;


import myFramework.myMybatis.com.asamiya.parse.xml.MyMapperXml;
import myFramework.myMybatis.com.asamiya.parse.xml.MyXMLNode;
import myFramework.myMybatis.com.asamiya.userInfo.User;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class Excutor {

    public Object invoke(Connection con ,Map<String, MyMapperXml> namespaceAndMapperImplContext ,
                         String namespace , String methodId , List paramList) throws SQLException {

        MyMapperXml myMapperXml = namespaceAndMapperImplContext.get(namespace);
        Class baseRusult = myMapperXml.getBaseRusult();
        List<MyXMLNode> xmlnodes = myMapperXml.getXmlnodes();
        MyXMLNode curNode = new MyXMLNode() ;
        for (MyXMLNode node : xmlnodes) {
            if(node.getId().equals(methodId)){
                curNode = node ;
                break;
            }
        }
        //todo：放到预编译里面去
        String sql = curNode.getSql();
        String paramType = curNode.getParamType();
        String returnType = curNode.getReturnType();

        Statement stmt = con.createStatement() ;
        PreparedStatement pstmt = con.prepareStatement(sql) ;
//        pstmt.setString(); 塞值
        ResultSet rs = pstmt.executeQuery();
        String name = "";
        String age = "";


//        返回值处理放到resultWrapper
        while(rs.next()){
            name = rs.getString("name") ;
            age = rs.getString("age") ;
        }

        return  new User(name,age);
    }


}
