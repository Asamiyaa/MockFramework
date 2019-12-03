package com.register;

import com.exception.CallBackException;
import com.register.bean.dataObj.RgctStudentHist;
import com.register.bean.dataObj.RgctStudentInfo;

/**
 * @author YangWenjun
 * @date 2019/12/3 15:44
 * @project MockFramework
 * @title: ICallback
 * @description:
 */
public interface ICallback {

    /**
     * 1.接口定义异常为了模块间信息通信   2.回调方法execute... 3.参数一定是整个模块顶级
     * @param rgctStudentInfo
     * @param rgctStudentHist
     * @return
     * @throws Exception
     */
    boolean execute(RgctStudentInfo rgctStudentInfo , RgctStudentHist rgctStudentHist) throws CallBackException;
}
