package com.karniyarik.categorizer.prep;

import java.util.HashMap;
import java.util.Map;

import com.karniyarik.categorizer.io.CategoryIO;
import com.karniyarik.categorizer.io.DBIO;
import com.karniyarik.categorizer.io.ProductIterator;
import com.karniyarik.categorizer.io.TrainingSetIO;
import com.karniyarik.categorizer.util.CategoryStore;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.categorizer.xml.category.CategoryType;
import com.karniyarik.categorizer.xml.category.RootType;
import com.karniyarik.categorizer.xml.trainset.ProductType;
import com.karniyarik.categorizer.xml.trainset.SetType;
import com.karniyarik.common.vo.Product;

public class SiteTrainingSetConstructor
{
	private Map<String, SetType> map = new HashMap<String, SetType>();
	
	public SiteTrainingSetConstructor()
	{
	}
	
	public void construct(String sitename, String delimiter)
	{
		RootType siteCatRoot = new CategoryIO().read(sitename);
		com.karniyarik.categorizer.xml.trainset.RootType trainSetRoot = new com.karniyarik.categorizer.xml.trainset.RootType();
		trainSetRoot.setName(sitename);
		
		CategoryStore categoryStore = new CategoryStore();
		categoryStore.constructCategoryMap(siteCatRoot.getCategory());
		
		ProductIterator pIterator = DBIO.getReader().iterate(sitename);
		Product product = null;
		
		while ((product = pIterator.next()) != null)
		{
			String breadcrumb = product.getBreadcrumb();
			breadcrumb = categoryStore.normalize(breadcrumb, delimiter);
			CategoryType category = categoryStore.getCategoryByRealPath(breadcrumb);
			
			if(category != null)
			{
				ProductType productType = new ProductType();
				productType.setBrand(product.getBrand());
				productType.setName(product.getName());						
				productType.setPrice(product.getPrice());
				SetType setType = map.get(category.getId());
				if(setType == null)
				{
					setType = new SetType();
					setType.setBreadcrumb(category.getRealPath());
					setType.setCatid(category.getId());
					trainSetRoot.getSet().add(setType);
					map.put(category.getId(), setType);
				}
				setType.getProduct().add(productType);
			}
		}
		
		new TrainingSetIO().write(trainSetRoot, sitename);

		pIterator.close();
	}
	
	public static void main(String[] args)
	{
		new SiteTrainingSetConstructor().construct(Constants.activeSite, Constants.getDelimiter());
	}
}
