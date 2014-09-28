package com.karniyarik.categorizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.categorizer.index.CategoryIndex;
import com.karniyarik.categorizer.index.CategoryIndexHit;
import com.karniyarik.categorizer.vo.Category;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.vo.Product;

public class ProductCategorizer
{
	private CategoryIndex			mIndex		= null;
	private CategoryTree			mTree		= null;
	private CategorizerConfig		mConfig		= null;
	private Map<String, ICategoryHandler> 	instancePool = null;
	
	private static ProductCategorizer instance = null;
	
	private ProductCategorizer()
	{
		mConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		mTree = new CategoryTree(mConfig);
		instancePool = new HashMap<String, ICategoryHandler>();		
		populateInstancePool();
	}
	
	@SuppressWarnings("unchecked")
	private void populateInstancePool()
	{
		String providerClassName = null;
		Class providerClass = null;
		for(String categoryName : mConfig.getCategoryConfigMap().keySet())
		{
			try
			{
				providerClassName = mConfig.getCategoryConfig(categoryName).getProviderClass();
				
				if (StringUtils.isNotBlank(providerClassName)) {
					providerClass =  Class.forName(providerClassName);
					instancePool.put(categoryName, (ICategoryHandler) providerClass.newInstance());
				}
			}
			catch (Throwable e)
			{
				//do nothing
				e.printStackTrace();
			}
		}
	}

	public static ProductCategorizer getInstance()
	{
		if(instance == null)
		{
			instance = new ProductCategorizer();
		}
		
		return instance;
	}
	
	public ICategoryHandler getCategoryHandler(String categoryName)
	{
		return instancePool.get(categoryName);
	}
	
	public void read()
	{
		mTree.read("karniyarik.categories.xml");
	}

	public void write()
	{
		mTree.write("karniyarik.categories.xml");
	}

	public CategoryTree getTree()
	{
		return mTree;
	}

	public String getCategory(String sitename)
	{
		SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(sitename);
		return siteConfig.getCategory();
	}
}