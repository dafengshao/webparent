package com.github.nfs;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.nfs.db.MongodbAssistor;
import com.github.nfs.model.MongoFile;



public class AppTest 
{
	static AbstractXmlApplicationContext context = 
			new ClassPathXmlApplicationContext(
					"classpath:spring/applicationContext.xml"
					
					);
	
	public static void main(String[] args) {
		MongodbAssistor mongodbAssistor = (MongodbAssistor)context.getBean("mongodbAssistor");
		
		MongoFile insertFile = new MongoFile();
		//insertFile.set_id("123");
		insertFile.setBody(new byte[20]);
		insertFile.setUri("/1231/13.jpg");
		mongodbAssistor.insert(insertFile);
		
		MongoFile findById = mongodbAssistor.findById("123");
		System.out.println(findById);
		context.close();
	}
}
