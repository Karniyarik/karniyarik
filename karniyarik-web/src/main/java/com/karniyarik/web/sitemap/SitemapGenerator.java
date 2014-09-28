package com.karniyarik.web.sitemap;

import java.io.File;
import java.util.Date;

import com.karniyarik.web.sitemap.providers.InsightProvider;
import com.karniyarik.web.util.WebLoggerProvider;

public class SitemapGenerator
{
	private String rootPath = "";
	
	URLProviderIterator[] providers = null ;
		
	public SitemapGenerator(String rootPath)
	{
		this.rootPath = rootPath;
		providers = new URLProviderIterator[]{
				//new StaticPagesProvider(),
				new InsightProvider(rootPath)
			};
	}
	
	public void generate(URLProviderIterator[] providers)
	{
		System.out.println("Sitemap generation started: " + new Date(System.currentTimeMillis()));

		try {
			File file = new File(rootPath);
			if(!file.exists())
			{
				file.mkdirs();
			}
			
			SitemapIndexGenerator indexGenerator = new SitemapIndexGenerator(rootPath);
			
			for(URLProviderIterator sourceProvider: providers)
			{
				Generator generator = new Generator(indexGenerator.getCache(), sourceProvider, rootPath);
				generator.generate();
				indexGenerator.addFiles(generator.getFileNames());
			}
			
			indexGenerator.generate();
		} catch (Throwable e) {
			WebLoggerProvider.logException("Unable to create sitemap ", e);		
		}
		System.out.println("Sitemap generation ended: " + new Date(System.currentTimeMillis()));		
	}
	
	public void generate()
	{
		generate(providers);
	}
	
	public static void main(String[] args) throws Exception
	{
		new SitemapGenerator("C:/java/krnyrk/sitemap").generate();
	}
}
