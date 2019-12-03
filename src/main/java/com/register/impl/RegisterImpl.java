package com.register.impl;

import com.exception.RegisterException;
import com.register.IRegister;
import com.register.bean.dataObj.RgctStudentInfo;

/**
 * @author YangWenjun
 * @date 2019/12/3 16:37
 * @project MockFramework
 * @title: RegisterImpl
 * @description:
 */
public class RegisterImpl implements IRegister {
    @Override
    public void addRgctStudentInfo(RgctStudentInfo rgctStudentInfo) throws RegisterException {

    }

    @Override
    public boolean lock(RgctStudentInfo rgctStudentInfo) throws RegisterException {
        return false;
    }

    @Override
    public boolean unlock(RgctStudentInfo rgctStudentInfo) throws RegisterException {
        return false;
    }

    @Override
    public boolean forceLock(RgctStudentInfo rgctStudentInfo) throws RegisterException {
        return false;
    }

    @Override
    public boolean reverseRgctCtrl(Long rcId) {
        return false;
    }
}
