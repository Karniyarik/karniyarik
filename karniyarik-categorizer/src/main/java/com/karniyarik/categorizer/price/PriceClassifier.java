package com.karniyarik.categorizer.price;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.List;

import com.karniyarik.categorizer.CategoryResult;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.util.StreamUtil;

public class PriceClassifier
{
	private PriceMap	priceMap	= null;

	public PriceClassifier()
	{
		try
		{
			String filename = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig().getModelPath() + "/price.model";
			File file = StreamUtil.getFile(filename);
			ObjectInput in = new ObjectInputStream(new FileInputStream(file));
			priceMap = (PriceMap) in.readObject();
			in.close();
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public List<CategoryResult> scorePrices(List<CategoryResult> list, double price)
	{
		for(CategoryResult resultItem: list)
		{
			CategoryPrice catPrice = priceMap.getCatPriceMap().get(resultItem.getId());
			double score = getScore(catPrice, price);
			resultItem.setScore(score*Constants.priceFactor + resultItem.getScore());
		}
		
		return list;
	}
	
	private double getScore(CategoryPrice catPrice, double price)
	{
		double min = catPrice.getMin();
		double max = catPrice.getMax();
		double mean = catPrice.getMean();
		double dev = catPrice.getStddev();
		double distToMean = Math.abs(mean-price);
		
		if(dev == 0)
		{
			dev = 0.00000000000001;
		}
		
		double c = 0 ;
		
		if(Math.abs(mean-price) < dev)
		{
			c = 0.8;
		}
		else if(price < min)
		{
			c = 1 - (0.6 * dev)/(mean-min);
		}
		else 
		{
			c = 1 - (0.6 * dev)/(max - mean);
		}
		
		return 10 * (c + ((1-(distToMean/dev)) * (1-c)));
	}
}
