package com.register.impl;

import com.exception.CallBackException;
import com.register.ICallback;
import com.register.bean.dataObj.RgctStudentHist;
import com.register.bean.dataObj.RgctStudentInfo;

/**
 * @author YangWenjun
 * @date 2019/12/3 16:35
 * @project MockFramework
 * @title: CallBackImpl
 * @description:
 */
public class CallBackImpl implements ICallback {
    @Override
    public boolean execute(RgctStudentInfo rgctStudentInfo, RgctStudentHist rgctStudentHist) throws CallBackException {
        return false;
    }
}
