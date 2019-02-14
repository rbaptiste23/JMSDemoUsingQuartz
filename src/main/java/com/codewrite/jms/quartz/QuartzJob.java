package com.codewrite.jms.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.codewrite.jms.JMSdemo.BankAccount;
import com.codewrite.jms.JMSdemo.Sender;

public class QuartzJob implements Job {
	


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
     	BankAccount ba = new BankAccount("1",750);				
		System.out.println(ba.toString());
		Sender sender= (Sender)context.getMergedJobDataMap().get("SenderKey");	
     	sender.sendMessage("hello-world-topic", ba);
		sender.sendHelloMessage("hello-world-topic", "Yo Yo");
	}

}
