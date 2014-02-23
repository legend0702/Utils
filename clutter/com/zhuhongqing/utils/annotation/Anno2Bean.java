package com.zhuhongqing.utils.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 请遵循COC原则!!
 * 
 * 注解方法名必须于beanClass中的属性名相对应
 * 
 * 例:
 * 
 * public @interface Column { String name(); String String dataType(); int
 * length(); }
 * 
 * 
 * public class ColumnBean{
 * 
 * private String name;
 * 
 * private String dateyType;
 * 
 * private int length; }
 * 
 * @author Mr.Yi 2013/5/29 10:24
 */

/**
 * 传入一个annotation 以及一个对应的bean
 * 
 * 以bean的属性名字为依据去annotation中的方法找
 * 
 * 找到一样的名字则启用annotation的方法 返回的值注入这个bean中
 * 
 * @author Mr.Yi 2013/5/29 10:24
 */

public class Anno2Bean {

	public static void annoToBean(Annotation anno, Object bean) {

		Method[] annoMethods = anno.getClass().getDeclaredMethods();

		Field[] beanFields = bean.getClass().getDeclaredFields();

		for (Field beanField : beanFields) {

			for (Method annoMethod : annoMethods) {

				if (annoMethod.getName().equalsIgnoreCase(beanField.getName())) {

					beanField.setAccessible(true);
					try {
						beanField.set(bean, annoMethod.invoke(anno));
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e);
					}
					beanField.setAccessible(false);
				}
			}
		}
	}

	public static <T> T annoToBean(Annotation anno, Class<T> beanClass) {

		T beanObj = null;

		try {
			beanObj = beanClass.newInstance();
			annoToBean(anno, beanObj);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		return beanObj;

	}

	/**
	 * 传入一个annotation 以及一个对应的class
	 * 
	 * 以class的属性名字为依据去annotation中的方法找
	 * 
	 * 找到一样的名字则将计数器加1 最后将计数器跟annotation中的方法数量做比较 一样则说明这个class于这个annotation相对应
	 */

	public static boolean annoIsBean(Annotation annotation, Class<?> clazz) {

		Method[] annoMethods = annotation.annotationType().getDeclaredMethods();

		Field[] beanFields = clazz.getDeclaredFields();

		int count = 0;

		for (Method annoMethod : annoMethods) {
			for (Field beanFiled : beanFields) {
				if (annoMethod.getName().equalsIgnoreCase(beanFiled.getName())) {
					count++;
				}
			}
		}

		if (count == annoMethods.length
				&& clazz.getSimpleName().contains(
						annotation.annotationType().getSimpleName())) {

			return true;
		}

		return false;

	}

	/**
	 * 以下方法效率比上面高 但是对对象、方法名有限制
	 * 
	 * 上面方法虽然多重循环 但是只在初始化时启用 效率不是关键
	 */

	/*
	 * public static void annoToBean(Object anno, Object bean) { Field[]
	 * beanFields = bean.getClass().getDeclaredFields();
	 * 
	 * for (Field beanFiled : beanFields) { Method annoMethod = null;
	 * 
	 * try { annoMethod = anno.getClass().getDeclaredMethod(
	 * beanFiled.getName()); } catch (SecurityException e) { // this is ok }
	 * catch (NoSuchMethodException e) { // this is ok }
	 * 
	 * if (annoMethod != null) {
	 * 
	 * beanFiled.setAccessible(true); try { beanFiled.set(bean,
	 * annoMethod.invoke(anno)); } catch (IllegalArgumentException e) { throw
	 * new RuntimeException(e); } catch (IllegalAccessException e) { throw new
	 * RuntimeException(e); } catch (InvocationTargetException e) { throw new
	 * RuntimeException(e); } beanFiled.setAccessible(false); } } }
	 * 
	 * 
	 * 
	 * }
	 */

	/*
	 * public static boolean annoIsBean(Object anno, Class<?> clazz) { Field[]
	 * beanFields = clazz.getDeclaredFields();
	 * 
	 * int count = 0;
	 * 
	 * for (Field beanFiled : beanFields) {
	 * 
	 * Method method = null; try { method =
	 * anno.getClass().getDeclaredMethod(beanFiled.getName());
	 * 
	 * } catch (SecurityException e) { // this is ok } catch
	 * (NoSuchMethodException e) { // this is ok } if (method != null) {
	 * System.out.println(method.getName());
	 * 
	 * count++; } }
	 * 
	 * if (count == anno.getClass().getDeclaredMethods().length) { count = 0;
	 * return true; }
	 * 
	 * return false; }
	 */

}
