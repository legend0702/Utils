package cn.zhuhongqing.utils;

import java.lang.reflect.ParameterizedType;

public class GenericsUtils {

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassGenricType(Class<?> clazz) {
		ParameterizedType clazzParameterType = (ParameterizedType) clazz
				.getGenericSuperclass();

		if (clazzParameterType.getActualTypeArguments().length < 1) {
			throw new RuntimeException("Con not find parameterType");
			// return (Class<T>) clazz;
		}

		return (Class<T>) clazzParameterType.getActualTypeArguments()[0];

		/*
		 * try { return Class.forName(className.substring(6)); } catch
		 * (ClassNotFoundException e) { throw new RuntimeException("Con't find "
		 * + className); }
		 */
	}

	public static Object getObjectGenricType(Class<?> clazz) {

		Class<?> claz = getClassGenricType(clazz);

		if (claz == null) {
			return null;
		}

		try {
			return claz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Con not newInstance: " + claz.getName());
		}

	}

	public static String getClassGenricTypeName(Class<?> clazz) {
		ParameterizedType clazzParameterType = (ParameterizedType) clazz
				.getGenericSuperclass();

		if (clazzParameterType.getActualTypeArguments().length < 1) {
			throw new RuntimeException("Con not find parameterType");
		}

		String className = clazzParameterType.getActualTypeArguments()[0]
				.toString();

		String nameBySpring = className.substring(0).toLowerCase()
				+ className.substring(1);

		return nameBySpring;
	}

}
