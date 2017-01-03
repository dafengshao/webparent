package com.github.nfs.db;

import java.io.Serializable;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DuplicateKeyException;
import com.mongodb.WriteResult;
/**
 * mongodb的分布式锁<br>
 * 可以根据不同的业务配置不同的db和table,分担mongodb服务器压力<br>
 * 同一个业务必须为相同的db和table<br>
 * */
public class MongodbLock {
	
	private DBCollection fileCollection ;
	private DB db;
	private String lockTable;
	
	public MongodbLock(DB db,String lockTable){
		this.db = db;
		this.lockTable = lockTable;
		fileCollection = db.getCollection(lockTable);
		//BasicDBObject basic = new BasicDBObject();
		//{"createDate":1},{expireAfterSeconds:600}
		//basic.put("time", 1);
		//basic.put("expireAfterSeconds", 1800);
		//fileCollection.createIndex(basic);
	}
	
	
	
	public LockOwner tryLock(String keyStr){
		String id = encryptId(keyStr);
		LockOwner key = new LockOwner(2);
		key.setKey(id);
		key.setTime(new Date());
		//key.setValue(UUID.randomUUID().toString());
		try{
			WriteResult insert = fileCollection.insert(key.getBasicDBObject());
			//System.out.println("tryLock:"+insert);
			//if()
		}catch(DuplicateKeyException e){
			//System.out.println("lock by other");
			key = null;
			return null;
		}catch(Exception e){
			//System.out.println("lock by other");
			key = null;
			throw new RuntimeException(e);
		}
		return key;
	}
	public LockOwner tryLock(String keyStr,long timeout){
		long endTime = System.currentTimeMillis()+timeout;
		LockOwner key = null;
		while((key=tryLock(keyStr))==null){
			long currentTime = System.currentTimeMillis();
			if(currentTime>endTime&&timeout>0){
				break;
			}
			try {
				Thread.sleep(101);
			} catch (InterruptedException e) {
				
			}
		}
		return key;
	}
	
	public boolean unlock(LockOwner key){
		if(key==null){
			return false;
		}
		int i = 0;
		while(i++<5){
			try{
				WriteResult remove = fileCollection.remove(key.getBasicDBObject());
				System.out.println("unlock:"+remove);
				//throw new RuntimeException();
				return true;
			}catch(Exception e){
				try {
					System.out.println(i);
					Thread.sleep(i*43L);
				} catch (InterruptedException e1) {
					
				}
				//logger
			}
		}
		return false;
	}
	
	protected String encryptId(String keyStr) {
		int fir = keyStr.hashCode()%1000;
		return fir+"_"+keyStr;
	}
	
	public DBCollection getFileCollection() {
		return fileCollection;
	}

	public void setFileCollection(DBCollection fileCollection) {
		this.fileCollection = fileCollection;
	}
	
	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public String getLockTable() {
		return lockTable;
	}

	public void setLockTable(String lockTable) {
		this.lockTable = lockTable;
	}

	public class LockOwner implements Serializable {

		private static final long serialVersionUID = 177535211425532106L;

		protected BasicDBObject basicDBObject;

		public LockOwner() {
			this.basicDBObject = new BasicDBObject();
		}

		public LockOwner(BasicDBObject basicDBObject) {
			this.basicDBObject = basicDBObject;
		}

		public LockOwner(int size) {
			this.basicDBObject = new BasicDBObject(size);
		}

		private BasicDBObject getBasicDBObject() {
			return basicDBObject;
		}

		/*安全起见
		private void setBasicDBObject(BasicDBObject basicDBObject) {
			this.basicDBObject = basicDBObject;
		}

		private String getKey() {
			return basicDBObject.getString("_id");
		}*/

		private LockOwner setKey(String key) {
			basicDBObject.put("_id", key);
			return this;
		}

		public Date getTime() {
			return basicDBObject.getDate("ctime");
		}

		private LockOwner setTime(Date createDate) {
			basicDBObject.put("ctime", createDate);
			return this;
		}
		
		public boolean unlock(){
			return MongodbLock.this.unlock(this);
		}

	}

}
