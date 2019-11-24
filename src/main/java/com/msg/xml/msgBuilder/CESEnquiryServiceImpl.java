package com.msg.xml.msgBuilder;

public class CESEnquiryServiceImpl {
	/**
	 *  1.在serviceimpl中对信息进行校验和对象组装
	 *  2.引入对应的builderService
	 *  3. Student 是业务对象 - req对象
	 */
    public void method1(Student s){
    	s.setId(MsgFactory.createMsgId());
    	//...其他塞值   可能需要引入其他service  UtilFactory
    	new  CESBuilderEnquiryService().buildCES001(s);
    }
}
