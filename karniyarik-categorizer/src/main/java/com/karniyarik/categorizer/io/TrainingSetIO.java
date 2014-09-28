package com.karniyarik.categorizer.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.categorizer.xml.trainset.RootType;
import com.karniyarik.common.util.StreamUtil;

public class TrainingSetIO
{
	@SuppressWarnings("unchecked")
	public RootType read(String sitename) throws RuntimeException
	{
		RootType result = null;

		try
		{
			InputStream inputStream = StreamUtil.getStream(Constants.trainsetDir + "/" + sitename + "-trainset.xml");
			JAXBContext indexFileJaxbContext = JAXBContext.newInstance("com.karniyarik.categorizer.xml.trainset");
			Unmarshaller unmarshaller = indexFileJaxbContext.createUnmarshaller();
			JAXBElement<RootType> root = (JAXBElement<RootType>) unmarshaller.unmarshal(inputStream);
			inputStream.close();

			result = root.getValue();
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}

		return result;
	}
	
	public void write(RootType root, String sitename) throws RuntimeException
	{
		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance("com.karniyarik.categorizer.xml.trainset");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
			
			File file = new File(Constants.trainsetDir);
			if(!file.exists())
			{
				file.mkdirs();
			}

			FileOutputStream os = new FileOutputStream(Constants.trainsetDir + "/" + sitename + "-trainset.xml");
			marshaller.marshal(root, os);
			os.close();
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
}
