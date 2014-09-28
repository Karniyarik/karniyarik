package com.karniyarik.brands;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.brands.util.ProductNameSplitter;
import com.karniyarik.common.util.StringUtil;

/**
 * @author meralan
 * 
 */
public class BrandServiceImpl implements BrandService
{

	public static final String	DEFAULT_BRAND	= "Diğer";

	private BrandStore			brandStore		= null;
	private static BrandService	INSTANCE		= null;

	private BrandServiceImpl(boolean initialized)
	{
		brandStore = BrandStore.getInstance(initialized);
	}

	public static BrandService getInstance()
	{
		if (INSTANCE == null)
		{
			synchronized (BrandServiceImpl.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = new BrandServiceImpl(true);
				}
			}
		}

		return INSTANCE;
	}

	@Override
	public String getActualBrand(String candidateBrand)
	{
		String result = null;

		candidateBrand = StringUtil.removePunctiations(candidateBrand);

		result = brandStore.get(candidateBrand);

		result = checkDefaultBrand(result);

		return result;
	}

	private String checkDefaultBrand(String result)
	{
		if (StringUtils.isBlank(result))
		{
			result = DEFAULT_BRAND;
		}

		return result;
	}

	@Override
	public String resolveBrand(String longProductName)
	{
		Map<String, Double> resultList = new HashMap<String, Double>();

		String result = null;

		List<String> aTokens = ProductNameSplitter.splitProductName(longProductName);

		for (int anIndex = 3; anIndex > 0; anIndex--)
		{
			loopTokens(aTokens, anIndex, resultList);
			// if(isBrandRecognized(result))
			// {
			// break;
			// }
		}

		if (resultList.size() > 0)
		{
			result = findMax(resultList);
		}

		result = checkDefaultBrand(result);

		return result;
	}

	public String findMax(Map<String, Double> scoreMap)
	{
		Double maxValue = 0d;
		String maxKey = null;

		if (scoreMap != null && scoreMap.size() > 0)
		{
			for (String key : scoreMap.keySet())
			{
				if (scoreMap.get(key) > maxValue)
				{
					maxKey = key;
					maxValue = scoreMap.get(key);
				}
			}
		}

		return maxKey;
	}

	@Override
	public boolean isBrandRecognized(String aBrand)
	{
		if (StringUtils.isBlank(aBrand) || DEFAULT_BRAND.equalsIgnoreCase(aBrand))
		{
			return false;
		}

		return true;
	}

	private void loopTokens(List<String> aTokens, int aGroupSize, Map<String, Double> resultList)
	{
		String aResult = null;

		if (aTokens.size() >= aGroupSize)
		{
			for (int anIndex = 0; anIndex < aTokens.size() - (aGroupSize - 1); anIndex++)
			{
				StringBuffer aString = new StringBuffer();

				aString.append(aTokens.get(anIndex));

				for (int aGroupIndex = 1; aGroupIndex < aGroupSize; aGroupIndex++)
				{
					aString.append(aTokens.get(anIndex + aGroupIndex));
				}

				aResult = getActualBrand(aString.toString());

				if (isBrandRecognized(aResult))
				{
					// give points for
					// the word count
					// the location in the name
					// the character count of brand found

					// formula by siyamed
					// Double score = aGroupSize*0.5 + (aTokens.size() -
					// anIndex)* 1.5 + aResult.length()*2;

					// formula by kaan
					// Double score = (aGroupSize * 5.0) + ((aTokens.size() +
					// anIndex) * (aTokens.size() - anIndex) * 3) +
					// aString.length()*2;

					Double score = (aGroupSize * 5.0) + (Math.pow(2, -anIndex) * 100) + aString.length() * 2;

					if (resultList.get(aResult) == null || resultList.get(aResult) < score)
					{
						resultList.put(aResult, score);
					}
				}
			}
		}

		// return aResult;
	}

	@Override
	public void exportTo(File file) throws Exception
	{
		brandStore.exportTo(file);
	}

	@Override
	public List<BrandHolder> getAllBrands()
	{
		return brandStore.getAllBrands();
	}

	@Override
	public void importFrom(InputStream is) throws Exception
	{
		brandStore.importFrom(is);
	}

	@Override
	public void organizeBrands(List<String> listOfBrands)
	{
		brandStore.organizeBrands(listOfBrands);
	}

	@Override
	public void updateBrandKB(String content) {
		brandStore.updateBrandKB(content);
	}
	
	public static void main(String[] args) {
		
		String resolveBrand = new BrandServiceImpl(true).resolveBrand("Warner Bros Pavilion DV6-3010ET  HP Intel Core i3 350M 2.26GHZ 3GB 320GB 15.6\" Taşınabilir Bilgisayar WN777EA");
		System.out.println(resolveBrand);
	}
}
