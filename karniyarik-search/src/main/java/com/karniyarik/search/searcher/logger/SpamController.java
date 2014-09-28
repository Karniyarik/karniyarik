package com.karniyarik.search.searcher.logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;

public class SpamController
{
	private List<String>			spamWordList	= null;
	private static SpamController	INSTANCE		= null;

	public SpamController()
	{
		try
		{
			InputStream aStream = StreamUtil.getStream("conf/dictionary/spam.txt");

			BufferedReader aReader = new BufferedReader(new InputStreamReader(aStream));

			spamWordList = new ArrayList<String>();

			String aLine = null;

			while (true)
			{
				aLine = aReader.readLine();

				if (aLine == null)
				{
					break;
				}

				spamWordList.add(StringUtil.convertTurkishCharacter(aLine.trim()).toLowerCase(Locale.ENGLISH));
			}

			aReader.close();
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Cannot find spam file", e);
		}
	}

	public static synchronized SpamController getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new SpamController();
		}

		return INSTANCE;
	}

	public boolean isSpam(String query)
	{
		boolean result = false;

		if (StringUtils.isNotBlank(query))
		{
			query = StringUtil.convertTurkishCharacter(query).replaceAll("\\W", "").toLowerCase(Locale.ENGLISH);
			String queryWords[] = query.split("\\s");

			for (String spamWord : spamWordList)
			{
				for (String qWord : queryWords)
				{

					if (qWord.equals(spamWord))
					{
						result = true;
						break;
					}
				}
			}
		}

		return result;
	}

	public List<String> getSpamWordList()
	{
		return spamWordList;
	}

}
