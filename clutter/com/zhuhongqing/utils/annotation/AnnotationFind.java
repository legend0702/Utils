package com.zhuhongqing.utils.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * 传入一个Annotation[]以及Annotation.class[]
 * 
 * 从Annotation[]中匹配你相找到的Annotataion.class[]
 * 
 * 最后返回找到的Annotataion集合
 * 
 * @author Mr.Yi 2013/5/29 10:24
 */

public class AnnotationFind {

	public static List<Annotation> findAnnos(Annotation[] annos,
			Class<Annotation>[] annotations) {
		List<Annotation> annotationList = new ArrayList<Annotation>();
		for (int i = 0; i < annos.length; i++) {

			Annotation an = findAnnoByClass(annos[i], annotations);

			if (an != null) {

				annotationList.add(an);
			}
		}

		return annotationList;

	}

	/**
	 * 传入一个Annotataion 以及Annotataion.class[]
	 * 
	 * 匹配这个Anotataion是否在你所期望的Annotataion.class[]中
	 * 
	 * 有则返回 无则null
	 */

	public static Annotation findAnnoByClass(Annotation anno,
			Class<Annotation>[] annotations) {

		for (int i = 0; i < annotations.length; i++) {
			if (annotations[i].equals(anno.annotationType())) {
				return anno;
			}
		}

		return null;

	}
}
