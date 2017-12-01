package com.lingdu.util;

import java.util.ArrayList;
import java.util.List;

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
	private static String CONN_HOST = "218.104.188.43";
	private static int CONN_PORT = 27017;
	
    /** 
     * ������ݿ����ӵ�ַ 
     */  
   // private final String CONN_HOST = "192.168.221.128";  
      
    /** 
     * ������ݿ����Ӷ˿ں� 
     */  
  //  private final int CONN_PORT = 27017;  
      
    /** 
     * MongoDB����ʵ�� 
     */  
    public MongoClient mongoClient = null;  
      
    /** 
     * MongoDB��ݿ�ʵ�� 
     */  
    public MongoDatabase mongoDatabase= null;  
      
    /** 
     * ���췽�� 
     * ��ȡ��ݿ�ʵ�� 
     * @param DB_Name 
     */  
    public MongodbUtil(String DB_Name){  
        this.mongoClient = new MongoClient(CONN_HOST, CONN_PORT);  
        this.mongoDatabase = this.mongoClient.getDatabase(DB_Name);  
    }  
  
    /** 
     * ������ݿ⼯�� 
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
     * ��ȡ��ݿ⼯�� 
     * @param collName 
     * @return 
     */  
    public MongoCollection<Document> getCollection(String collName){  
        return this.mongoDatabase.getCollection(collName);  
    }  
      
    /** 
     * ���뵥���ĵ� 
     * @param doc Bson�ĵ� 
     * @param collName ������� 
     */  
    public void insert(Document doc, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        coll.insertOne(doc);  
    }  
      
    /** 
     * ���������ĵ� 
     * @param list List�����ĵ� 
     * @param collName ������� 
     */  
    public void insert(List<Document> list, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        coll.insertMany(list);  
    }  
  
    /** 
     * ���Ҽ���������Document 
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
     * ָ���������� 
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
     * ָ����������ָ���ֶ� 
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
     * ����һ�� 
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
     * ɾ����е�������� 
     * @param collName 
     */  
    public void deleteAll(String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        BasicDBObject delDbo=new BasicDBObject();  
        delDbo.append("_id", -1);  
        coll.deleteMany(Filters.not(delDbo));  
    }  
      
      
    /** 
     * ɾ��ָ����������� 
     * @param b 
     * @param collName 
     */  
    public void deleteAll(Bson b, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        coll.deleteMany(b);  
    }  
      
      
    /** 
     * ɾ��ָ����һ����� 
     * @param b 
     * @param collName 
     */  
    public void deleteOne(Bson b, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        coll.deleteOne(b);  
    }  
      
      
    //collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes",200)) );    
      
    /** 
     * ����ѯ���������޸� 
     * @param b 
     * @param doc 
     * @param collName 
     */  
    public void updateAll(Bson b, Document doc, String collName){  
        MongoCollection<Document> coll = this.mongoDatabase.getCollection(collName);  
        coll.updateMany(b, doc);  
    }  
    /**
     * һ����ݽ��и���
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
     * һ����ݽ��и���
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
