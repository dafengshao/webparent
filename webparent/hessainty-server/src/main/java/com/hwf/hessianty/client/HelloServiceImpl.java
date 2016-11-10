package com.hwf.hessianty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hwf.hessianty.annotation.RemoteService;
@Service("helloService")
public class HelloServiceImpl implements HelloService{
	private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
	
	public HelloServiceImpl() {
		//logger.info("HelloServiceImpl init,"+this);
	}
	
	
	@Override
	public String hello(String parms) {
		logger.info("HelloServiceImpl.hello.this={},parms={}",this,parms);
		return "hello,"+parms;
	}

}
