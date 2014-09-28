package com.karniyarik.categorizer.tagger;

import java.util.List;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

public class ZemberekFactory
{
	public Zemberek createZemberek()
	{
		Zemberek zemberek = new com.karniyarik.ir.analyzer.ZemberekFactory().createZemberek();
		addNewWords(zemberek);
		return zemberek;
	}
	
	private void addNewWords(Zemberek zemberek)
	{
		List<String> newWords = new NewWordsCreator().getNewWords();		
		for (String kokStr : newWords)
		{
			Kok kok = new Kok(kokStr, KelimeTipi.ISIM);
			zemberek.dilBilgisi().kokler().ekle(kok);
		}
	}
}
