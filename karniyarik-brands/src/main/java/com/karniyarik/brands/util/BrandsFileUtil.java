package com.karniyarik.brands.util;

import java.io.File;
import java.io.IOException;

public class BrandsFileUtil {

	public static void createFile(File file) throws IOException {
		if (file.isDirectory()) {
			throw new IllegalArgumentException("File path can not be a directory name.");
		}
		
		if (!file.exists()) {
			File parentFile = file.getParentFile();
			
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			
			file.createNewFile();
		}
	}
	
}
