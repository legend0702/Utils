package com.zhuhongqing.mongoconnectionpool;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.zhuhongqing.connectionoptions.ConnectionOptions;
import com.zhuhongqing.mongointerface.MongoDBConnectionInterface;

public class MongoConnectionPool implements MongoDBConnectionInterface {

	private static List<Mongo> mongoList;

	public MongoConnectionPool() {
		initPool();

	}

	private void initPool() {
		mongoList = new LinkedList<Mongo>();

		for (int i = 0; i < ConnectionOptions.getMaxMongo(); i++) {
			mongoList.add(initMongo());
		}

	}

	public synchronized Mongo getMongo() {

		Mongo mongo = mongoList.remove(0);

		MongoProxy mp = new MongoProxy(mongo);

		return mp.getProxy();
	}

	private void initOption(MongoOptions mo) {

		mo.setConnectionsPerHost(ConnectionOptions.getConnectionSperHost());

		mo.setMaxAutoConnectRetryTime(ConnectionOptions
				.getMaxAutoConnectRetryTime());

		mo.setMaxWaitTime(ConnectionOptions.getMaxWaitTime());

		mo.setConnectTimeout(ConnectionOptions.getConnectTimeOut());

		mo.setSocketTimeout(ConnectionOptions.getSocketTimeOut());

		mo.setThreadsAllowedToBlockForConnectionMultiplier(ConnectionOptions
				.getThreadsAllowedToBlockForConnectionMultiplier());

		mo.setAutoConnectRetry(ConnectionOptions.isAutoConnectRetry());
		mo.setSafe(ConnectionOptions.isSafe());
		mo.setFsync(ConnectionOptions.isFsync());
		mo.setSocketKeepAlive(ConnectionOptions.isSocketKeepAlive());

	}

	private Mongo initMongo() {

		try {

			Mongo mongo = new Mongo(ConnectionOptions.getUrl());

			initOption(mongo.getMongoOptions());

			return mongo;
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	protected class MongoProxy implements MethodInterceptor {

		private Mongo binder;

		private Mongo proxy;

		public MongoProxy(Mongo mongo) {

			binder = mongo;

			Enhancer enhancer = new Enhancer();

			enhancer.setSuperclass(binder.getClass());

			enhancer.setCallback(this);

			proxy = (Mongo) enhancer.create();

		}

		public Object intercept(Object object, Method method, Object[] params,
				MethodProxy methodProxy) throws Throwable {

			if ("close".equals(method.getName())) {

				return mongoList.add((Mongo) object);

			} else {

				return method.invoke(object, params);
			}

		}

		public Mongo getProxy() {
			return proxy;
		}

	}

}
