package com.core.draft.tmpBBSP.dto;

/**
 * @author YangWenjun
 * @date 2019/8/14 16:08
 * @project hook
 * @title: DraftHead
 * @description:   根据相关的报文规范（概述分册） 对应值的设计  这写信息都象socket那样，占用指定字节位
 */
public class DraftHead {

    private String BeginFlag;
    private String VersionID;
    private String SenderID;
    private String ReceiverID;
    private String OrigSender;
    private String OrigReceiver;
    private String OrigSendDate;
    private String OrigSendTime;
    private String MesgType;
    private String MesgID;
    private String MesgRefID;
    private String MesgDirection;
    private String MesgPriority;
    private String EncryptFlag;
    private String BodyLength;
    private String BodyChksum;
    private String Reserve;
    private String EndFlag;

    //get - set 可以进行相关操作比如校验，截取，填充 ... 是面象 领域编程  。 即对象完成自己的事，但绝不是单单记录 值
    //比如msgType 可以直接从前台传入draftNo 在这里进行截而无需在client
    //StringUtils.rightPd(draftNo.toUpperCase(),20," ") 填充

    public String draftHeadAsString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(BeginFlag);    //也可以固定分隔符
        stringBuffer.append(MesgID);
        stringBuffer.append(EncryptFlag);
        //.....
        return stringBuffer.toString();
    }
}