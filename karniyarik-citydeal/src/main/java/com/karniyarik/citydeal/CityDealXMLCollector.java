package com.karniyarik.citydeal;

import java.util.ArrayList;
import java.util.List;

public class CityDealXMLCollector
{
	public CityDealXMLCollector()
	{
		// TODO Auto-generated constructor stub
	}
	
	public List<CityDeal> getDeals()
	{
		List<CityDeal> result = new ArrayList<CityDeal>();
		
		String[] affnames = new String[] {"sehirfirsati","markapon","aktifkampanya", "piriveta","firmanya","grupca","mekanist","firsatkulubu","grupfoni", "delikupon","sehrikeyif", "romantikfirsatlar","guzellikfirsatim", "yakalaco","kacanbbo","ekoloni", "devirfirsatdevri","iyifirsat","firsatbugun","birliktealalim"};
		String[] sourcenames = new String[] {"Şehir fırsatı","Markapon","Aktifkampanya", "Piriveta","Firmanya","Grupca","Mekanist", "Fırsat Kulübü", "Grupfoni", "Delikupon", "Şehri Keyif", "Romantik Fırsatlar", "Güzellik Fırsatım", "Yakala.co","Kaçan Balık Büyük Olur","Ekoloni", "Devir Fırsat Devri","İyi Fırsat","Fırsat Bugün","Birlikte Alalım"};
		int index = 0;
		for(String affname: affnames)
		{
			List<CityDeal> tmp = new CityDealXMLDatafeed().parse("http://feed.gelirortaklari.com/p/download.php?aff=239&source=" + affname, 
					"UTF-8", sourcenames[index], "//urunler/urun", "baslik", "metin", "urun_url", 
					"fiyat", "indirimlifiyat", "birim","sehir", "baslangictarihi", "bitistarihi", "resim_url", 
					"EEE, dd MMM yyyy HH:mm:ss z", null, true, "en", "&amp;source=web");
			
			result.addAll(tmp);
			index++;
		}

		List<CityDeal> tmp = new CityDealXMLDatafeed().parse("http://www.ekozon.com/XMLDeals.aspx", 
				"UTF-8", "ekozon", "//deals/deal", "title", "description", "dealUrl", 
				"realPrice", "dealPrice", null ,"city", "startDate", "endDate", "imageUrl", 
				"MM/dd/yyyy HH:mm:ss aa", null, false, null, "?ref=karniyarik");
		
		result.addAll(tmp);

		return result;
	}
	
	public static void main(String[] args)
	{
		new CityDealXMLCollector().getDeals();
	}
}
