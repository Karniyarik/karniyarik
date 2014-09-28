package com.karniyarik.common.ga;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.karniyarik.common.util.StreamUtil;

public class GoogleAnalyticsIO
{
	public void write(Analytics analytics, Date date)
	{
		try {
			File file = getFile(date);
			FileOutputStream stream = new FileOutputStream(file);
			JAXBContext context = JAXBContext.newInstance(Analytics.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(analytics, stream); 
			stream.close();
		} catch (Throwable e) {
			e.printStackTrace();
		} 
	}
	
	public void write(Analytics analytics, String filename)
	{
		try {
			File file = getFile(filename);
			FileOutputStream stream = new FileOutputStream(file);
			JAXBContext context = JAXBContext.newInstance(Analytics.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(analytics, stream); 
			stream.close();
		} catch (Throwable e) {
			e.printStackTrace();
		} 
	}
	
	public Analytics read(String filename)
	{
		try {
			File file = getFile(filename);
			return read(file);
		} catch (Throwable e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	public Analytics read(Date date)
	{
		try {
			File file = getFile(date);
			
			return read(file);
		} catch (Throwable e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	public Analytics read(File file)
	{
		try {
			if(file.exists())
			{
				JAXBContext context = JAXBContext.newInstance(Analytics.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				//note: setting schema to null will turn validator off
				unmarshaller.setSchema(null);
				Analytics result = Analytics.class.cast(unmarshaller.unmarshal(file));
				return result;				
			}
		} catch (Throwable e) {
			//e.printStackTrace();
		} 
		
		return null;
	}

	private File getFile(Date date) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(date);
		File file = checkFile(dateStr);
		return file;
	}

	private File getFile(String filename) {
		File file = checkFile(filename);
		return file;
	}
	
	private File checkFile(String filename)
	{
		String tempDir = StreamUtil.getTempDir();
		File directory = new File(tempDir + "/analytics/");
		
		if(!directory.exists())
		{
			directory.mkdirs();	
		}
		
		File file = new File(tempDir + "/analytics/" + filename + ".xml");
		
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return file;
	}
}
