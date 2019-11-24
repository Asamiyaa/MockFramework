package com.msg;

/**
 * @author YangWenjun
 * @date 2019/7/25 10:39
 * @project hook
 * @title: NetCom
 * @description:网络通讯接口
 */
public interface INetCom {

     void send(String msg);

     String receive(String msg);
}
