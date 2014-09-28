package com.karniyarik.categorizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.categorizer.io.CategoryIO;
import com.karniyarik.categorizer.lingpipe.LingpipeClassifier;
import com.karniyarik.categorizer.lucene.LuceneClassifier;
import com.karniyarik.categorizer.price.PriceClassifier;
import com.karniyarik.categorizer.util.CategoryStore;
import com.karniyarik.categorizer.xml.category.CategoryType;
import com.karniyarik.categorizer.xml.category.RootType;

public class ProductClassifier
{
	private static ProductClassifier	instance		= null;
	private LingpipeClassifier			ngramClassifier	= null;
	private LuceneClassifier			tfidfClassifier	= null;
	private PriceClassifier				priceClassifier	= null;
	private CategoryStore				categoryStore	= null;
	private RootType					categoryRoot	= null;
	private static final String			DEFAULT_CAT		= "Diğer";
	
	private ProductClassifier(boolean initModels)
	{
		categoryRoot = new CategoryIO().read("karniyarik");
		categoryStore = new CategoryStore();
		categoryStore.constructCategoryMap(categoryRoot.getCategory());
		
		if(initModels)
		{
			ngramClassifier = new LingpipeClassifier();
			tfidfClassifier = new LuceneClassifier();
			priceClassifier = new PriceClassifier();			
		}
	}

	public static ProductClassifier getInstance(boolean initModels)
	{
		if (instance == null)
		{
			instance = new ProductClassifier(initModels);
		}

		return instance;
	}

	public synchronized String resolveCategoryId(String name, String brand, String breadcrumb, double price)
	{
		String result = DEFAULT_CAT;
		CategoryType category = findCategory(name, brand, breadcrumb, price);
		if(category != null)
		{
			result= category.getId();
		}
		
		return result;
	}
	
	private synchronized CategoryType findCategory(String name, String brand, String breadcrumb, double price)
	{
		List<CategoryResult> ngram = ngramClassifier.resolve(name, brand, breadcrumb);
		List<CategoryResult> tfidf = tfidfClassifier.resolve(name, brand, breadcrumb);
		List<List<CategoryResult>> list = new ArrayList<List<CategoryResult>>();
		list.add(ngram);
		list.add(tfidf);

		List<CategoryResult> finalList = mergeResults(list);
		finalList = priceClassifier.scorePrices(finalList, price);
		Collections.sort(finalList);
		
		if (finalList != null && finalList.size() > 0 && finalList.get(0).getScore() > 2)
		{
			return categoryStore.getCategory(finalList.get(0).getId());
		}
		
		return null;
	}
	
	private List<CategoryResult> mergeResults(List<List<CategoryResult>> resultList)
	{
		Map<String, CategoryResult> map = new HashMap<String, CategoryResult>();
		
		for(List<CategoryResult> list: resultList)
		{
			for(CategoryResult oldItem: list)
			{
				CategoryResult item = map.get(oldItem.getId());
				if(item == null)
				{
					item = new CategoryResult();
					item.setId(oldItem.getId());
					map.put(item.getId(), item);
				}
				item.setScore(item.getScore() + oldItem.getScore());				
			}
		}
	
		List<CategoryResult> finalResultList = new ArrayList<CategoryResult>(map.values());
		
		return finalResultList;
	}

//	private CategoryType getCategoryById(String id)
//	{
//		return getCategoryStore().getCategory(id);
//	}
//
//	private CategoryType getCategoryByName(String name)
//	{
//		return getCategoryStore().getCategoryBySingleName(name);
//	}

	public String getCategoryName(String id)
	{
		String result = DEFAULT_CAT;
		CategoryType category = getCategoryStore().getCategory(id);
		if(category != null)
		{
			result = category.getName();
		}
		return result;
	}

	public String getCategoryId(String name)
	{
		String result = "";
		CategoryType category = getCategoryStore().getCategoryBySingleName(name);
		if(category != null)
		{
			result = category.getId();
		}
		return result;
	}

	public CategoryStore getCategoryStore()
	{
		return categoryStore;
	}

	public static void main(String[] args) throws Exception
	{
		Object[][] testdata = new Object[][]{
				{"5276 NH","Arçelik", "Buzdolabı", 2400.33},
				{"Temat Kapaklı Sekreter Altlığı Mavi", "Temat", "Sekreterlikler", 4.13},
				{"Gecelik gül desenli ip askılı beyaz - L beden", "Ahu Lingerie", "Gecelikler", 28d}
		};
		
		for(Object[] item: testdata)
		{
			String resolveCategoryId = ProductClassifier.getInstance(true).resolveCategoryId((String)item[0], (String)item[1], (String)item[2], (Double)item[3]);
			
			System.out.println(resolveCategoryId);
			System.out.println(ProductClassifier.getInstance(false).getCategoryName(resolveCategoryId));
			
		}
	}
}
