package com.zhuhongqing.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 一个简单的文件工具类
 * 
 * 用于对文件进行简单的IO处理
 * 
 * @author Mr.Yi 2013/7/2 9:30
 * 
 */

public class FileUtils {

	/**
	 * 用来同步的对象
	 */

	private static final Object synObj = new Object();

	/**
	 * 
	 * A method to find File which you want
	 * 
	 * 
	 * @param fileName
	 *            传入一个想要找到的文件的文件名
	 * @param fileSet
	 *            将找到的文件全部存入改set中
	 */

	public static void findFile(String fileName, Set<File> fileSet) {

		findFile(getBaseFile(), fileName, fileSet);
	}

	/**
	 * @return 返回当前应用所在文件
	 */

	public static File getBaseFile() {

		return new File(getAbsolutPath());

	}

	/**
	 * 
	 * @return 返回当前应用的根路径
	 */

	public static String getAbsolutPath() {
		// System.getProperty("user.dir");

		return new File("").getAbsolutePath();
	}

	/**
	 * 从zipFile中找fileName文件，并输出到当前应用下的filePath中 并命名为fileName
	 * 
	 * @param zipFile
	 *            从该zip中搜索
	 * @param filePath
	 *            文件在zip中所处的位置
	 * @param fileName
	 *            文件的名字
	 */

	public static void zipFile2File(ZipFile zipFile, String filePath,
			String fileName) {

		ZipEntry zipEntry = zipFile.getEntry(filePath + "//" + fileName);

		if (zipEntry == null) {
			return;
		}

		try {
			outFile(zipFile.getInputStream(zipEntry), filePath, fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 从zipFile中找fileName文件，并输出到当前应用下的filePath中 并命名为fileName最后返回该文件
	 * 
	 * @param zipFile
	 *            从该zip中搜索
	 * @param filePath
	 *            文件在zip中所处的位置
	 * @param fileName
	 *            文件的名字
	 * @return 所找到的文件
	 */

	public static File getZipFile(ZipFile zipFile, String filePath,
			String fileName) {

		zipFile2File(zipFile, filePath, fileName);

		return new File(filePath + "//" + fileName);

	}

	/**
	 * 
	 * 从jarFile中找fileName文件，并输出到当前应用下的filePath中 并命名为fileName
	 * 
	 * @param jarFile
	 *            从该jar中搜索
	 * @param filePath
	 *            文件在zip中所处的位置
	 * @param fileName
	 *            文件的名字
	 */

	public static void jarFile2File(JarFile jarFile, String filePath,
			String fileName) {

		zipFile2File(jarFile, filePath, fileName);
	}

	/**
	 * 
	 * 从jarFile中找fileName文件，并输出到当前应用下的filePath中 并命名为fileName最货返回该文件
	 * 
	 * @param jarFile
	 *            从该jar中搜索
	 * @param filePath
	 *            文件在zip中所处的位置
	 * @param fileName
	 *            文件的名字
	 * @return 所找到的文件
	 */

	public static File getJarFile(JarFile jarFile, String filePath,
			String fileName) {

		jarFile2File(jarFile, filePath, fileName);

		return new File(filePath + "//" + fileName);
	}

	/**
	 * 将输入流存到指定路径的文件中
	 * 
	 * @param inputStream
	 *            输入流，数据来源
	 * @param filePath
	 *            存储位置
	 * @param fileName
	 *            存储文件
	 */

	public static void outFile(InputStream inputStream, String filePath,
			String fileName) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] bytes = new byte[1024];

		int len = -1;

		try {
			while ((len = inputStream.read(bytes)) != -1) {
				baos.write(bytes, 0, len);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		outFile(baos.toByteArray(), filePath, fileName);

		closeIO(inputStream, baos);

	}

	/**
	 * 将bytes储存到指定路径的指定文件中
	 * 
	 * @param inputStream
	 *            输入流，数据来源
	 * @param filePath
	 *            存储位置
	 * @param fileName
	 *            存储文件
	 */

	public static void outFile(byte[] fileBytes, String filePath,
			String fileName) {

		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(filePath + "//" + fileName);
		} catch (FileNotFoundException e) {

			return;
		}

		try {
			fos.write(fileBytes);

		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeIO(null, fos);
		}
	}

	/**
	 * 
	 * A method to find File which you want
	 * 
	 * @param rootFile
	 *            在该文件中寻找
	 * @param fileName
	 *            想要找到的文件的文件名
	 * @param fileSet
	 *            将找到的文件全部存入改set中
	 */

	public static void findFile(File rootFile, String fileName,
			Set<File> fileSet) {
		File[] files = rootFile.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				findFile(file, fileName, fileSet);
			} else if (file.getName().equalsIgnoreCase(fileName)) {
				synchronized (synObj) {
					fileSet.add(file);
				}

			}
		}
	}

	/**
	 * A method to find File which you want
	 * 
	 * 
	 * @param rootFile
	 *            在该文件中寻找
	 * @param containName
	 *            想要找到的文件的文件名所包含的文字
	 * @param fileSet
	 *            将找到的文件全部存入改set中
	 */

	public static void findcontainFile(File rootFile, String containName,
			Set<File> fileSet) {
		File[] files = rootFile.listFiles();

		for (File file : files) {

			if (file.isDirectory()) {
				findcontainFile(file, containName, fileSet);
			} else if (file.getName().contains(containName)) {
				synchronized (synObj) {
					fileSet.add(file);
				}

			}
		}
	}

	/**
	 * 
	 * A method to find File which you want
	 * 
	 * @param rootFile
	 *            在该文件中寻找
	 * @param fileSet
	 *            将找到的文件全部存入改set中
	 * @param suffix
	 *            想要什么后缀的文件
	 */

	public static void findFile(File rootFile, Set<File> fileSet, String suffix) {
		File[] files = rootFile.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				findFile(file, fileSet, suffix);
			} else if (file.getName().endsWith(suffix)) {
				synchronized (synObj) {

					fileSet.add(file);
				}

			}
		}
	}

	/**
	 * 
	 * close IO
	 * 
	 * @param is
	 * @param os
	 */

	private static void closeIO(InputStream is, OutputStream os) {
		try {
			if (os != null)
				os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			if (is != null)
				is.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
