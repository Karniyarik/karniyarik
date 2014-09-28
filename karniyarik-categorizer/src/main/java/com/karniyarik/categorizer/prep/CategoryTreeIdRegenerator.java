package com.karniyarik.categorizer.prep;

import java.io.Serializable;

import javax.xml.bind.JAXBElement;

import com.karniyarik.categorizer.io.CategoryIO;
import com.karniyarik.categorizer.xml.category.CategoryType;
import com.karniyarik.categorizer.xml.category.RootType;

public class CategoryTreeIdRegenerator
{
	public CategoryTreeIdRegenerator()
	{
		RootType rootType = new CategoryIO().read("karniyarik");
		CategoryType rootCategory = rootType.getCategory();
		
		rootCategory.setId("1");
		regenerateIds(rootCategory);
		new CategoryIO().write(rootType, "karniyarik1");
	}
	
	private void regenerateIds(CategoryType category)
	{
		int index = 1;
		CategoryType childCategory = null;
		for(Serializable content: category.getContent())
		{
			if (content instanceof JAXBElement<?>)
			{
				childCategory = (CategoryType)((JAXBElement) content).getValue(); 
				childCategory.setId(category.getId() + "." + index);
				childCategory.setInstanceCount(0);
				regenerateIds(childCategory);
				index++;
			}
		}
	}

	public static void main(String[] args)
	{	
		new CategoryTreeIdRegenerator();
	}
}
