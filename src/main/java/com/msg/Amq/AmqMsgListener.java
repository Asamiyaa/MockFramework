package com.msg.Amq;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author YangWenjun
 * @date 2019/7/29 16:18
 * @project hook
 * @title: AmqMsgListener
 * @description: 接受消息
 */
//@Component
public class AmqMsgListener implements SessionAwareMessageListener {
    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        System.out.println("--------------------------------------AmqMsgListener------------------------------");
    }
}
