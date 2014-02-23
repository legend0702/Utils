package com.zhuhongqing.mongohelp;

import com.zhuhongqing.connectionoptions.ConnectionOptions;
import com.zhuhongqing.mongoassist.MongoDao;
import com.zhuhongqing.mongoconnectionpool.MongoConnectionPool;

public class MongoHelp extends MongoDao {

	private static ThreadLocal<MongoHelp> mongoHelp = new ThreadLocal<MongoHelp>();

	private MongoHelp() {
		super(new MongoConnectionPool());
	}

	public static MongoHelp getMongoHelp(String[] url, String dateBase,
			String table, int maxMongo) {
		MongoHelp mh = mongoHelp.get();

		if (mh != null) {
			return mh;
		}

		ConnectionOptions.setUrl(url);

		ConnectionOptions.setDatabase(dateBase);

		ConnectionOptions.setSheet(table);

		ConnectionOptions.setMaxMongo(maxMongo);

		mh = new MongoHelp();

		mongoHelp.set(mh);

		return mh;
	}

	public static MongoHelp getMongoHelp(String[] url, String dateBase,
			int maxMongo) {
		MongoHelp mh = mongoHelp.get();

		if (mh != null) {
			return mh;
		}

		ConnectionOptions.setUrl(url);

		ConnectionOptions.setDatabase(dateBase);

		ConnectionOptions.setMaxMongo(maxMongo);

		mh = new MongoHelp();

		mongoHelp.set(mh);

		return mh;
	}

}
