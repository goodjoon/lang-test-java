package mongodb.basic;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public class QuickTourMain {
	public static Logger logger = LoggerFactory.getLogger(QuickTourMain.class);
	public static void main(String[] args) {
		try {
			MongoClient mongoClient = new MongoClient("165.243.187.147", 8089);
			
			DB db = mongoClient.getDB("test");
			boolean auth = db.authenticate("test", "test".toCharArray()); // DB 별로 로그인 한다. 
			logger.debug("Auth : " + auth);
			
			try {// DB 리스트 가져오기 -> clusterAdmin 권한이 필요함~
				List<String> dbNames = mongoClient.getDatabaseNames();
				logger.debug("dbNames", dbNames);
			} catch (MongoException e) {
				logger.error("권한 없어서 DB 리스트는 못본데이");
			}
			
			// Collection 의 리스트 가져오기
			Set<String> colls = db.getCollectionNames();
			for (String s : colls) {
				logger.debug(s);
			}
			
			// 작업 할 컬렉션 가져오기
			DBCollection coll = db.getCollection("test");
			// Collection 을 Drop 한다.
			coll.drop();
			
			// Write Concern 을 설정한다
			mongoClient.setWriteConcern(WriteConcern.JOURNALED);
			
			// insert() 할 데이터 객체 만들어봄.
			BasicDBObject doc = new BasicDBObject()
										.append("name", "MongoDB")
										.append("type", "database")
										.append("count", 1)
										.append("info", new BasicDBObject("x", 203).append("y", 102));
			
			// insert 테스트
			WriteResult wr = coll.insert(doc);
			logger.debug("Write Result" + wr.toString());
			
			// 한개 Document 가져와봄
			DBObject myDoc = coll.findOne();
			logger.debug("findOne() : " + myDoc.toString());
			
			// 여러 Document 를 insert() 한다
			Date start = new Date();
			for (int i = 0 ; i < 100 ; i++)  // INSERT 하는데, 시간이 많이걸림.. Loop 돌면 장난아니네.. 이건 미췬거지..
				coll.insert(new BasicDBObject("i", i));
			Date p1 = new Date();
			
			// 여러 Document 를 제대로 insert 하기!
			List<DBObject> insertList = new Vector<DBObject>();
			for (int i = 0 ; i < 100 ; i++)
				insertList.add(new BasicDBObject("i", i+100));
			coll.insert(insertList);
			Date p2 = new Date();
			
			logger.debug("루프로 INSERT : " + (p1.getTime() - start.getTime()) + " ms");
			logger.debug("한방에 INSERT : " + (p2.getTime() - p1.getTime()) + " ms");
			
			// Document 의 개수 Count 하기
			logger.debug("Document 개수 : " + coll.getCount());
			
			// 전체 다큐먼트 가져오기 위해 Cursor 사용
			DBCursor cursor = coll.find();
			try {
				while(cursor.hasNext())
					logger.debug("가져온 데이터 : " + cursor.next());
			} finally {
				cursor.close(); // Cursor 는 항상 쓰고나서 close() 해야 함.
			}
			
			// 쿼리 조건으로 가져오기 : {"info" : { "x" : 203}} 인거 가져오기
//			BasicDBObject query = new BasicDBObject("info", new BasicDBObject().append("x", 203)); ==> 엇.. 요거 안됨..
//			BasicDBObject query = new BasicDBObject("i", 90); // 간단히 됨.
			/*
			DBObject query = BasicDBObjectBuilder.start() //==> 요거는 { "j" : {$ne : 3}, "k" : {$gt ; 10}} 과 같은거임.
					.push("j")
						.add("$ne", 3)
						.pop()
					.push("k")
						.add("$gt", 10).get();
						*/
			DBObject query = BasicDBObjectBuilder.start() // ==> 요거는 { "i" : {$gte : 1, $lt : 3}} 과 같은거임
					.push("i")
						.append("$gte", 1)
						.append("$lt", 2).get();
						
			cursor = coll.find(query);
			try {
				while(cursor.hasNext())
					logger.debug("Query 해서 가져온 데이터 : " + cursor.next());
			} finally {
				cursor.close();
			}
			
			// Collection 에 INDEX 만들기
			coll.createIndex(new BasicDBObject("i", 1)); // 1 : Ascending, -1 : Descending
			
			// Index 정보 가져오기
			List<DBObject> list = coll.getIndexInfo();
			for (DBObject o : list)
				logger.debug("INDEX : " + o);
			
			mongoClient.close();
		}
		catch (UnknownHostException e) {
		
			e.printStackTrace();
		}
		
	}
}
