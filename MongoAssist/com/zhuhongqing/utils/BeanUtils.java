package com.zhuhongqing.utils;

import java.util.Map;

import cn.zhuhongqing.annotation.Table;

public class BeanUtils {

	public static <T> T populate(Class<T> clazz, Map<?, ?> properties) {

		T t = null;

		t = createT(clazz);

		try {
			org.apache.commons.beanutils.BeanUtils.populate(t, properties);
		} catch (Exception e) {
			return null;
		}

		return t;
	}

	public static Map<?, ?> describe(Object bean) {
		try {
			return org.apache.commons.beanutils.BeanUtils.describe(bean);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getTable(Class<?> beanClass) {
		Table table = beanClass.getClass().getAnnotation(Table.class);
		if (table == null) {
			return beanClass.getSimpleName();
		}

		return table.tableName();
	}

	public static String getPrimaryKey(Class<?> beanClass) {
		Table table = beanClass.getClass().getAnnotation(Table.class);
		if (table == null) {
			return "_id";
		}

		return table.primaryKey();
	}

	private static <T> T createT(Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
		} catch (Exception e) {
			return null;
		}

		return t;
	}

}
