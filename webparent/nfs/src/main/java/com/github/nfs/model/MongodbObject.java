package com.github.nfs.model;

import java.io.Serializable;

import com.mongodb.BasicDBObject;

public class MongodbObject implements Serializable{
	private static final long serialVersionUID = 5206406187290320220L;
	protected BasicDBObject basicDBObject;
	public MongodbObject(){
		this.basicDBObject = new BasicDBObject();
	}
	public MongodbObject(BasicDBObject basicDBObject){
		this.basicDBObject = basicDBObject;
	}
	public MongodbObject(int size){
		this.basicDBObject = new BasicDBObject(size);
	}
	public BasicDBObject getBasicDBObject() {
		return basicDBObject;
	}

	public void setBasicDBObject(BasicDBObject basicDBObject) {
		this.basicDBObject = basicDBObject;
	}
	
	
}
