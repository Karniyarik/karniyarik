package com.karniyarik.categorizer.io;

import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.karniyarik.categorizer.vo.Category;
import com.karniyarik.categorizer.vo.Keyword;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.exception.KarniyarikBaseException;
import com.karniyarik.common.util.StreamUtil;

public class CategoryReader
{
	private CategorizerConfig	mConfig	= null;

	public CategoryReader(CategorizerConfig aConfig)
	{
		mConfig = aConfig;
	}

	public Category read(String aFileName)
	{
		InputStream aFile = StreamUtil.getStream(aFileName);

		try
		{
			if (aFile != null)
			{
				DocumentBuilderFactory aDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder aDocumentBuilder = aDocumentBuilderFactory.newDocumentBuilder();
				Document aDocument = aDocumentBuilder.parse(aFile);
				aDocument.getDocumentElement().normalize();

				Node aNode = aDocument.getFirstChild();
				Category aCategory = readCategory(aNode);

				return aCategory;
			}
		}
		catch (Throwable e)
		{
			throw new KarniyarikBaseException(e);
		}

		return null;
	}

	private Category readCategory(Node aParentNode)
	{
		Category aCategory = new Category();

		try
		{
			aCategory.setName(aParentNode.getAttributes().getNamedItem(CategoryIOUtils.TAG_NAME).getNodeValue());
			aCategory.setID(aParentNode.getAttributes().getNamedItem(CategoryIOUtils.TAG_ID).getNodeValue());
			
			if(aParentNode.getAttributes().getNamedItem(CategoryIOUtils.TAG_IC) != null)
			{
				String anInstanceCount = aParentNode.getAttributes().getNamedItem(CategoryIOUtils.TAG_IC).getNodeValue();
				if(StringUtils.isNotBlank(anInstanceCount))
				{
					aCategory.setInstanceCount(Integer.parseInt(anInstanceCount));
				}				
			}
			
			Node aKeywordNode = aParentNode.getAttributes().getNamedItem(CategoryIOUtils.TAG_KEYWORD);
			Node aBrandNode = aParentNode.getAttributes().getNamedItem(CategoryIOUtils.TAG_BRAND);

			addKeywords(aCategory, aKeywordNode);
			addCommaSeperatedString(aCategory.getBrands(), aBrandNode);

			NodeList aNodeList = aParentNode.getChildNodes();

			for (int anIndex = 0; anIndex < aNodeList.getLength(); anIndex++)
			{
				Node aNode = aNodeList.item(anIndex);

				if (aNode.getNodeType() == Node.ELEMENT_NODE)
				{
					aCategory.addChild(readCategory(aNode));
				}
			}
		}
		catch (Throwable e)
		{
			throw new KarniyarikBaseException(e);
		}

		return aCategory;
	}

	private void addCommaSeperatedString(List<String> aList, Node aNode)
	{
		if (aNode != null)
		{
			String aStrings = aNode.getNodeValue();
			if (StringUtils.isNotBlank(aStrings))
			{
				String[] aStrArr = aStrings.split(CategoryIOUtils.DEFAULT_DELIMITER);
				for (String aStr : aStrArr)
				{
					aList.add(aStr.trim());
				}
			}
		}
	}

	private void addKeywords(Category aCategory, Node aNode) throws ParseException
	{
		if (aNode != null)
		{
			String aStrings = aNode.getNodeValue();

			Keyword aKeyword = null;

			if (StringUtils.isNotBlank(aStrings))
			{
				String[] aKeyArr = aStrings.split(CategoryIOUtils.DEFAULT_DELIMITER);
				String[] aStrArr = null;

				for (String aKey : aKeyArr)
				{
					aKeyword = new Keyword();
					aStrArr = aKey.trim().split(CategoryIOUtils.KEYWORD_DELIMITER);
					aKeyword.setValue(aStrArr[0]);

					if (aStrArr.length > 1 && StringUtils.isNotBlank(aStrArr[1]))
					{
						aKeyword.setBoost(CategoryIOUtils.mFormat.parse(aStrArr[1]).floatValue());						
					}
					else
					{
						aKeyword.setBoost(mConfig.getCategoryKeywordDefaultBoost());
					}
					
					if (aStrArr.length > 2 && StringUtils.isNotBlank(aStrArr[2]))
					{
						aKeyword.setFreq(Integer.valueOf(aStrArr[2]));						
					}
					else
					{
						aKeyword.setFreq(1);
					}

					if (aStrArr.length > 3 && StringUtils.isNotBlank(aStrArr[3]))
					{
						aKeyword.setNegativeFreq(Integer.valueOf(aStrArr[3]));
					}
					else
					{
						aKeyword.setNegativeFreq(0);
					}

					aCategory.addKeyword(aKeyword);
				}
			}
		}
	}
}
