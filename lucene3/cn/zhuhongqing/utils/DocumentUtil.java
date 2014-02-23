package cn.zhuhongqing.utils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import cn.zhuhongqing.forbean.DocumentBean;
import cn.zhuhongqing.forbean.DocumentBean.StoreIndex;

public class DocumentUtil {

	public static <T> T Document2Bean(Document document, Class<T> beanClass) {

		T t = null;

		try {
			t = beanClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Document2Bean(document, t);

		return t;
	}

	public static void Document2Bean(Document document, Object bean) {
		Map<String, String> documentMap = new HashMap<String, String>();

		java.lang.reflect.Field[] beanFields = bean.getClass()
				.getDeclaredFields();

		for (java.lang.reflect.Field field : beanFields) {
			String documentStr = document.get(field.getName());

			if (documentStr != null) {
				documentMap.put(field.getName(), documentStr);
			}

		}

		try {
			BeanUtils.populate(bean, documentMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static Collection<Document> Bean2Documents(
			DocumentBean documentBean, Object... beans) {

		Collection<Document> documents = new HashSet<Document>();

		for (Object bean : beans) {
			documents.add(Bean2Document(bean, documentBean));
		}

		return documents;

	}

	/**
	 * COC原则 class bean{
	 * 
	 * private String bean_name;
	 * 
	 * public getBean_name(){ return bean_name; }
	 * 
	 * 
	 * }
	 */

	public static Document Bean2Document(Object bean, DocumentBean documentBean) {

		Document document = new Document();

		java.lang.reflect.Field[] beanFields = bean.getClass()
				.getDeclaredFields();

		for (java.lang.reflect.Field field : beanFields) {

			StoreIndex storeIndex = documentBean.getStoreIndex(field.getName());

			if (storeIndex == null) {
				continue;
			}

			Method getMethod = null;

			String methodName = field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);

			try {

				getMethod = bean.getClass().getDeclaredMethod(
						"get" + methodName);
			} catch (Exception e) {
				try {
					getMethod = bean.getClass().getDeclaredMethod(
							"is" + methodName);
				} catch (Exception e1) {
					continue;
				}
			}

			Object getObj = null;

			try {
				getObj = getMethod.invoke(bean);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			Field documentField = new Field(field.getName(), getObj.toString(),
					storeIndex.getStore(), storeIndex.getIndex());

			document.add(documentField);
		}

		return document;
	}
}
