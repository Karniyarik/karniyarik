package com.karniyarik.categorizer.prep;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.karniyarik.categorizer.io.CategoryIO;
import com.karniyarik.categorizer.io.DBIO;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.categorizer.xml.category.CategoryType;
import com.karniyarik.categorizer.xml.category.ObjectFactory;
import com.karniyarik.categorizer.xml.category.RootType;
import com.karniyarik.common.vo.Product;

public class SiteCategoryConstructor
{
	public SiteCategoryConstructor()
	{
	}

	public void construct(String sitename, String delimiter)
	{
		List<Product> productList = DBIO.getReader().readAll(sitename);
		CategoryType categoryRoot = new CategoryType();
		categoryRoot.setId("1");

		RootType rootType = new RootType();
		rootType.setCategory(categoryRoot);
		ObjectFactory objectFactory = new ObjectFactory();

		for (Product product : productList)
		{
			if(product.getBreadcrumb() == null)
			{
				continue;
			}
			
			String[] path = product.getBreadcrumb().split(delimiter);
			CategoryType parentCategory = categoryRoot;
			CategoryType childCategory = null;
			StringBuffer currentPath = new StringBuffer();

			for (String pathElement : path)
			{
				pathElement = pathElement.trim();
				currentPath.append(pathElement);
				currentPath.append(" ## ");
				childCategory = getChildCategory(parentCategory, pathElement);
				if (childCategory == null)
				{
					childCategory = new CategoryType();
					childCategory.setName(pathElement);
					childCategory.setId(parentCategory.getId() + "." + (parentCategory.getContent().size() + 1));
					childCategory.setRealPath(currentPath.toString().trim());
					parentCategory.getContent().add(objectFactory.createCategoryTypeCategory(childCategory));
					childCategory.setInstanceCount(1);
				}
				else
				{
					childCategory.setInstanceCount(childCategory.getInstanceCount()+1);
				}

				parentCategory = childCategory;
			}
		}

		new CategoryIO().write(rootType, sitename);
	}

	private CategoryType getChildCategory(CategoryType parent, String name)
	{
		for (Serializable content : parent.getContent())
		{
			JAXBElement<CategoryType> childCatType = (JAXBElement<CategoryType>) content;

			if (childCatType.getValue().getName().equals(name))
			{
				return childCatType.getValue();
			}
		}

		return null;
	}

	public static void main(String[] args)
	{
		new SiteCategoryConstructor().construct(Constants.activeSite, Constants.getDelimiter());
	}
}
