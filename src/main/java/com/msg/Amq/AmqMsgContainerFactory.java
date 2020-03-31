package com.msg.Amq;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * ----------------------------------fqa-------------------------------------
 * 1,activemq 本身的特性(类似去quartz本地保存...jobDetail。。。）等
 *      相对于quartz，mq是更加成熟的体系，这里提供了在proeprty配置和在conf/...对应的actvemq.xml等中间件配置信息中配置
 *
 *      原版：https://activemq.apache.org/features
 *      1.ActiveMQ的安装与配置和登录页面信息:https://www.ilanni.com/?p=13543
 *      2.ActiveMQ的activemq.xml配置（内存设置、broker策略配置、流控、协议、认证授权） https://blog.csdn.net/fenglongmiao/article/details/79175062
 *      3.异步、讯息策略、重发策略、持久化、死信队列、消息积压：https://www.cnblogs.com/jinloooong/p/9609420.html额 -->
 *          TODO:****  满足上面mq特性从哪些配置中可以看到 ,解决问题的根本就是上面的特性。关键  . mq提供不同解决策略 + 代码本身实现对应
 *          TODO:二者配合呼应才能完成
 *
 *          TODO: 基本的实现  -- 配合 property配置信息 +  @configure类配置 + xml（比如axtivemq.xml）= 企业级（如何区分呢 依赖于第一步的有哪些配置）
                    从默认配置中获取可以配哪些(https://spring.hhui.top/spring-blog/2018/09/25/180925-SpringBoot%E5%9F%BA%E7%A1%80%E7%AF%87%E9%85%8D%E7%BD%AE%E4%BF%A1%E6%81%AF%E4%B9%8B%E9%BB%98%E8%AE%A4%E9%85%8D%E7%BD%AE/)
                                可以在没有配置类的情况下使用springboot自动配置 完成基本的。 使用configure时需要将property中的默认配置通过java的
                                形式再配置一遍，因为这里是new了对应的工厂....
                    configure 结合redisConfigure得到重写了redisTempl          ate加入了除默认属性配置外的其他.对象、列表间引用。 同样可以注入自动配置的connenctory
                                在这个基础上进行添加配置。
                    CONF/*.xml


                 如何去配合选择，共同作用才是最重要的，依据来自哪里？

             TODO: 区分客户端 vs 服务端  2的配置信息内容是接近服务端 3接近client,每个client的配置可以自定义

            TODO:****其实这些不同形式的配置都是为了真正发挥外部模块的作用,只不过表现形式不同罢了-****-------------


            问题：如果我现在在配置文件配置了一些在本身框架比如mq有的属性，并且按照spring的格式进行配置可以解析到吗？
                  如果没有的属性可以解析吗？
                  那么configure类的作用是啥？还需要吗？

                    下面的内容来自： https://activemq.apache.org/features **********************

        4.spring中配置：https://blog.csdn.net/lr131425/article/details/68064022    可以通过参数形式，结合@ConfigurationProperties 完成对@configureation的配置工作
                        https://ningyu1.github.io/site/post/06-activemq-settings/  XML形式配置
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

    @Autowired
    private ConnectionFactory activeMQConnectionFactory;
    //***可以通过这种方式获取信息，也可以通过入参来获取。
    //***可以注入factory 或者 template 获取  参考redisConfigure

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
//        factory.setConnectionFactory(connectionFactory()); //这里相当于xml <bean ref="connectionFactory">****    这个和下面都可以
        factory.setConnectionFactory(activeMQConnectionFactory); //直接使用默认构建好的工厂 直接注入能不能使用？ 可以 。
        /**
         *  template auto  --> 配置listener
         */

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
