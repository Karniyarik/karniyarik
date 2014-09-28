package com.karniyarik.categorizer.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.categorizer.xml.category.RootType;
import com.karniyarik.common.util.StreamUtil;

public class CategoryIO
{
	public RootType read(String sitename) throws RuntimeException
	{
		RootType result = null;

		try
		{
			InputStream inputStream = StreamUtil.getStream(Constants.categoryDir + "/" +sitename + "-categories.xml");
			JAXBContext indexFileJaxbContext = JAXBContext.newInstance("com.karniyarik.categorizer.xml.category");
			Unmarshaller unmarshaller = indexFileJaxbContext.createUnmarshaller();
			result  = (RootType) unmarshaller.unmarshal(inputStream);
			inputStream.close();
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}

		return result;
	}

	public void write(RootType root, String filename) throws RuntimeException
	{
		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance("com.karniyarik.categorizer.xml.category");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
			File file = new File(Constants.categoryDir);
			if(!file.exists())
			{
				file.mkdirs();
			}
			FileOutputStream os = new FileOutputStream(Constants.categoryDir + "/" + filename + "-categories.xml");
			marshaller.marshal(root, os);
			os.close();
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}
}
