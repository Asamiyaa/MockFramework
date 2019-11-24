package com.msg.xml.msgBuilder;

public class DraftBuildData {
		private String draftNo ;
		private String senderId ;
		private String receiverId ;
		private String msgId ;
		private String isAddSign ;
		private RequestInfo  reqInfo ;
		public String getDraftNo() {
			return draftNo;
		}
		public void setDraftNo(String draftNo) {
			this.draftNo = draftNo;
		}
		public String getSenderId() {
			return senderId;
		}
		public void setSenderId(String senderId) {
			this.senderId = senderId;
		}
		public String getReceiverId() {
			return receiverId;
		}
		public void setReceiverId(String receiverId) {
			this.receiverId = receiverId;
		}
		public String getMsgId() {
			return msgId;
		}
		public void setMsgId(String msgId) {
			this.msgId = msgId;
		}
		public String getIsAddSign() {
			return isAddSign;
		}
		public void setIsAddSign(String isAddSign) {
			this.isAddSign = isAddSign;
		}
		public RequestInfo getReqInfo() {
			return reqInfo;
		}
		public void setReqInfo(RequestInfo reqInfo) {
			this.reqInfo = reqInfo;
		}
		
		
		
		
}
