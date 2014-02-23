package com.zhuhongqing.utils.sql;

public class SimpleSqlFactory {

	public static String creatSelectSql(Object[] params, String what,
			String sheet) {

		String sql = "SELECT " + what + " FROM " + sheet + " WHERE ";

		String where = creatSql(params, " = ? and ");

		where = where.substring(0, where.lastIndexOf("a"));

		sql = sql + where;

		return sql;

	}

	public static String creatUpdateSql(Object[] Params, String logicPrmaryKey,
			String sheet) {

		String sql = "UPDATE " + sheet + " SET ";

		String newParam = creatSql(Params, " = ?,");

		newParam = newParam.substring(0, newParam.lastIndexOf(","));

		sql = sql + newParam + " WHERE " + logicPrmaryKey + " = ?";

		return sql;

	}

	public static String creatUpdateSql(Object[] oldParams, Object[] newParams,
			String sheet) {

		String sql = "UPDATE " + sheet + " SET ";

		String newParam = creatSql(newParams, " = ?,");

		String oldParam = creatSql(oldParams, " = ? and ");

		newParam = newParam.substring(0, newParam.lastIndexOf(","));

		oldParam = oldParam.substring(0, oldParam.lastIndexOf("a"));

		sql = sql + newParam + " WHERE " + oldParam;

		return sql;

	}

	public static String creatSelectSql(String what, String logicPrmaryKey,
			String sheet) {

		String sql = "SELECT " + what + " FROM " + sheet + " WHERE "
				+ logicPrmaryKey + " = ? ";

		return sql;
	}

	public static String creatDelSql(String logicPrmaryKey, String sheet) {
		String sql = "DELECT FROM " + sheet + " WHERE " + logicPrmaryKey
				+ " = ? ";

		return sql;
	}

	public static String creatDelSql(Object[] params, String sheet) {

		String sql = "DELECT FROM " + sheet + " WHERE "
				+ creatSql(params, " = ? and ");

		sql = sql.substring(0, sql.lastIndexOf("a"));

		return sql;
	}

	public static String creatInsertSql(Object[] params, String sheet) {

		String param = creatSql(params, ",");

		param = param.substring(0, param.lastIndexOf(","));

		String values = creatSql(params, null);

		values = values.substring(0, values.lastIndexOf(","));

		String sql = "INSERT INTO " + sheet + " (" + param + ")VALUES("
				+ values + ")";

		return sql;

	}

	private static String creatSql(Object[] params, String format) {
		String sql = "";

		int i = 0;

		if (format != null) {

			String[] paramsName = new String[params.length];

			for (i = 0; i < paramsName.length; i++) {
				paramsName[i] = (String) params[i];

			}

			for (i = 0; i < paramsName.length; i++) {
				sql = sql + paramsName[i] + " " + format + " ";
			}

		} else {
			for (i = 0; i < params.length; i++) {
				sql = sql + " ? , ";
			}
		}

		return sql;
	}

}
