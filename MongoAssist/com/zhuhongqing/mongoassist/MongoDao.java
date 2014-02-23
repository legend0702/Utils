package com.zhuhongqing.mongoassist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.mongodb.WriteConcern;
import com.zhuhongqing.mongohandler.MongoBeanHandler;
import com.zhuhongqing.mongohandler.MongoListBeanHandler;
import com.zhuhongqing.mongointerface.MongoDBConnectionInterface;
import com.zhuhongqing.utils.BeanUtils;

import cn.zhuhongqing.dao.Dao;
import cn.zhuhongqing.dao.QueryResult;

public class MongoDao extends MongoAssist implements Dao {

	public MongoDao(MongoDBConnectionInterface mdbc) {
		super(mdbc);
	}

	public Serializable save(Object bean) {

		synchronized (this) {

			createPrimaryKey(bean);
		}

		return getDbCollection(bean.getClass()).insert(createDBObject(bean),
				WriteConcern.SAFE).getN();

	}

	public List<Serializable> saveAll(Collection<Object> beans) {

		List<Serializable> serList = new ArrayList<Serializable>();

		Iterator<Object> it = beans.iterator();

		while (it.hasNext()) {
			serList.add(save(it.next()));
		}

		return serList;
	}

	public void delete(Object bean) {
		getDbCollection(bean.getClass()).remove(createDBObject(bean),
				WriteConcern.SAFE);
	}

	public void deleteAll(Collection<Object> beans) {
		Iterator<Object> it = beans.iterator();

		while (it.hasNext()) {
			delete(it.next());
		}

	}

	public void update(Object bean) {
		getDbCollection(bean.getClass())
				.findAndModify(createDBObject(createPrimaryKeyMap(bean)),
						createDBObject(bean));
	}

	public void updateAll(Collection<Object> beans) {
		Iterator<Object> it = beans.iterator();

		while (it.hasNext()) {
			update(it.next());
		}

	}

	public <T> T get(Class<T> clazz, Serializable beanId) {

		Map<String, Object> primaryValueMap = new HashMap<String, Object>();

		primaryValueMap.put(BeanUtils.getPrimaryKey(clazz), beanId);

		return find(clazz, primaryValueMap, new MongoBeanHandler<T>(clazz));
	}

	public List<?> find(String sql) {
		return null;
	}

	public <T> List<T> find(Class<T> clazz) {
		return find(clazz, new MongoListBeanHandler<T>(clazz));
	}

	public Object findUniqueResult(String sql) {
		return null;
	}

	public <T> List<T> findByBean(Class<T> clazz, Object bean) {
		return find(clazz, createDBObject(bean), new MongoListBeanHandler<T>(
				clazz));
	}

	public <T> List<T> findByProperty(Class<T> clazz, String prop, Object value) {

		Map<String, Object> propMap = new HashMap<String, Object>();

		propMap.put(prop, value);

		return find(clazz, propMap, new MongoListBeanHandler<T>(clazz));
	}

	public <T> QueryResult<T> findPagesByBean(Class<T> clazz, Object bean,
			int pageIndex, int pageSize) {

		return new QueryResult<T>(find(clazz, bean,
				new MongoListBeanHandler<T>(clazz), pageIndex, pageSize),
				showCount(clazz));
	}

	public <T> QueryResult<T> findPages(Class<T> clazz, int pageIndex,
			int pageSize) {

		return new QueryResult<T>(find(clazz,
				new MongoListBeanHandler<T>(clazz), pageIndex, pageSize),
				showCount(clazz));
	}

}
