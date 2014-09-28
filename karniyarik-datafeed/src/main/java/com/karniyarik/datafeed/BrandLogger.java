package com.karniyarik.datafeed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.karniyarik.common.util.StringUtil;

public class BrandLogger {
	private static BrandLogger instance = null;

	private Set<String> brands = new HashSet<String>();
	
	private BrandLogger() {
				
	}

	public static BrandLogger getInstance() {
		if(instance == null)
		{
			instance = new BrandLogger();
		}
		return instance;
	}
	
	public void logMissedBrand(String str) {
		brands.add(str);
	}
	public void write()
	{
		File file = new File("C:/work/brands.txt");
		try {
			if(file.exists())
			{
				file.delete();
			}
			file.createNewFile();
			
			FileOutputStream os = new FileOutputStream(file);
			FileUtils.writeLines(file, StringUtil.DEFAULT_ENCODING, brands);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
