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
        //重连间隔时间
        factory.setRecoveryInterval(1000L);

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
