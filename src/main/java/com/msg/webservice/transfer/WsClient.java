/*
package com.msg.webservice.transfer;

import com.zzj.wsclient.HobbyServiceImpl;
import com.zzj.wsclient.HobbyServiceImplService;

import java.text.SimpleDateFormat;
import java.util.Date;


import com.messageparse.webservice_mule.webservicefromjava.publish.HobbyServiceImplService;

*
 * 1.这里生成的class ,并且打包为jar为项目引用 
 *   				报错：The method getHobbyServiceImplPort() from the type HobbyServiceImplService refers to the missing type HobbyServiceImpl
 *    					wsimport -clientjar D:\\hobbyClient.jar -p com.zzj.wsclient http://localhost:1111/hobbyService/queryHobby?wsdl  指定了com.zzj.w...路径  必须
 * 2.在没有指定co'm.zzj.....情况下为什么会在引用包时报错 ？
 * 3.wsimport找不到命令  到对应的路径下   +   将jdk拷到非系统盘
 * 4.注意eclipse中 包 结构  / 源码包 
 * 5.AR打包的误区：第三方包的嵌套打包  https://guhanjie.iteye.com/blog/1912769   --- 打war  部署包 
 * 6.webservice项目可以在同一个项目中  --可以




public class WsClient {
	public static void main(String[] args) {
	//	com.messageparse.webservice_mule.webservicefromjava.publish.HobbyServiceImplService
		HobbyServiceImplService hs = new HobbyServiceImplService();
		HobbyServiceImpl hl = hs.getHobbyServiceImplPort();
		String result = hl.queryHobby("yangwenjun" + new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()));
		System.out.println(result);
	}

}
*/
