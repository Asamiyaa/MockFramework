package com.register;

import com.exception.RegisterException;
import com.register.bean.dataObj.RgctStudentInfo;

/**
 * @author YangWenjun
 * @date 2019/8/28 11:31
 * @project hook
 * @title: IRegister
 * @description:
 *
 *            如果说Istatus和IMethod是从状态、方法、通过方法事件驱动状态变化来，
 *            那么IRegister则是从业务角度出发，比如关于学生自身(相当于票)做业务方法梳理
 */
public interface IRegister
{

       //基本curd - 衍生重载 - 衍生业务方法比如这里的lock其实就是update
       //这些方法的servcie没有太多业务逻辑，都是直接调用dao
       void addRgctStudentInfo(RgctStudentInfo rgctStudentInfo)throws RegisterException;

       /**
        * 锁定当前学生状态，不允许‘ 挑票 ’
        * @param rgctStudentInfo
        * @return
        * @throws Exception
        */
       boolean lock(RgctStudentInfo rgctStudentInfo) throws RegisterException;

       boolean unlock(RgctStudentInfo rgctStudentInfo) throws RegisterException;

       /**
        * 不进行业务逻辑合理性判断，直接塞值Rc
        * @param rgctStudentInfo
        * @return
        * @throws Exception
        */
       boolean forceLock(RgctStudentInfo rgctStudentInfo) throws RegisterException;



       boolean reverseRgctCtrl(Long rcId);




}
