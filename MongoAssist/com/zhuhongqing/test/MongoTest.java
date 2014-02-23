package com.zhuhongqing.test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zhuhongqing.mongohelp.MongoHelp;

public class MongoTest {

	public static void main(String[] args) {

		MongoHelp mh = MongoHelp.getMongoHelp(
				new String[] { "127.0.0.1:40000" }, "test", "user", 10);

		DBObject dbObject = new BasicDBObject();

		dbObject.put("email", "qwepoidjdj@hotmail.com");

		dbObject.put("password", "7QW0g9o1d4U/w8V2Zvi+Aw==");

		for (int i = 0; i < 2; i++) {
			// System.out.println(mh.findOne(dbObject));
		}
	}
}
