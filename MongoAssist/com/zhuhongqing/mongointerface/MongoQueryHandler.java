package com.zhuhongqing.mongointerface;

import com.mongodb.DBCursor;

public interface MongoQueryHandler<T> {

	T MongQueryHandler(DBCursor dbc);

}
