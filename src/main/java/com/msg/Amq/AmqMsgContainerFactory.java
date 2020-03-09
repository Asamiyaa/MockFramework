package com.msg.Amq;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

/**
 * @author YangWenjun
 * @date 2019/7/30 13:41
 * @project hook
 * @title: AmqMsgContainerFactory
 * @description: 为了将注解和listener/container连接起来 ， 这里没有继承和实现...
 *
 *
 *          1.使用场景
 *              1.非及时   2 弱一致性(订单删除)
 *
 *          2. 一致性       分布式事务   --- ACID 本地数据库事务 。从一致性上引入弱一致性、顺序一致性、最终一致性...进行取舍(没有银弹)
 *                         两阶段提交、TCC... ->异常：事务补偿（使用序列化将当时的场景保存再反..处理 -保护小内存.）、重试、异常抛出.. 手工处理-冲正
 *
 *                          1，调用账户失败，可以在异步callBack里执行通知客户端的逻辑；
                            2，如果是第一次失败，那后面的那一步就不用执行了，所以转账失败；如果是第一次成功
                            但是第二次失败，首先考虑重试，如果转账服务是幂等的,可以考虑一定次数的重试，如果
                            不能重试，可以考虑采用补偿机制，undo第一次的转账操作。  …

 *             可靠性       发送、接受创建拦截器注入序号、分区判断消息是否连续
 *                         生产阶段：client-broker 确认响应机制 正确处理返回值/或者捕获异常  --异步 回调处理返回值
 *                         存储阶段：至少两个节点同步、复制刷盘/磁盘落地 再返回确认    -- 引入管理节点后，管理节点是否集群呢 集群后如何保证高可用 多少个副本写入算成功写入呢？
 *                         消费阶段：先存储库，发送确认消息。执行完消费业务逻辑后发送消息确认
 *                      问题：如果回复确认报文在网络中丢失，如何处理？
 *
 *            重复消息     由于重试机制导致重复   at least once 至少一次(可靠性导致允许重复)  最高：exactly once 很难
 *                           --> 任意多次所产生的影响均与一次处理的一样(把账户余额设置为100元 vs ....加100元)  --> 支付接口
 *                                  1.数据库约束
 *                                          1.转账流水表  转账id-用户id-金额 为转账id设置唯一约束
 *                                          2.前置条件比如如果当前为500元，则加100  --> 版本号
 *                                   2.记录并检查操作 全局发送id、先去判断是否已执行
 *                                          但是在分布式系统下、原子性很难保证 --> 分布式锁 、 分布式事务
 *                                   3.状态机
 *
 *              消息积压    发送端：发消息之前业务逻辑 +  并发数量(时延要求高) + 批量大小(吞吐量要求高)
 *                          消费端：.......            +  consumer和分区同步   / onMessage中将消息保存在内存中返回，在启动多线程处理业务逻辑->丢消息
 *                                  出现情况：在分析.快速解决增加consumer数量  消费错误 、重复消费、死锁
 *
 *              顺序性   分布式下如何保证发送到统一处理机？hash算法，指定队列发送  /  队列取模
 *                          全局有序 --> 局部有序 账户流水
 *
 *            3.队列实现柔和技术
 *
 *            4.socketmq ：productor  / consumer 代码实现 查看 mq 20/21/23、25事务节  - 带着问题去看具体代码实现上面的具体实现  nameServer(路由信息)= 注册中心  brocker  -- 代码的整洁性
 *                      reocket路由寻址 vs  kafka通过zookeeper分布式协调  ZooKeeper 作为一个分布式的协调服务框架，主要用来解决分布式集群中，应用系统需 面对的各种通用的一致性问题。
                     * 它提供了一个分布式的存储系统，数据的组织方式类似于、UNIX 文件系统的树形结构。ZooKeeper 来实现业务集群的快速选举、节点间的简单通信、分布式锁等很多功能。
                     3分布式系统中一些需要整个集群所有节点都访问的元数据，比如集群节点信息、公共配置信息等，特别适 合保存在 ZooKeeper 中。
                事务：
                    Kafka 的事务解决的问题和 RocketMQ 是不太一样的。RocketMQ 中的事务，它解决的问题是，确保执行本地事务和发消息这两个操作，要么都成功，要么都失败。并且，RocketMQ 增加了一个事务
                             反查的机制，来尽量提高事务执行的成功率和数据一致性。
                    Kafka 中的事务，它解决的问题是，确保在一个事务中发送的多条消息，要么都成功， 要么都失败。注意，这里面的多条消息不一定要在同一个主题和分区中，可以是发往多个主
                            题和分区的消息。

Kafka 在内的几个常见的开源消息队列，都只能做到 At Least Once，也就是至少一次，保证消息不丢，但有可能会重复。做不到Exactly Once。
1. completableFuture.thenCompose  completableFuture<Void> void是真实的返回类型，xxfure是线程级别的类型。从中获取信息
 *                      同步：线程创建限制 / 线程满后执行时间=等待时间+平均每笔消耗时间

 *
 * ----------------------------------fqa-------------------------------------
 * 1,activemq 本身的特性(类似去quartz本地保存...jobDetail。。。）等
 *      相对于quartz，mq是更加成熟的体系，这里提供了在proeprty配置和在conf/...对应的actvemq.xml等中间件配置信息中配置
 *
 *      原版：https://activemq.apache.org/features
 *      1.ActiveMQ的安装与配置和登录页面信息:https://www.ilanni.com/?p=13543
 *      2.ActiveMQ的activemq.xml配置（内存设置、策略配置、流控、协议、认证授权） https://blog.csdn.net/fenglongmiao/article/details/79175062
 *      3.异步、讯息策略、重发策略、持久化、死信队列、消息积压：https://www.cnblogs.com/jinloooong/p/9609420.html额 -->
 *          TODO:****  满足上面mq特性从哪些配置中可以看到 ,解决问题的根本就是上面的特性。关键  . mq提供不同解决策略 + 代码本身实现对应
 *          TODO:二者配合呼应才能完成
 *
 *          TODO: 基本的实现  -- 配合 property配置信息 +  @configure类配置 + xml（比如axtivemq.xml）= 企业级（如何区分呢 依赖于第一步的有哪些配置）
                    从默认配置中获取可以配哪些(https://spring.hhui.top/spring-blog/2018/09/25/180925-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E9%BB%98%E8%AE%A4%E9%85%8D%E7%BD%AE/)
                    configure 结合redisConfigure得到重写了redisTemplate加入了除默认属性配置外的其他
                    CONF

            TODO:****其实这些不同形式的配置都是为了真正发挥外部模块的作用,只不过表现形式不同罢了-****-------------

        4.spring中配置：https://blog.csdn.net/lr131425/article/details/68064022   可以通过参数形式，结合@ConfigurationProperties 完成对@configureation的配置工作
                        https://ningyu1.github.io/site/post/06-activemq-settings/
                        https://github.com/DongCarzy/springboot-activemq
                        https://juejin.im/post/5b756fefe51d456643556056
                        https://blog.csdn.net/gwd1154978352/article/details/78772164
                        https://www.devglan.com/spring-boot/spring-boot-jms-activemq-example

 *      2.queue / topic 各自属性 共同点 区别点 ...@enableJms
 *      3.事务 @Transactional、 异步 、线程  、new MessageCreator(){、onMessage -->api选择 sendConvert...string..
 *
 *
 *
 * 2.springboot默认配置信息：spring-boot-starter-web  https://spring.hhui.top/spring-blog/2018/09/25/180925-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E9%BB%98%E8%AE%A4%E9%85%8D%E7%BD%AE/
 * 2.spring整合过程jms - rmi ...（https://docs.spring.io/spring/docs/5.2.4.RELEASE/spring-framework-reference/integration.html#jms
 *              1.事务
 *              2.重载
 * 2.在@configure/@bean中如何和property中的属进行区分和配置使用，哪些配置在哪些地方.哪些应用又是auto集成的?
 *   --- auto设计  :https://leokongwq.github.io/2019/08/28/springboot-jms.html  类似于springbean初始化 refresh一样
 *              顺序执行注解 - 注解的注解 - 注解对应的解析器 - 加载对应信息ActiveMQConnectionFactory/ActiveMQProperties加载信息 -？- 生成template
 *        从上往下依次执行
 *              @Configuration
                 @AutoConfigureBefore(JmsAutoConfiguration.class) --- 核心 其中new jmsTemplate(factory) -- 注入到springbean
                 @AutoConfigureAfter({ JndiConnectionFactoryAutoConfiguration.class })
                 @ConditionalOnClass({ ConnectionFactory.class, ActiveMQConnectionFactory.class })
                 @ConditionalOnMissingBean(ConnectionFactory.class)
                 @EnableConfigurationProperties(ActiveMQProperties.class) --- 加载了属性默认JavaBeanBinder加载sender/如果有 ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();//ActiveMQConnectionFactoryCustomizer
                                                                                                                connectionFactory.setBrokerURL(jmsBrokerUrl);则会实现赋值
                 @Import({ ActiveMQXAConnectionFactoryConfiguration.class,
                 truetrueActiveMQConnectionFactoryConfiguration.class })
                 public class ActiveMQAutoConfiguration {

                 }
 *       @ConfigurationProperties  https://juejin.im/post/5d3e40ec51882551c37fc309
 *       @ConditionalOnMissingBean https://blog.csdn.net/andy_zhang2007/article/details/81285130
 *       开启applicaton.propety debu=true 来启动时设置日志级别 查看哪些自动配置已经加载，哪些没有  https://www.jianshu.com/p/ddb6e32e3faf
 *（     三）启动原理解析：http://tengj.top/2017/03/09/springboot3/
 *   --- spring中factory设计
                  spring整合其他技术使用较多的设计模式：工厂、策略、代理、委托....

 *   --- template设计
         TODO: 基本的实现  -- 配合  xml（比如axtivemq.xml） + property配置信息 +  @configure类配置 = 企业级（如何区分呢 依赖于第一步的有哪些配置）
                                                                                        从默认配置中获取可以配哪些(https://spring.hhui.top/spring-blog/2018/09/25/180925-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E9%BB%98%E8%AE%A4%E9%85%8D%E7%BD%AE/)
                 jmsTemplate/redisTemlate/jdbcTemplate


 *
 * 3.多线程等完成是否需要在自己代码中，这样会不会矛盾？quartz/activemq内部是否已经帮你完成了？
 * 4.消费者有三个 如何查看，为什么没有销毁
 *
 *
 *
 */
@Component
public class AmqMsgContainerFactory {

    @Value("${spring.activemq.broker-url}")
    private String jmsBrokerUrl;

    @Value("${spring.activemq.user}")
    private String jmsUser;

    @Value("${spring.activemq.password}")
    private String jmsPassword;

    @Value("${amq.listener.switch}")
    private String listenerSwitch ;


    /**
     * 创建 ActiveMQ 的连接工厂
     */
    @Bean(name="connectionFactory")
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();//ActiveMQConnectionFactoryCustomizer
        connectionFactory.setBrokerURL(jmsBrokerUrl); //这些全部是自动的由template完成 ，如需这样加载
/*        connectionFactory.setUserName(jmsUser);
        connectionFactory.setPassword(jmsPassword);*/
        //connectionFactory.set先从property中扩展，这是由框架定义好的，再想着去扩展类(前面扩展是基本的，想要定义仍然需要对类结构进行管理)
        return connectionFactory;
    }

    /*@Autowired
    private AmqMsgListenerContainer mqMsgListenerContainer;
*/
    /**
     * JMS 队列的监听容器工厂
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory() {

       // if(YON.NO.getValue().equalsIgnoreCase(listenerSwitch)){return null;}

        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory()); //这里相当于xml <bean ref="connectionFactory">****
        //设置连接数
        factory.setConcurrency("1"); //设置1个就是一个consumer 5
        //重连间隔时间  - 这里暂时用不到，所以这样设置
        factory.setRecoveryInterval(100000000000000L);


//        factory.setDestinationResolver(destinationResolver());
//        factory.setSessionTransacted(true);
//        factory.setConcurrency("5");
        //TODO:糅不进去 那么应该在哪里处理呢?
        /*factory.createListenerContainer(new JmsListenerEndpoint() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public void setupListenerContainer(MessageListenerContainer messageListenerContainer) {
                messageListenerContainer.setupMessageListener(mqMsgListenerContainer);
            }
        });*/
        return factory;
    }
}
