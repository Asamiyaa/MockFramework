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
 *                         存储阶段：至少两个节点同步、复制刷盘/磁盘落地 再返回确认
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
