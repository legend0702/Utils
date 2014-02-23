package cn.zhuhongqing.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface Dao {

	public abstract Serializable save(Object bean);

	public abstract List<Serializable> saveAll(Collection<Object> beans);

	public abstract void delete(Object bean);

	public abstract void deleteAll(Collection<Object> beans);

	public abstract void update(Object bean);

	public abstract void updateAll(Collection<Object> beans);

	public abstract <T> T get(Class<T> clazz, Serializable beanId);

	public abstract List<?> find(String sql);

	public abstract <T> List<T> find(Class<T> clazz);

	public abstract Object findUniqueResult(String sql);

	public abstract <T> List<T> findByBean(Class<T> clazz, Object bean);

	public abstract <T> List<T> findByProperty(Class<T> clazz, String proper,
			Object value);

	public abstract <T> QueryResult<T> findPagesByBean(Class<T> clazz,
			Object bean, int pageIndex, int pageSize);

	public abstract <T> QueryResult<T> findPages(Class<T> clazz, int pageIndex,
			int pageSize);

}
