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
 *                         两阶段提交、TCC... ->异常：事务补偿、重试、异常抛出..
 *             可靠性       发送、接受创建拦截器注入序号、分区判断消息是否连续
 *                         生产阶段：client-broker 确认响应机制 正确处理返回值/或者捕获异常  --异步 回调处理返回值
 *                         存储阶段：至少两个节点同步、复制刷盘/磁盘落地 再返回确认    -- 引入管理节点后，管理节点是否集群呢 集群后如何保证高可用 多少个副本写入算成功写入呢？
 *                         消费阶段：先存储库，发送确认消息。执行完消费业务逻辑后发送消息确认
 *                      问题：如果回复确认报文在网络中丢失，如何处理？
 *             重复消息     由于重试机制导致重复   at least once 至少一次(可靠性导致允许重复)  最高：exactly once 很难
 *                          幂等性  --> 任意多次所产生的影响均与一次处理的一样(把账户余额设置为100元 vs ....加100元)  --> 支付接口
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
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(jmsBrokerUrl);
        connectionFactory.setUserName(jmsUser);
        connectionFactory.setPassword(jmsPassword);
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
        factory.setConnectionFactory(connectionFactory()); //because use createListenerContainer so remove here
        //设置连接数
        factory.setConcurrency("3-10");
        //重连间隔时间  - 这里暂时用不到，所以这样设置
        factory.setRecoveryInterval(100000000000000L);

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
