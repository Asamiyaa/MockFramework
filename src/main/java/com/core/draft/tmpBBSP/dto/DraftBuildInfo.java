package com.core.draft.tmpBBSP.dto;

/**
 * @author YangWenjun
 * @date 2019/8/14 12:00
 * @project hook
 * @title: DraftBuildData
 * @description:
 */
//针对报文发送(通讯层)  报文头+体
public class DraftBuildInfo {

    private String draftNo ;            //报文编号

    private String senderId ;           //报文发起方会员代码
    private String receiverId ;         //报文接受放会员代码，票交所接受为000000，票交所官博999999
    private String origSender ;         //报文发起人
    private String origReceiver ;       //接收人  票交所
    private String mesgId ;

    private String isAddSign ;          //是否加签

    private RequestInfo requestInfo ;   //请求信息
}
