package com.msg.xml.msgBuilder;

public class CESBuilderEnquiryService {
//可能这里会有return msgId
	public void buildCES001(Student s) {
		   CESInfo ci = new CESInfo();
		   //可能需要在s和ci之间互相get / set 
		   ci.setMsgId(MsgFactory.createMsgId()); //生成msgid
		   ci.setAge(s.getAge());
		   ci.setName(s.getName());
		   s.setMsgId(ci.getMsgId());  //这里是虚拟的，实际比这规范
		   
		   new cesDao().updateEntity(s);      //为了方便 所以直接new
		  
		   new DraftService().buildDraft(ci);   //没有通过继承实现复用。可以工厂 ，也可以通过继承 这里为了方便直接new  --》已经发送了报文
		   
		   //return 需要的msgId
	}
}
