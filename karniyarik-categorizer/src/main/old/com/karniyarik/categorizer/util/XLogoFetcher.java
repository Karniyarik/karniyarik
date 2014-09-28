package com.karniyarik.categorizer.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class XLogoFetcher
{
	public XLogoFetcher()
	{
		// TODO Auto-generated constructor stub
	}

	public void getAkakce() throws Exception
	{
		File file = new File("akakce");
		if(!file.exists())
		{
			file.mkdir();
			System.out.println(file.getAbsolutePath() + " created");
		}
		
		for (int index=0; index < 536; index++)
		{
			try
			{
				URL aURL = new URL("http://www.akakce.com/m/" + index + ".gif");
				FileOutputStream anOutputStream = new FileOutputStream("akakce/" + index + ".gif");
				
				InputStream aStream = aURL.openStream();
				anOutputStream.write(IOUtils.toByteArray(aStream));
				aStream.close();
				
				System.out.println("akakce/" + index + ".gif written");
			}
			catch (Exception e)
			{
			}
		}
	}

	public void getNeredenAldin() throws Exception
	{
		File file = new File("neredenaldin");
		if(!file.exists())
		{
			file.mkdir();
			System.out.println(file.getAbsolutePath() + " created");
		}
		
		for (int index=0; index < 253; index++)
		{
			try
			{
				URL aURL = new URL("http://neredenaldin.com/siteler/" + index + ".jpg");
				
				FileOutputStream anOutputStream = new FileOutputStream("neredenaldin/" + index + ".jpg");
				
				InputStream aStream = aURL.openStream();
				anOutputStream.write(IOUtils.toByteArray(aStream));
				aStream.close();
				
				System.out.println("neredenaldin/" + index + ".jpg written");
			}
			catch (Exception e)
			{
			}
		}
	}

	public void getCimri() throws Exception
	{
		File file = new File("cimri");
		if(!file.exists())
		{
			file.mkdir();
			System.out.println(file.getAbsolutePath() + " created");
		}
		
		for (int index=0; index < 1000; index++)
		{
			try
			{
				URL aURL = new URL("http://www.cimri.com/logos/" + index + ".gif");
				
				FileOutputStream anOutputStream = new FileOutputStream("cimri/" + index + ".gif");
				
				InputStream aStream = aURL.openStream();
				anOutputStream.write(IOUtils.toByteArray(aStream));
				aStream.close();
				
				System.out.println("cimri/" + index + ".gif written");
			}
			catch (Exception e)
			{
			}
		}
	}

	public void getNeKadar() throws Exception
	{
		File file = new File("nekadar");
		if(!file.exists())
		{
			file.mkdir();
			System.out.println(file.getAbsolutePath() + " created");
		}
		
		for (int index=0; index < 2000; index++)
		{
			try
			{
				URL aURL = new URL("http://resim.nekadar.com/logos/" + index + ".gif");
				
				FileOutputStream anOutputStream = new FileOutputStream("nekadar/" + index + ".gif");
				
				InputStream aStream = aURL.openStream();
				anOutputStream.write(IOUtils.toByteArray(aStream));
				aStream.close();
				
				System.out.println("nekadar/" + index + ".gif written");
			}
			catch (Exception e)
			{
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		new XLogoFetcher().getNeKadar();
	}
}
