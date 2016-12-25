package com.github.nfs;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class ServerMain 
{
    public static void main( String[] args )
    {
    	AbstractXmlApplicationContext context = 
    			new ClassPathXmlApplicationContext(
    					"classpath:spring/applicationContext.xml"
    					
    					);
    }
}
