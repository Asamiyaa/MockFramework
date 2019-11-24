package com.msg.webservice.publish;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class HobbyServiceImpl implements HobbyService {

	@Override
	@WebMethod
	public String queryHobby(String name) {
		return  name+"有打篮球爱好" ;
	}
}
