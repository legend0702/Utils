package com.zhuhongqing.utils.generic;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 简单显示工具
 */

public class OutputCollection {

	public static void OutputMap(Map<?, ?> map) {
		OutputIterable(map.entrySet());

	}

	public static void OutputIterable(Iterable<?> iterable) {
		Iterator<?> it = iterable.iterator();
		while (it.hasNext()) {
			Entry<?, ?> entry = (Entry<?, ?>) it.next();
			Screen("--------------------------------");
			Screen("key:" + entry.getKey());
			Screen("value:" + entry.getValue());
			Screen("--------------------------------");

		}

	}

	public static void OutputCol(Collection<?> col) {
		OutputObjs(col.toArray());

	}

	public static void OutputObjs(Object[] objs) {
		for (Object obj : objs) {
			OutputObj(obj);
		}
	}

	public static void OutputObj(Object obj) {
		Screen(obj.toString());
		Screen("--------------------------------");
	}

	private static void Screen(Object output) {
		System.out.println(output);
	}
}
