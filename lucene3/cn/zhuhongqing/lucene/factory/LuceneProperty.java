package cn.zhuhongqing.lucene.factory;

import java.io.IOException;
import java.util.Properties;

public class LuceneProperty {

	private static Object obj = new Object();

	private static Properties prop;

	private LuceneProperty() {

	}

	static {
		synchronized (obj) {
			prop = new Properties();

			try {

				prop.load(LuceneProperty.class.getClassLoader()
						.getResourceAsStream("luceneProperty.properties"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T createClass(Class<T> clazz) {

		Object realObj = null;

		try {
			realObj = Class.forName(prop.getProperty(clazz.getSimpleName()))
					.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return (T) realObj;
	}
}
