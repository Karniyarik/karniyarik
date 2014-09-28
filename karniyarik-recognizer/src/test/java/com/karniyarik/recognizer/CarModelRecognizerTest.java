package com.karniyarik.recognizer;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.recognizer.ext.CarModelRecognizer;

public class CarModelRecognizerTest
{
	private static RecognizerImpl	recognizer	= null;

	@BeforeClass
	public static void setUp()
	{
		recognizer = RecognizerImpl.getInstance();
	}

	@Test
	public void testRecognizeColors() throws Exception
	{
		InputStream is = StreamUtil.getStream("carmodel.txt");
		List<String> readLines = (List<String>) IOUtils.readLines(is);
		is.close();
		
		for(String line: readLines)
		{
			String value = recognizer.resolveCarModel(line);
			if(value == null)
			{
				System.out.println(line.trim());	
			}
		}
		System.out.println("Total:" +  CarModelRecognizer.total) ;
		System.out.println("Success:" +  CarModelRecognizer.success) ;
		System.out.println(CarModelRecognizer.success * 1.0 / CarModelRecognizer.total * 100) ;
	}
}
