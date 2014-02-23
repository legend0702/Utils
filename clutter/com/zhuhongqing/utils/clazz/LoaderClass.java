package com.zhuhongqing.utils.clazz;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 文件类加载器 需要一个加载器实例 将文件以二进制的方式读入 最后产生这个文件的Class并返回
 * 
 * @author Mr.Yi 2013/5/29 10:24
 */

public class LoaderClass<T> extends ClassLoader {

	public LoaderClass(ClassLoader loader) {
		super(loader);
	}

	public synchronized Class<?> loaderClass(File file) {

		ByteArrayOutputStream baos = null;
		FileInputStream fis = null;

		try {
			baos = new ByteArrayOutputStream();
			fis = new FileInputStream(file);

			int len = -1;
			byte[] b = new byte[1024];

			while ((len = fis.read(b)) != -1) {
				baos.write(b, 0, len);
			}

			fis.close();
			baos.close();
			byte[] classByte = baos.toByteArray();
			return super.defineClass(null, classByte, 0, classByte.length);
		} catch (IOException e) {
			return null;
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					return null;
				}
			}

			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					return null;
				}
			}
		}

	}
}
