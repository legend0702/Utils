package com.zhuhongqing.utils.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 头注解只拿一次
 * 
 * 例:
 * 
 * @Table
 * public class User{
 * 
 * 		@Column
 * 		@PrimaryKey
 * 		private String userId;
 * 
 * 		@Column
 * 		private String userName;
 * 		
 * 		@ForeignKey
 * 		private Book book;
 * }
 * 
 * 
 * 那么这个Map为:
 * key:Table value:List<Annotation> ===> Column,PrimaryKey,Column,ForeignKey 
 * 
 * @author Mr.Yi 2013/5/29 10:24
 * 
 */

public class AnnotationSelect {

	/**
	 * 传入一个需要扫描的类 beanClass
	 * 
	 * 以及想要找到的Annotations Class<Annotation>[]
	 * 
	 * 并且传入一个Map容器 里面的值为上所述
	 * 
	 * 如果这个类被纳入*Map 则返回这个类 不符合描述则抛弃
	 */

	public static Class<?> AnnotationSel(Class<?> beanClass,
			Class<Annotation>[] annotations,
			Map<Annotation, List<Annotation>> map) {

		Annotation[] beanClassAnnos = beanClass.getDeclaredAnnotations();

		Annotation beanTopAnno = null;

		List<Annotation> MethodAnnotationsList = null;

		for (Annotation annotation : beanClassAnnos) {

			beanTopAnno = AnnotationFind.findAnnoByClass(annotation,
					annotations);
			if (beanTopAnno != null) {
				break;
			}

		}

		if (beanTopAnno != null) {
			MethodAnnotationsList = new ArrayList<Annotation>();

			for (Field beanField : beanClass.getDeclaredFields()) {

				MethodAnnotationsList.addAll(getFieldAnnotations(beanField,
						annotations));
			}

		}

		if (beanTopAnno != null && MethodAnnotationsList != null) {
			map.put(beanTopAnno, MethodAnnotationsList);
			return beanClass;
		}

		return null;
	}
	
	/**
	 * 传入一个属性 beanField
	 * 
	 * 以及想要查找到的Annotaion.Class [] 
	 * 
	 * 最后返回找到的Annotataion集合
	 */

	private static List<Annotation> getFieldAnnotations(Field beanField,
			Class<Annotation>[] annotations) {

		Annotation[] fieldAnnotations = beanField.getAnnotations();

		return AnnotationFind.findAnnos(fieldAnnotations, annotations);
	}
}
