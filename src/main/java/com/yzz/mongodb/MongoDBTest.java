package com.yzz.mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDBTest {

	private static final String host = "localhost";
	private static final int port = 27017;

	public static void main(String[] args) {
		String databaseName = "test";

		MongoDatabase mongoDatabase = getMongoDatabase(databaseName);

		MongoCollection<Document> collection = mongoDatabase.getCollection("test");
		System.out.println("集合 test 选择成功");
		// 插入文档
		/**
		 * 1. 创建文档 org.bson.Document 参数为key-value的格式 
		 * 2. 创建文档集合List<Document> 
		 * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用
		 * mongoCollection.insertOne(Document)
		 */
		/*Document document = new Document();
		document.append("description", "database");
		document.append("likes", 100);
		document.append("by", "Fly");
		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		collection.insertMany(documents);
		System.out.println("文档插入成功");*/
		
		
		//检索所有文档  
        /** 
        * 1. 获取迭代器FindIterable<Document> 
        * 2. 获取游标MongoCursor<Document> 
        * 3. 通过游标遍历检索出的文档集合 
        * */  
        FindIterable<Document> findIterable = collection.find();  
        MongoCursor<Document> mongoCursor = findIterable.iterator();  
        while(mongoCursor.hasNext()){  
           System.out.println(mongoCursor.next());  
        }  

	}

	public static MongoDatabase getMongoDatabase(String databaseName) {
		// 连接到 mongodb 服务
		MongoClient mongoClient = new MongoClient(host, port);

		// 连接到数据库
		MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);

		return mongoDatabase;
	}
}
