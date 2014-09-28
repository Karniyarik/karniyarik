package com.karniyarik.web.servlet.image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class StreamUtil
{
	public static URL getURL(String aPath)
	{
		URL aURL = null;

		aURL = StreamUtil.class.getResource(aPath);

		if(aURL != null)
		{
			return aURL;
		}

		aURL = StreamUtil.class.getClassLoader().getResource(aPath);

		if(aURL != null)
		{
			return aURL;
		}

		aURL = Thread.currentThread().getContextClassLoader().getResource(aPath);

		if(aURL != null)
		{
			return aURL;
		}

		File aFile = new File(aPath);

		try
		{
			return aFile.toURI().toURL();
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static InputStream getStream(String aPath)
	{
		try
		{
			return getURL(aPath).openStream();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static InputStream getStream(URL aURL)
	{
		try
		{
			return aURL.openStream();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}


	public static File getFile(String aPath)
	{
		try
		{
			File aFile = new File(getURL(aPath).toURI());

			return aFile;
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static String getTempDir()
	{
		String tempDir = System.getProperty("java.io.tmpdir");
		return tempDir;
	}
}
