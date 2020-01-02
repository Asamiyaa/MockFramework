package com.msg.Amq;

import com.core.constant.YON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * @author YangWenjun
 * @date 2019/7/29 15:54
 * @project hook
 * @title: AmqMsgListenerContainer
 * @description:
 *
 *通信模块
        1.需求流程（从需求中长度截取写对应报文处理逻辑） - 流程图。(嵌在别的框架中...导致不知道执行流程) --/spring+activmq)
        我如何知道考虑哪些东西(不同协议 / 模块vs工具类)，如何全面
        --书籍/博客/github项目平时积累
        1.activmq 查看官网概述/api -

        2.书籍 快速 技能点 出现问题好定位 找书(专业点)  ActiveMQ in Action 英文版
        https://jfires.iteye.com/category/181422
        https://www.cnblogs.com/ainima/p/6331700.html  并实现简单示例 --在扩展
        1.JMS理解 在java api 实际使用封装  生产者/消费者 | jms api(https://www.orcode.com/question/314145_k8bf14.html)
        1.松耦合一般是为了减轻经典RPC（Remote Procedure Calls）调用的紧耦合架构而被引入的。
        2.大数据量催生了rpc下的微服务，将原来单个服务进行拆分部署.这些是需要服务同时启动，存在的，实时的。
        同步和异步都找到了各自的场景和最终的实践
        3.brokers
        4. 企业消息传送到目的就是在系统间传递数据。这些年来已经有各种不同的技术可以进行消息传送，如下列表所示。
        通过远程过程调用（RPC）的解决方案，例如COM，CORBA,DCE及EJB
        使用事件通知，内部交互，消息队列的，例如FIFO缓冲，消息队列，管道，信号，socket等。
        使用异步可靠消息队列的中间件的，例如IBM WebSphere MQ, SonicMQ, TIBCO Rendezvous,
        and Apache ActiveMQ，它们都可用在企业消息集成。
        2.从别的项目中可以知道：框架对url端口/pool...都封装到对应的类中，所以配置类
        时，注意框架已经封装好的类及其配置
        AmqMessageSender - jmsTemplate - AmqConnnetionPoolFactory - AmqConnnetionFactory - brokerURL（el表达式 -直接从applicationContextproperty中取）-哪里加载到spring容器中的 vs 从getProperty...获取https://blog.lovian.org/spring/2018/04/09/java-spring-bean-spel.html  vs 代码中取值呢?也可以使用spel吗？还是getProperty/Resource https://blog.csdn.net/honghailiang888/article/details/51880312对比b'b's'p这种代码加载
        - maxConnections

        从博客中找到有点杂，或者简单，角度不同。我不知道如何找到有用的。
        从api中找到方法全，但是不知道如何组织这些方法

        3.配置分层  resource.file / shcpeResource.xml中import...
 *
 *
 */
@SuppressWarnings("ALL")
//@Component("amqMsgListenerContainer") //不去扫描该类,扫描会报后面的错。Property 'connectionFactory' is required
public class AmqMsgListenerContainer {//extends DefaultMessageListenerContainer{

    @Value("${amq.listener.switch}")
    private String listener ;//注入 + 从配置文件读取 （配置文件）
    //@Autowired
    //@Qualifier("connectionFactory")
    //private ConnectionFactory connectionFactory = (ConnectionFactory) ContextLoader.getCurrentWebApplicationContext().getBean("connectionFactory");
    @Autowired
    //@Qualifier("")
    private AmqMsgListener amqMsgListener ;  //TODO:BBSP 配合框架，这些属性都没有直接调用，只是实现了接口，注入到特定的bean中。extend隐藏了太多。

  //TODO:可以debug吗?

    //@Override
    public void initialize(){
        /**避免当前你环境报错，先注销掉**/
        /*super.initialize();
        if(YON.NO.getValue().equalsIgnoreCase(listener)){
            super.setConnectionFactory(null);
        }*/
    }

    public void setListener(String listener) {
        this.listener = listener;
    }
}
