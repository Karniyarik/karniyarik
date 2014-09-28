package com.karniyarik.categorizer.tagger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.util.StringUtil;

public class TrainingSetConstructor
{
	public TrainingSetConstructor()
	{
		try
		{
			Tagger tagger = new Tagger(true, true);
			
			CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
			File file = new File(categorizerConfig.getModelPath());
			
			if(!file.exists())
			{
				file.mkdirs();
			}
			
			File outfile = new File(categorizerConfig.getModelPath() + "/trainingset.csv");
			FileOutputStream os = new FileOutputStream(outfile);
			String productsDirectory = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig().getProductsDirectory();
			File productsDir = new File(productsDirectory);
			for(File productFile: productsDir.listFiles())
			{
				System.out.println("Processing " + productFile.getName());
				if(productFile.getName().contains("nadirkitap"))
				{
					continue;
				}
				List<Product> products = JSONUtil.parseFile(productFile, Product.class);
				
				for(Product product: products)
				{
					if(product.getCategory().equalsIgnoreCase("araba"))
					{
						break;
					}
					if(StringUtils.isNotBlank(product.getBreadcrumb()))
					{
						StringBuffer buff = new StringBuffer();

						List<String> newTags = tagger.getTags(product.getBreadcrumb());
						buff.append(clean(product.getName()));
						buff.append("\t");
						buff.append(clean(product.getBrand()));
						buff.append("\t");
						buff.append(clean(product.getBreadcrumb()));
						buff.append("\t");
						
						for(String newTag: newTags)
						{
							if(StringUtils.isNotBlank(newTag))
							{
								buff.append(newTag);
								buff.append("\t");
							}
						}
						buff.append("\n");
						IOUtils.write(buff.toString(), os, StringUtil.DEFAULT_ENCODING);
					}
				}				
			}

			os.close();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	public String clean(String string)
	{
		if(string!=null)
		{
			string = StringUtil.removeMultiEmptySpaces(string);
		}
		
		return string;
	}

	public static void main(String[] args)
	{
		new TrainingSetConstructor();
	}
}
