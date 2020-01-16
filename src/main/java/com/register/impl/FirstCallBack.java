package com.register.impl;

import com.exception.CallBackException;
import com.register.bean.dataObj.RgctStudentHist;
import com.register.bean.dataObj.RgctStudentInfo;

/**
 * @author YangWenjun
 * @date 2020/1/16 14:37
 * @project MockFramework
 * @title: FirstCallBack
 * @description:
 */
public class FirstCallBack extends AbstractCallBack {

    @Override
    public boolean execute(RgctStudentInfo rgctStudentInfo, RgctStudentHist rgctStudentHist) throws CallBackException {
        return false;
    }
}
