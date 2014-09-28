package com.karniyarik.citydeal;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.karniyarik.common.util.StreamUtil;

public class CityDealIO
{
	public void writeDeals(CityDealList cityDealList)
	{
		try {
			File file = getFile();
			FileOutputStream stream = new FileOutputStream(file);
			
			JAXBContext context = JAXBContext.newInstance(CityDealList.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(cityDealList, stream); 
			stream.close();
		} catch (Throwable e) {
			e.printStackTrace();
		} 
	}
	
	public CityDealList readDeals()
	{
		try {
			File file = getFile();
			
			if(file.exists())
			{
				JAXBContext context = JAXBContext.newInstance(CityDealList.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				//note: setting schema to null will turn validator off
				unmarshaller.setSchema(null);
				CityDealList result = CityDealList.class.cast(unmarshaller.unmarshal(file));
				return result;				
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} 
		
		return null;
	}

	private File getFile() {
		String tempDir = StreamUtil.getTempDir();
		File file = new File(tempDir + "/citydeals.xml");
		return file;
	}

}
