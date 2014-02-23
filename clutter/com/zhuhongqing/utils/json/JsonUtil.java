package com.zhuhongqing.utils.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	public static String bean2Json(Object bean) {

		return JSONObject.fromObject(bean).toString();
	}

	public static String objs2Json(Object[] objs) {
		return JSONArray.fromObject(objs).toString();
	}

	public static String list2Json(List<?> list) {
		return JSONArray.fromObject(list).toString();
	}

	public static String map2Json(Map<?, ?> map) {
		return JSONObject.fromObject(map).toString();
	}

	// xom is error
	public static String xml2Json(String xml) {

		return null;
	}

	public static String json2Xml(String json) {

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T json2Bean(String json, Class<?> clazz) {

		JSONObject jo = JSONObject.fromObject(json);

		return (T) JSONObject.toBean(jo, clazz);

	}

	public static Collection<?> json2List(String json) {
		return JSONArray.toCollection(JSONArray.fromObject(json));
	}

	public static Collection<?> json2List(String json, Class<?> beanClass) {
		return JSONArray.toCollection(JSONArray.fromObject(json), beanClass);
	}

	public static Object json2Map(String json) {

		JSONObject jo = JSONObject.fromObject(json);

		return JSONObject.toBean(jo, HashMap.class);

	}

	public static Object json2Map(String json, Map<?, ?> mapClass) {

		JSONObject jo = JSONObject.fromObject(json);

		return JSONObject.toBean(jo, HashMap.class, mapClass);

	}

	public static Object json2Objs(String json) {

		return JSONArray.toArray(JSONArray.fromObject(json));

	}
}
