package com.karniyarik.brands.supervisor;

import java.util.ArrayList;
import java.util.List;

import com.karniyarik.brands.BrandHolder;

public class BrandsAnalyzer
{
	private BrandNameComparator mComparator = null;
	
	public BrandsAnalyzer()
	{
		mComparator = new BrandNameComparator();
	}
	
	
	public List<BrandHolder> analyzeBrands(List<BrandHolder> aBrands)
	{
		List<BrandHolder> aResult = new ArrayList<BrandHolder>();
		
		aResult.clear();
		
		BrandHolder aMaxSimilarityTarget = null;
		
		for(BrandHolder aSource: aBrands)
		{
			
			aMaxSimilarityTarget = getMaximumSimilarityBrand(aSource, aResult);
			
			if(aMaxSimilarityTarget != null)
			{
				aMaxSimilarityTarget.addAlternateBrand(aSource.getActualBrand());				
			}
			else
			{
				aResult.add(aSource);
			}
		}
		
		return aResult;
	}
	
	public BrandHolder getMaximumSimilarityBrand(BrandHolder aSource, List<BrandHolder> aBrands)
	{
		double aTmpSimilarity = 0;
		double aMaxSimilarity = 0;
		BrandHolder aMaxSimilarityTarget = null;
		List<String> aList = new ArrayList<String>();

		BrandHolder aResult = null;
		
		for(BrandHolder aTarget: aBrands)
		{
			aList.addAll(aTarget.getListOfAlternateBrands());
			aList.add(aTarget.getActualBrand());
			
			for(String aTargetStr: aList)
			{
				if(aSource != aTarget)
				{
					aTmpSimilarity = mComparator.getSimilarity(aSource.getActualBrand(), aTargetStr);
					
					if(aTmpSimilarity > aMaxSimilarity)
					{
						aMaxSimilarity = aTmpSimilarity;
						aMaxSimilarityTarget = aTarget;
					}					
				}
			}
			
			aList.clear();
		}

		if(aMaxSimilarity > 0.92)
		{
			aResult = aMaxSimilarityTarget;
		}

		return aResult;
	}
}	
