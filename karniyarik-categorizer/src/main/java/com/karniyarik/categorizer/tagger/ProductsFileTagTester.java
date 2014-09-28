package com.karniyarik.categorizer.tagger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.categorizer.CategoryResult;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.util.JSONUtil;

public class ProductsFileTagTester
{
	public ProductsFileTagTester()
	{
		try
		{
			AutomaticTagger tagger= AutomaticTagger.getInstance();
			int maxcount = 500;
			int count = 0;
			StringBuffer buff = new StringBuffer();
			String productsDirectory = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig().getProductsDirectory();
			File productsDir = new File(productsDirectory);
			for(File productFile: productsDir.listFiles())
			{
				if(count >= maxcount)
				{
					break;
				}
				System.out.println("Processing " + productFile.getName());
				if(productFile.getName().contains("nadirkitap"))
				{
					continue;
				}
				List<Product> products = JSONUtil.parseFile(productFile, Product.class);
				
				int siteProductCount = 0;
				int maxSiteProductCount = 10;
				
				for(Product product: products)
				{
					if(siteProductCount >= maxSiteProductCount)
					{
						break;
					}
					if(product.getCategory().equalsIgnoreCase("araba"))
					{
						break;
					}
					if(StringUtils.isBlank(product.getBreadcrumb()))
					{
						siteProductCount++;
						count++;
						List<CategoryResult> resolved = tagger.resolve(product.getName(), product.getBrand(), "");

						if(resolved.size() > 0)
						{
							int end = 3;
							
							if(end > resolved.size())
							{
								end = resolved.size();
							}

							List<CategoryResult> subList = resolved.subList(0, end);
							StringBuffer lineOut = new StringBuffer();
							lineOut.append("\t");
							lineOut.append(product.getName());
							lineOut.append("\t");
							lineOut.append(product.getBrand());
							lineOut.append("\t");
							for(CategoryResult result: subList)
							{
								lineOut.append(result.getId());
								lineOut.append("\t");
							}				
							lineOut.append("\n");
							System.out.print(lineOut);
							buff.append(lineOut);
						}
					}
				}				
			}
			
			CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
			File file = new File(categorizerConfig.getModelPath() + "/testresults2.csv");
			FileOutputStream os = new FileOutputStream(file);
			IOUtils.write(buff, os);
			os.close();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		new ProductsFileTagTester();
	}
}
