package com.register.impl;

import com.core.draft.tmpBBSP.dto.RequestInfo;
import com.exception.CallBackException;
import com.register.ICallback;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YangWenjun
 * @date 2019/12/3 16:35
 * @project MockFramework
 * @title: CallBackImpl
 * @description:  basexxx baseDao + 泛型  == 任意地方调用 / baseController 统一处理 /
 */
abstract class AbstractCallBack implements ICallback {

    //属性
    // 定义加签 .. 拼接头信息...流程

    public RequestInfo processMsg(DraftMessage draftMessage) throws XmlException, CallBackException {
        XmlOptions options = new XmlOptions();
        Map<String, String> map = new HashMap<String, String>();
        String nameSpace = "http://www." + draftMessage.getDraftNo().toLowerCase() + "d.afcat.com.cn";
        map.put("", nameSpace);
        options.setLoadAdditionalNamespaces(map);
        XmlObject xml = XmlObject.Factory.parse(draftMessage.getMsgBody(), options);
        RequestInfo req = doProcess(draftMessage,xml);
        req.setServiceName(getServiceName());
        return req;
    }

   public  abstract RequestInfo doProcess(DraftMessage draftMessage,XmlObject xml) throws CallBackException;
    @Override
    public abstract String getServiceName();
    //其他默认实现 以供子类调用

}
