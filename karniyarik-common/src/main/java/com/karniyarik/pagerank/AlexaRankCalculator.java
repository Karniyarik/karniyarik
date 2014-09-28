package com.karniyarik.pagerank;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class AlexaRankCalculator
{
	public static int fetch(String aSiteURL)
	{
		int aResult = 0;

		String aFetchURL = "http://www.alexa.com/xml/dad?url=" + aSiteURL;

		try
		{
			URL aURL = new URL(aFetchURL);

			InputStream aStream = aURL.openStream();

			if (aStream != null)
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				// Use the factory to create a builder
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(aStream);

				NodeList aNodeList = doc.getElementsByTagName("POPULARITY");

				aResult = Integer.valueOf(aNodeList.item(0).getAttributes()
						.getNamedItem("TEXT").getNodeValue());
			}

			aStream.close();
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Cannot fetch alexa rank");
		}

		return aResult;
	}
	
	public static void main(String[] args)
	{
		String[] aList = new String[]{
				"www.hepsiburada.com",
				"www.webdenal.com",
				"www.adresimegelsin.com",
				"www.hipernex.com",
				"www.deveyuku.com",
				"www.doktorteknoloji.com",
				"www.hitbox.com.tr",
				"www.nebbu.com",
				"www.netsiparis.com",
				"www.ereyon.com.tr",
				"www.estore.com.tr",
				"www.sanalmarketim.com",
				"www.gondolda.com",
				"www.e-bebek.com",
				"www.alisveris.com",
				"www.vatancomputer.com",
				"www.mavibilgisayar.com.tr",
				"www.teknosa.com.tr",
				"www.genpatech.com",
				"www.cabukalisveris.com",
				"www.garantialisveris.com",
				"www.ekopasaj.com",
				"www.ekimmarket.com",
				"www.bilgisayarmarket.com",
				"www.elektrikdeposu.com",
				"www.enuygunuz.biz",
				"www.sanalmagaza.com.tr",
				"www.BTdepo.com",
				"www.e-bebek.com",
				"www.hemensatinal.com"
		};
		
		for(String aStr: aList)
		{
			System.out.println(aStr + "-\t" + fetch(aStr));
		}
	}
}
