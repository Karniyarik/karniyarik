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

public class BrandLogoFetcher
{
	
	public BrandLogoFetcher()
	{
		// TODO Auto-generated constructor stub
	}

	public void getBrands() throws Exception
	{
		String[] aCapitalArray = new String[] { "1", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "y", "z", "x" };

		int anID = 0;
		List<KelkooBrand> aBrandList = new ArrayList<KelkooBrand>();

		File file = new File("brandsoftheworld");
		if (!file.exists())
		{
			file.mkdir();
			System.out.println(file.getAbsolutePath() + " created");
		}

		for (String aCapital : aCapitalArray)
		{
			file = new File("brandsoftheworld/" + aCapital);
			if (!file.exists())
			{
				file.mkdir();
				System.out.println(file.getAbsolutePath() + " created");
			}

			URL aURL = new URL("http://www.brandsoftheworld.com/catalogue/" + aCapital.toUpperCase());

			URLConnection aOpenConnection = aURL.openConnection();
			aOpenConnection.setConnectTimeout(10000);
			String aContent = IOUtils.toString(aOpenConnection.getInputStream(), "UTF-8");

			Source aSource = new Source(aContent);

			List<Element> last = aSource.findAllElements("class", "last", true);
			List<Element> lastA = last.get(0).findAllElements("a");
			String lastStr = ((Element)lastA.get(0)).getAttributeValue("href");
			String lastIndex = lastStr.split("-")[1].split("\\.")[0];
			int lastInt = Integer.parseInt(lastIndex);
			
			for(int index = 1; index <= lastInt; index++)
			{
				Thread.sleep(500);
				aURL = new URL("http://www.brandsoftheworld.com/catalogue/" + aCapital.toUpperCase() + "/index-"+ index + ".html");
				aOpenConnection = aURL.openConnection();
				aOpenConnection.setConnectTimeout(10000);
				aContent = IOUtils.toString(aOpenConnection.getInputStream(), "UTF-8");
				aSource = new Source(aContent);
				
				KelkooBrand aBrand = null;
				List<Element> aBrands = aSource.findAllElements("class", "row", true);
				for (Element aBrandTD : aBrands)
				{
					try
					{
						aBrand = new KelkooBrand();
						aBrand.setID(anID++);
						aBrandList.add(aBrand);
						List<Element> anAElement = aBrandTD.findAllElements("a");
						String aName = anAElement.get(0).getTextExtractor().toString().trim();
						aBrand.setName(aName);
						String aLogoPage = ((Element) anAElement.get(0).findAllElements("a").get(0)).getAttributeValue("href");
						aLogoPage = "http://www.brandsoftheworld.com" + aLogoPage;

						Thread.sleep(300);
						URL logoURL = new URL(aLogoPage);
						URLConnection aLogoOpenConnection = logoURL.openConnection();
						aLogoOpenConnection.setConnectTimeout(10000);
						String aLogoContent = IOUtils.toString(aLogoOpenConnection.getInputStream(), "UTF-8");
						Source aLogoSource = new Source(aLogoContent);
						List<Element> details = aLogoSource.findAllElements("class", "image", true);
						String imageURL = ((Element) details.get(0).findAllElements("img").get(0)).getAttributeValue("src");
						imageURL = "http://www.brandsoftheworld.com" + imageURL;

						URL anImgURL = new URL(imageURL);
						FileOutputStream anOutputStream = new FileOutputStream("brandsoftheworld/" + aCapital + "/"+ aBrand.getName() + ".gif");
						InputStream aStream = anImgURL.openStream();
						anOutputStream.write(IOUtils.toByteArray(aStream));
						aStream.close();
						if(anID % 100 == 0)
						{
							System.out.println(aBrand.getID() + " - brandsoftheworld/" + aBrand.getName() + ".png written");	
						}
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
			
			writeTofile(aBrandList, aCapital);
			
			aBrandList.clear();
		}
	}

	private void writeTofile(List<KelkooBrand> aBrandList, String capital) throws Exception
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

		FileOutputStream aStream = new FileOutputStream("all.brands."+capital+"xml");

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
		new BrandLogoFetcher().getBrands();
	}
}
