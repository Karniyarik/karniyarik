package com.karniyarik.recognizer;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.recognizer.ext.GearRecognizer;

public class GearRecognizerTest
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
		InputStream is = StreamUtil.getStream("gear.txt");
		List<String> readLines = (List<String>) IOUtils.readLines(is);
		is.close();
		
		for(String line: readLines)
		{
			String value = recognizer.recognizeGear(line);
			if(value == null)
			{
				System.out.println(line.trim() + " --> " + value);	
			}
		}
		
		System.out.println(GearRecognizer.success * 1.0 / GearRecognizer.total * 100) ;
	}
}
