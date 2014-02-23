package com.zhuhongqing.mongohandler;

import com.mongodb.DBCursor;
import com.zhuhongqing.mongointerface.MongoQueryHandler;
import com.zhuhongqing.utils.BeanUtils;

public class MongoBeanHandler<T> implements MongoQueryHandler<T> {

	private Class<T> tClass;

	public MongoBeanHandler(Class<T> clazz) {
		tClass = clazz;
	}

	public T MongQueryHandler(DBCursor dbc) {
		if (dbc.count() < 1) {
			return null;
		}
		return BeanUtils.populate(tClass, dbc.next().toMap());
	}

}
