package com.zhuhongqing.utils.bean;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

/**
 * 为Bean做的aop系统
 * 
 * 需要传入一个bean
 * 
 * 再传入一个切入点:BeanService
 * 
 * 最后执行连接点:doServer()方法即可
 * 
 * @param bean
 * @param serviceRun
 * 
 * @author Mr.Yi 2013/5/29 10:24
 * 
 *         更新Collection Server
 * 
 * @author Mr.Yi 2013/6/13 17:35
 */

public class BeanServer {

	private Object bean;

	private Collection<Object> col;

	private BeanService serviceRun;

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Collection<Object> getCol() {
		return col;
	}

	public void setCol(Collection<Object> col) {
		this.col = col;
	}

	public BeanService getServiceRun() {
		return serviceRun;
	}

	public void setServiceRun(BeanService serviceRun) {
		this.serviceRun = serviceRun;
	}

	public BeanServer(Object bean, BeanService serviceRun) {
		setBean(bean);
		setServiceRun(serviceRun);
	}

	public BeanServer(Collection<Object> col, BeanService serviceRun) {
		setCol(col);
		setServiceRun(serviceRun);
	}

	public void doBeanFieldServer() throws Exception {

		for (Field field : getBean().getClass().getDeclaredFields()) {
			Object fieldValue = BeanUtils.findFieldValue(getBean(),
					field.getName());
			getServiceRun().RunService(fieldValue);
		}

	}

	public void doBeanCollectionServer() {
		Iterator<Object> it = getCol().iterator();

		while (it.hasNext()) {
			getServiceRun().RunService(it.next());
		}

	}

}