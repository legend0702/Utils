package com.zhuhongqing.mongohandler;

import java.util.Map;

import com.mongodb.DBCursor;
import com.zhuhongqing.mongointerface.MongoQueryHandler;

public class MongoMapHandler implements MongoQueryHandler<Map<?, ?>> {

	public Map<?, ?> MongQueryHandler(DBCursor dbc) {
		if (dbc.count() < 1) {
			return null;
		}
		return dbc.next().toMap();
	}

}
