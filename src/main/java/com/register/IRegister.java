package com.register;

/**
 * @author YangWenjun
 * @date 2019/8/28 11:31
 * @project hook
 * @title: IRegister
 * @description:
 */
public interface IRegister
{
       boolean lock(String rcId, String lockBrchId, String lockFlowName) throws Exception;

       boolean unlock(String rcId, String lockBrchId, String lockFlowName) throws Exception;

       boolean forceLock(String rcId, String lockBrchId, String lockFlowName) throws Exception;//不进行业务逻辑合理性判断，直接赛值Rc

      // List queryBill(RcBaseSearchBean sb) throws Exception ;  //searchBean

       boolean reverseRgctCtrl(Long rcId);

       //.....

}
