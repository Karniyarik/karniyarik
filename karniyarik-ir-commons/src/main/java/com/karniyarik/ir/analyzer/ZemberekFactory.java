package com.karniyarik.ir.analyzer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;

public class ZemberekFactory
{

	private static final String	DEFAULT_NEW_WORDS_FILE	= "new_words_for_zemberek.txt";
	
	public Zemberek createZemberek()
	{
		Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
		addNewWords(zemberek, readNewWordsFromFile(DEFAULT_NEW_WORDS_FILE));
		return zemberek;
	}

	private List<String> readNewWordsFromFile(String fileName)
	{

		List<String> result = new ArrayList<String>();

		try
		{
			InputStream is = StreamUtil.getStream(fileName);

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, StringUtil.DEFAULT_ENCODING));

			String line = null;

			while ((line = reader.readLine()) != null)
			{
				result.add(line);
			}

			reader.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException("cannot read new words from file " + fileName);
		}

		return result;
	}

	private void addNewWords(Zemberek zemberek, List<String> newWordList)
	{
		for (String kokStr : newWordList)
		{
			Kok kok = new Kok(kokStr, KelimeTipi.ISIM);
			zemberek.dilBilgisi().kokler().ekle(kok);
		}
	}
}
