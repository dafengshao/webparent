package com.github.nfs.model;

import java.io.Serializable;

import org.apache.commons.codec.digest.Md5Crypt;

import com.mongodb.BasicDBObject;

public class MongoFile implements Serializable{

	private static final long serialVersionUID = 177535211425532106L;
	
	private BasicDBObject basicDBObject;
	public MongoFile(){
		basicDBObject = new BasicDBObject(7);
	}
	public MongoFile(BasicDBObject basicDBObject){this.basicDBObject=basicDBObject;}
	public String getType() {
		return basicDBObject.getString("type");
	}
	public MongoFile setType(String type) {
		basicDBObject.put("type", type);
		return this;
	}
	public long getLength() {
		return basicDBObject.getLong("length");
	}
	public MongoFile setLength(long length) {
		basicDBObject.put("length", length);
		return this;
	}
	public String getName() {
		return basicDBObject.getString("name");
	}
	public MongoFile setName(String name) {
		basicDBObject.put("name", name);
		return this;
	}
	public byte[] getBody() {
		return (byte[]) basicDBObject.get("body");
	}
	public MongoFile setBody(byte[] body) {
		basicDBObject.put("body", body);
		return this;
	}
	public String get_id() {
		return basicDBObject.getString("_id");
	}
	/*public MongoFile set_id(String _id) {
		basicDBObject.put("_id", _id);
		return this;
	}*/
	public String getUri() {
		return basicDBObject.getString("uri");
	}
	public MongoFile setUri(String uri) {
		basicDBObject.put("uri", uri);
		basicDBObject.put("_id", Md5Crypt.apr1Crypt(uri));
		return this;
	}
	public String getDescribe() {
		return basicDBObject.getString("describe");
	}
	public MongoFile setDescribe(String describe) {
		basicDBObject.put("describe", describe);
		return this;
	}
	public BasicDBObject getBasicDBObject() {
		return basicDBObject;
	}
	public void setBasicDBObject(BasicDBObject basicDBObject) {
		this.basicDBObject = basicDBObject;
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
