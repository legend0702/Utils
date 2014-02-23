package com.zhuhongqing.mongohandler;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBCursor;
import com.zhuhongqing.mongointerface.MongoQueryHandler;
import com.zhuhongqing.utils.BeanUtils;

public class MongoListBeanHandler<T> implements MongoQueryHandler<List<T>> {

	private Class<T> tClass;

	public MongoListBeanHandler(Class<T> clazz) {
		tClass = clazz;
	}

	public List<T> MongQueryHandler(DBCursor dbc) {

		List<T> beanList = new ArrayList<T>();

		if (dbc.count() < 1) {
			return beanList;
		}

		while (dbc.hasNext()) {
			beanList.add(BeanUtils.populate(tClass, dbc.next().toMap()));
		}

		return beanList;
	}

}
