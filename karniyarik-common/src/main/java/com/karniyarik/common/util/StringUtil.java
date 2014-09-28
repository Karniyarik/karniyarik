package com.karniyarik.common.util;

import java.net.URLEncoder;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

/**
 * Utility class that allows for easy string manipulation. This class will be
 * deprecated once the support for JDK 1.3 will no longer be given, as the
 * functionality delivered by this class is incorporated out-of-the box in Java
 * 1.4 (String class, replace methods, etc ...)
 * 
 * $Id: StringUtil.java,v 1.3 2003/05/02 17:36:59 vanrogu Exp $
 * 
 */
public class StringUtil
{

	public static final String	DEFAULT_ENCODING	= "UTF-8";
	
	public static final Locale Locale_TR = new Locale("tr", "tr");

	/**
	 * Replaces the occurences of a certain pattern in a string with a
	 * replacement String.
	 * 
	 * @param string
	 *            the string to be inspected
	 * @param pattern
	 *            the string pattern to be replaced
	 * @param replacement
	 *            the string that should go where the pattern was
	 * @return the string with the replacements done
	 */
	public static String replace(String string, String pattern,
			String replacement)
	{
		String replaced = null;

		if (string == null)
		{
			replaced = null;
		}
		else if (pattern == null || pattern.length() == 0)
		{
			replaced = string;
		}
		else
		{

			StringBuffer sb = new StringBuffer();

			int lastIndex = 0;
			int index = string.indexOf(pattern);
			while (index >= 0)
			{
				sb.append(string.substring(lastIndex, index));
				sb.append(replacement);
				lastIndex = index + pattern.length();
				index = string.indexOf(pattern, lastIndex);
			}
			sb.append(string.substring(lastIndex));
			replaced = sb.toString();
		}
		return replaced;
	}

	/**
	 * @todo add Junit tests for this one
	 */
	public static String replace(String string, String pattern,
			String replacement, int start)
	{
		String begin = string.substring(0, start);
		String end = string.substring(start);
		return begin + replace(end, pattern, replacement);
	}

	public static String removePunctiations(String aStr)
	{
		aStr = aStr.replaceAll("[\\p{Punct}]", "");
		aStr = aStr.replaceAll("[\\s]", "");
		return aStr;
	}

	public static String removePunctiationsAndReduceWhitespace(String aStr)
	{
		if(StringUtils.isNotBlank(aStr))
		{
			aStr = aStr.replaceAll("[\\p{Punct}]", " ");
			aStr = aStr.replaceAll("\\s+", " ");
			aStr = aStr.trim();
		}
		return aStr;
	}

	public static String convertTurkishCharacter(String string)
	{
		char[] chars = string.toCharArray();

		for (int index = 0; index < chars.length; index++)
		{
			chars[index] = convertTurkishCharacter(chars[index]);
		}

		return new String(chars);
	}

	public static char convertTurkishCharacter(char aChar)
	{
		switch (aChar)
		{
		case 'ç':
			return 'c';
		case 'Ç':
			return 'C';
		case 'ğ':
			return 'g';
		case 'Ğ':
			return 'G';
		case 'ı':
			return 'i';
		case 'İ':
			return 'I';
		case 'ö':
			return 'o';
		case 'Ö':
			return 'O';
		case 'ş':
			return 's';
		case 'Ş':
			return 'S';
		case 'ü':
			return 'u';
		case 'Ü':
			return 'U';
		default:
			break;
		}

		return aChar;
	}
	public static String removeMultiEmptySpaces(String str)
	{
		return str.replaceAll("\\s+", " ");
	}
	
	public static String getValue(String str)
	{
		if (str == null)
		{
			return "";
		}
		else
			return str;
	}

	public static String toLowerCase(String str)
	{
		if (str == null)
		{
			return null;
		}
		return str.toLowerCase(Locale.ENGLISH);
	}
	
	public static boolean contains(String str1, String str2)
	{
		return StringUtil.toLowerCase(str1).contains(
				StringUtil.toLowerCase(str2));
	}
	
	public static String capitilizeFirst(String str)
	{
		if(StringUtils.isNotBlank(str))
		{
			StringBuffer buff = new StringBuffer(str);
			buff.setCharAt(0, str.substring(0, 1).toUpperCase(Locale_TR).charAt(0));
			
			return buff.toString();
		}
		
		return str;
	}
	
	public static String optJSONString(JSONObject jsonObject, String name)
	{
		if(jsonObject != null)
		{
			String str = jsonObject.optString(name);
			if(StringUtils.isNotBlank(str) && !str.equalsIgnoreCase("null"))
			{
				return str;
			}
		}
		
		return "";
	}
	
	public static String removeHTMLTags(String str)
	{
		if(str != null)
		{
			str = str.replaceAll("<[^>]+>", "");	
		}
		
		return str;
	}
	
	public static void main(String[] args)
	{
		System.out.println(URLEncoder.encode("http://www.karniyarik.com/ne-kadar/brand-1-city"));
	}
}
