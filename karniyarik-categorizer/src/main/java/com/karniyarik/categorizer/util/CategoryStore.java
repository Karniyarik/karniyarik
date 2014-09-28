package com.karniyarik.categorizer.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import com.karniyarik.categorizer.xml.category.CategoryType;

public class CategoryStore
{
	private Map<String, CategoryType>	categoryIdMap		= null;
	private Map<String, CategoryType>	categoryRealPathMap	= null;
	private Map<String, CategoryType>	categoryNameMap		= null;

	public CategoryStore()
	{
		categoryIdMap = new HashMap<String, CategoryType>();
		categoryRealPathMap = new HashMap<String, CategoryType>();
		categoryNameMap = new HashMap<String, CategoryType>();
	}

	public void constructCategoryMap(CategoryType category)
	{
		if (!categoryIdMap.containsKey(category.getId()))
		{
			categoryIdMap.put(category.getId(), category);
			categoryRealPathMap.put(normalize(category.getRealPath(), "##"), category);
			categoryNameMap.put(removeCommas(category.getName()), category);

			for (Serializable content : category.getContent())
			{
				if (content instanceof JAXBElement<?>)
				{
					constructCategoryMap(((JAXBElement<CategoryType>) content).getValue());
				}
			}
		}
	}

	public CategoryType getCategory(String id)
	{
		return categoryIdMap.get(id);
	}

	public CategoryType getCategoryByRealPath(String name)
	{
		return categoryRealPathMap.get(name);
	}

	public CategoryType getCategoryBySingleName(String name)
	{
		return categoryNameMap.get(removeCommas(name));
	}

	public String removeCommas(String value)
	{
		if (value != null)
		{
			value = value.replaceAll(",", " ");
			value = value.replaceAll("\\s+", "");
		}
		return value;
	}
	
	public String normalize(String path, String delimiter)
	{
		if (path != null)
		{
			path = path.replaceAll(delimiter, "");
			path = path.replaceAll("\\s", "");
		}
		return path;
	}
}
