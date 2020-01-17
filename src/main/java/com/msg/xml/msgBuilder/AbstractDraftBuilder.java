package com.msg.xml.msgBuilder;

public abstract class AbstractDraftBuilder {

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

}
