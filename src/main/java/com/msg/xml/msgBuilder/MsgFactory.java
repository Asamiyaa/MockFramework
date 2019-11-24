package com.msg.xml.msgBuilder;

public class MsgFactory {

	public static long createMsgId() {
      //生成逻辑
		return (long)Math.random()*100;
	}

}
