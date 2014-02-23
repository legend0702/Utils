package com.zhuhongqing.mongoassist;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.zhuhongqing.connectionoptions.ConnectionOptions;
import com.zhuhongqing.mongointerface.MongoDBConnectionInterface;
import com.zhuhongqing.mongointerface.MongoQueryHandler;
import com.zhuhongqing.utils.BeanUtils;

public class MongoAssist {

	protected MongoDBConnectionInterface mdbc = null;

	protected Mongo mongo = null;

	protected DB db = null;

	protected DBCollection dbCollection = null;

	public MongoAssist(MongoDBConnectionInterface mdbc) {
		this.mdbc = mdbc;
		mongo = this.mdbc.getMongo();
		db = mongo.getDB(ConnectionOptions.getDatabase());
	}

	public MongoDBConnectionInterface getMdbc() {
		return mdbc;
	}

	public void setMdbc(MongoDBConnectionInterface mdbc) {
		this.mdbc = mdbc;
	}

	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public DBCollection getDbCollection() {
		return dbCollection;
	}

	public void setDbCollection(DBCollection dbCollection) {
		this.dbCollection = dbCollection;
	}

	public DBCollection getDbCollection(Class<?> beanClass) {
		return getDb().getCollection(BeanUtils.getTable(beanClass));
	}

	protected void createPrimaryKey(Object bean) {
		String primaryFieldName = BeanUtils.getPrimaryKey(bean.getClass());

		Field primaryField = null;

		try {
			primaryField = bean.getClass().getField(primaryFieldName);
		} catch (Exception e) {
			// this is ok;
		}

		primaryField.setAccessible(true);

		if (String.class.isAssignableFrom(primaryField.getType())) {
			try {
				primaryField.set(bean, UUID.randomUUID().toString());
			} catch (Exception e) {
				// this is ok;
			}
		}

		if (Number.class.isAssignableFrom(primaryField.getType())) {
			try {
				primaryField.set(bean,
						showCount(bean.getClass()).intValue() + 1);
			} catch (Exception e) {
				// this is ok;
			}
		}

		primaryField.setAccessible(false);

	}

	protected Object getPrimaryKeyValue(Object bean) {
		String primaryFieldName = BeanUtils.getPrimaryKey(bean.getClass());

		Field primaryField = null;

		try {
			primaryField = bean.getClass().getField(primaryFieldName);
		} catch (Exception e) {
			// this is ok;
		}

		primaryField.setAccessible(true);

		Object primaryFieldValue = null;
		try {
			primaryFieldValue = primaryField.get(bean);

		} catch (Exception e) {
			// this is ok;
		} finally {
			primaryField.setAccessible(false);
		}

		return primaryFieldValue;

	}

	protected Map<String, Object> createPrimaryKeyMap(Object bean) {
		Map<String, Object> primaryValueMap = new HashMap<String, Object>();

		primaryValueMap.put(BeanUtils.getPrimaryKey(bean.getClass()),
				getPrimaryKeyValue(bean));

		return primaryValueMap;
	}

	protected DBObject createDBObject(Object bean) {
		return new BasicDBObject(BeanUtils.describe(bean));
	}

	protected DBObject createDBObject(Map<?, ?> beanMap) {
		return new BasicDBObject(beanMap);
	}

	public <T> T find(Class<?> beanClass, MongoQueryHandler<T> mqh) {

		return find(beanClass, mqh, 0, 0);

	}

	public <T> T find(Class<?> beanClass, MongoQueryHandler<T> mqh,
			int pageSize, int pageIndex) {

		if (pageSize == 0) {
			return mqh.MongQueryHandler(getDbCollection(beanClass).find());
		}

		return mqh.MongQueryHandler(getDbCollection(beanClass).find()
				.limit(pageSize).skip(pageSize * pageIndex));

	}

	protected <T> T find(Class<?> beanClass, DBObject dbObject,
			MongoQueryHandler<T> mqh) {

		return find(beanClass, dbObject, mqh, 0, 0);

	}

	protected <T> T find(Class<?> beanClass, DBObject dbObject,
			MongoQueryHandler<T> mqh, int pageSize, int pageIndex) {

		if (pageSize == 0) {
			return mqh.MongQueryHandler(getDbCollection(beanClass).find(
					dbObject));
		}

		return mqh.MongQueryHandler(getDbCollection(beanClass).find(dbObject)
				.limit(pageSize).skip(pageSize * pageIndex));

	}

	public <T> T find(Class<?> beanClass, Map<?, ?> beanMap,
			MongoQueryHandler<T> mqh) {

		return find(beanClass, createDBObject(beanMap), mqh);

	}

	public <T> T find(Class<?> beanClass, Object bean,
			MongoQueryHandler<T> mqh, int pageSize, int pageIndex) {

		return find(beanClass, createDBObject(bean), mqh, pageSize, pageIndex);

	}

	protected Integer showCount(Class<?> beanClass) {
		return Integer.valueOf(String.valueOf(getDbCollection(beanClass)
				.getCount()));
	}
}
