package com.karniyarik.web.sitemap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.web.sitemap.vo.index.Sitemapindex;
import com.karniyarik.web.sitemap.vo.urlset.Urlset;

public class SitemapIO {
	
	public static Sitemapindex readIndex(String rootPath)
	{
		File file = new File(rootPath + "/sitemap-1.xml");
		Sitemapindex result = null;
		
		try {
			InputStream inputStream = StreamUtil.getStream(file.toURI().toURL());
			JAXBContext indexFileJaxbContext = JAXBContext.newInstance("com.karniyarik.web.sitemap.vo.index");
			Unmarshaller unmarshaller = indexFileJaxbContext.createUnmarshaller();
			result  = (Sitemapindex) unmarshaller.unmarshal(inputStream);
			inputStream.close();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} 
		
		return result;
	}
	
	public static void writeIndex(String rootPath, Sitemapindex sitemapIndex)
	{
		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance("com.karniyarik.web.sitemap.vo.index");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
			File file = new File(rootPath + "/sitemap.xml");
			FileOutputStream os = new FileOutputStream(file);
			marshaller.marshal(sitemapIndex, os);
			os.close();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	public static Urlset readMap(String rootPath, String filename)
	{
		File file = new File(rootPath + "/" + filename);
		Urlset result;
		
		try {
			InputStream inputStream = StreamUtil.getStream(file.toURI().toURL());
			JAXBContext indexFileJaxbContext = JAXBContext.newInstance("com.karniyarik.web.sitemap.vo.urlset");
			Unmarshaller unmarshaller = indexFileJaxbContext.createUnmarshaller();
			result  = (Urlset) unmarshaller.unmarshal(inputStream);
			inputStream.close();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} 
		
		return result;
	}

	public static void writeMap(String rootPath, String filename, Urlset urlset)
	{
		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance("com.karniyarik.web.sitemap.vo.urlset");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
			if(!filename.endsWith(".xml"))
			{
				filename = filename + ".xml"; 
			}
			File file = new File(rootPath + "/" + filename);
			FileOutputStream os = new FileOutputStream(file);
			marshaller.marshal(urlset, os);
			os.close();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

}
