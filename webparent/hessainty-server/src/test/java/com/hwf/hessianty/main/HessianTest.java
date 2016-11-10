package com.hwf.hessianty.main;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.hwf.hessianty.client.HelloService;

public class HessianTest {
	public static void main(String[] args) throws MalformedURLException {
		HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
		HelloService helloService = (HelloService)hessianProxyFactory.create(HelloService.class,
				"http://192.168.0.126:9527/helloService");
		String hello = helloService.hello("hwf");
		System.out.println(hello);
	}
}
