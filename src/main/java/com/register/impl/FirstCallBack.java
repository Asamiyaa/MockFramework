package com.register.impl;

import com.core.draft.tmpBBSP.dto.RequestInfo;
import com.exception.CallBackException;
import com.msg.xml.msgBuilder.DraftBuildData;
import com.register.bean.dataObj.RgctStudentHist;
import com.register.bean.dataObj.RgctStudentInfo;
import org.apache.xmlbeans.XmlObject;

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

    @Override
    public RequestInfo doProcess(DraftMessage draftMessage, XmlObject xml) throws CallBackException {
        return null;
    }

    @Override
    public String getServiceName() {
        return "firstCallBack";
    }
}
