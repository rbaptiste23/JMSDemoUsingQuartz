package com.codewrite.jms.JMSdemo;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(String destination, BankAccount out) {
		jmsTemplate.convertAndSend(destination, out);
		
	}
	
	public void sendHelloMessage(String destination, String message) {
		jmsTemplate.convertAndSend(destination, message);
		
	}

}
