package com.karniyarik.brands.nb;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import com.karniyarik.brands.BrandStore;
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
	public String recognize(String candidateBrand)
	{
		String result = null;

		candidateBrand = StringUtil.removePunctiations(candidateBrand);

		result = brandStore.get(candidateBrand);

		ResolveResult resolveResult = new ResolveResult();
		resolveResult.setFoundValue(result);
		result = checkDefaultBrand(resolveResult).getFoundValue();

		return result;
	}

	private ResolveResult checkDefaultBrand(ResolveResult result)
	{
		if (result == null || StringUtils.isBlank(result.getFoundValue()))
		{
			result = new ResolveResult();
			result.setFoundValue(DEFAULT_BRAND);
			result.setDefault(true);
		}

		return result;
	}

	public ResolveResult resolve(String longProductName)
	{
		try
		{
			Map<String, ResolveResult> resultList = new HashMap<String, ResolveResult>();

			ResolveResult result = null;

			BrandAnalyzer analyzer = new BrandAnalyzer();
			TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(longProductName));
			List<Token> tokenList = new ArrayList<Token>();

			Token token = null;
			for (token = tokenStream.next(); token != null; token = tokenStream.next())
			{
				tokenList.add(token);
			}

			for (int anIndex = 3; anIndex > 0; anIndex--)
			{
				loopTokens(tokenList, anIndex, resultList);
			}

			List<ResolveResult> resolveResult = new ArrayList<ResolveResult>(resultList.values());
			Collections.sort(resolveResult);

			if (resultList.size() > 0)
			{
				result = resolveResult.get(0);
			}

			result = checkDefaultBrand(result);

			return result;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
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

	private void loopTokens(List<Token> aTokens, int aGroupSize, Map<String, ResolveResult> resultList)
	{
		String aResult = null;

		if (aTokens.size() >= aGroupSize)
		{
			for (int anIndex = 0; anIndex < aTokens.size() - (aGroupSize - 1); anIndex++)
			{
				List<Token> tokensUnderAnalysis = new ArrayList<Token>();

				tokensUnderAnalysis.add(aTokens.get(anIndex));

				for (int aGroupIndex = 1; aGroupIndex < aGroupSize; aGroupIndex++)
				{
					tokensUnderAnalysis.add(aTokens.get(anIndex + aGroupIndex));
				}

				StringBuffer aString = new StringBuffer();
				for (Token token : tokensUnderAnalysis)
				{
					aString.append(token.term());
				}

				aResult = recognize(aString.toString());

				if (isBrandRecognized(aResult))
				{
					Double score = (aGroupSize * 5.0) + (Math.pow(2, -anIndex) * 100) + aString.length() * 2;

					if (resultList.get(aResult) == null || resultList.get(aResult).getScore() < score)
					{
						ResolveResult resolveResult = new ResolveResult();
						resolveResult.setFoundValue(aResult);
						resolveResult.setScore(score);
						resolveResult.setToken(tokensUnderAnalysis);
						resultList.put(aResult, resolveResult);
					}
				}
			}
		}

		// return aResult;
	}
}
