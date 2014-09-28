package com.karniyarik.categorizer.io;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.karniyarik.categorizer.vo.Category;
import com.karniyarik.categorizer.vo.Keyword;
import com.karniyarik.common.exception.KarniyarikBaseException;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class CategoryWriter
{
	public void write(Category aRootCategory, String aFileName)
	{
		Document aDoc = new DocumentImpl();

		aDoc.appendChild(constructCategoryTree(aRootCategory, aDoc));

		try
		{
			FileOutputStream aStream = new FileOutputStream(aFileName);

			OutputFormat anOutputFormat = new OutputFormat("XML", "UTF-8", true);
			anOutputFormat.setIndenting(true);
			anOutputFormat.setIndent(3);
			XMLSerializer aSerializer = new XMLSerializer(aStream, anOutputFormat);
			aSerializer.asDOMSerializer();
			aSerializer.serialize(aDoc.getDocumentElement());
			aStream.close();
		}
		catch (Exception e)
		{
			throw new KarniyarikBaseException(e);
		}
	}

	private Element constructCategoryTree(Category aCategory, Document aDoc)
	{
		Element anElement = aDoc.createElement(CategoryIOUtils.TAG_CATEGORY);

		String aBrands = getCommaSeparatedString(aCategory.getBrands());
		String aKeywords = getKeywords(aCategory.getKeywords());

		anElement.setAttribute(CategoryIOUtils.TAG_NAME, aCategory.getName());
		anElement.setAttribute(CategoryIOUtils.TAG_ID, aCategory.getID());
		anElement.setAttribute(CategoryIOUtils.TAG_IC, Integer.toString(aCategory.getInstanceCount()));

		anElement.setAttribute(CategoryIOUtils.TAG_BRAND, aBrands);

		anElement.setAttribute(CategoryIOUtils.TAG_KEYWORD, aKeywords);

		for (Category aChildCategory : aCategory.getChildren())
		{
			anElement.appendChild(constructCategoryTree(aChildCategory, aDoc));
		}

		return anElement;
	}

	private String getKeywords(Map<String, Keyword> aKeywords)
	{
		StringBuffer aBuffer = new StringBuffer();

		if (aKeywords.values().size() > 0)
		{
			boolean anAppended = false;

			for (Keyword aKeyword : aKeywords.values())
			{
				aBuffer.append(aKeyword.getValue());
				aBuffer.append(CategoryIOUtils.KEYWORD_DELIMITER);
				aBuffer.append(aKeyword.getBoost());
				aBuffer.append(CategoryIOUtils.KEYWORD_DELIMITER);
				aBuffer.append(aKeyword.getFreq());
				aBuffer.append(CategoryIOUtils.KEYWORD_DELIMITER);
				aBuffer.append(aKeyword.getNegativeFreq());
				aBuffer.append(CategoryIOUtils.DEFAULT_DELIMITER);
			}

			if (anAppended)
			{
				aBuffer.setCharAt(aBuffer.length() - 1, ' ');
			}
		}

		return aBuffer.toString();
	}

	private String getCommaSeparatedString(List<String> aList)
	{
		StringBuffer aResult = new StringBuffer();

		if (aList.size() > 0)
		{
			boolean anAppended = false;

			for (Object anObject : aList)
			{
				if (anObject instanceof String && StringUtils.isNotBlank(anObject.toString()))
				{
					aResult.append(((String) anObject).trim());
					aResult.append(CategoryIOUtils.DEFAULT_DELIMITER);
					anAppended = true;
				}
			}

			if (anAppended)
			{
				aResult.setCharAt(aResult.length() - 1, ' ');
			}
		}

		return aResult.toString().trim();
	}
}
