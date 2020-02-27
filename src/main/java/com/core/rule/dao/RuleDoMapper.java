package com.core.rule.dao;

import com.core.rule.bean.dataObj.DraftDo;
import com.core.rule.bean.dataObj.RuleDo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
     1.理解索引的存储结构(两层+有序->范围查询)，
     2.理解回表，是否全表扫描，复合索引、索引字节数、缓存命中
     3.锁、竞争 、行锁 、表锁
     4.同步 、 可靠性
     5.慢日志监控查询
     6.大数据量的插入和读取  order by 是将所有数据一起吗？
     7.大文件保存  blob 、视频
     8.根据业务场景合理优化业务逻辑，写出sql 对join 、exsist 、distinct 、group by 等组合嵌套思考
     性能优化之查询转换 - 子查询类：http://www.10tiao.com/html/188/201612/2650272664/1.html



 1.循环赋值修改  https://blog.51cto.com/alun51cto/2050064
   DB2 游标  https://blog.csdn.net/feier7501/article/details/25922303


 * 1.sql编写技巧 - sql 的编写需要注意优化
 *
 * 数据库的查询优化技术：https://blog.csdn.net/littledream/article/details/4243491
 *                    https://blog.csdn.net/littledream/article/details/4439812
 *
     1. 使用 limit 对查询结果的记录进行限定
     2. 避免 select *，将需要查找的字段列出来
     3. 使用连接（join）来代替子查询
     4. 拆分大的 delete 或 insert 语句
     5. 可通过开启慢查询日志来找出较慢的 SQL
     6. 不做列运算：SELECT id WHERE age + 1 = 10，‘ 任何对列的操作都将导致表扫描 ’，它包括数据库教程函数、计算表达式等等，查询时要尽可能将操作移至等号右边
     7.sql 语句尽可能简单：一条 sql 只能在一个 cpu 运算；大语句拆小语句，减少锁时间；一条大 sql 可以堵死整个库
     8.OR 改写成 IN：OR 的效率是 n 级别，IN 的效率是 log (n) 级别，in 的个数建议控制在 200 以内
     9. 不用函数和触发器，在应用程序实现
     10. 避免 % xxx 式查询
     11. 少用 JOIN
     12. 使用同类型进行比较，比如用 '123' 和 '123' 比，123 和 123 比
     13. 尽量避免在 WHERE 子句中使用！= 或 <> 操作符，否则将引擎放弃使用索引而进行全表扫描
     14. 对于连续数值，使用 BETWEEN 不用 IN：SELECT id FROM t WHERE num BETWEEN 1 AND 5
     15. 列表数据不要拿全表，要使用 LIMIT 来分页，每页数量也不要太大
 *
 * 索引：
 *   1.索引
*        1.命名：sample 表 member_id 上的索引:sample_mid_ind。
*        2.当删除约束的时候，为了确保不影响到 index，最好加上 keep index 参数。
         索引并不是越多越好，要根据查询有针对性的创建，考虑在 WHERE 和 ORDER BY 命令上涉及的列建立索引，
         可根据 EXPLAIN 来查看是否用了索引还是全表扫描
         应尽量避免在 WHERE 子句中对字段进行 NULL 值判断，否则将导致引擎放弃使用索引而进行全表扫描
         值分布很稀少的字段不适合建索引，例如 "性别" 这种只有两三个值的字段
         字符字段只建前缀索引
         字符字段最好不要做主键
         不用外键，由程序保证约束
         尽量不用 UNIQUE，由程序保证约束
         使用多列索引时主意顺序和查询条件保持一致，同时删除不必要的单列索引

         查询频繁的列，在 where，group by，order by，on 从句中出现的列
         where 条件中 <，<=，=，>，>=，between，in，以及 like 字符串 + 通配符（%）出现的列
         长度小的列，索引字段越小越好，因为数据库的存储单位是页，一页中能存下的数据越多越好
         离散度大（不同的值多）的列，放在联合索引前面。查看离散度，通过统计不同的列值来实现，count 越大，离散程度越高：

         索引失效：

         WHERE字句的查询条件里有不等于号（WHERE column!=…），MYSQL将无法使用索引
         类似地，如果WHERE字句的查询条件里使用了函数（如：WHERE DAY(column)=…），MYSQL将无法使用索引
         在JOIN操作中（需要从多个数据表提取数据时），MYSQL只有在主键和外键的数据类型相同时才能使用索引，否则即使建立了索引也不会使用
         如果WHERE子句的查询条件里使用了比较操作符LIKE和REGEXP，MYSQL只有在搜索模板的第一个字符不是通配符的情况下才能使用索引。比如说，如果查询条件是LIKE 'abc%',MYSQL将使用索引；如果条件是LIKE '%abc'，MYSQL将不使用索引。
         在ORDER BY操作中，MYSQL只有在排序条件不是一个查询条件表达式的情况下才使用索引。尽管如此，在涉及多个数据表的查询里，即使有索引可用，那些索引在加快ORDER BY操作方面也没什么作用。
         如果某个数据列里包含着许多重复的值，就算为它建立了索引也不会有很好的效果。比如说，如果某个数据列里包含了净是些诸如“0/1”或“Y/N”等值，就没有必要为它创建一个索引。
         对于多列索引，不是使用的第一部分，则不会使用索引
         如果条件中有or(并且其中有or的条件是不带索引的)，即使其中有条件带索引也不会使用(这也是为什么尽量少用or的原因)。注意：要想使用or，又想让索引生效，只能将or条件中的每个列都加上索引
         如果列类型是字符串，那一定要在条件中将数据使用引号引用起来,否则不使用索引
         如果mysql估计使用全表扫描要比使用索引快,则不使用索引
 *
 * 2.思路转换编写sql
 *
 *
 *
 *
 * 3.plsql
 *  虽然推崇减少‘数据库端’逻辑处理，但是比如要临时查看数据库数据，比对，批量插入，学会使用plsql编程可以加快速度而无需写一个程序。
     每次写完一条sql都需要进行执行计划，查看是否合理。
     0.explain   https://www.cnblogs.com/xuanzhi201111/p/4175635.html
     1.含义

     场景:-未添加索引
     查看两个截图

     1.视图
         CREATE view testVVV AS SELECT ID , 1+2 AS total from draft ;
         1.用于多个表连接，这些表在设计上是遵循原则的，但是业务逻辑又常常结合使用
         2.对数据进行处理返回 ，过滤...

         drop view testv;

     2.存储过程
         1.错误

         create or REPLACE PROCEDURE testProcedur AS
         BEGIN
         for i in 1..5 loop

         insert into draft values("test" , "test")
         end loop
         end ;

     2.正确  -- 语法学习
         create  PROCEDURE testProcedure1()
         BEGIN
         declare i int ;
         set i = 0 ;
         while i<5 do
         insert  into draft values('abc','abcTest');
         set i= i+1;
         end while ;
         end ;

         https://www.cnblogs.com/linjiqin/archive/2011/04/16/2018463.html
         https://blog.csdn.net/moxigandashu/article/details/64616135#1-%E5%AD%98%E5%82%A8%E8%BF%87%E7%A8%8B
         --有参
         --有输出的

     1.便于直接从客户端插入处理数据 if / for逻辑

        游标

     3.索引
         create index index_a on draft(draftNo)
         唯一索引 vs 普通索引

     3.函数 + 操作符  -- 哪些逻辑放到数据库哪些放到程序，放到数据库 sql中的算吗？还是只算存储过程...
         https://www.jianshu.com/p/32bc449a1bf6
         数学函数
         字符串函数 //mysql中处理字符串时，默认第一个字符下标为1，即参数position必须大于等于1
         日期和时间函数
         条件判断函数
         系统信息函数
         加密函数
         格式化函数

         select abs(t.draftDescribe)+100 as absvalue from draft t where t.id = 4;

         select FLOOR(RAND()*100)

         select ROUND(1.234,2)

         select CONCAT_WS('abc','a','b')

         select strcmp('a','B')

         SELECT reverse('ABC');

         load_file(file_name)

         select MONTH(now());

         select ADDDATE(NOW(),2);

         select DATEDIFF(NOW(),(ADDDATE(NOW(),2)))

         为输出进行转化
         select
         CASE
         when 1<2
         then 's'
         when 1>2
         then 'e'
         end

         select if(1 > 0,'正确','错误')

         加密
         select MD5('ab')
         select DECODE('ab','a')

         union
         minix

         子查询 = 临时表( 关联子查询-嵌套子查询 ) vs  join( 连接 )
         join与子查询对比:https://blog.csdn.net/wangzhuo14/article/details/51771472
         MySQL嵌套查询（子查询）：https://blog.csdn.net/Trisyp/article/details/78460289
         为什么MySQL不推荐使用子查询和join：https://blog.csdn.net/weixin_38676357/article/details/81510079


     navicat 快捷键

     ctrl+shift+r   命令执行
     编辑sql时选中一行快捷键：鼠标三击当前行
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

@Repository
public interface RuleDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    int insert(RuleDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    RuleDo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    List<RuleDo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rule
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    int updateByPrimaryKey(RuleDo record);

    //TODO ：SQL实现
    List<RuleDo> selectRuleDoByNo(String draftNo);
}