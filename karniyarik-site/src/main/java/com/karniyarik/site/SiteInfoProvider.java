package com.karniyarik.site;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class SiteInfoProvider 
{
	private static SiteInfoProvider instance = null;
	
	private SiteInfoListConfig config = null;
	
	private SiteInfoProvider() 
	{
		refresh();
	}
	
	public static SiteInfoProvider getInstance()
	{
		if(instance == null)
		{
			instance = new SiteInfoProvider();
		}
		
		return instance;
	}
	
	public void refresh() 
	{
		try {
			URL url = new URL("http://www.neredenaldin.com/APIs/karniyarik.xml");
			URLConnection urlConnection = url.openConnection();
			urlConnection.setReadTimeout(3000);
			urlConnection.setDefaultUseCaches(false);
			urlConnection.setUseCaches(false);
			//urlConnection.setRequestProperty("User-agent", getBotName());
			InputStream stream = urlConnection.getInputStream();
			String content = IOUtils.toString(stream, "windows-1254");
			stream.close();
			//String result = content;
			String result = new NeredenAldinCleaner().clean(content);
			File file = new File("neredenaldin.xml");
			FileUtils.writeStringToFile(file, result, "windows-1254");
			config = new SiteInfoListConfig(file.toURI().toURL());
		} 
		catch (Throwable e) 
		{
			
		} 
	}
	
	public SiteInfoConfig getSiteInfo(String siteName)
	{
		if(config != null)
		{
			return config.getSiteInfoConfig(siteName);	
		}
		else
		{
			return null;
		}
	}
	
}
