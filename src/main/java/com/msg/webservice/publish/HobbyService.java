package com.msg.webservice.publish;

import javax.jws.WebService;

@WebService
public interface HobbyService {
	
	   String queryHobby(String name);

}
