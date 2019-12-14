package com.core.rule.bean.dataObj;

/**
 * 如何设计表 - 扩展性、性能
 *         0.领域（表的拆分一定意义上和代码一致，但存在表特定的字段）  脏数据
 *              是否历史表、是否建立mapping、后台控制表、timeStamp create 、version、del标识 、info/detail(当表字段多时)
 *              建议每个表都维护create_id、create_time、update_id、update_time额外字段
                从数据安全性方面来讲方便问题追踪;  当高并发操作的情况下，采用乐观锁机制可以提高并发性能。
 *
 *           范式：第一范式(确保每列保持原子性，不可以再次拆分)；
 *                第二范式(确保表中的每列都和主键相关)；
 *                第三范式(确保每列都和主键列直接相关,而不是间接相关)。
 *
 *         1.字段要哪些？是否可以整合而不是业务有啥就建啥？是否要关联？vs 冗余
 *         2.关联 一对一 一对多....  其实外键只是从’数据库层面强行约束，表的设计在哪个上面多加，代码不变，‘
 *              参考:https://www.jianshu.com/p/b3969c49dfaa
 *              1.人和身份证 代码层面上二者互相持有，但在表设计上可以一个外键-相互关系，那么谁是提供外键呢？主体持有，这里是人
 *                      这里不使用外键，则身份证表建立冗余，保存人的id
 *              2.部门和员工 在多的一方加外键列描述数据之间的关系。 这样的话，一方间表无需冗余，多方需要外键。
 *                      无需mapping表
 *              3.老师学生  中间表
 *              4.冗余 自关联 一个家庭里有多个人，家族成员之间的关系是自关联的。
 *
 *         3.主键
 *              1.业务主键 、逻辑主键+业务唯一 优先选择逻辑主键，并且是int,自增长的 - 分布式下？以xx_pk结尾
 *              2.单库单表情况下的主键设计使用big int 自增型数据类型
                  无序主键会导致索引页频繁分裂，影响性能。
                  主键数据类型过大，会影响查询性能和内存、带宽、IO资源。
                3.UUID ()，USER () 这样的 MySQL INSIDE 函数对于复制来说是很危险的，会导致主备数据不一致，所以请不要使用。
                 如果一定要使用 UUID 作为主键，让应用程序来产生。
 *         4.各字段类型、长度、约束、默认值、精度 大字段(text/blob)如何处理(各数据库类型不同)
 *                1.当字段的类型为枚举型或布尔型时，建议使用 char (1) 类型
                  字段类型及字段大小的设计要合理
                  主要还是从磁盘存储、网络带宽、节省内存空间、减少磁盘IO几方面来考量。
 *         5.表名、字段名
 *              1.模块名_子模块名_sys_param
 *              2.动词被动形式+描述性后缀 created_by: integer  created_at: timestamp  expires_in - 未来
 *              3.单复数
 *         6.优化
 *
 *           1.设计表时要注意：
                 表字段避免 null 值出现，null 值很难查询优化且占用额外的索引空间，推荐默认数字 0 代替 null。
                 尽量使用 INT 而非 BIGINT，如果非负则加上 UNSIGNED（这样数值容量会扩大一倍），当然能使用 TINYINT、SMALLINT、MEDIUM_INT 更好。
                 使用枚举或整数代替字符串类型
                 尽量使用 TIMESTAMP 而非 DATETIME
                 单表不要有太多字段，建议在 20 以内
                 用整型来存 IP
 *           2.索引
     *              1.命名：sample 表 member_id 上的索引:sample_mid_ind。
     *              2.当删除约束的时候，为了确保不影响到 index，最好加上 keep index 参数。
                     索引并不是越多越好，要根据查询有针对性的创建，考虑在 WHERE 和 ORDER BY 命令上涉及的列建立索引，可根据 EXPLAIN 来查看是否用了索引还是全表扫描
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

 *           3.选择合适的数据类型
 *             使用可存下数据的最小的数据类型，整型 < date,time < char,varchar < blob
                 * 使用简单的数据类型，整型比字符处理开销更小，因为字符串的比较更复杂。如，int 类型存储时间类型，bigint 类型转 ip 函数
                 使用合理的字段属性长度，固定长度的表会更快。使用 enum、char 而不是 varchar
                 * 尽可能使用 not null 定义字段
                 尽量少用 text，非用不可最好分表

              4.***sql 的编写需要注意优化

                 1. 使用 limit 对查询结果的记录进行限定
                 2. 避免 select *，将需要查找的字段列出来
                 3. 使用连接（join）来代替子查询
                 4. 拆分大的 delete 或 insert 语句
                 5. 可通过开启慢查询日志来找出较慢的 SQL
                 6. 不做列运算：SELECT id WHERE age + 1 = 10，任何对列的操作都将导致表扫描，它包括数据库教程函数、计算表达式等等，查询时要尽可能将操作移至等号右边
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
 *             5.分区 、分表 、分库
 *             1. 分区

                     MySQL 在 5.1 版引入的分区是一种简单的水平拆分，用户需要在建表的时候加上分区参数，对应用是透明的无需修改代码

                     对用户来说，分区表是一个独立的逻辑表，但是底层由多个物理子表组成，实现分区的代码实际上是通过对一组底层表的对象封装，但对 SQL 层来说是一个完全封装底层的黑盒子。MySQL 实现分区的方式也意味着索引也是按照分区的子表定义，没有全局索引

                     用户的 SQL 语句是需要针对分区表做优化，SQL 条件中要带上分区条件的列，从而使查询定位到少量的分区上，否则就会扫描全部分区，可以通过 EXPLAIN PARTITIONS 来查看某条 SQL 语句会落在那些分区上，从而进行 SQL 优化，我测试，查询时不带分区条件的列，也会提高速度，故该措施值得一试。

                     分区的好处是：

                     1. 可以让单表存储更多的数据
                     2. 分区表的数据更容易维护，可以通过清楚整个分区批量删除大量数据，也可以增加新的分区来支持新插入的数据。另外，还可以对一个独立分区进行优化、检查、修复等操作
                     3. 部分查询能够从查询条件确定只落在少数分区上，速度会很快
                     4. 分区表的数据还可以分布在不同的物理设备上，从而搞笑利用多个硬件设备
                     5. 可以使用分区表赖避免某些特殊瓶颈，例如 InnoDB 单个索引的互斥访问、ext3 文件系统的 inode 锁竞争
                     6. 可以备份和恢复单个分区
                     分区的限制和缺点：

                     1. 一个表最多只能有 1024 个分区
                     2. 如果分区字段中有主键或者唯一索引的列，那么所有主键列和唯一索引列都必须包含进来
                     3. 分区表无法使用外键约束
                     4.NULL 值会使分区过滤无效
                     5. 所有分区必须使用相同的存储引擎
                     分区的类型：

                     1.RANGE 分区：基于属于一个给定连续区间的列值，把多行分配给分区
                     2.LIST 分区：类似于按 RANGE 分区，区别在于 LIST 分区是基于列值匹配一个离散值集合中的某个值来进行选择
                     3.HASH 分区：基于用户定义的表达式的返回值来进行选择的分区，该表达式使用将要插入到表中的这些行的列值进行计算。这个函数可以包含 MySQL 中有效的、产生非负整数值的任何表达式
                     4.KEY 分区：类似于按 HASH 分区，区别在于 KEY 分区只支持计算一列或多列，且 MySQL 服务器提供其自身的哈希函数。必须有一列或多列包含整数值
                     5. 具体关于 mysql 分区的概念请自行 google 或查询官方文档
                     我首先根据月份把上网记录表 RANGE 分区了 12 份，查询效率提高 6 倍左右，效果不明显，故：换 id 为 HASH 分区，分了 64 个分区，查询速度提升显著。问题解决！

                     结果如下：PARTITION BY HASH (id) PARTITIONS 64

                     select count () from readroom_website; --11901336 行记录

                     / 受影响行数: 0 已找到记录: 1 警告: 0 持续时间 1 查询: 5.734 sec. /

                     select * from readroom_website where month(accesstime) =11 limit 10;

                     / 受影响行数: 0 已找到记录: 10 警告: 0 持续时间 1 查询: 0.719 sec.

                    4. 分表

        分表就是把一张大表，按照如上过程都优化了，还是查询卡死，那就把这个表分成多张表，把一次查询分成多次查询，然后把结果组合返回给用户。

        分表分为垂直拆分和水平拆分，通常以某个字段做拆分项。比如以 id 字段拆分为 100 张表： 表名为 tableName_id%100

        但：分表需要修改源程序代码，会给开发带来大量工作，极大的增加了开发成本，故：只适合在开发初期就考虑到了大量数据存在，做好了分表处理，不适合应用上线了再做修改，成本太高！！！而且选择这个方案，都不如选择我提供的第二第三个方案的成本低！故不建议采用。

        5. 分库

        把一个数据库分成多个，建议做个读写分离就行了，真正的做分库也会带来大量的开发成本，得不偿失！不推荐使用。

        方案二：升级数据库类型，换一种 100% 兼容 mysql 的数据库。优点：不影响现有业务，源程序不需要修改代码，你几乎不需要做任何操作就能提升数据库性能，缺点：多花钱
        mysql 性能不行，那就换个。为保证源程序代码不修改，保证现有业务平稳迁移，故需要换一个 100% 兼容 mysql 的数据库。

        开源选择

        1.tiDB https://github.com/pingcap/tidb

        2.Cubrid https://www.cubrid.org/

        3. 开源数据库会带来大量的运维成本且其工业品质和 MySQL 尚有差距，有很多坑要踩，如果你公司要求必须自建数据库，那么选择该类型产品。

        云数据选择

        1. 阿里云 POLARDB

        2.https://www.aliyun.com/product/polardb?spm...

        官方介绍语：POLARDB 是阿里云自研的下一代关系型分布式云原生数据库，100% 兼容 MySQL，存储容量最高可达 100T，性能最高提升至 MySQL 的 6 倍。POLARDB 既融合了商业数据库稳定、可靠、高性能的特征，又具有开源数据库简单、可扩展、持续迭代的优势，而成本只需商用数据库的 1/10。

        1. 阿里云 OcenanBase

        2. 淘宝使用的，扛得住双十一，性能卓著，但是在公测中，我无法尝试，但值得期待

        3. 阿里云 HybridDB for MySQL (原 PetaData)

        4.https://www.aliyun.com/product/petadata?sp...

        官方介绍：云数据库 HybridDB for MySQL （原名 PetaData）是同时支持海量数据在线事务（OLTP）和在线分析（OLAP）的 HTAP（Hybrid Transaction/Analytical Processing）关系型数据库。
        。

        1. 腾讯云 DCDB

        2.https://cloud.tencent.com/product/dcdb_for...

        官方介绍：DCDB 又名 TDSQL，一种兼容 MySQL 协议和语法，支持自动水平拆分的高性能分布式数据库 —— 即业务显示为完整的逻辑表，数据却均匀的拆分到多个分片中；每个分片默认采用主备架构，提供灾备、恢复、监控、不停机扩容等全套解决方案，适用于 TB 或 PB 级的海量数据场景。

        方案三：一步到位，大数据解决方案，更换 newsql/nosql 数据库。优点：扩展性强，成本低，没有数据容量瓶颈，缺点：需要修改源程序代码
        数据量过亿了，没得选了，只能上大数据了。

        开源解决方案

        hadoop 家族。hbase/hive 怼上就是了。但是有很高的运维成本，一般公司是玩不起的，没十万投入是不会有很好的产出的！

        云解决方案

        这个就比较多了，也是一种未来趋势，大数据由专业的公司提供专业的服务，小公司或个人购买服务，大数据就像水 / 电等公共设施一样，存在于社会的方方面面。

        国内做的最好的当属阿里云。

        使用超级舒服，按量付费，成本极低。

        MaxCompute 可以理解为开源的 Hive，提供 sql/mapreduce/ai 算法 /python 脚本 /shell 脚本等方式操作数据，数据以表格的形式展现，以分布式方式存储，采用定时任务和批处理的方式处理数据。DataWorks 提供了一种工作流的方式管理你的数据处理任务和调度监控。
 *
 *   TODO:  https://blog.csdn.net/jack__frost/article/details/72672252 + https://www.cnblogs.com/hoojjack/p/4705830.html
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
 *
 *
 *
 *
 *
 *
 */



public class RuleDo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.id
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.draftNo
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    private String draftno;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.property
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    private String property;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.isRegex
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    private Integer isregex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.isJsEngine
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    private Integer isjsengine;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.isEmpty
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    private Integer isempty;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.length
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    private Integer length;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column rule.type
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    private String type;


    public RuleDo(){}
    public RuleDo(String draftno, String property, Integer isregex, Integer isjsengine, Integer isempty, Integer length, String type) {
        this.draftno = draftno;
        this.property = property;
        this.isregex = isregex;
        this.isjsengine = isjsengine;
        this.isempty = isempty;
        this.length = length;
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.id
     *
     * @return the value of rule.id
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */



    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.id
     *
     * @param id the value for rule.id
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.draftNo
     *
     * @return the value of rule.draftNo
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public String getDraftno() {
        return draftno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.draftNo
     *
     * @param draftno the value for rule.draftNo
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public void setDraftno(String draftno) {
        this.draftno = draftno == null ? null : draftno.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.property
     *
     * @return the value of rule.property
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public String getProperty() {
        return property;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.property
     *
     * @param property the value for rule.property
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.isRegex
     *
     * @return the value of rule.isRegex
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public Integer getIsregex() {
        return isregex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.isRegex
     *
     * @param isregex the value for rule.isRegex
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public void setIsregex(Integer isregex) {
        this.isregex = isregex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.isJsEngine
     *
     * @return the value of rule.isJsEngine
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public Integer getIsjsengine() {
        return isjsengine;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.isJsEngine
     *
     * @param isjsengine the value for rule.isJsEngine
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public void setIsjsengine(Integer isjsengine) {
        this.isjsengine = isjsengine;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.isEmpty
     *
     * @return the value of rule.isEmpty
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public Integer getIsempty() {
        return isempty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.isEmpty
     *
     * @param isempty the value for rule.isEmpty
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public void setIsempty(Integer isempty) {
        this.isempty = isempty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.length
     *
     * @return the value of rule.length
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public Integer getLength() {
        return length;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.length
     *
     * @param length the value for rule.length
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column rule.type
     *
     * @return the value of rule.type
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column rule.type
     *
     * @param type the value for rule.type
     *
     * @mbg.generated Thu Nov 21 16:34:50 CST 2019
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}