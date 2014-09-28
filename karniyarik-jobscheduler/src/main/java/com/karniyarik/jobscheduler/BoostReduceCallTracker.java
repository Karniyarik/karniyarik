package com.karniyarik.jobscheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.karniyarik.common.util.StreamUtil;

public class BoostReduceCallTracker {

	//keeps track of last reduce boost call for a site
	//a value of true means a reduceboost is called, and no standard merge is 
	//called for this site again
	private Properties reduceBoostCalled = null;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public BoostReduceCallTracker() {
		try {
			File file = getStorageFile();
			reduceBoostCalled = new Properties();
			InputStream is = new FileInputStream(file);
			reduceBoostCalled.load(is);
			is.close();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}

	private File getStorageFile() throws IOException {
		String tempDir = StreamUtil.getTempDir();
		String filename = tempDir + "/reduceboost.properties";
		File file = new File(filename);
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
	}
	
	public void mergeCalled(String sitename, boolean reduceBoost)
	{
		if(reduceBoost)
		{
			reduceBoostCalled.setProperty(sitename, "true");
			writeProperties();
		}
		else
		{
			String property = reduceBoostCalled.getProperty(sitename, "false");
			if(Boolean.valueOf(property))
			{
				reduceBoostCalled.remove(sitename);
				writeProperties();
			}	
		}
	}
	
	public boolean isReduceCalled(String sitename)
	{
		String property = reduceBoostCalled.getProperty(sitename, "false");
		return Boolean.valueOf(property);
	}
	
	private void writeProperties(){
		try {
			lock.writeLock().lock();
			File storageFile = getStorageFile();
			FileOutputStream os = new FileOutputStream(storageFile);
			reduceBoostCalled.store(os, "");
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			lock.writeLock().unlock();
		}
	}
	
}
