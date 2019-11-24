package com.core.draft.tmpBBSP.dto;

import java.util.Date;

/**
 * @author YangWenjun
 * @date 2019/8/14 11:18
 * @project hook
 * @title: RequestInfo
 * @description:
 */
//针对报文体：head(公共信息)+body  ()   每个业务模块的继承自该类，并扩展
public class RequestInfo {

    private String serviceName ;    //业务名

    private String respMsgId ;      //回复ID
    private String orgnReqDraftNo ; //原申请报文编号
    private String orgnMsgId ;      //原申请报文id
    private Date draftCreDtTm ;
    private String reqBrchId ;      //申请机构id
    private String receiveBrchId ;  //接受结构id
    private String receiveMbrId ;
    private String prcCd ;          //报文处理码
    private String prcMsg ;         //处理信息

    //....
}
