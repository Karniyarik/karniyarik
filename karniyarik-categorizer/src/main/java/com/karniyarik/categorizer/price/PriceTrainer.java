package com.karniyarik.categorizer.price;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import com.karniyarik.categorizer.io.TrainingSetIO;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.categorizer.xml.trainset.ProductType;
import com.karniyarik.categorizer.xml.trainset.RootType;
import com.karniyarik.categorizer.xml.trainset.SetType;
import com.karniyarik.common.util.StreamUtil;

public class PriceTrainer
{
	private Map<String, SetType>		map			= new HashMap<String, SetType>();
	private PriceMap 					priceMap	= new PriceMap();

	public PriceTrainer()
	{
		try
		{
			RootType rootType = new TrainingSetIO().read("karniyarik");

			for (SetType setType : rootType.getSet())
			{
				map.put(setType.getCatid(), setType);
			}

			System.out.println("Training");
			List<String> categoryList = new ArrayList<String>(map.keySet());

			for (String category : categoryList)
			{
				SetType setType = map.get(category);

				for (ProductType productType : setType.getProduct())
				{
					CategoryPrice catPrice = priceMap.getCatPriceMap().get(category);
					
					if (catPrice == null)
					{
						catPrice = new CategoryPrice();
						catPrice.setStat(new SummaryStatistics());
						priceMap.getCatPriceMap().put(category, catPrice);
					}
					
					catPrice.getStat().addValue(productType.getPrice());
				}
			}
			
			for(CategoryPrice catPrice: priceMap.getCatPriceMap().values())
			{
				catPrice.setValues();
			}

			File file = StreamUtil.getFile(Constants.price);
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(file));
		    out.writeObject(priceMap);
		    out.close();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}


	public static void main(String[] args)
	{
		new PriceTrainer();
	}
}
