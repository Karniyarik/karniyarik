package com.karniyarik.categorizer.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

import au.id.jericho.lib.html.Element;
import au.id.jericho.lib.html.Source;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class KelkooBrandFetcher
{
	public KelkooBrandFetcher()
	{
		// TODO Auto-generated constructor stub
	}

	public void getBrands() throws Exception
	{
		String[] aCapitalArray = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
				"u", "v", "y", "z", "x", "0" };

		int anID = 0;
		List<KelkooBrand> aBrandList = new ArrayList<KelkooBrand>();

		File file = new File("images");
		if(!file.exists())
		{
			file.mkdir();
			System.out.println(file.getAbsolutePath() + " created");
		}

		
		for (String aCapital : aCapitalArray)
		{
			URL aURL = new URL("http://www.kelkoo.co.uk/bz-" + aCapital + "-brands?currentPage=0&numPerPage=10000");

			URLConnection aOpenConnection = aURL.openConnection();
			aOpenConnection.setConnectTimeout(10000);
			String aContent = IOUtils.toString(aOpenConnection.getInputStream(), "ISO-8859-1");

			Source aSource = new Source(aContent);

			List<Element> aBrands = aSource.findAllElements("class", "brand-name", true);

			KelkooBrand aBrand = null;

			for (Element aBrandTD : aBrands)
			{
				aBrand = new KelkooBrand();
				aBrand.setID(anID++);
				aBrandList.add(aBrand);
				List<Element> anAElement = aBrandTD.findAllElements("a");
				String aName = anAElement.get(0).getTextExtractor().toString().trim();
				aBrand.setName(aName);
				String anImgURLStr = ((Element) anAElement.get(0).findAllElements("img").get(0)).getAttributeValue("src");
				if (!anImgURLStr.contains("/generic-75.gif"))
				{
					URL anImgURL = new URL(anImgURLStr);
					try
					{
						FileOutputStream anOutputStream = new FileOutputStream("images/" + aBrand.getName() + ".png");
						
						InputStream aStream = anImgURL.openStream();
						anOutputStream.write(IOUtils.toByteArray(aStream));
						aStream.close();
						
						System.out.println(aBrand.getID() + " - images/" + aBrand.getName() + ".png written");
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				Element aTRElement = aSource.findEnclosingElement(aBrandTD.getBegin(), "tr");

				List<Element> aCategoryList = ((Element) aTRElement.findAllElements("class", "popcats-per-brand", true).get(0)).findAllElements("a");
				for (Element aCategoryElement : aCategoryList)
				{
					aBrand.getCategoryList().add(aCategoryElement.getTextExtractor().toString().trim());
				}
			}
		}

		writeTofile(aBrandList);
	}

	private void writeTofile(List<KelkooBrand> aBrandList) throws Exception
	{
		Document aDoc = new DocumentImpl();
		org.w3c.dom.Element aRootElement = aDoc.createElement("brands");
		aDoc.appendChild(aRootElement);

		org.w3c.dom.Element aBrandElement = null;
		org.w3c.dom.Element aCategoriesElement = null;
		org.w3c.dom.Element aCategoriyElement = null;

		for (KelkooBrand aBrand : aBrandList)
		{
			aBrandElement = aDoc.createElement("brand");
			aBrandElement.setAttribute("id", Integer.toString(aBrand.getID()));
			aBrandElement.setAttribute("name", aBrand.getName());
			aCategoriesElement = aDoc.createElement("categories");
			aBrandElement.appendChild(aCategoriesElement);
			for (String aCategory : aBrand.getCategoryList())
			{
				aCategoriyElement = aDoc.createElement("category");
				aCategoriyElement.setAttribute("name", aCategory);
				aCategoriesElement.appendChild(aCategoriyElement);
			}

			aRootElement.appendChild(aBrandElement);
		}

		FileOutputStream aStream = new FileOutputStream("kelkoo.brands.xml");

		OutputFormat anOutputFormat = new OutputFormat("XML", "UTF-8", true);
		anOutputFormat.setIndenting(true);
		anOutputFormat.setIndent(3);
		XMLSerializer aSerializer = new XMLSerializer(aStream, anOutputFormat);
		aSerializer.asDOMSerializer();
		aSerializer.serialize(aDoc.getDocumentElement());
		aStream.close();
	}

	public static void main(String[] args) throws Exception
	{
		new KelkooBrandFetcher().getBrands();
	}
}
