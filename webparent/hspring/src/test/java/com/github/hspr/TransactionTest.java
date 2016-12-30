package com.github.hspr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.hspr.service.DataService;

public class TransactionTest {
	static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
	
	public static void main(String[] args) {
		DataService dataService = (DataService) context.getBean("dataService");
		String insert = dataService.insert(null);
		System.out.println(insert);
	}
}
