package com.github.nfs;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.nfs.db.MongodbAssistor;
import com.github.nfs.db.MongodbLock;
import com.github.nfs.db.MongodbLock.LockOwner;
import com.github.nfs.model.MongodbFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;



public class AppTest 
{
	static AbstractXmlApplicationContext context = 
			new ClassPathXmlApplicationContext(
					"classpath:test-applicationContext.xml"
					
					);
	
	public static void main12(String[] args) throws Exception {
		@SuppressWarnings("unchecked")
		MongodbAssistor mongodbAssistor = (MongodbAssistor)context.getBean("mongodbAssistor");
		MongodbFile insertFile = new MongodbFile();
		File originalImage = new File("F:\\downimg\\123.jpg");
		RandomAccessFile in = new RandomAccessFile(originalImage, "r");
		byte[] bys = new byte[(int) in.length()];
		in.read(bys);
		insertFile.setBody(bys);
		insertFile.setName("123.jpg");
		insertFile.getBasicDBObject().put("createTime", new Date());
		mongodbAssistor.insert(insertFile);
		
		DBObject findById = mongodbAssistor.find("123.jpg");
		System.out.println(((BasicDBObject)findById).getDate("createTime"));
		
		
		context.close();
	}
	
	public static void main(String[] args) {
		DB db =	(DB) context.getBean("mongoFileDB");
		MongodbLock lock =	new MongodbLock(db,"keyinfo");
		String key = "hwf";
		LockOwner lockOwner = lock.tryLock(key);
		lock.tryLock(key, 1000);
		//lock.unlock(key);
		lock.tryLock(key, 100);
		//tryLock.setCreateDate(new Date());
		//lock.unlock(tryLock);
		context.close();
		
	}
}
