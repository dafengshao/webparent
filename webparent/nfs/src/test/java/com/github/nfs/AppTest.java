package com.github.nfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.nfs.db.MongodbAssistor;
import com.github.nfs.model.MongoFile;



public class AppTest 
{
	static AbstractXmlApplicationContext context = 
			new ClassPathXmlApplicationContext(
					"classpath:test-applicationContext.xml"
					
					);
	
	public static void main(String[] args) throws Exception {
		MongodbAssistor mongodbAssistor = (MongodbAssistor)context.getBean("mongodbAssistor");
		
		MongoFile insertFile = new MongoFile();
		File originalImage = new File("F:/hunter/2016-gj/MIGD-727/migd00727pl.jpg");
		RandomAccessFile in = new RandomAccessFile(originalImage, "r");
		byte[] bys = new byte[(int) in.length()];
		in.read(bys);
		insertFile.setBody(bys);
		insertFile.setName("migd00727.jpg");
		mongodbAssistor.insert(insertFile);
		
		/*MongoFile findById = mongodbAssistor.findById("123");
		System.out.println(findById);*/
		context.close();
	}
}
