package com.github.nfs.db;

import java.util.ArrayList;
import java.util.List;

import com.github.nfs.model.MongodbFile;
import com.github.nfs.model.MongodbObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class MongodbAssistor {
	
	private DB db;
	
	public MongodbAssistor(){
		
	}
	
	public MongodbAssistor(DB db,String collectionName){
		this.db = db;
		this.fileCollection = db.getCollection(collectionName);
	}
	
	private DBCollection fileCollection ;
	public <T> List<T> find(MongodbObject mongoFile,MongodbObjectBuilder mongodbObjectBuilder){
		BasicDBObject basicDBObject = mongoFile.getBasicDBObject();
		DBCursor find = fileCollection.find(basicDBObject);
		
		//find.
		 return null;
	}
	
	public DBObject find(String name){
		DBObject findOne = fileCollection.findOne(new BasicDBObject("name",name));
		if(findOne==null){
			return null;
		}
		return findOne;
	}

	private <T> T build(MongodbObjectBuilder mongodbObjectBuilder,DBObject findOne) {
		if(mongodbObjectBuilder==null){
			return (T) findOne;
		}
		T builder = mongodbObjectBuilder.builder((BasicDBObject) findOne);
		return builder;
	}
	
	public DBObject findById(String _id){
		DBObject findOne = fileCollection.findOne(_id);
		return findOne;
	}
	
	public WriteResult insert(MongodbFile mongoFile){
		WriteResult insert = fileCollection.insert(mongoFile.getBasicDBObject());
		
		return insert;
	}
	
	public WriteResult insert(List<MongodbFile> mongoFile){
		if(mongoFile==null){
			return null;
		}
		List<BasicDBObject> list = new ArrayList<>(mongoFile.size());
		for(MongodbFile file :mongoFile){
			list.add(file.getBasicDBObject());
		}
		WriteResult insert = fileCollection.insert(list);
		return insert;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public DBCollection getFileCollection() {
		return fileCollection;
	}

	public void setFileCollection(DBCollection fileCollection) {
		this.fileCollection = fileCollection;
	}

	
	
}
