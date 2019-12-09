package com.msg.Amq;

import com.msg.INetCom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author YangWenjun
 * @date 2019/7/25 11:54
 * @project hook
 * @title: ActiveMQComImpl
 * @description:
 */
@Component
public class AmqSender implements INetCom {
    @Override
    public String receive(String msg) {
        return null;
    }

    @Autowired
    private JmsTemplate jmsTemplate;    //配置自身定义的bean可以，使用框架的bean也可以。并且这里的bean还配置信息broker.url 这些读取都是默认的
   // private ActiveMQQueue destination ; 这个在配置中可以通过${}读取

    @Override
    public void send(String msg) {  //TODO :是否应该设置返回标识
        /*jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException { //TODO:类似于hibernate - 使用session中的数据
                return null;
            }
        });*/
        jmsTemplate.convertAndSend("amq", msg);
    }

    /*@Override
    public String receive() {
        return null;
    }*/

    //单例 -- 这里是为了练手，spring中没有必要自己实现单例子
    private static volatile  AmqSender activeMQCom ;

    private AmqSender() {}

    public static AmqSender getActiveMQCom() {
        if( activeMQCom == null ){
           synchronized (AmqSender.class){
                if(activeMQCom == null){
                     return new AmqSender();
                }
           }
        }
        return activeMQCom;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


}
