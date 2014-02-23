package com.zhuhongqing.utils.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.zhuhongqing.utils.generic.ClassUtils;
import com.zhuhongqing.utils.generic.StringUtils;

public class BeanUtils {

	public static void extendsBean(Object targetBean, Object resouceBean)
			throws IllegalAccessException, InvocationTargetException {
		populate(targetBean, filterBean(resouceBean));
	}

	public static void populate(Object bean, Map<String, Object> properties)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		// Do nothing unless both arguments have been specified
		if ((bean == null) || (properties == null)) {
			return;
		}

		// Loop through the property name/value pairs to be set
		Iterator<Entry<String, Object>> entries = properties.entrySet()
				.iterator();

		while (entries.hasNext()) {

			// Identify the property name and value(s) to be assigned
			Entry<String, Object> entry = entries.next();
			String name = entry.getKey();
			if (name == null) {
				continue;
			}

			// Perform the assignment for this property
			setFieldValue(bean, name, entry.getValue());

		}

	}

	public static Object findFieldValue(Object bean, String fieldName)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		Method getMethod = findBeanReadMethod(bean, fieldName);

		if (getMethod == null) {
			return null;
		}

		return getMethod.invoke(bean);
	}

	public static Method findBeanReadMethod(Object bean, String fieldName) {

		fieldName = StringUtils.firstToUpper(fieldName);

		try {
			return bean.getClass().getMethod("get" + fieldName);
		} catch (Exception e) {
			try {
				return bean.getClass().getMethod("is" + fieldName);
			} catch (Exception e1) {
				return null;
			}
		}
	}

	public static void setFieldValue(Object bean, String fieldName, Object parm)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		Method setMethod = findBeanWriterMethod(bean, fieldName,
				parm.getClass());

		if (setMethod != null) {
			setMethod.invoke(bean, parm);
		}
	}

	public static Map<String, Object> filterBean(Object bean) {

		Map<String, Object> beanMap = new HashMap<String, Object>();

		Field[] beanFields = bean.getClass().getDeclaredFields();

		for (Field field : beanFields) {

			field.setAccessible(true);

			Object beanFieldValue = null;

			try {
				beanFieldValue = field.get(bean);
			} catch (Exception e) {

				continue;
			} finally {
				field.setAccessible(false);
			}

			if (beanFieldValue == null) {

				continue;
			}

			if (beanFieldValue.getClass().isPrimitive()) {

				String beanFieldValueStr = String.valueOf(beanFieldValue);

				if ("0".equalsIgnoreCase(beanFieldValueStr)) {

					continue;
				}
			}

			if (Collection.class.isAssignableFrom(beanFieldValue.getClass())) {
				Collection<?> col = (Collection<?>) beanFieldValue;
				if (col.size() < 1) {

					continue;
				}

			}

			beanMap.put(field.getName(), beanFieldValue);
			
		}

		return beanMap;

	}

	public static Method findBeanWriterMethod(Object bean, String fieldName,
			Class<?> type) {
		fieldName = StringUtils.firstToUpper(fieldName);

		try {
			return bean.getClass().getMethod("set" + fieldName, type);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isSimpleValueType(Class<?> clazz) {
		return ClassUtils.isPrimitiveOrWrapper(clazz)
				|| CharSequence.class.isAssignableFrom(clazz)
				|| Number.class.isAssignableFrom(clazz)
				|| Date.class.isAssignableFrom(clazz)
				|| clazz.equals(URI.class) || clazz.equals(URL.class)
				|| clazz.equals(Locale.class) || clazz.equals(Class.class);
	}

}
