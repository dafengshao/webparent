package com.hwf.hessianty.main;

import javax.annotation.Resource;

import org.junit.Test;

import com.hwf.hessianty.client.HelloService;

public class TestMain extends TestBase{
	@Resource
	HelloService helloService;
	
	@Test
	public void main(){
		//HelloService bean = (HelloService) this.applicationContext.getBean("/remote/helloService");
		//bean.hello("456");
		//helloService.hello("123");
		
		//com_hwf_hessianty_client_HelloService.hello("1231");
		//org.springframework.beans.factory.support.DefaultListableBeanFactory@61c2eee6: defining beans [helloService,beanScannerConfigurer,remoteServcieExporter,org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.annotation.ConfigurationClassPostProcessor.importAwareProcessor]; root of factory hierarchy
		
	}
}
