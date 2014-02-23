package com.zhuhongqing.utils.generic;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 简单的ClassUtils类
 * 
 * 提供简单的Class操作
 * 
 * @author Mr.Yi 2013/5/29 10:00
 * 
 */

public class ClassUtils {

	/**
	 * 基础类型跟包装类型的Map
	 */

	private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<Class<?>, Class<?>>(
			8);

	/**
	 * 基础类型数组类型以名字对应的Map
	 */

	private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<String, Class<?>>(
			16);

	static {
		primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
		primitiveWrapperTypeMap.put(Byte.class, byte.class);
		primitiveWrapperTypeMap.put(Character.class, char.class);
		primitiveWrapperTypeMap.put(Double.class, double.class);
		primitiveWrapperTypeMap.put(Float.class, float.class);
		primitiveWrapperTypeMap.put(Integer.class, int.class);
		primitiveWrapperTypeMap.put(Long.class, long.class);
		primitiveWrapperTypeMap.put(Short.class, short.class);

		Set<Class<?>> primitiveTypeNames = new HashSet<Class<?>>(16);
		primitiveTypeNames.addAll(primitiveWrapperTypeMap.values());
		primitiveTypeNames.addAll(Arrays.asList(new Class<?>[] {
				boolean[].class, byte[].class, char[].class, double[].class,
				float[].class, int[].class, long[].class, short[].class }));
		for (Iterator<Class<?>> it = primitiveTypeNames.iterator(); it
				.hasNext();) {
			Class<?> primitiveClass = (Class<?>) it.next();
			primitiveTypeNameMap.put(primitiveClass.getName(), primitiveClass);
		}
	}

	/**
	 * 
	 * 简单匹配是否为简单类型
	 * 
	 * @param clazz
	 * @return boolean
	 */

	public static boolean isSimpleType(Class<?> beanClass) {

		if (beanClass.isPrimitive()) {
			return true;
		} else if (String.class.isAssignableFrom(beanClass)) {
			return true;
		} else if (Number.class.isAssignableFrom(beanClass)) {
			return true;
		} else if (Date.class.isAssignableFrom(beanClass)) {
			return true;
		} else if (Calendar.class.isAssignableFrom(beanClass)) {
			return true;
		} else if (beanClass.isArray()) {
			return true;
		} else if (Map.class.isAssignableFrom(beanClass)) {
			return false;
		} else if (Enum.class.isAssignableFrom(beanClass)) {
			return true;
		} else if (Collection.class.isAssignableFrom(beanClass)) {
			return false;
		} else {
			return false;
		}
	}

	/**
	 * 检测是否为简单类型或者是包装类型
	 * 
	 * @param clazz
	 * @return boolean
	 */

	public static boolean isPrimitiveWrapper(Class<?> clazz) {
		return primitiveWrapperTypeMap.containsKey(clazz);
	}

	/**
	 * Check if the given class represents a primitive (i.e. boolean, byte,
	 * char, short, int, long, float, or double) or a primitive wrapper (i.e.
	 * Boolean, Byte, Character, Short, Integer, Long, Float, or Double).
	 * 
	 * @param clazz
	 *            the class to check
	 * @return whether the given class is a primitive or primitive wrapper class
	 */
	public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
		return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
	}

	/**
	 * Check if the given class represents an array of primitives, i.e. boolean,
	 * byte, char, short, int, long, float, or double.
	 * 
	 * @param clazz
	 *            the class to check
	 * @return whether the given class is a primitive array class
	 */
	public static boolean isPrimitiveArray(Class<?> clazz) {
		return (clazz.isArray() && clazz.getComponentType().isPrimitive());
	}

	/**
	 * Check if the given class represents an array of primitive wrappers, i.e.
	 * Boolean, Byte, Character, Short, Integer, Long, Float, or Double.
	 * 
	 * @param clazz
	 *            the class to check
	 * @return whether the given class is a primitive wrapper array class
	 */
	public static boolean isPrimitiveWrapperArray(Class<?> clazz) {
		return (clazz.isArray() && isPrimitiveWrapper(clazz.getComponentType()));
	}

	/**
	 * 从Thread里拿一个类加载器
	 * 
	 * @return ClassLoader
	 */

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back to system
			// class loader...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtils.class.getClassLoader();
		}
		return cl;
	}
}
