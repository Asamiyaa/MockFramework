package com.msg.Amq;

import com.utils.constant.YON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * @author YangWenjun
 * @date 2019/7/29 15:54
 * @project hook
 * @title: AmqMsgListenerContainer
 * @description:
 */
@SuppressWarnings("ALL")
//@Component("amqMsgListenerContainer") //不去扫描该类,扫描会报后面的错。Property 'connectionFactory' is required
public class AmqMsgListenerContainer extends DefaultMessageListenerContainer{

    @Value("${amq.listener.switch}")
    private String listener ;//注入 + 从配置文件读取 （配置文件）
    //@Autowired
    //@Qualifier("connectionFactory")
    //private ConnectionFactory connectionFactory = (ConnectionFactory) ContextLoader.getCurrentWebApplicationContext().getBean("connectionFactory");
    @Autowired
    //@Qualifier("")
    private AmqMsgListener amqMsgListener ;  //TODO:BBSP 配合框架，这些属性都没有直接调用，只是实现了接口，注入到特定的bean中。extend隐藏了太多。

  //TODO:可以debug吗?

    @Override
    public void initialize(){
        super.initialize();
        if(YON.NO.getValue().equalsIgnoreCase(listener)){
            super.setConnectionFactory(null);
        }
    }

    public void setListener(String listener) {
        this.listener = listener;
    }
}
