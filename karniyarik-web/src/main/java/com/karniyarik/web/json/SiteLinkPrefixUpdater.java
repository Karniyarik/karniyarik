package com.karniyarik.web.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.notifier.ExceptionNotifier;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.web.util.WebLoggerProvider;

public class SiteLinkPrefixUpdater {
	
	private static SiteLinkPrefixUpdater instance = null;
	private static String filename = "system/site.prefix.properties";
	private Properties list = null;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private SiteLinkPrefixUpdater() {
		initList();
	}
		
	private void initList() {
		lock.writeLock().lock();
		
		try{
			list = new Properties();
			InputStream is = StreamUtil.getStream(filename);
			list.load(is);
			is.close();
		}
		catch(Throwable t)
		{
			ExceptionNotifier.sendException("web-exception", "Site Prefix file cannot be loaded", "Cannot load site prefix file", t);
		}
		finally {
			lock.writeLock().unlock();
		}		
	}

	public static SiteLinkPrefixUpdater getInstance() {
		if(instance == null)
		{
			instance = new SiteLinkPrefixUpdater();
		}
		return instance;
	}
	
	public boolean isShallBeChanged(String source)
	{
		lock.readLock().lock();
		boolean result = list.containsKey(source);
		lock.readLock().unlock();
		return result;
	}
	
	public String updateURL(String url, String source)
	{
		lock.readLock().lock();
		String result = url;
		try {
			String prefix = list.getProperty(source);
			if(StringUtils.isNotBlank(source))
			{
				result = prefix + URLEncoder.encode(url, StringUtil.DEFAULT_ENCODING);	
			}
		} catch (Throwable e) {
			WebLoggerProvider.logException("Cannot add site prefix", e);
		}
		finally 
		{
			lock.readLock().unlock();
		}
		return result;
	}

	public String extractURL(String url, String source)
	{
		lock.readLock().lock();
		String result = url;
		try {
			String prefix = list.getProperty(source);
			if(StringUtils.isNotBlank(source))
			{
				result = result.replace(prefix,"");
				result = URLDecoder.decode(url, StringUtil.DEFAULT_ENCODING);	
			}
		} catch (Throwable e) {
			WebLoggerProvider.logException("Cannot add site prefix", e);
		}
		finally 
		{
			lock.readLock().unlock();
		}
		return result;
	}

	public void updateSources(String content)
	{
		File file = StreamUtil.getFile(filename);
		try{
		if(file.exists())
		{
			file.delete();
		}
		FileOutputStream os = new FileOutputStream(file);
		IOUtils.write(content, os, StringUtil.DEFAULT_ENCODING);
		os.close();
		}catch(Throwable t) {
			
		}
		initList();
	}
}
