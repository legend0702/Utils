package com.zhuhongqing.utils.bean;

/**
 * 
 * 为BeanFieldServer服务的切面
 * 
 * fieldValue为Bean中属性,没有滤空
 * 
 * @author Mr.Yi 2013/5/29 10:24
 * 
 */

public interface BeanService {

	public abstract void RunService(Object obj);

}
