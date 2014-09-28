package com.karniyarik.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONUtil
{

	private final static ObjectMapper	mapper	= new ObjectMapper();

	public static <T> List<T> parseFile(File file, Class<T> aClass)
	{
		List<T> list = new ArrayList<T>();
		LineIterator iterator = null;
		try
		{
			iterator = FileUtils.lineIterator(file, StringUtil.DEFAULT_ENCODING);
			T t;
			while(iterator.hasNext()) {
				t = parseJSON(iterator.nextLine(), aClass);
				if (t != null) {
					list.add(t);
				}
			}
			iterator.close();
		}
		catch (Throwable e)
		{
			Logger.getLogger(JSONUtil.class.getName()).error("Could not parse statistics log file " + file.getAbsolutePath(), e);
		}
		finally
		{
			if(iterator != null)
			{
				try{
					LineIterator.closeQuietly(iterator);
				}catch(Throwable t){}
			}
		}

		return list;
	}

	public static <T> T parseJSON(String json, Class<T> aClass)
	{
		T t = null;
		try
		{
			t = mapper.readValue(json, aClass);
		}
		catch (Throwable e)
		{
			Logger.getLogger(JSONUtil.class.getName()).error("Could not create " + aClass.getName() + " instance from json: " + json, e);
		}
		return t;
	}

	public static String getJSON(Object obj)
	{
		String json = "";

		try
		{
			json = mapper.writeValueAsString(obj);
		}
		catch (Throwable e)
		{
			Logger.getLogger(JSONUtil.class.getName()).error("Could not create json from object", e);
		}

		return json;
	}

}
