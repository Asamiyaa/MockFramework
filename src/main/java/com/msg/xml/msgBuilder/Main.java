package com.msg.xml.msgBuilder;

public class Main {

	
	public static void main(String[] args) {
		Student s = new Student();
		s.setAge(1);
		s.setName("Yangwenjun");
		new CESEnquiryServiceImpl().method1(s);
	}
}
