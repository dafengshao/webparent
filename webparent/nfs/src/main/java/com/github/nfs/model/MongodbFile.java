package com.github.nfs.model;

import java.io.Serializable;

import com.mongodb.BasicDBObject;

public class MongodbFile extends MongodbObject implements Serializable{

	private static final long serialVersionUID = 177535211425532106L;
	
	
	public MongodbFile(){
		basicDBObject = new BasicDBObject(7);
	}
	public MongodbFile(BasicDBObject basicDBObject){this.basicDBObject=basicDBObject;}
	public String getType() {
		return basicDBObject.getString("type");
	}
	public MongodbFile setType(String type) {
		basicDBObject.put("type", type);
		return this;
	}
	public long getLength() {
		return basicDBObject.getLong("length");
	}
	private MongodbFile setLength(long length) {
		basicDBObject.put("length", length);
		return this;
	}
	public String getName() {
		return basicDBObject.getString("name");
	}
	public MongodbFile setName(String name) {
		basicDBObject.put("name", name);
		return this;
	}
	public byte[] getBody() {
		return (byte[]) basicDBObject.get("body");
	}
	public MongodbFile setBody(byte[] body) {
		basicDBObject.put("body", body);
		setLength(body.length);
		return this;
	}
	public String get_id() {
		return basicDBObject.getString("_id");
	}
	public MongodbFile set_id(String _id) {
		basicDBObject.put("_id", _id);
		return this;
	}
	public String getUri() {
		return basicDBObject.getString("uri");
	}
	public MongodbFile setUri(String uri) {
		basicDBObject.put("uri", uri);
		return this;
	}
	public String getDescribe() {
		return basicDBObject.getString("describe");
	}
	public MongodbFile setDescribe(String describe) {
		basicDBObject.put("describe", describe);
		return this;
	}
	
	private String objString;
	@Override
	public String toString() {
		if(objString!=null){
			return objString;
		}
		if(basicDBObject==null){
			objString="{}";
		}else{
			objString  = basicDBObject.toString();
		}
		return objString;
	}
}
