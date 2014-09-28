package com.karniyarik.recognizer.ext;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.recognizer.BaseFeatureRecognizer;

public class CountryRegistry {
	
	private Map<String, AlternateNamesHolder> codeMap = new HashMap<String, AlternateNamesHolder>();
	
	private static CountryRegistry instance = new CountryRegistry();
	
	private CountryRegistry() {
		try {
			InputStream stream = StreamUtil.getStream("country_codes.txt");
			List<String> lines = IOUtils.readLines(stream, StringUtil.DEFAULT_ENCODING);
			for(String line: lines)
			{
				String[] split = line.split("\\t");
				if(split != null && split.length > 0 && StringUtils.isNotBlank(split[0]))
				{
					AlternateNamesHolder holder = new AlternateNamesHolder(split);
					codeMap.put(normalize(holder.getName()), holder);
					for(String str: holder.getAlternateNames())
					{
						codeMap.put(normalize(str), holder);
					}
				}
			}
			stream.close();
		} catch (Throwable e) {
			throw new RuntimeException("Cannot read country codes", e);
		}
	}
	
	public static CountryRegistry getInstance() {
		return instance;
	}
	
	public String getCountyName(String code)
	{
		String tmp = normalize(code);
		AlternateNamesHolder countryHolder = codeMap.get(tmp);
		if(countryHolder != null)
		{
			return countryHolder.getName();
		}
		else
		{
			//System.out.println(code);
		}
		
		return null;
	}

	private String normalize(String code) {
		String tmp = BaseFeatureRecognizer.toLowercase(code);
		BaseFeatureRecognizer.removeNonTrimmableSpaces(tmp);
		BaseFeatureRecognizer.removePunctuations(tmp, "");
		BaseFeatureRecognizer.removeWhitespaces(tmp, "");
		return tmp;
	}
}
