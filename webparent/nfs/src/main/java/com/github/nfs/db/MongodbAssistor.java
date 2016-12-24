package com.github.nfs.db;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.nfs.model.MongoFile;
import com.github.nfs.model.ResultMessage;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

@Component
public class MongodbAssistor {
	@Resource
	private DBCollection fileCollection ;
	
	public List<MongoFile> find(MongoFile mongoFile){
		BasicDBObject basicDBObject = mongoFile.getBasicDBObject();
		DBCursor find = fileCollection.find(basicDBObject);
		//find.
		 return null;
	}
	
	public MongoFile find(String uri){
		DBObject findOne = fileCollection.findOne(new BasicDBObject("uri",uri));
		MongoFile file = new MongoFile((BasicDBObject) findOne);
		return file;
	}
	
	public MongoFile findById(String _id){
		DBObject findOne = fileCollection.findOne(_id);
		MongoFile file = new MongoFile((BasicDBObject) findOne);
		return file;
	}
	
	public ResultMessage insert(MongoFile mongoFile){
		WriteResult insert = fileCollection.insert(mongoFile.getBasicDBObject());
		
		return ResultMessage.OK;
	}
	
	public ResultMessage insert(List<MongoFile> mongoFile){
		return null;
	}
	
}
