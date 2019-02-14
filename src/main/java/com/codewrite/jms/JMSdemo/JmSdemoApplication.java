package com.codewrite.jms.JMSdemo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.codewrite.jms.quartz.QuartzJob;

@EnableJms
@SpringBootApplication
public class JmSdemoApplication {

	
	
	public static void main(String[] args) throws IOException, SchedulerException {
		
		ConfigurableApplicationContext context = SpringApplication.run(JmSdemoApplication.class, args);
		Sender sender = context.getBean(Sender.class); 
		
		JobDataMap jdm = new JobDataMap();
		jdm.put("SenderKey", sender);
			
		JobDetail job = JobBuilder.newJob(QuartzJob.class).build();
		Trigger t1 = TriggerBuilder.newTrigger().usingJobData(jdm).withIdentity("CronTrigger").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
	
        Scheduler sc = StdSchedulerFactory.getDefaultScheduler();    	
    	sc.start();
    	sc.scheduleJob(job, t1);
	}

	
	@Bean 
	public JmsListenerContainerFactory warehouseFactory(ConnectionFactory factory, 
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory(); 
		configurer.configure(containerFactory, factory);
		return containerFactory; 
	}
	
	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616"); 
		return factory;
	}
	
	@Bean 
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(connectionFactory());
	}
	
	@Bean
	public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrency("1-1");
		return factory; 
				
	}
	

}

