package com.karniyarik.crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.parser.pojo.Product;

public class BreadcrumbAnalyzer
{
	List<String>	allBreadcrumns		= new LinkedList<String>();
	List<String>	sampleBreadcrumbs	= new LinkedList<String>();

	public BreadcrumbAnalyzer()
	{
	}

	public void construct()
	{
		try
		{
			File dir = new File("C:/work/karniyarik/files/products/");
			File[] files = dir.listFiles();
			for (File file : files)
			{
				List<Product> parseFile = JSONUtil.parseFile(file, Product.class);
				boolean added = false;
				System.out.println(file.getName());
				for (Product p : parseFile)
				{
					if(StringUtils.isNotBlank(p.getBreadcrumb()))
					{
						String b = p.getBreadcrumb();
						b = b.replaceAll("\\s+", " "); 
						allBreadcrumns.add(b);
						if(added == false)
						{
							sampleBreadcrumbs.add(b + "\t" + file.getName());
							added=  true;
						}
					}
				}
			}
			
			
			IOUtils.writeLines(allBreadcrumns, "\n", new FileOutputStream("C:/work/karniyarik/files/breadcrumb_all.txt"));
			IOUtils.writeLines(sampleBreadcrumbs, "\n", new FileOutputStream("C:/work/karniyarik/files/breadcrumb_sample.txt"));
		}
		catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new BreadcrumbAnalyzer().construct();
	}
}
