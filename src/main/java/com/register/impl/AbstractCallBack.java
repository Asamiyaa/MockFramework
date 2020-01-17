package com.register.impl;

import com.exception.CallBackException;
import com.msg.xml.msgBuilder.DraftBuildData;
import com.register.ICallback;
import com.register.bean.dataObj.RgctStudentHist;
import com.register.bean.dataObj.RgctStudentInfo;

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

    public String assembleDraft(DraftBuildData dbd) {
        String msgBody = buildBody(dbd);
        msgBody="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+msgBody;
        schemaValidate(dbd , msgBody);
        String msgHead = buildHead(dbd,msgBody);
        return msgHead+msgBody;
    }

    protected  abstract String buildBody(DraftBuildData dbd) ; //由子类来实现 根据不同业务员实现

    private String buildHead(DraftBuildData dbd, String msgBody) {
        //通过xmlOptions来xml进行
        return null;
    }

    private void schemaValidate(DraftBuildData dbd, String msgBody) {
        //通过xmlOptions来xml进行
    }

    //其他默认实现 以供子类调用

    @Override
   public  abstract boolean execute(RgctStudentInfo rgctStudentInfo, RgctStudentHist rgctStudentHist) throws CallBackException;

}
