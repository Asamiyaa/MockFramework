package com.msg.xml.msgBuilder;

import java.util.Date;

public class RequestInfo {
			//从请求角度分析对象
	private Long msgId ;
	private String serviceName ;
	private String respMsgId ;
	private String orgnReqDraftNo ;
	private String orgnMsgId ;		
	private Date draftCreDtTm ;
	private String reqBrchId ;
	private String receiveBrchId ;
	private String prcCd ;    //报文处理码
	private String prcMsg ;  //处理信息 
	private String respDtTm;
	
	
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getRespMsgId() {
		return respMsgId;
	}
	public void setRespMsgId(String respMsgId) {
		this.respMsgId = respMsgId;
	}
	public String getOrgnReqDraftNo() {
		return orgnReqDraftNo;
	}
	public void setOrgnReqDraftNo(String orgnReqDraftNo) {
		this.orgnReqDraftNo = orgnReqDraftNo;
	}
	public String getOrgnMsgId() {
		return orgnMsgId;
	}
	public void setOrgnMsgId(String orgnMsgId) {
		this.orgnMsgId = orgnMsgId;
	}
	public Date getDraftCreDtTm() {
		return draftCreDtTm;
	}
	public void setDraftCreDtTm(Date draftCreDtTm) {
		this.draftCreDtTm = draftCreDtTm;
	}
	public String getReqBrchId() {
		return reqBrchId;
	}
	public void setReqBrchId(String reqBrchId) {
		this.reqBrchId = reqBrchId;
	}
	public String getReceiveBrchId() {
		return receiveBrchId;
	}
	public void setReceiveBrchId(String receiveBrchId) {
		this.receiveBrchId = receiveBrchId;
	}
	public String getPrcCd() {
		return prcCd;
	}
	public void setPrcCd(String prcCd) {
		this.prcCd = prcCd;
	}
	public String getPrcMsg() {
		return prcMsg;
	}
	public void setPrcMsg(String prcMsg) {
		this.prcMsg = prcMsg;
	}
	public String getRespDtTm() {
		return respDtTm;
	}
	public void setRespDtTm(String respDtTm) {
		this.respDtTm = respDtTm;
	}
	
	
	
}
