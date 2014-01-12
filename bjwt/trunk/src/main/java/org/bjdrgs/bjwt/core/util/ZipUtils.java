package org.bjdrgs.bjwt.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.bjdrgs.bjwt.core.exception.BaseException;

public class ZipUtils {
	public static void zip(String sourceDir, String zipFileName) {
		File source = new File(sourceDir);
		File zipFile = new File(zipFileName);
		zip(source, zipFile);
	}

	public static void zip(File source, File zipFile) {
		zip(Arrays.asList(source), zipFile);
	}

	public static void zip(List<File> source, File zipFile) {
		ZipOutputStream zos = null;
		try {
			OutputStream os = new FileOutputStream(zipFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			zos = new ZipOutputStream(bos);

			for (File file : source) {
				zipFile(file, "", zos);
			}

			zos.closeEntry();
			zos.close();
		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			try {
				if (zos != null) {
					zos.closeEntry();
					zos.close();
				}
			} catch (Exception e) {

			}
		}
	}

	/**
	 * 
	 * create date:2009- 6- 9 author:Administrator
	 * 
	 * @param source
	 * @param basePath
	 * @param zos
	 * @throws IOException
	 */
	private static void zipFile(File source, String baseRelativePath,
			ZipOutputStream zos) {
		InputStream input = null;
		try {
			if (source.isDirectory()) {
				String relativePath = baseRelativePath + source.getName() + "/";
				zos.putNextEntry(new ZipEntry(relativePath));
				for(File file : source.listFiles()){
					zipFile(file, relativePath, zos);
				}
			} else {
				String relativePath = baseRelativePath + source.getName();
				zos.putNextEntry(new ZipEntry(relativePath));
				
				input = new BufferedInputStream(new FileInputStream(source));
				int length = 0;
				byte[] buf = new byte[2048];
				while ((length = input.read(buf)) > 0) {
					zos.write(buf, 0, length);
				}
			}
		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeQuietly(input);
		}

	}

	/**
	 * 解压 zip 文件，注意不能解压 rar 文件哦，只能解压 zip 文件 解压 rar 文件 会出现 java.io.IOException:
	 * Negative seek offset 异常 create date:2009- 6- 9 author:Administrator
	 * 
	 * @param zipFileName
	 *            zip 文件，注意要是正宗的 zip 文件哦，不能是把 rar 的直接改为 zip 这样会出现
	 *            java.io.IOException: Negative seek offset 异常
	 * @param destDir
	 * @throws IOException
	 */
	public static void unZip(String zipFileName, String destDir) {
		unZip(new File(zipFileName), new File(destDir));
	}

	public static void unZip(File file, File destDir) {
		byte b[] = new byte[2048];
		int length;

		ZipFile zipFile = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			zipFile = new ZipFile(file);
			Enumeration<ZipEntry> enumeration = zipFile.getEntries();
			ZipEntry zipEntry = null;

			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();
				File loadFile = new File(destDir, zipEntry.getName());

				if (zipEntry.isDirectory()) {
					loadFile.mkdirs();
				} else {
					if (!loadFile.getParentFile().exists()) {
						loadFile.getParentFile().mkdirs();
					}

					outputStream = new FileOutputStream(loadFile);
					inputStream = zipFile.getInputStream(zipEntry);

					while ((length = inputStream.read(b)) > 0) {
						outputStream.write(b, 0, length);
					}
					outputStream.close();
					inputStream.close();
				}
			}
		} catch (IOException e) {
			throw new BaseException(e);
		} finally {
			ZipFile.closeQuietly(zipFile);
			closeQuietly(inputStream);
			closeQuietly(outputStream);
		}
	}

	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}
}
