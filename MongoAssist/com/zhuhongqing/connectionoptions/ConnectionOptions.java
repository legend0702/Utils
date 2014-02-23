package com.zhuhongqing.connectionoptions;

import java.io.IOException;
import java.util.Properties;

public class ConnectionOptions {

	public ConnectionOptions() {
		loadProperties();
	}

	private static Properties prop = new Properties();

	static {

		try {
			prop.load(ConnectionOptions.class.getClassLoader()
					.getResourceAsStream("mongdbProperty.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			new RuntimeException(e);
		}

		loadProperties();

	}

	private static void loadProperties() {
		setUrl();
		setDatabase();
		setSheet();
		setConnectionSperHost();
		setConnectTimeOut();
		setMaxAutoConnectRetryTime();
		setMaxWaitTime();
		setSocketTimeOut();
		setAutoConnectRetry();
		setFsync();
		setSafe();
		setSocketKeepAlive();
		setThreadsAllowedToBlockForConnectionMultiplier();

	}

	private static String url;
	private static String database;
	private static String sheet;
	private static int connectionSperHost;
	private static int connectTimeOut;
	private static int maxAutoConnectRetryTime;
	private static int maxWaitTime;
	private static int socketTimeOut;
	private static int threadsAllowedToBlockForConnectionMultiplier;
	private static boolean autoConnectRetry;
	private static boolean fsync;
	private static boolean safe;
	private static boolean socketKeepAlive;

	private static int maxMongo;

	private static void setUrl() {

		String url2 = "";

		String link = prop.getProperty("host");

		if (link.contains(",")) {
			String[] linkHost = prop.getProperty("host").split(",");
			String[] linkPort = prop.getProperty("port").split(",");
			if (linkHost.length != linkPort.length) {
				throw new RuntimeException("HostNumberNotEqualsPortNumber");
			}

			for (int i = 0; i < linkHost.length; i++) {

				url2 += linkHost[i] + ":" + linkPort[i] + ",";

				if ((i + 1) == linkHost.length) {
					url2 = url2.substring(0, url2.lastIndexOf(","));
				}
			}
		} else {
			url2 = link + ":" + prop.getProperty("port");
		}

		url = url2;
	}

	private static void setDatabase() {
		database = prop.getProperty("database");
	}

	private static void setSheet() {
		sheet = prop.getProperty("sheet");
	}

	private static void setConnectionSperHost() {
		connectionSperHost = Integer.parseInt(prop
				.getProperty("connectionsperhost"));
	}

	private static void setConnectTimeOut() {
		connectTimeOut = Integer.parseInt(prop.getProperty("connecttimeout"));
	}

	private static void setMaxAutoConnectRetryTime() {
		maxAutoConnectRetryTime = Integer.parseInt(prop
				.getProperty("maxautoconnectretrytime"));
	}

	private static void setMaxWaitTime() {
		maxWaitTime = Integer.parseInt(prop.getProperty("maxwaittime"));
	}

	private static void setSocketTimeOut() {
		socketTimeOut = Integer.parseInt(prop.getProperty("sockettimeout"));
	}

	private static void setAutoConnectRetry() {
		autoConnectRetry = prop.getProperty("autoconnectretry") == "0" ? true
				: false;
	}

	private static void setFsync() {
		fsync = prop.getProperty("fsync") == "0" ? true : false;
	}

	private static void setSafe() {
		safe = prop.getProperty("safe") == "0" ? true : false;
	}

	private static void setSocketKeepAlive() {
		socketKeepAlive = prop.getProperty("socketkeepalive") == "0" ? true
				: false;
	}

	private static void setThreadsAllowedToBlockForConnectionMultiplier() {
		threadsAllowedToBlockForConnectionMultiplier = Integer.parseInt(prop
				.getProperty("threadsallowedtoblockforconnectionmultiplier"));
	}

	public static String getUrl() {

		return url;
	}

	public static String getDatabase() {
		return database;
	}

	public static String getSheet() {
		return sheet;
	}

	public static int getConnectionSperHost() {
		return connectionSperHost;
	}

	public static int getConnectTimeOut() {
		return connectTimeOut;
	}

	public static int getMaxAutoConnectRetryTime() {
		return maxAutoConnectRetryTime;
	}

	public static int getMaxWaitTime() {
		return maxWaitTime;
	}

	public static int getSocketTimeOut() {
		return socketTimeOut;
	}

	public static boolean isAutoConnectRetry() {
		return autoConnectRetry;
	}

	public static boolean isFsync() {
		return fsync;
	}

	public static boolean isSafe() {
		return safe;
	}

	public static boolean isSocketKeepAlive() {
		return socketKeepAlive;
	}

	public static int getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	/*
	 * url:
	 * 
	 * String[] urls = new String[]{"127.0.0.1:8080","127.0.0.2:8080"];
	 */
	public static void setUrl(String[] anyUrl) {

		url = "";

		for (int i = 0; i < anyUrl.length; i++) {
			url += anyUrl[i] + ",";
		}

		url = url.substring(0, url.lastIndexOf(","));
	}

	public static void setDatabase(String dateBase) {
		database = dateBase;
	}

	public static void setSheet(String table) {
		sheet = table;
	}

	public static int getMaxMongo() {
		return maxMongo;
	}

	public static void setMaxMongo(int maxMongo) {
		ConnectionOptions.maxMongo = maxMongo;
	}

}
