package com.zhuhongqing.utils.clazz;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * 类文件扫描器
 * 
 * 
 * 遍历rootFile目录下所有的带有.class后缀的文件
 * 
 * 通过LoaderClass创建出这个.class文件的Class
 * 
 * 并存入classFiles中 一般都筛选.class
 * 
 * @author Mr.Yi 2013/5/29 10:24
 */

public class FilesDic {

	public Set<Class<?>> FileDic(File rootFile) {

		Set<Class<?>> classSet = new HashSet<Class<?>>();

		if (rootFile.isFile()) {
			if (rootFile.getName().endsWith(".class")) {
				synchronized (this) {
					LoaderClass<?> lc = new LoaderClass<Object>(
							FilesDic.class.getClassLoader());
					Class<?> t = null;

					t = lc.loaderClass(rootFile);

					if (t != null) {

						classSet.add(t);
					}
				}
			}

		} else {
			File[] files = rootFile.listFiles();
			for (int i = 0; i < files.length; i++) {

				FileDic(files[i]);
			}
		}

		return classSet;

	}

}
