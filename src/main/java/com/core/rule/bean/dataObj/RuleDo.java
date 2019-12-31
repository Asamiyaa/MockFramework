package com.core.rule.bean.dataObj;

import org.springframework.stereotype.Repository;

/**数据库设计没有最好，只有最合适( 不同场景和未来方向 )  -  过程迭代和逐步求精
 * 如何设计表 - 扩展性（ 当前 - 未来发展方向 ）、性能  （数据库修改会涉及到数据迁移、上下游、各种风险 所以改类的代价小于数据库）
 *
 *
 *          1. 需求分析阶段
 *                  理解客户需求，询问用户如何看待未来需求变化。让客户解释其需求，而且随着开发的继续，还要经常询问客户保证其需求仍然在开发的目的之中
 *                  ER 图表和数据字典可以让任何了解数据库的人都明确如何从数据库中获得数据
 *
 *          2. 领域技巧-字段列确定
 *
 *                 是否历史表、是否建立mapping、后台控制表(中间状态)、timeStamp create 、creator 、version、del标识(维护索引整体性) 、info/detail(当表字段多时)
                    建议每个表都维护create_id、create_time、update_id、update_time额外字段
                    从数据安全性方面来讲方便问题追踪;  当高并发操作的情况下，采用乐观锁机制可以提高并发性能。

                  数据驱动
                     户界面要访问外部数据源(文件、XML 文档、其他数据库等)，不妨把相应的连接和路径信息存储在用户界面支持
                     表里。还有，如果用户界面执行工作流之类的任务(发送邮件、打印信笺、修改记录状态等)，那么产生工作流的数
                     据也可以存放在数据库里。角色权限管理也可以通过数据驱动来完成。
                     事实上，如果过程是数据驱动的，你就可以把相当大的责任推给用户，由用户来维护自己的工作流过程。

     *            树型关系的数据表 - 商品信息表的设计 - 多用户及其权限管理的设计 - 简洁的批量m:n设计 - 冗余数据的取舍
     *               https://blog.csdn.net/littledream/article/details/4243517
 *
                  字段设计 - 考虑变化
                    性能、扩展性和数据完整性方面达到了最好平衡  3nf -> 是否需要将字段拆分为不同的表 以便后期扩展比如商品表

                  关联 一对一 一对多....  其实外键只是从’数据库层面强行约束，表的设计在哪个上面多加，代码不变，‘
                 *              参考:https://www.jianshu.com/p/b3969c49dfaa
                 *              1.人和身份证 代码层面上二者互相持有，但在表设计上可以一个外键-相互关系，那么谁是提供外键呢？主体持有，这里是人
                 *                      这里不使用外键，则身份证表建立冗余，保存人的id
                 *              2.部门和员工 在多的一方加外键列描述数据之间的关系。 这样的话，一方间表无需冗余，多方需要外键。
                 *                      无需mapping表
                 *              3.老师学生  中间表
                 *              4.冗余 自关联 一个家庭里有多个人，家族成员之间的关系是自关联的。

                 数据表分类说明
                 业务数据表：记录业务发生的过程和结果。如，合同、出仓单、申请单、凭证。
                 基本编码表：描述业务实体的基本信息和编码。如，产品、客户、供应商、雇员。
                 辅助编码表：描述属性的列表值。如，合同类型、职称、民族、付款方式。
                 系统信息表：存放与系统操作、业务控制有关的参数。如，用户信息、权限、用户配
                 置信息、成本核算方式。
                 累计数据表：存放业务的当前值和累计值。如，当前库存、当前存款、累计销售、累
                 计支出、应收账款。
                 结算数据表：存放各个时期末的结存数。如，月末库存、月末银行存款、应收账款月
                 结。
                 决策数据表：存放各个时期内发生的统计值。如，月销售统计、月回款统计、出入库
                 统计。

                 程序模块分类说明
                 初始化：系统运行前对系统进行数据的初始化。如，库存初始化。
                 业务处理：业务过程的控制和结果记录。如，合同录入、费用审批、出入库。
                 完整性检测与修正：对累计数据表进行检查并自动修正。如对当前库存、当前存款、
                 累计销售的检查和重新计算。
                 结算处理：计算并记录各个时期末的结存数。库存月结、应收账款月结。
                 统计处理：计算并记录各个时期内发生的统计数。如，统计月销售、统计月回款、统
                 计出入库。
                 3.2 数据表间的关系
                 业务数据表<-->基本编码表 主-外键关系。如，合同表<-->客户编码表;
                 业务数据表<-->辅助编码表 主-外键关系。如，合同表<-->付款方式;
                 业务数据表、累计数据表、结算数据表：累计数据表=结算数据表(上期末) + 业务数

                 据表(本期内发生)。如当前库存=上月末库存数+(本月入库数-本月出库数);
                 决策数据表<-->业务数据表 决策数据表的数据是由业务数据表中数据导出(统计)的

                 辅助编码表
                 为了使辅助编码表能起到预期的效能，又不因过多的辅助编码表难以管理，故对辅助
                 编码表的使用作如下规定：
                 1. 当某辅助编码表的编码允许用户添加时，应设计成“独立”的数据表；否则，将不
                 允许用户添加编码的各辅助编码表合并成一个“通用”的辅助编码表。
                 2. “独立”的辅助编码表与主表的列采用主-外约束保证列数据完整性。
                 3. “通用”的辅助编码表与各主表间没有约束关系，主表列的数据完整性由列说明的
                 “域”来保证。
                 4. “通用”的辅助编码表除编码和名称列外，还有一个标识列，用来标识合并前的各
                 码表，该标识列+编码列作为该表的主键。
                 5. 对于“独立”的辅助编码表，用户只可添加新的编码和改变名称，并且不能改变一
                 个编码所代表的意义；对于“通用”的辅助编码表，原则上不允许用户修改，或只有限地
                 允许修改名称。

                 业务数据表
                 1. 设有‘录入人’和‘录入日期’列，由系统自动记录。
                 2. 记录单据的表中设置“自动单据号”，由两个字符开始以区分单据类型，后跟一数
                 字序列表示序号。‘自动单据号’由系统自动生成，作为主表的主键，不允许用户修改。
                 当有对应的纸质单据时，设置“单据号”用于记录纸质单据的单据号。
                 3. 明细表中设有行序号，自动记录行的录入顺序。
                 4. 设置“存档标记”列，用于抽取数据到决策数据库时的更新标记。插入新行或修改
                 已有行时设置该标记；数据抽取后清除该标记。
                 5. 对于用于查询过滤条件的列，不可为空，以免行“丢失”。
                 6. 对于数值列，不可为空，“0”作为默认值。
                 7. 对于必要的“冗余”列，如客户名称，应有相应的程序保持各“冗余”列的同一性
                 ，以免出现异议。
                 8. 设置“过程状态”列和“记录状态”列。过程状态列用于记录如创建、审核、记账
                 、冲红等状态；记录状态用于记录如有效、删除等状态。

 *
 * *          3.具体建表
 *                 1.主键(以xx_pk结尾，由系统生成，不具业务含义，不可修改)
         *              1.对逻辑主键、业务主键和复合主键的思考
 *                          https://blog.csdn.net/sunrise918/article/details/5575054
 *                          对于‘ 业务表 大表’ ，优先使用逻辑主键，便于关联、业务变化等，对于查询无需担心多一次业务和逻辑主键映射，因为
 *                          给业务主键列添加唯一索引可以使用进行即可
 *                          对于普通的辅助表可以直接使用业务主键。
 *                       2.逻辑主键生成逻辑对比
         *                     业务上生成比如流水号、编号等 + 不可修改唯一性 + 特别长并且是非全数字呢？
 *                              file:///C:/Users/73699/Desktop/think/%E8%A1%A8%E8%AE%BE%E8%AE%A1%E6%80%9D%E8%80%83/MySQL%20%E6%95%B0%E6%8D%AE%E5%BA%93%E4%B8%BB%E9%94%AE%E7%9A%84%E7%AD%96%E7%95%A5%20-%20oLiHongMing%E7%9A%84%E5%8D%9A%E5%AE%A2%20-%20CSDN%E5%8D%9A%E5%AE%A2.html
         *              3.单库单表情况下的主键设计使用big int 自增型数据类型 业务表注意使用bigint 普通其他量不大的比如码值..无需那么大
                             无序主键会导致索引页频繁分裂，影响性能。
                             主键数据类型过大，会影响查询性能和内存、带宽、IO资源。
                             3.UUID ()，USER () 这样的 MySQL INSIDE 函数对于复制来说是很危险的，会导致主备数据不一致，所以请不要使用。
                             如果一定要使用 UUID 作为主键，让应用程序来产生。

                         TODO:分布式、大数据量场景下如何
                   2.表名、字段名
         *              1.模块名_子模块名_sys_param
         *              2.动词被动形式+描述性后缀
 *                          created_by: integer  created_at: timestamp  expires_in - 未来
         *              3.单复数

         *         3.各字段类型(大字段(text/blob))、长度、约束(唯一索引 - 流水号 行号..、是否为空 -默认值）、精度
         *                1.选择数字类型和文本类型尽量充足  - 。假设客户ID 为10 位数长。那你应该把数据库表字段的长度设为12 或者13 个字符长。
         *                2.优先考虑数字类型，其次是日期或二进制类型，最后应该是字符类型
 *                          当字段的类型为枚举型或布尔型时，建议使用 char (1) 类型
                          字段类型及字段大小的设计要合理
                            file:///C:/Users/73699/Desktop/think/%E8%A1%A8%E8%AE%BE%E8%AE%A1%E6%80%9D%E8%80%83/mysql%E6%95%B0%E6%8D%AE%E5%BA%93%E5%AD%97%E6%AE%B5%E7%B1%BB%E5%9E%8B%E7%9A%84%E9%80%89%E6%8B%A9%E5%8E%9F%E5%88%99%20-%20BuildyMan%20-%20%E5%8D%9A%E5%AE%A2%E5%9B%AD.html
                          主要还是从磁盘存储、网络带宽、节省内存空间、减少磁盘IO几方面来考量。
                             表字段避免 null 值出现，null 值很难查询优化且占用额外的索引空间，推荐默认数字 0 代替 null。
                             尽量使用 INT 而非 BIGINT，如果非负则加上 UNSIGNED（这样数值容量会扩大一倍），当然能使用 TINYINT、SMALLINT、MEDIUM_INT 更好。
                             使用枚举或整数代替字符串类型
                             尽量使用 TIMESTAMP 而非 DATETIME
                             单表不要有太多字段，建议在 20 以内
                             用整型来存 IP
                    4.默认值

                        * 1. 对于用于查询过滤条件的列，不可为空，以免行“丢失”。
                          2. 对于数值列，不可为空，“0”作为默认值。
                         非空+默认值——一种选择方案思路：http://blog.itpub.net/17203031/viewspace-692784/ 非空+不提供默认值，要求client必须填入对应数据
                         一保证数据库层面的数据完整性

                        目前bs写的数据库设计都是主键非空，其他可为null,这种设计其实给别人无法开发的。因为很多‘ 要素其实是必输 ’的。在前台页面校验了。

 * *          4.优化 -- 设计表时就要思考如何编写sql
                     1.索引
         *              1.命名：sample 表 member_id 上的索引:sample_mid_ind。
         *              2.当删除约束的时候，为了确保不影响到 index，最好加上 keep index 参数。
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

 *           2.选择合适的数据类型
         *             使用可存下数据的最小的数据类型，整型 < date,time < char,varchar < blob
                         * 使用简单的数据类型，整型比字符处理开销更小，因为字符串的比较更复杂。如，int 类型存储时间类型，bigint 类型转 ip 函数
                         使用合理的字段属性长度，固定长度的表会更快。使用 enum、char 而不是 varchar
                         * 尽可能使用 not null 定义字段
                         尽量少用 text，非用不可最好分表


         *
         *             4.分区 、分表 、分库
         *               1. 分区
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


         *
 *                      7.万字归总表设计与 SQL 编写技巧  https://www.infoq.cn/article/DGMlqL9x0maeHGRltOKT
 *
 */


@Repository
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