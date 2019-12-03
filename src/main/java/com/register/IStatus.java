package com.register;

import com.register.bean.dataObj.Status;

/**
 * @author YangWenjun
 * @date 2019/8/28 20:45
 * @project hook
 * @title: IStatus
 * @description:
 */
public interface IStatus {

    /**
     * 通过前置状态、当前状态、事件获得transite状态
     * @param beforeStatus
     * @param runStatus
     * @param eventId
     * @return
     */
    Status getStatus(String beforeStatus , String runStatus , Integer eventId);



    //从dao层来看两个表，越往上抽象越是公用

    /**
     * 从dictionary中获取状态描述
     * @param status
     * @return
     */
    String getStatusName(String status);
}
