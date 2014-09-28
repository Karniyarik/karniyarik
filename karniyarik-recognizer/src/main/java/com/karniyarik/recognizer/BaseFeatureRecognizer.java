package com.karniyarik.recognizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StringUtil;
import com.karniyarik.recognizer.xml.item.ItemType;

public abstract class BaseFeatureRecognizer
{
	private ItemStore			itemStore		= null;
	private Zemberek			zemberek		= null;
	public static final String	DEFAULT_VALUE	= "Diğer";

	public void setZemberek(Zemberek zemberek)
	{
		this.zemberek = zemberek;
	}

	public Zemberek getZemberek()
	{
		return zemberek;
	}

	public String getLongestAsciiCorrectedWord(String string)
	{
		Kelime[] asciidenTurkceyeCevirilmisKelimeler = zemberek.asciiCozumle(string);

		Kelime enUzunKokluKelime = null;

		for (Kelime cevirilmisKelime : asciidenTurkceyeCevirilmisKelimeler)
		{

			if (enUzunKokluKelime != null)
			{
				if (cevirilmisKelime.kok().icerik().length() > enUzunKokluKelime.kok().icerik().length())
				{
					enUzunKokluKelime = cevirilmisKelime;
				}
			}
			else
			{
				enUzunKokluKelime = cevirilmisKelime;
			}
		}

		if (enUzunKokluKelime != null)
		{
			string = enUzunKokluKelime.kok().icerik();
		}

		return string;
	}

	public String getWordRoot(String string)
	{
		Kok[] kok = zemberek.kokBulucu().kokBul(string);

		return string;
	}

	protected final void setItemStore(ItemStore itemStore)
	{
		this.itemStore = itemStore;
	}

	protected final ItemStore getItemStore()
	{
		return itemStore;
	}

	public final static String removePunctuations(String str, String replace)
	{
		return str.replaceAll("[\\p{Punct}]", replace);
	}

	public final static String removeWhitespaces(String str, String replace)
	{
		return str.replaceAll("[\\s+]", replace);
	}

	public final static String convertTurkishChars(String str)
	{
		return StringUtil.convertTurkishCharacter(str);
	}

	public final static String removeNonTrimmableSpaces(String str)
	{
		str = str.replace(Character.valueOf((char) 160), ' ');
		return str;
	}

	public final static String toLowercase(String str)
	{
		return str.toLowerCase(Locale.ENGLISH);
	}

	protected List<ScoreHit> findHits(String featureName, String value)
	{
		Map<String, List<ItemType>> valueMap = getItemStore().getValueMap(featureName);
		String[] tokens = value.split(" ");
		List<ItemType> hitTypeList = null;
		String tmpResult = null;
		Map<String, ScoreHit> hitMap = new HashMap<String, ScoreHit>();

		for (String token : tokens)
		{
			token = removeWhitespaces(token, "");

			if (valueMap.containsKey(token))
			{
				hitTypeList = valueMap.get(token);
				for (ItemType itemType : hitTypeList)
				{
					tmpResult = itemType.getName();

					if (tmpResult != null)
					{
						ScoreHit hit = hitMap.get(tmpResult);
						if (hit != null)
						{
							hit.setScore(hit.getScore() + 1);
						}
						else
						{
							hit = new ScoreHit();
							hit.setValue(tmpResult);
							hit.setScore(1);
							hitMap.put(value, hit);
						}
					}
				}
			}
		}

		List<ScoreHit> hitList = new ArrayList<ScoreHit>(hitMap.values());
		Collections.sort(hitList, new ScoreComparator());

		return hitList;
	}

	protected List<ScoreHit> resolveHits(String featureName, String value, int maxGroupSize)
	{
		String[] tokens = value.split(" ");

		Map<String, ScoreHit> hitMap = new HashMap<String, ScoreHit>();

		for (int index = maxGroupSize; index > 0; index--)
		{
			loopTokens(Arrays.asList(tokens), index, hitMap);
		}

		List<ScoreHit> hitList = new ArrayList<ScoreHit>(hitMap.values());
		Collections.sort(hitList, new ScoreComparator());

		return hitList;
	}

	private void loopTokens(List<String> aTokens, int aGroupSize, Map<String, ScoreHit> scoreList)
	{
		String aResult = null;

		if (aTokens.size() >= aGroupSize)
		{
			for (int anIndex = 0; anIndex < aTokens.size() - (aGroupSize - 1); anIndex++)
			{
				List<String> tokensUnderAnalysis = new ArrayList<String>();

				tokensUnderAnalysis.add(aTokens.get(anIndex));

				for (int aGroupIndex = 1; aGroupIndex < aGroupSize; aGroupIndex++)
				{
					tokensUnderAnalysis.add(aTokens.get(anIndex + aGroupIndex));
				}

				StringBuffer aString = new StringBuffer();

				for (String string : tokensUnderAnalysis)
				{
					aString.append(string);
				}

				aResult = recognize(aString.toString());

				if (isRecognized(aResult))
				{
					ScoreHit hit = scoreList.get(aResult);
					if (hit != null)
					{
						hit.setScore(hit.getScore() + 1);
					}
					else
					{
						Double score = (aGroupSize * 5.0) + (Math.pow(2, -anIndex) * 100) + aString.length() * 2;
						ScoreHit scoreHit = new ScoreHit();
						scoreHit.setScore(score);
						scoreHit.setValue(aResult);
						scoreList.put(aResult, scoreHit);
					}

				}
			}
		}

		// return aResult;
	}

	private boolean isRecognized(String value)
	{
		if (StringUtils.isNotBlank(value) && !value.equalsIgnoreCase(DEFAULT_VALUE))
		{
			return true;
		}
		return false;
	}

	public abstract String recognize(String value);

	public abstract String resolve(String sentence);

	public abstract String normalize(String str);
}
