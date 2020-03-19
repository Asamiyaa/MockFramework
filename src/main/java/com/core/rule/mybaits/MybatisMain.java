package com.core.rule.mybaits;

import boot.SpringBootApplication;
import com.core.rule.bean.dataObj.CompositDTO;
import com.core.rule.bean.dataObj.DraftDo;
import com.core.rule.dao.DraftDoMapper;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = SpringBootApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MybatisMain {
    /***
     *
     * ******这些同样是在你明确你要做什么，以及sql已经在db上测试无问题，满足条件的前提下。mybatis是为了连接sql和java的工具*******
     *
     * https://mybatis.org/mybatis-3/zh/index.html
     * 1.namespace:完全限定名（比如 “com.mypackage.MyMapper.selectAllThings）将被直接用于查找及使用。--getName定位 锚
     *
     *2.不同作用域和生命周期类是至关重要的，因为错误的使用会导致非常严重的并发问题。理解作用域的选择
     *   SqlSessionFactoryBuilder
     这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。 因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）。 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，但是最好还是不要让其一直存在，以保证所有的 XML 解析资源可以被释放给更重要的事情。

     SqlSessionFactory
     SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏味道（bad smell）”。因此 SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。

     SqlSession
     每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。 绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。 也绝不能将 SqlSession 实例的引用放在任何类型的托管作用域中，比如 Servlet 框架中的 HttpSession。 如果你现在正在使用一种 Web 框架，要考虑 SqlSession 放在一个和 HTTP 请求对象相似的作用域中。 换句话说，每次收到的 HTTP 请求，就可以打开一个 SqlSession，返回一个响应，就关闭它。 这个关闭操作是很重要的，你应该把这个关闭操作放到 finally 块中以确保每次都能执行关闭。 下面的示例就是一个确保 SqlSession 关闭的标准模式：
     *  try (SqlSession session = sqlSessionFactory.openSession()) {}
     *  try (SqlSession session = sqlSessionFactory.openSession()) {BlogMapper mapper = session.getMapper(BlogMapper.class);// 你的应用逻辑代码
     *  SqlSessionFactoryBuilder -resource/envriment/reader../prop- SqlSessionFactory -SqlSession
     *
     * 3.配置   https://mybatis.org/mybatis-3/zh/configuration.html#typeAliases
     *  -- main 方法 + mybatis提供的工具类 + 加载配置文件xx.xml + getSqlSession调用即可 ---> 后期融合了spring则
     *             由spring扫描该信息，使用spring包装后的类进行调用处理
     *      1.setting 全局配置
     *
     *      2.proper 配置属性
     *
     *      3.typeAliases
     *
     *      4.typeHandlers  无论是 MyBatis 在预处理语句（PreparedStatement）中设置一个参数时，还是从结果集中取出一个值时， 都会用类型处理器将获取的值以合适的方式转换成 Java 类型。下表描述了一些默认的类型处理器。
     *                      你可以重写类型处理器或创建你自己的类型处理器来处理不支持的或非标准的类型。 具体做法为：实现 org.apache.ibatis.type.TypeHandler 接口， 或继承一个很便利的类 org.apache.ibatis.type.BaseTypeHandler， 然后可以选择性地将它映射到一个 JDBC 类型。比如：
     *
     *      5.处理枚举类型  直接映射无需单独
     *              insert into users2 (id, name, funkyNumber, roundingMode) values (
                     #{id}, #{name}, #{funkyNumber}, #{roundingMode, typeHandler=org.apache.ibatis.type.EnumTypeHandler}
     *
     *     6.objectFactory  MyBatis 每次创建结果对象的新实例时，它都会使用一个对象工厂（ObjectFactory）实例来完成。
     *
     *     7.plugins  MyBatis 允许你在已映射语句执行过程中的某一点进行拦截调用。 --- Btrace  - 拦截器模式 - 切面
     *
             Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
             ParameterHandler (getParameterObject, setParameters)
             ResultSetHandler (handleResultSets, handleOutputParameters)
             StatementHandler (prepare, parameterize, batch, update, query)

         8.environments   devlopment/..   transactionManager jdbc/manu  如果你正在使用 Spring + MyBatis，则没有必要配置事务管理器， 因为 Spring 模块会使用自带的管理器来覆盖前面的配置。
         数据源type=”[UNPOOLED|POOLED|JNDI]”）：

     *   9.databaseIdProvider
     *
     *   10.mappers     *
             <mappers>
             <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
             <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
             <mapper resource="org/mybatis/builder/PostMapper.xml"/>


         11.TODO:<cache></cache>  MyBatis 内置了一个强大的事务性查询缓存机制，它可以非常方便地配置和定制。 查看：https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#Parameters最后章节
             默认情况下，只启用了本地的会话缓存，它仅仅对一个会话中的数据进行缓存。 要启用全局的二级缓存，
             二级缓存是事务性的。这意味着，当 SqlSession 完成并提交时，或是完成并回滚，但没有执行 flushCache=true 的 insert/delete/update 语句时，缓存会获得更新。
     *
     *
     * 4.映射文件
     *        ---回忆jdbc流程 - 思考省略了哪些，扩展了哪些 - 技巧 源代码实现
     *       1.元素允许你配置很多属性来配置每条语句的作用细节。
     <select
     id="selectPerson"
     parameterType="int"
     parameterMap="deprecated"
     resultType="hashmap"         //较简单的 mybatis直接自动完成映射
     resultMap="personResultMap"  //TODO 是否可以定义多个对于不同的复杂查询中 如果没有匹配? null or 报错 resultMap	外部 resultMap 的命名引用。结果集的映射是 MyBatis 最强大的特性，如果你对其理解透彻，许多复杂映射的情形都能迎刃而解
     flushCache="false"
     useCache="true"
     timeout="10"
     fetchSize="256"
     statementType="PREPARED"
     resultSetType="FORWARD_ONLY">
     resultOrdered	这个设置仅针对嵌套结果 select 语句适用：如果为 true，就是假设包含了嵌套结果集或是分组

     <insert>.....</insert>   useGeneratedKeys
     *
     *      2.批量操作
     *              <insert id="insertAuthor" useGeneratedKeys="true"
     keyProperty="id">
     insert into Author (username, password, email, bio) values
     <foreach item="item" collection="list" separator=",">
     (#{item.username}, #{item.password}, #{item.email}, #{item.bio})
     </foreach>
     </insert>
     *
     *       3.sql
     这个元素可以被用来定义可重用的 SQL 代码段，这些 SQL 代码可以被包含在其他语句中。
     它可以（在加载的时候）被静态地设置参数。 在不同的包含语句中可以设置不同的值到参数占位符上
     *
     *        4.参数和返回值
     *           参数是对象  -- 自动识别
     *              <insert id="insertUser" parameterType="User">
     insert into users (id, username, password)
     values (#{id}, #{username}, #{password})
     </insert>
     *
     *          空参数
     *               JDBC 要求，如果一个列允许 null 值，并且会传递值 null 的参数，就必须要指定 JDBC Type。阅读 PreparedStatement.setNull()的 JavaDoc 文档来获取更多信息。
     *              #{age,javaType=int,jdbcType=NUMERIC,typeHandler=MyTypeHandler}可以没有typeHandler
     *              #{height,javaType=double,jdbcType=NUMERIC,numericScale=2}
     *
     *           直接替换和占位
     *           其中 ${column} 会被直接替换，而 #{value} 会被使用 ? 预处理。
     *           @Select("select * from user where ${column} = #{value}")
                    User findByColumn(@Param("column") String column, @Param("value") String value);
                    用这种方式接受用户的输入，并将其用于语句中的参数是不安全的，会导致潜在的 SQL 注入攻击，因此要么不允许用户输入这些字段，要么自行转义并检验。
     *
     *          resultType
     *                  map / pojo对象  /通过as进行别名匹配
     *          resultMap
     *                  构建返回值嵌套模型
     *                      结果映射（resultMap）

     *           支持的 JDBC 类型  详见官方文档 - 和java类型对应关系
     *           构造方法    见对应xml
     *
     *           关联的嵌套 Select 查询  N+1问题 这种方式虽然很简单，但在大型数据集或大型数据表上表现不佳。这个问题被称为“N+1 查询问题”。 概括地讲，N+1 查询问题是这样子的：
                                         你执行了一个单独的 SQL 语句来获取结果的一个列表（就是“+1”）。 -----TODO: 详见mybatis官方
     *                  <resultMap id="blogResult" type="Blog">
                        <association property="author" column="author_id" javaType="Author" select="selectAuthor"/>
                        </resultMap>

                        <select id="selectBlog" resultMap="blogResult">
                        SELECT * FROM BLOG WHERE ID = #{id}
                        </select>

                        <select id="selectAuthor" resultType="Author">
                        SELECT * FROM AUTHOR WHERE ID = #{id}
                        </select>
     *           关联的多结果集（ResultSet）
                        1.集合嵌套select
                        2.集合嵌套映射
                        3.集合的多结果集（ResultSet） + 存储过程
                鉴别器 - 对返回值进行判断并映射到不同的resultType  -- extends
                        <discriminator javaType="int" column="draft">
                        <case value="1" resultType="DraftPost"/>
                        </discriminator>

     *
     *
     * 5.动态sql  -- 模糊查询 精确查询需要动态sql吗？目前只知道id查询方式
     *      如果你有使用 JDBC 或其它类似框架的经验，你就能体会到根据不同条件拼接 SQL 语句的痛苦。例如拼接时要确保不能忘记添加必要的空格，还要注意去掉列表最后一个列名的逗号。
     *      利用动态 SQL 这一特性可以彻底摆脱这种痛苦。
     *      hql
     *
     *      1.mybatis能够对入参判断，比如查询 传入了对象，含有部分属性，那么sql能否自动拼接成这些有用的
     *        update 呢，其他不修改的值都是null,会不会重置了那些元素呢？
     *        delete 呢 会不会无需指定 自动呢？
     *
     *        ********   myabtis 不会直接将searchbean转为where条件需要在sql中完 **********  删-改-查
     *
     *        deleteByPrimaryKey3 测试案例
     *
     *       2.查询也一样
     *       3.更新
     *
     *     2.    <if test = "draftno != null">draftno = #{draftno},</if>  1.是否都是判断 !=null ==》 是 这里byte类型也是判断的null   2.句末的,
     *          <choose></choose>
     *
     *              <select id="findActiveBlogLike"
                        resultType="Blog">
                        SELECT * FROM BLOG WHERE state = ‘ACTIVE’
                        <choose>
                        <when test="title != null">
                        AND title like #{title}
                        </when>
                        <when test="author != null and author.name != null">
                        AND author_name like #{author.name}
                        </when>
                        <otherwise>
                        AND featured = 1
                        </otherwise>
                        </choose>
                        </select>
     *          <set></set>
     *
     *         <trim, where, set?
     *                  delete from draft where 1=1  ===>修改为<where>标签</where>
     *                  TODO：注意多个条件间的and
     *                      <if test="author != null and author.name != null">
                                AND author_name like #{author.name}
                            </if>
     *
     *      3.注解类型 可以使用script完成类似功能
     *      4.bind
     *      5.多数据库支持 <if test="_databaseId == 'oracle'">
     *      6.动态 SQL 中的可插拔脚本语言
     *
     *
     * 6.java api  -- https://mybatis.org/mybatis-3/zh/java-api.html
     *      builder /sqlsession /RowBounds/rollback  --通过sqlsession得到通过basedao的调用
     *
     *      本地缓存  vs 二级缓存 https://tech.meituan.com/2018/01/19/mybatis-cache.html
                    Mybatis 使用到了两种缓存：本地缓存（local cache）和二级缓存（second level cache）。
                    每当一个新 session 被创建，MyBatis 就会创建一个与之相关联的本地缓存。任何在 session 执行过的查询语句本身都会被保存在本地缓存中，
                    那么，相同的查询语句和相同的参数所产生的更改就不会二度影响数据库了。本地缓存会被增删改、提交事务、关闭事务以及关闭 session 所清空。
                    默认情况下，本地缓存数据可在整个 session 的周期内使用，这一缓存需要被用来解决循环引用错误和加快重复嵌套查询的速度，所以它可以不被禁用掉，
                    但是你可以设置 localCacheScope=STATEMENT 表示缓存仅在语句执行时有效。
                    注意，如果 localCacheScope 被设置为 SESSION，那么 MyBatis 所返回的引用将传递给保存在本地缓存里的相同对象。对返回的对象（例如 list）
                    做出任何更新将会影响本地缓存的内容，进而影响存活在 session 生命周期中的缓存所返回的值。因此，不要对 MyBatis 所返回的对象作出更改，以防后患。
     *
     *
     *7.sql构建器
     *      public String deletePersonSql() {
                return new SQL() {{
                DELETE_FROM("PERSON");
                WHERE("ID = #{id}");
                }}.toString();
                }

       8.日志
     *
     *

     *窥探数据库元信息来 嗅探

    不建议注解
        public interface BlogMapper {
            @Select("SELECT * FROM blog WHERE id = #{id}")
            Blog selectBlog(int id);
            }
     */

/*
    public static void main(String[] args) {
       // Json
    }
*/


//***********************一定要保持最小改动快速迭代而不是一下跳跃多个变量，导致排查加剧，思路混乱*****************************
    @Autowired
    private DraftDoMapper draftDoMapper;

    @Test
    public void test1() {
        DraftDo draftDo = new DraftDo();
        //draftDo.setId(1l);
        draftDo.setDraftno("2");
        draftDo.setDrafttemplate("2".getBytes());
        draftDo.setDraftdescribe("2");
        //1.测试主键生成 - 未配置useGeneratedKeys仍然生成
        draftDoMapper.insert(draftDo);
        //加上useGeneratedKeys查看生成逻辑 - 仍热和未配置一样 TODO:主键策略思考

        //主键返回这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键（比如：像 MySQL 和 SQL Server 这样的关系数据库管理系统的自动递增字段）
        // 默认值：false。 ------> 1.能否自动生成由数据库决定，这也影响着是否先从库中获取主键，在插入等     2.这里useGeneratedKeys是为了得到返回的值
        //输出查看插入返回的值
        System.out.println(draftDo.getId()); //50031
        // setId ==> this.id = id+100000000000l; 为了在调用时修改值
        System.out.println(draftDo.getId()); //100000050032

        //TODO:得到上一个的id值直接插到另一个列中 -- 使用类似于其他数据库生成主键那样，先执行语句，得到值后插入 ---参数呢
    }

    @Test
    public void test2() {
        DraftDo draftDo = new DraftDo();
        draftDo.setDraftno("4");
        draftDo.setDrafttemplate("4".getBytes());
        draftDo.setDraftdescribe("4");
        DraftDo draftDo2 = new DraftDo();
        //draftDo.setId(1l);
        draftDo2.setDraftno("4");
        draftDo2.setDrafttemplate("4".getBytes());
        draftDo2.setDraftdescribe("4");
        List list = new ArrayList<>();
        list.add(draftDo);
        list.add(draftDo2);
        //测试批量
        draftDoMapper.insertBatch(list);
        //获取批量返回的值 --遍历当前对象id ---> 这里得到的id暂存 可以用于后续操作--〉TODO：能否直接将id插入接下来的语句呢？
        System.out.println(draftDo.getId() + "-------" + draftDo2.getId());

    }

    @Test
    public void test3() {
        DraftDo draftDo = new DraftDo();
        //draftDo.setId(1l);
        draftDo.setDraftno(null);
        draftDo.setDrafttemplate("5".getBytes());
        draftDo.setDraftdescribe("5");

        //测试插入的对象参数为null --修改xml 不适用jdbcType 后报错，因为mybatis不知道塞入哪种默认值 --> 那么设置了后放进啥默认值呢？
        //TODO:MYBATIS 中数据类型和java对应关系
        draftDoMapper.insert(draftDo);
        System.out.println(draftDo.getId());

    }

    @Test
    public void test4() {
        DraftDo draftDo = new DraftDo();
        //draftDo.setId(1l);
        draftDo.setDraftno(null);
        draftDo.setDrafttemplate("5".getBytes());
        draftDo.setDraftdescribe("5");

        //$ vs #
        List<DraftDo> draftno = draftDoMapper.selectByDraftNo1("draftno", "2");
        System.out.println(draftno.size());

    }

    @Test
    public void test5() {
        DraftDo draftDo = new DraftDo();
        //draftDo.setId(1l);
        draftDo.setDraftno(null);
        draftDo.setDrafttemplate("5".getBytes());
        draftDo.setDraftdescribe("5");
        //测试返回resulttype / map
        /**
         * MyBatis 创建时的一个思想是：数据库不可能永远是你所想或所需的那个样子。 我们希望每个数据库都具备良好的第三范式或 BCNF 范式，可惜它们不总都是这样。
         * 如果能有一种完美的数据库映射模式，所有应用程序都可以使用它，那就太好了，但可惜也没有。 而 ResultMap 就是 MyBatis 对这个问题的答案。
         */
        List draftC = draftDoMapper.selectComposit();
//        CompositDTO compositDTO = draftDoMapper.selectComposit();　　定义者必须知道可能是否返回list,这样的话，就将interface中定义为list
        System.out.println(draftC.size());

        //TODO ----------贫血模型 - 充血模型
        /**
         * 你可能想把它映射到一个智能的对象模型，这个对象表示了一篇博客，它由某位作者所写，有很多的博文，每篇博文有零或多条的评论和标签。 我们来看看下面这个完整的例子，
         * 它是一个非常复杂的结果映射（假设作者，博客，博文，评论和标签都是类型别名）。 不用紧张，我们会一步一步来说明。虽然它看起来令人望而生畏，但其实非常简单。
         *
         *
         *
         *
         */
}

    /**
     * 为了测试 mybatis能够根据参数 ，自动生成对应的sql  还是说需要手动if...choose
     */
    @Test
    public void test6() {
        DraftDo draftDo = new DraftDo();
        //draftDo.setId(1l);
        draftDo.setDraftno("4");
        draftDo.setDraftdescribe("5");

//        int draftC = draftDoMapper.deleteByPrimaryKey(1l);
//        int draftC = draftDoMapper.deleteByPrimaryKey2("2");   删除肯定是要加if 进行where判断的 --跟select一样， myabtis 不会直接将searchbean转为where条件需要在sql中完成
        int draftC = draftDoMapper.deleteByPrimaryKey3(draftDo);  //根据bean的属性进行组合sql语句，这里的bean类似于searcbean 了
        System.out.println(draftC);


    }
@Test
    public void test7(){
        DraftDo draftDo2 = new DraftDo();
        draftDo2.setId(50044l);
        draftDo2.setDraftno("6666666");
       // draftDo2.setDraftdescribe("533wwwww33");  设为null是否会修改呢？
        draftDo2.setDrafttemplate("3343wwww4".getBytes());
//    int i = draftDoMapper.updateByPrimaryKey(draftDo2);
//    System.out.println("------------->>>>>>"+i);

    //假设一个update对象其他值不修改，这样改的话就会将所有的有值改为null - so 修改语句  --> <set >...<if>组合  其实这种才是真正用到的，mybatis自动生成大多是模板不具有实战型
    //仍然需要自己定义修改
    //大小写是对象的，而不是数据库

    int i1 = draftDoMapper.updateByPrimaryKey2(draftDo2); //不会修改，但是调用updateByPrimaryKey就会
    System.out.println(i1);


}

//--------------  ******** 一个完整查询套路 ************  ---------------
    @Test
    public void test8(){
        DraftDo draftDo2 = new DraftDo();
  //      draftDo2.setId(50044l);
        draftDo2.setDraftno("6666666");
 //       draftDo2.setDrafttemplate("3343wwww4".getBytes());
        List list = draftDoMapper.selectBySearchBean(draftDo2);
        System.out.println(list.size());

        //  in 的使用
        ArrayList<Object> ll = new ArrayList<>();
        ll.add(50044l);ll.add(50043l);ll.add(50041l);
        List list1 = draftDoMapper.selectByin(ll);
        System.out.println(list1.size());

        //like 的使用
        DraftDo draftDo3 = new DraftDo();
        draftDo3.setDraftno("6");
        List list2 = draftDoMapper.selectLike(draftDo3);
        System.out.println(list2.size());
    }


}