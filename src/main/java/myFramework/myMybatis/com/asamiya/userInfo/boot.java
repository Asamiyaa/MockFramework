package myFramework.myMybatis.com.asamiya.userInfo;

import myFramework.myMybatis.com.asamiya.executor.Excutor;
import myFramework.myMybatis.com.asamiya.parse.xml.MapperParse;
import myFramework.myMybatis.com.asamiya.session.MysqlSession;

import java.sql.SQLException;

public class boot {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //初始化信息到内存

        MysqlSession mysqlSession = new MysqlSession();
        mysqlSession.initMymybatis();

        //ImyMapper -- 实现类
        // 调用
        Excutor excutor = new Excutor();
        MapperParse mapperParse = new MapperParse();
        Object selectAll = excutor.invoke(mysqlSession.getConnection(), mapperParse.parse(), "myFramework.myMybatis.com.asamiya.userInfo.ImyMapper",
                "selectAll", null);
        System.out.println("------this is mymyBatis ret ----"+selectAll);

        //---问题 执行器和返回包装没有用到



    }



}
