package com.karniyarik.pagerank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GoogleRankCalculator
{
	public GoogleRankCalculator() throws Throwable
	{
		InputStream aStream = getClass().getClassLoader().getResourceAsStream("siteler.total.txt");
		
		BufferedReader aReader = new BufferedReader(new InputStreamReader(aStream));

		String aLine = null;
		int anIndex = 0;
		
		PageRankService prService = new PageRankService();
		
		while(true)
		{
			aLine = aReader.readLine();
			anIndex++;
			
			if(aLine != null)
			{
				int aRank = prService.getPR(aLine);
				
				System.out.println(anIndex + ", " + aLine + ", " + aRank);
				
				Thread.sleep(200);
			}	
			else
			{
				break;
			}
		}	
	}
	
	public static void main(String[] args) throws Throwable
	{
		new GoogleRankCalculator();
	}
}