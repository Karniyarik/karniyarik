package com.karniyarik.categorizer.prep;

import java.util.HashMap;
import java.util.Map;

import com.karniyarik.categorizer.io.MappingIO;
import com.karniyarik.categorizer.io.TrainingSetIO;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.categorizer.xml.mapping.MappingType;
import com.karniyarik.categorizer.xml.trainset.ProductType;
import com.karniyarik.categorizer.xml.trainset.RootType;
import com.karniyarik.categorizer.xml.trainset.SetType;

public class KarniyarikTrainingSetConstructor
{
	private Map<String, String> siteToKarniyarikCatIdMap = new HashMap<String, String>();
	private Map<String, SetType> karniyarikSetMap = new HashMap<String, SetType>();
	
	public KarniyarikTrainingSetConstructor()
	{
	}
	
	public void construct(String[] sitenames)
	{	
		RootType karniyarikRoot = new RootType();
		com.karniyarik.categorizer.xml.mapping.RootType mappingRoot = null;
		RootType siteRoot = null;
		
		for(String sitename: sitenames)
		{
			mappingRoot = new MappingIO().read(sitename);
			fillMap(mappingRoot);
			siteRoot = new TrainingSetIO().read(sitename);
			for(SetType setType: siteRoot.getSet())
			{
				String karniyarikCatId = siteToKarniyarikCatIdMap.get(setType.getCatid());
				if(karniyarikCatId != null)
				{
					SetType karniyarikSetType = karniyarikSetMap.get(karniyarikCatId);
					
					if(karniyarikSetType == null)
					{
						karniyarikSetType = new SetType();
						karniyarikSetType.setCatid(karniyarikCatId);
						karniyarikRoot.getSet().add(karniyarikSetType);
						karniyarikSetMap.put(karniyarikCatId, karniyarikSetType);
					}
					
					for(ProductType productType: setType.getProduct())
					{
						productType.setBreadcrumb(setType.getBreadcrumb());
						karniyarikSetType.getProduct().add(productType);
					}
				}
			}			
		}
		
		new TrainingSetIO().write(karniyarikRoot, "karniyarik");
	}
	
	private void fillMap(com.karniyarik.categorizer.xml.mapping.RootType mappingRoot)
	{
		siteToKarniyarikCatIdMap.clear();
		for(MappingType type: mappingRoot.getMapping())
		{
			siteToKarniyarikCatIdMap.put(type.getFrom(), type.getTo());
		}
	}
	
	public static void main(String[] args)
	{
		new KarniyarikTrainingSetConstructor().construct(new String[]{Constants.activeSite});
	}
}
