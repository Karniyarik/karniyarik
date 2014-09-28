package com.karniyarik.recognizer.ext;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.recognizer.BaseFeatureRecognizer;

public class CityRegistry {
	
	private Map<String, AlternateNamesHolder> codeMap = new HashMap<String, AlternateNamesHolder>();
	
	private static CityRegistry instance = new CityRegistry();
	
	private CityRegistry() {
		try {
			InputStream stream = StreamUtil.getStream("cities1000.txt");
			List<String> lines = IOUtils.readLines(stream, StringUtil.DEFAULT_ENCODING);
			for(String line: lines)
			{
				String[] split = line.split("\\t");
				if(split != null && split.length > 0 && StringUtils.isNotBlank(split[1]))
				{
					List<String> names = new ArrayList<String>();
					names.add(split[1]);
					String altNames = split[3];
					if(StringUtils.isNotBlank(altNames))
					{
						String[] altNamesArr = altNames.split(",");
						for(String altName: altNamesArr)
						{
							if(StringUtils.isNotBlank(altName))
							{
								names.add(altName);	
							}
						}
					}
					
					AlternateNamesHolder holder = new AlternateNamesHolder(names.toArray(new String[names.size()]));
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
	
	public static CityRegistry getInstance() {
		return instance;
	}
	
	public String getCityName(String code)
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
	
	public Collection<String> getCityNames()
	{
		return codeMap.keySet();
	}

	private String normalize(String code) {
		String tmp = BaseFeatureRecognizer.toLowercase(code);
		BaseFeatureRecognizer.removeNonTrimmableSpaces(tmp);
		BaseFeatureRecognizer.removePunctuations(tmp, "");
		BaseFeatureRecognizer.removeWhitespaces(tmp, "");
		return tmp;
	}
}
