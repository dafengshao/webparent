package com.hwf.hessianty.main;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.hwf.hessianty.client.HelloService;

public class HessianTest {
	public static void main(String[] args) throws MalformedURLException {
		HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
		hessianProxyFactory.setConnectTimeout(10000);
		hessianProxyFactory.setReadTimeout(10000);
		HelloService helloService = (HelloService)hessianProxyFactory.create(HelloService.class,
				"http://192.168.0.126:9527/helloService");
		try{
			String hello = helloService.hello("<!-- <property name=minEvictableIdleTimeMillis "
					+ "property name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillis "
					+ "property name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillis "
					+ "property name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillis "
					+ "property name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillis "
					+ "property name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillis "
					+ "property name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillis "
					+ "property name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillis "
					+ "property name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillis "
					+ "property name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillis "
					+ "property name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillisproperty "
					+ "name=minEvictableIdleTimeMillisproperty name=minEvictableIdleTimeMillis "
					+ "value=${minEvictableIdleTimeMillis} /> -->");
			System.out.println(hello);
		}catch(Exception e){
			e.printStackTrace();
		}
	
		
		
	}
}
