package com.zhuhongqing.utils.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 简单的StringUtils类
 * 
 * 提供简单的String操作
 * 
 * @author Mr.Yi 2013/5/29 10:23
 * 
 */

public class StringUtils {

	/**
	 * 判断一个字符串是否为null或者长度为0
	 * 
	 * 是则返回true,不是则false
	 * 
	 * @param str
	 * @return boolean
	 */

	public static boolean isNull(String str) {
		if (str == null | str.length() < 1) {
			return true;
		}

		return false;
	}

	/**
	 * 将一个字符串的头位变成大写
	 * 
	 * @param str
	 * @return String
	 */

	public static String firstToUpper(String str) {
		str = str.substring(0, 1).toUpperCase() + str.substring(1);
		return str;
	}

	/**
	 * 
	 * 判断一个数组是否为null或是长度小于1
	 * 
	 * 是则返回true,不是则false.
	 * 
	 * @param str
	 * @return boolean
	 */

	public static boolean isEmpry(String[] str) {
		if (str == null | str.length < 1) {
			return true;
		}

		return false;
	}

	/**
	 * 将一个String依照reg进行截断存入一个List中,最后toArray返回
	 * 
	 * @param str
	 * @param reg
	 * @return String[]
	 */

	public static String[] splitString(String str, String reg) {

		List<String> strList = new ArrayList<String>();

		if (isNull(str) || str.indexOf(reg) < 0) {
			return new String[0];
		}

		StringTokenizer st = new StringTokenizer(str, reg);

		while (st.hasMoreElements()) {
			strList.add(st.nextToken());
		}

		return strList.toArray(new String[strList.size()]);

	}
}
