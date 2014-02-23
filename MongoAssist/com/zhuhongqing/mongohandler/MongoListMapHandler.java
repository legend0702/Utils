package com.zhuhongqing.mongohandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zhuhongqing.mongointerface.MongoQueryHandler;

public class MongoListMapHandler implements MongoQueryHandler<List<Map<?, ?>>> {

	public List<Map<?, ?>> MongQueryHandler(DBCursor dbc) {

		List<Map<?, ?>> mapList = new ArrayList<Map<?, ?>>();

		if (dbc.count() < 1) {
			return mapList;
		}

		List<DBObject> dboList = dbc.toArray();
		Iterator<DBObject> it = dboList.iterator();

		while (it.hasNext()) {
			mapList.add(it.next().toMap());
		}

		return mapList;
	}

}
