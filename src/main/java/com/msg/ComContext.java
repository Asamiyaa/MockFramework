package com.msg;

/**
 * @author YangWenjun
 * @date 2019/7/25 11:57
 * @project hook
 * @title: ComContext
 * @description: 相对于策略和多态区别
 *                 无需在调用地new subxx()  --》factory + context类包装
 */
public class ComContext {

    //private INetCom  iNetCom ;
    //TODO :改模块和其他模块/框架进行整合的接口类，获取context信息
    private NetComFactory netComFactory;



    //依赖而非关联
    public void sendMsg( String netComId ,String msg){
         netComFactory.getInstance(netComId).send(msg);
    }


    public NetComFactory getNetComFactory() {
        return netComFactory;
    }

    public void setNetComFactory(NetComFactory netComFactory) {
        this.netComFactory = netComFactory;
    }

    /*public void setiNetCom(INetCom iNetCom) {
        this.iNetCom = iNetCom;
    }

    public INetCom getiNetCom() {
        return iNetCom;
    }*/
}
