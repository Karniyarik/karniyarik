package com.karniyarik.common.util;

import org.apache.commons.lang.ClassUtils;

@SuppressWarnings("unchecked")
public class ClassUtil
{
	public static Class loadClass(String aClassName)
	{
		Class aResult = null;

		aResult = loadClass(ClassUtil.class.getClassLoader(), aClassName);

		if (aResult == null)
		{
			aResult = loadClass(ClassLoader.getSystemClassLoader(), aClassName);
		}

		if (aResult == null)
		{

			aResult = loadClass(Thread.currentThread().getContextClassLoader(),
					aClassName);
		}

		return aResult;
	}

	private static Class loadClass(ClassLoader aClassLoader, String aClassName)
	{
		try
		{
			return ClassUtils.getClass(aClassLoader, aClassName);
		}
		catch (ClassNotFoundException e)
		{
		}

		return null;
	}
}
