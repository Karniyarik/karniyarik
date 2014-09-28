package com.karniyarik.categorizer.tagger;

import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.zemberek.araclar.JaroWinkler;

import com.karniyarik.common.util.StringUtil;

public class TagNameComparator
{
	private JaroWinkler mWinklerAlgo = null;
	static Pattern pluralPattern = Pattern.compile("(l[ae]r[i]?)\\s*$");
	
	public TagNameComparator()
	{
		mWinklerAlgo = new JaroWinkler();		
	}
				
	public double getSimilarity(String aSource, String aTarget)
	{
		aSource = clearPlural(StringUtil.removePunctiations(StringUtil.convertTurkishCharacter(aSource).toLowerCase(Locale.ENGLISH)));
		aTarget = clearPlural(StringUtil.removePunctiations(StringUtil.convertTurkishCharacter(aTarget).toLowerCase(Locale.ENGLISH)));
		return aSource.equals(aTarget) ? 1 : 0;//mWinklerAlgo.benzerlikOrani(aSource,aTarget);
	}

	public static String clearPlural(String str)
	{
		Matcher sMatcher = pluralPattern.matcher(str);
		
		if(sMatcher.find())
		{
			String group = sMatcher.group(0);
			int lastIndexOf = str.lastIndexOf(group);
			str = str.substring(0, lastIndexOf);				
		}
		
		return str;
	}
	
	public String getMaximumSimilarity(String sourceTag, Collection<String> tags)
	{
		double aTmpSimilarity = 0;
		double aMaxSimilarity = 0;
		String maxSimilarTag = null;
		
		for(String targetTag: tags)
		{
			aTmpSimilarity = getSimilarity(sourceTag, targetTag);
			
			if(aTmpSimilarity > aMaxSimilarity)
			{
				aMaxSimilarity = aTmpSimilarity;
				maxSimilarTag = targetTag;
			}					
		}

		if(aMaxSimilarity < 0.92)
		{
			maxSimilarTag = null;
		}

		return maxSimilarTag;
	}
	
	public static void main(String[] args) {
		TagNameComparator tagNameComparator = new TagNameComparator();
		System.out.println(tagNameComparator.clearPlural("araclarlari"));
		System.out.println(tagNameComparator.clearPlural("aracler"));
	}
}
