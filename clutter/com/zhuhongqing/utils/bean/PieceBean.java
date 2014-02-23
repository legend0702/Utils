package com.zhuhongqing.utils.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 针对一个javabean引用其他javabean 以及一个javabean内部有一个集合 而这个集合在另一个javabean中 可多对个集合进行操作
 * 
 * @author Mr.Yi 2013/5/29 10:24
 */

public class PieceBean {

	public static void innerBeanCol(Object bean, Collection<?> beans) {
		innerBeanObjs(bean, beans.toArray());
	}

	public static void innerBeanObjs(Object bean, Object[] beans) {

		for (int i = 0; i < beans.length; i++) {
			innerBean(bean, beans[i]);
		}

	}

	/**
	 * 将一个innerbean注入到另一个bean中
	 * 
	 * ps:判断bean中方法的参数类型是否符合innerbean的类型 是则注入
	 */

	public static void innerBean(Object bean, Object innerBean) {

		Method[] methods = bean.getClass().getDeclaredMethods();

		Method setMethod = null;

		for (Method method : methods) {
			for (Class<?> clazz : method.getParameterTypes()) {
				if (innerBean.getClass().getName().equals(clazz.getName())) {
					setMethod = method;
				}
			}
		}

		if (setMethod != null) {
			try {
				synchronized (setMethod) {
					setMethod.invoke(bean, innerBean);
				}

			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}

	}

	/**
	 * 针对2个bean中集合属性的转移
	 * 
	 * 例：
	 * 
	 * public class beanOne{
	 * 
	 * private Collection<T> tCol;
	 * 
	 * public void setTCol(Collection<T> tCol);
	 * 
	 * public Collection<T> getTCol();
	 * 
	 * }
	 * 
	 * public class beanTwo{
	 * 
	 * private Collection<T> tCol;
	 * 
	 * public void setTCol(Collection<T> tCol);
	 * 
	 * public Collection<T> getTCol();
	 * 
	 * }
	 * 
	 * 将beanTwo中的tCol赋到beanOne中
	 * 
	 * 可以支持多个
	 * 
	 * ps:判断集合中T的类型进行注入 请务必将范型赋对象
	 */

	public static void innerBeanList(Object setListbean, Object getListBean) {

		Method[] setListbeanMethods = setListbean.getClass().getMethods();

		Method[] getListbeanMethods = getListBean.getClass().getMethods();

		Method setListMethod = null;

		Method getListMethod = null;

		for (Method setMethod : setListbeanMethods) {
			for (Method getMethod : getListbeanMethods) {

				for (Type type : setMethod.getGenericParameterTypes()) {

					if (type.equals(getMethod.getGenericReturnType())) {
						if (setMethod.getName().startsWith("set")
								&& getMethod.getName().startsWith("get")) {
							setListMethod = setMethod;
							getListMethod = getMethod;
						}
					}
				}

				if (setListMethod != null && getListMethod != null) {
					try {
						setListMethod.invoke(setListbean,
								getListMethod.invoke(getListBean));
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				}

				setListMethod = null;

				getListMethod = null;

			}

		}

	}
}
