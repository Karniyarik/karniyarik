package com.karniyarik.recognizer.ext;

import java.util.List;

import org.apache.lucene.analysis.Token;

import com.karniyarik.brands.nb.BrandService;
import com.karniyarik.brands.nb.BrandServiceImpl;
import com.karniyarik.brands.nb.ResolveResult;
import com.karniyarik.recognizer.BaseFeatureRecognizer;
import com.karniyarik.recognizer.ScoreHit;

public class CarModelRecognizer extends BaseFeatureRecognizer
{
	public static final String featureName = "car.models";
	public static int success = 0;
	public static int total = 0;

	
	@Override
	public String recognize(String value)
	{
		value = removeWhitespaces(value, "");
		value = removePunctuations(value, "");
		List<ScoreHit> findHits = findHits(featureName, value);
		if(findHits.size()>0)
		{
			return findHits.get(0).getValue();
		}
		
		return null;
	}
	
	@Override
	public String resolve(String sentence)
	{
		total += 1;
		
		sentence = removeNonTrimmableSpaces(sentence);
		sentence = removeWhitespaces(sentence, " ");
		
		//remove engine powers
		//sentence = sentence.replaceAll("[123]\\.[\\d]", "");
		//remove prices
		sentence = sentence.replaceAll("\\d+\\.\\d+\\s+TL", "");

		BrandService brandService = BrandServiceImpl.getInstance();
		ResolveResult brand = brandService.resolve(sentence);
		 
		if(!brand.isDefault())
		{
			List<Token> brandTokenList = brand.getToken();
			Token lastBrandToken = brandTokenList.get(brandTokenList.size()-1);
			sentence = sentence.substring(lastBrandToken.endOffset());
		}
		
		sentence = sentence.trim();
		sentence = convertTurkishChars(sentence);
		sentence = toLowercase(sentence);
		
		List<ScoreHit> hitList = resolveHits(featureName, sentence,3);
		
		String result = null;

		if(hitList.size() > 0)
		{
			if(!brand.isDefault())
			{
				//TODO:find first hit with correct brand
			}
			result = hitList.get(0).getValue();	
			success++;
		}
		return result;
	}
	
	@Override
	public String normalize(String value)
	{
		value = convertTurkishChars(value);
		value = toLowercase(value);
		value = removePunctuations(value,"");
		value = removeWhitespaces(value,"");
		return value;
	}
	
	
}
