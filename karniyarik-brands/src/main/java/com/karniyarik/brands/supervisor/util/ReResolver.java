package com.karniyarik.brands.supervisor.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.karniyarik.brands.BrandService;
import com.karniyarik.brands.BrandServiceImpl;
import com.karniyarik.common.util.StreamUtil;

@SuppressWarnings("unchecked")
public class ReResolver
{
	private List<String> mUnResolved = null;
	private List<String> mResolved = null;
	
	public ReResolver()
	{
		mUnResolved = new ArrayList<String>();
		mResolved = new ArrayList<String>();
	}
	
	public void resolveAll(String[] aFileNameArr) throws Exception
	{
		for(String aFileName: aFileNameArr)
		{
			resolve(aFileName);
		}
		
		Collections.sort(mUnResolved);
		Collections.sort(mResolved);
		FileUtils.writeLines(StreamUtil.getFile("unresolved.txt"), "UTF-8", mUnResolved);
		FileUtils.writeLines(StreamUtil.getFile("resolved.txt"), "UTF-8", mResolved);
	}
	
	public void resolve(String aFileName) throws Exception
	{
		File aFile = StreamUtil.getFile(aFileName);
		List<String> aLines = FileUtils.readLines(aFile, "UTF-8");
		
		BrandService aService = BrandServiceImpl.getInstance();
		String aBrand = null;
		String aName = null;
		
		for(String aLine: aLines)
		{
			aName = aLine.split("->")[0];
			aBrand = aService.resolveBrand(aName);
			if(!aService.isBrandRecognized(aBrand))
			{
				mUnResolved.add(aName);
			}
			else
			{
				mResolved.add(aName + " -> " + aBrand);
			}
		}		
	}
	
	public static void main(String[] args) throws Exception
	{
		String[] aFileNameList = new String[]{
			"brands_service.log"
		};
		
		new ReResolver().resolveAll(aFileNameList);
	}
}
