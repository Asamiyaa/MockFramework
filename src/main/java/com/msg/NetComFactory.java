package com.msg;

import com.com.Amq.AmqSender;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author YangWenjun
 * @date 2019/7/25 10:41
 * @project hook
 * @title: NetComFactory
 * @description:通过策略模式加载不同net com impl
 */
public class NetComFactory implements ApplicationContextAware,FactoryBean<INetCom>
{

    private Map<String,INetCom> netComMap ;
    private ApplicationContext applicationContext;

    //----------method2 通过FactoryBean来实现初始化实现---------
    private String netComType ;
    //-----------------------------------------------------------

    //初始化
    //启动初始化 对于构造或者重载框架的方法没有参数时，通过关联
    public NetComFactory(){
        init();
    }

    private void init(){
       // netComMap.put("mq", ActiveMQComImpl.getActiveMQCom()); spring 中不应该通过单例/new来操作获取 通过
        netComMap.put("mq", applicationContext.getBean(AmqSender.class)); //或者可以将不使用接口将applicationContext封装成工具类
        //...
    }

    public INetCom  getInstance(String netComId){
        return  netComMap.get(netComId);
    }

    //------------method2 通过FactoryBean来实现初始化实现----------------

    @Override
    public INetCom getObject() throws Exception {
        if("mq".equalsIgnoreCase(netComType)){
            return applicationContext.getBean(AmqSender.class);
        }else if(true){

        }
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }


    //-------------------------------------------------------------------

    public Map getNetComMap() {
        return netComMap;
    }

    public void setNetComMap(Map netComMap) {
        this.netComMap = netComMap;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
    }
}
