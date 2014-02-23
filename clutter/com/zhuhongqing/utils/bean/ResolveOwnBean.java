package com.zhuhongqing.utils.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.zhuhongqing.utils.generic.StringUtils;

/**
 * 传入一个Object 
 * 
 * 判断其是否是Map 是则直接赋给 map
 * 
 * 如果不是 则按照key,value方式进行分解 存入map
 * 
 * accessible:
 * true则为暴力破解(有不稳定性，很有可能拿到你不希望的东西) 
 * flase则为安全解析 但必须符合javabean规范
 * 
 * @author Mr.Yi 2013/5/29 10:24
 * 
 */

public class ResolveOwnBean {

	private Map<String, Object> map;

	@SuppressWarnings("unchecked")
	public void SetBean(Object obj, boolean accessible) {
		if (obj instanceof Map) {
			map = (Map<String, Object>) obj;

			if (accessible) {
				map.remove("class");
				map.remove("serialVersionUID");
			}

		} else {

			if (accessible) {
				map = this.Accessibledescribe(obj);
				/*
				 * 对暴力破解进行简单的数据过滤
				 */
				map.remove("class");
				map.remove("serialVersionUID");
			} else {
				map = this.unAccessibledescribe(obj);
			}
		}
	}

	public Map<String, Object> getMap() {
		return map;
	}

	/**
	 * 过滤该Map的值 并返回 （不会破坏本身map）
	 * 
	 * 只返回value为String 或者int的
	 * 
	 * 过滤value为null或者int为0的数据
	 */

	public Map<String, Object> getFilterMap() {
		Map<String, Object> filterMap = new HashMap<String, Object>();

		Iterator<Entry<String, Object>> it = map.entrySet().iterator();

		while (it.hasNext()) {

			Entry<String, Object> entry = it.next();

			if (entry.getValue() == null) {
				continue;
			}

			Object entryValue = entry.getValue();

			if (entryValue.getClass().isPrimitive()) {

				String beanFieldValueStr = String.valueOf(entryValue);

				if ("0".equalsIgnoreCase(beanFieldValueStr)) {

					continue;
				}
			}

			if (Collection.class.isAssignableFrom(entryValue.getClass())) {
				Collection<?> col = (Collection<?>) entryValue;
				if (col.size() < 1) {

					continue;
				}

			}

			filterMap.put(entry.getKey(), entry.getValue());
		}

		return filterMap;
	}

	/**
	 * 得到所有的参数
	 * 
	 * @return Object[]
	 */

	public Object[] CreatParams() {

		return map.keySet().toArray();

	}
	
	/**
	 * 得到所有的值
	 * 
	 * @return Object[]
	 */

	public Object[] CreatValues() {

		return map.values().toArray();

	}

	/*
	 * 创建一个values 最后一个value为录入参数所对应的value
	 */

	public Object[] CreatValues(String logicPrmaryKey) {

		Object pramryValue = map.get(logicPrmaryKey);

		Iterator<Object> it = map.values().iterator();

		List<Object> valueList = new ArrayList<Object>();

		while (it.hasNext()) {
			valueList.add(it.next());
		}

		valueList.add(pramryValue);

		return valueList.toArray();

	}

	/*
	 * 返回你想要得到的key的值
	 */

	public Object[] CreatValue(String Key) {

		Object prmaryValue = map.get(Key);

		return new Object[] { prmaryValue };

	}

	/*
	 * 安全破解
	 * 
	 * 必须符合javabean规范
	 */

	private Map<String, Object> unAccessibledescribe(Object bean) {

		if (bean == null) {
			throw new IllegalArgumentException("No bean specified");
		}

		Map<String, Object> property = new HashMap<String, Object>();

		Field[] fields = bean.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {

			String methodName = StringUtils.firstToUpper(fields[i].getName());

			Method propertyGetMethod = null;

			try {
				propertyGetMethod = bean.getClass().getMethod(
						"get" + methodName);
			} catch (NoSuchMethodException e) {
				try {
					propertyGetMethod = bean.getClass().getMethod(
							"is" + methodName);
				} catch (Exception e1) {
					// this ok
				}
			}

			if (propertyGetMethod != null) {
				try {
					property.put(fields[i].getName(),
							propertyGetMethod.invoke(bean));
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}

		}
		return property;
	}

	/*
	 * 暴力破解
	 * 
	 * 不推荐使用...
	 */

	private Map<String, Object> Accessibledescribe(Object bean) {

		if (bean == null) {
			throw new IllegalArgumentException("No bean specified");
		}

		Map<String, Object> property = new HashMap<String, Object>();

		Field[] fields = bean.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			try {

				fields[i].setAccessible(true);
				property.put(fields[i].getName(), fields[i].get(bean));
				fields[i].setAccessible(false);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		return property;

	}

}
