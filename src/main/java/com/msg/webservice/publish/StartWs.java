package com.msg.webservice.publish;

import javax.xml.ws.Endpoint;

public class StartWs {
	public static void main(String[] args) {
		String address = "http://localhost:1111/hobbyService/queryHobby";
		Endpoint.publish(address, new HobbyServiceImpl());
		System.out.println("HobbyService query  publish successful ");
	}
}
