package com.msg.xml.msgBuilder;

public class DraftFactory {

	public static AbstractDraftBuilder getDraftBuilder(String draftNo) {
         try{
        	 return (AbstractDraftBuilder)Class.forName("DraftBuilder001").newInstance() ; //cn.com.abc"+draftNo+"DraftBuilder"实际业务中
         }catch(Exception e){
        	 //.....
         }return null;
	}	

}
