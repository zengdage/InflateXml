package com.example.view.engine;

import java.io.File;

public class ResourceUtil {
	public static File searchFile(String keyword, File filepath) {
		File result = null;
		File[] files = filepath.listFiles();
		if (files.length > 0) {
			for (File file : files) {
				if (file.isDirectory()) {
					// 如果目录可读就执行（一定要加，不然会挂掉）
					if (file.canRead()) {
						result = searchFile(keyword, file); // 如果是目录，递归查找
						if (result != null)
							break;
					}
				} else {
					try {
						String filename = file.getName();
						boolean flag1 = filename.indexOf(keyword) > -1;
						boolean flag2 = filename.indexOf(keyword.toUpperCase()) > -1;
						if (flag1 || flag2) {
							return file;
						}
					} catch (Exception e) {
						return null;
					}
				}
			}
		}
		return result;
	}
}
