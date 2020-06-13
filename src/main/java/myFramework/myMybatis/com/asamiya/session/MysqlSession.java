package myFramework.myMybatis.com.asamiya.session;


import myFramework.myMybatis.com.asamiya.parse.xml.MapperParse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * 模拟sqlSeesion 加载管理数据库资源 ,作为客户调用和框架之间 ‘门面’
 */
public class MysqlSession {

    private MyDataSource dataSource;
    private String timeOut ;
    private String threadInfo;//Thread对象

    private Map context ;//全局上下文 便于类之间对象传递 vs 直接属性
    private Connection connection;


    public void initMymybatis() throws SQLException, ClassNotFoundException {

           connection = initDataSource(dataSource);
//        initPrestateSQL(connection,new MapperParse().parse());





    }

    private void initPrestateSQL(Connection connection ,Map namespaceAndmapperIml) throws SQLException {
        Statement statement = connection.createStatement();




    }


    private Connection initDataSource(MyDataSource dataSource) throws ClassNotFoundException, SQLException {

        Class.forName(dataSource.getJdbcDriver());
        return  DriverManager.getConnection(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword());
    }

    public Connection getConnection() {
        return connection;
    }
}
