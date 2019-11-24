package com.msg.Amq;

import com.com.INetCom;
import com.msg.INetCom;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author YangWenjun
 * @date 2019/7/29 16:19
 * @project hook
 * @title: AmqReceiver
 * @description:   TODO :Thread
 */
@Component
public class AmqReceiver implements INetCom {
    @Override
    public void send(String msg) {
    }


    @JmsListener(destination = "amq", containerFactory="jmsQueueListenerContainerFactory")//根据提示信息进行和已有关联
    public void receive1(String msg) {
        System.out.println("收到的消息是：-----" + msg);
       // return new String() ; 这里return就会由问题，只会处理一个
    }
    @Override
    public String  receive(String msg) { return null ;}

}
