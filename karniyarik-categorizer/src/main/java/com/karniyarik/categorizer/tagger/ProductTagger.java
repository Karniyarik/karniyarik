package com.karniyarik.categorizer.tagger;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.config.system.CategorizerConfig;

public class ProductTagger {
	
	private AutomaticTagger automaticTagger = null;
	private Tagger tagger = null;
	
	private static ProductTagger instance = null;
	
	private ProductTagger() {
		automaticTagger = AutomaticTagger.getInstance();
		tagger = new Tagger(true, true);
	}
	
	public static ProductTagger getInstance() {
		if(instance == null)
		{
			instance = new ProductTagger();
		}
		return instance;
	}
	
	public String getTags(String name, String brand, String breadcrumb, String category)
	{
		List<String> tags = null;
		
		int categoryType = CategorizerConfig.getCategoryType(category);
		if(categoryType == CategorizerConfig.PRODUCT_TYPE)
		{
			if(StringUtils.isNotBlank(breadcrumb))
			{
				tags = tagger.getTags(breadcrumb);	
			}
			
			if(tags == null || tags.size() < 1)
			{
				tags = automaticTagger.resolveTags(name, brand, breadcrumb);
			}
			
			String result = "";
			
			if(tags != null)
			{
				result = StringUtils.join(tags, ", ");	
			}
			
			return result;
		}
		
		return "";
	}
}
