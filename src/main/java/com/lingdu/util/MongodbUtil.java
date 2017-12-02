package com.lingdu.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongodbUtil {  
	private static String addr;
	private static String port;
	
	static {
		Properties prop = new Properties();
		try {
			InputStream in =MongodbUtil.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(in);     ///加载属性列表
            addr = prop.getProperty("mongodb.addr");
            port = prop.getProperty("mongodb.port");
         in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
	}

 
    public MongoClient mongoClient = null;  
  
    public MongoDatabase mongoDatabase= null;  
      
    /** 
     * @param DB_Name 
     */  
    public MongodbUtil(String DB_Name){  
        this.mongoClient = new MongoClient(addr, Integer.parseInt(port));  
        this.mongoDatabase = this.mongoClient.getDatabase(DB_Name);  
    }  
  
    /** 
     * @param collName ��ݿ���� 
     */  
    public boolean createCollection(String collName){  
        try  
        {  
            this.mongoDatabase.createCollection(collName);  
        }  
        catch(Exception e)  
        {  
            System.out.println(e.getMessage());  
            return false;  
        }  
        return true;  
    }  
      
    /** 
     * @param collName 
     * @return 
     */  
    public MongoCollection<Document> getCollection(String collName){  
        return this.mongoDatabase.getCollection(collName);  
    }  
      
    /** 
     * @param doc
     * @param collName
     */
    public void insert(Document doc, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        coll.insertOne(doc);  
    }  
      
    /** 
     * @param list
     * @param collName
     */
    public void insert(List<Document> list, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        coll.insertMany(list);  
    }  
  
    /** 
     * @param collName 
     * @return 
     */  
    public List<Document> findAll(String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        List<Document> result = new ArrayList<Document>();  
        FindIterable<Document> findIterable = coll.find();  
        MongoCursor<Document> mongoCursor = findIterable.iterator();  
        while(mongoCursor.hasNext()){  
            result.add(mongoCursor.next());  
        }  
        return result;  
    }  
      
    /** 
     * @param query 
     * @param collName 
     * @return 
     */  
    public List<Document> findAll(BasicDBObject query, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        List<Document> result = new ArrayList<Document>();  
        FindIterable<Document> findIterable = coll.find(query);  
        MongoCursor<Document> mongoCursor = findIterable.iterator();  
        while(mongoCursor.hasNext()){  
            result.add(mongoCursor.next());  
        }  
        return result;  
    }  
  
    /** 
     * @param query 
     * @param collName 
     * @return 
     */  
    public List<Document> findAll(BasicDBObject query, BasicDBObject key, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        List<Document> result = new ArrayList<Document>();  
        FindIterable<Document> findIterable = coll.find(query).projection(key);  
        MongoCursor<Document> mongoCursor = findIterable.iterator();  
        while(mongoCursor.hasNext()){  
            result.add(mongoCursor.next());  
        }  
        return result;  
    }  
      
      
      
    /** 
     * @param query 
     * @param collName 
     * @return 
     */  
    public Document findOne(BasicDBObject query,String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        Document result = new Document();  
        FindIterable<Document> findIterable = coll.find(query).limit(1);  
        result = findIterable.iterator().next();  
        return result;  
    }  
      
  
    /** 
     * @param collName 
     */  
    public void deleteAll(String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        BasicDBObject delDbo=new BasicDBObject();  
        delDbo.append("_id", -1);  
        coll.deleteMany(Filters.not(delDbo));  
    }  
      
      
    /** 
     * @param b 
     * @param collName 
     */  
    public void deleteAll(Bson b, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        coll.deleteMany(b);  
    }  
      
      
    /** 
     * @param b 
     * @param collName 
     */  
    public void deleteOne(Bson b, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        coll.deleteOne(b);  
    }  
      
      
    //collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes",200)) );    
      
    /** 
     * @param b 
     * @param doc 
     * @param collName 
     */  
    public void updateAll(Bson b, Document doc, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        coll.updateMany(b, doc);  
    }  
    /**
     * @param coll
     * @param id
     * @param newdoc
     * @return
     */
    public Document updateById(String id, Document newdoc,String collName) {
    	MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
		ObjectId _idobj = null;
		try {
			_idobj = new ObjectId(id);
		} catch (Exception e) {
			return null;
		}
		Bson filter = Filters.eq("_id", _idobj);
		// coll.replaceOne(filter, newdoc); // ��ȫ���
		coll.updateOne(filter, new Document("$set", newdoc));
		return newdoc;
	}
    /**
     * @param coll
     * @param id
     * @param newdoc
     * @return
     */
    public Document updateByFiel(Bson filter, Document newdoc,String collName) {
    	MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
		coll.updateOne(filter, new Document("$set", newdoc));
		return newdoc;
	}
      
}
