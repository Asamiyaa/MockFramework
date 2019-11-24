package com.msg;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

/**
 * @author YangWenjun
 * @date 2019/7/11 11:44
 * @project hook
 * @title: commMq
 * @description:
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = boot.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class commMq {
    /**
     * 1.直接使用mq 类似于jdbc一样
     * 2.引入spring 配置相关对象和参数 ActiveMQConnectionFactory/Connection/JMSTemplate/ActiveMQQueue/DefaultMessageListenerContainer
     * 3.使用springboot下配置
     *
     * 详情查看：https://blog.csdn.net/qq_33404395/article/details/80590113
     *
     * */
    //private JmsMessagingTemplate jmsMessagingTemplate = new JmsMessagingTemplate();
    private JmsTemplate jmsTemplate = new JmsTemplate();
    //@Test
    public void testSend() throws JMSException { //不能有参数  spring下测试环境
        //1、创建工厂连接对象，需要制定ip和端口号
        //ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:8161");
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Queue queue = session.createQueue("dem1o");// create queue new
        //6、使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(queue);
        //7、使用会话对象创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello!test-queue");
        //8、发送消息
        producer.send(textMessage);
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
        /*jmsTemplate.setDefaultDestinationName("tcp://localhost:8161");
        jmsTemplate.convertAndSend("demo","testDemo");
        System.out.println("demo下会有testDemo");*/
    }


    public void TestMQConsumerQueue() throws Exception {
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Queue queue = session.createQueue("demo");
        //6、使用会话对象创建生产者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {            //TODO   引入线程
                // TODO Auto-generated method stub
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        //8、程序等待接收用户消息
        System.in.read();
        //9、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

        //need spring env
    public static void main(String[] args) throws Exception {
        //new commMq().testSend();
        new commMq().TestMQConsumerQueue();

    }


}
