package com.msg.xml.msgBuilder;
     //很多对象是共同的元素 ，元素在不同的对象间流转
public class DraftService {
    //注入其他service 获取信息如：系统状态 ..  报文mapping -存储报文的操作规则如加签 ，报文no和name  ，方向 时序 ...
	public void buildDraft(RequestInfo  reqInfo) {
   //获取上面信息
		DraftBuildData dbd = new DraftBuildData();
		dbd.setReqInfo(reqInfo);
		dbd.setDraftNo(reqInfo.getServiceName());
		dbd.setMsgId(reqInfo.getOrgnMsgId().substring(15));
		//dbd.setIsAddSign();从mapping表中获取是否是否需要加签
		//...
		
		canSendDraft("common1","common2");//判断因素填入
		AbstractDraftBuilder draftBuilder = DraftFactory.getDraftBuilder(dbd.getDraftNo()); //抽象类定义模板
		String draft = draftBuilder.assembleDraft(dbd);
		
		IClientProcessor sender = DraftSendFactory.getDraftSender();
		sender.sendMessage(draft) ;
		
		//创建DaftLog对象  调用方法addDraftLog(log);
		
		//记录日志文件到本地指定目录   
		//String fileddir = PropAdapter.getMsgDir()+File.separator + log.getSendDate() + File.separator + "up" + File.separator 
		//String fileName = fileDir + log.getMsgId();
		//DraftUtil.doWrite(filedir , fileName ,draft );
		
	}

	private void canSendDraft(String command1 , String Common2  ) {
    		//判断逻辑
	}
}
