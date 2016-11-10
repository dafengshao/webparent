package com.hwf.hessianty.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class SpringBootstrap {
	static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
	
	
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		Object bean = applicationContext.getBean(name);
		if(bean==null){
			throw new RuntimeException(name+" bean not found");
		}
		T t= (T)bean ;
		return t;
	}
	
}
