package com.karniyarik.brands.supervisor;

import java.util.Locale;

import net.zemberek.araclar.JaroWinkler;

import com.karniyarik.common.util.StringUtil;

public class BrandNameComparator
{
	private JaroWinkler mWinklerAlgo = null;
	
	public BrandNameComparator()
	{
		mWinklerAlgo = new JaroWinkler();		
	}
				
	public double getSimilarity(String aSource, String aTarget)
	{
		aSource = StringUtil.removePunctiations(aSource.toLowerCase(Locale.ENGLISH));
		aTarget = StringUtil.removePunctiations(aTarget.toLowerCase(Locale.ENGLISH));
		
		return mWinklerAlgo.benzerlikOrani(aSource,aTarget);
	}
}
