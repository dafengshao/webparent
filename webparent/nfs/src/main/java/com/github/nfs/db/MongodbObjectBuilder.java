package com.github.nfs.db;

import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

public interface MongodbObjectBuilder {
	public <T> T builder(BasicDBObject object);
	public <T> T builderWriteResult(WriteResult object);
}
