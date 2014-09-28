package com.karniyarik.categorizer.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

import au.id.jericho.lib.html.Element;
import au.id.jericho.lib.html.Source;

import com.karniyarik.categorizer.vo.Category;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class KelkooCategoryFetcher
{
	public KelkooCategoryFetcher() throws Exception
	{
		Category aRoot = extractCategories();

		writeTofile(aRoot);
	}

	private void writeTofile(Category aRoot) throws Exception
	{
		Document aDoc = new DocumentImpl();
		org.w3c.dom.Element aRootElement = aDoc.createElement("category");
		aRootElement.setAttribute("name", aRoot.getName());
		aRootElement.setAttribute("id", aRoot.getID());
		addCategoryToDocument(aRoot, aRootElement, aDoc);
		aDoc.appendChild(aRootElement);
		FileOutputStream aStream = new FileOutputStream("kelkoo.categories.xml");
		OutputFormat anOutputFormat = new OutputFormat("XML", "UTF-8", true);
		anOutputFormat.setIndenting(true);
		anOutputFormat.setIndent(1);
		XMLSerializer aSerializer = new XMLSerializer(aStream, anOutputFormat);
		aSerializer.asDOMSerializer();
		aSerializer.serialize(aDoc.getDocumentElement());
		aStream.close();
	}

	private void addCategoryToDocument(Category aParentCategory, org.w3c.dom.Element aParent, Document aDoc)
	{
		for (Category aChildCat : aParentCategory.getChildren())
		{
			org.w3c.dom.Element aCategoryElement = aDoc.createElement("category");
			aCategoryElement.setAttribute("name", aChildCat.getName());
			aCategoryElement.setAttribute("id", aChildCat.getID());
			aParent.appendChild(aCategoryElement);
			addCategoryToDocument(aChildCat, aCategoryElement, aDoc);
		}
	}

	private Category extractCategories() throws MalformedURLException, IOException
	{
		int anID = 0;

		URL aURL = new URL("http://www.kelkoo.co.uk/sm_site-map.html");
		String aContent = IOUtils.toString(aURL.openConnection().getInputStream(), "ISO-8859-1");

		Source aSource = new Source(aContent);

		int aStart = aSource.getParseText().indexOf("xoxo", 0);

		Category aRootCategory = new Category();
		aRootCategory.setName("Root");
		// DIKKAT !! aRootCategory.setID(anID++);
		String aCategoryName = null;

		Category aCategory1 = null;
		Category aCategory2 = null;
		Category aCategory3 = null;

		Element anElement = aSource.findEnclosingElement(aStart);
		List<Element> aCategory1ElementList = anElement.getChildElements();
		Element aCategory1Element = null;
		List<Element> aCategory2ElementList = null;
		List<Element> aCategory3ElementList = null;

		int b = 5;

		for (Element aFirstCat : aCategory1ElementList)
		{
			List<Element> aHeadList = aFirstCat.findAllElements("class", "section-head", true);

			if (aHeadList.size() > 0)
			{
				aCategory1Element = aHeadList.get(0);
				aCategoryName = ((Element) aCategory1Element.findAllElements("h3").get(0)).getTextExtractor().toString().trim();
				aCategory1 = new Category();
				aCategory1.setName(aCategoryName);
				// DIKKAT aCategory1.setID(anID++);
				aRootCategory.getChildren().add(aCategory1);

				if (aFirstCat.getChildElements().size() > 1)
				{
					aCategory2ElementList = ((Element) aFirstCat.getChildElements().get(1)).getChildElements();

					for (Element aCategory2Element : aCategory2ElementList)
					{
						aCategoryName = ((Element) aCategory2Element.findAllElements("h4").get(0)).getTextExtractor().toString().trim();
						aCategory2 = new Category();
						aCategory2.setName(aCategoryName);
						// DIKKAT aCategory2.setID(anID++);
						aCategory1.getChildren().add(aCategory2);

						if (aCategory2Element.getChildElements().size() > 1)
						{
							for (int anIndex = 1; anIndex < aCategory2Element.getChildElements().size(); anIndex++)
							{
								aCategory3ElementList = ((Element) aCategory2Element.getChildElements().get(anIndex)).getChildElements();

								for (Element aCategory3Element : aCategory3ElementList)
								{
									aCategoryName = aCategory3Element.getTextExtractor().toString().trim();
									aCategory3 = new Category();
									aCategory3.setName(aCategoryName);
									// DIKKAT aCategory3.setID(anID++);
									aCategory2.getChildren().add(aCategory3);
								}
							}
						}
					}
				}
			}
		}

		return aRootCategory;
	}

	public static void main(String[] args) throws Exception
	{
		new KelkooCategoryFetcher();
	}
}
