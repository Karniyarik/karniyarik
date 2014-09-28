package com.karniyarik.search.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.junit.Assert;
import org.junit.Test;

import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.analyzer.TurkishAnalyzerPool;

public class AnalyzerTest
{
	@Test
	public void testAlphaNumericSplitter() throws Exception
	{
		Analyzer anAnalyzer = TurkishAnalyzerPool.getInstance().borrowIndexAnalyzer();
	
		TokenStream aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("banabirgusel"));		
		checkResults(aStream, new String[]{"banabirgusel"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("16GB"));		
		checkResults(aStream, new String[]{"16gb", "gb", "16"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("GB16"));		
		checkResults(aStream, new String[]{"gb16", "16", "gb"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("A.1.2"));		
		checkResults(aStream, new String[]{"a.1.2"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("nokia"));		
		checkResults(aStream, new String[]{"nokia"});		
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("GC2630"));		
		checkResults(aStream, new String[]{"gc2630", "2630", "gc"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("2630GC"));		
		checkResults(aStream, new String[]{ "2630gc", "gc", "2630"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("2630g"));		
		checkResults(aStream, new String[]{"2630g", "g", "2630"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("g2630"));		
		checkResults(aStream, new String[]{"g2630", "2630", "g"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("a.data"));		
		checkResults(aStream, new String[]{"a.data"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("a-data"));		
		checkResults(aStream, new String[]{"a", "data"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("   adi gusel g simdi  "));		
		checkResults(aStream, new String[]{"adi", "gusel", "g", "si"});
		
		TurkishAnalyzerPool.getInstance().returnIndexAnalyzer(anAnalyzer);
	}
	

	@Test
	public void testAnalyzer() throws Exception
	{
		Analyzer anAnalyzer = TurkishAnalyzerPool.getInstance().borrowIndexAnalyzer();
	
		TokenStream aStream = anAnalyzer.tokenStream(null, new StringReader("a-data"));
				
		checkResults(aStream, new String[]{"a", "data"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("a.data"));
		
		checkResults(aStream, new String[]{"a.data"});
		TurkishAnalyzerPool.getInstance().returnIndexAnalyzer(anAnalyzer);
	}
	
	@Test
	public void testBrandNamesAreNotStemmed() throws Exception
	{
		Analyzer anAnalyzer = TurkishAnalyzerPool.getInstance().borrowIndexAnalyzer();
	
		TokenStream aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("nokia n90"));
				
		checkResults(aStream, new String[]{"nokia", "n90", "90", "n"});
		TurkishAnalyzerPool.getInstance().returnIndexAnalyzer(anAnalyzer);
	}
	
	@Test
	public void testSynonyms() throws Exception
	{
		Analyzer anAnalyzer = TurkishAnalyzerPool.getInstance().borrowIndexAnalyzer();
		
		TokenStream aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("taşınabilir"));
		
		checkResults(aStream, new String[]{"tasi", "laptop", "dizustu", "notebook"});
		TurkishAnalyzerPool.getInstance().returnIndexAnalyzer(anAnalyzer);
	}

	
	@Test
	public void testTurkishCharConversion() throws Exception
	{
		Analyzer anAnalyzer = TurkishAnalyzerPool.getInstance().borrowIndexAnalyzer();
		
		TokenStream aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("çğiöşü"));
		
		checkResults(aStream, new String[]{"cgiosu"});
		
		aStream = anAnalyzer.tokenStream(SearchConstants.NAME, new StringReader("ÇĞİÖŞÜ"));
		
		checkResults(aStream, new String[]{"cgiosu"});	
		TurkishAnalyzerPool.getInstance().returnIndexAnalyzer(anAnalyzer);
	}

	private void printStream(TokenStream aStream) throws IOException
	{
		
		Token aToken = aStream.next();
		
		while(aToken != null)
		{
			System.out.println(aToken.termBuffer());
			
			aToken = aStream.next();
		}
	}
	
	private void checkResults(TokenStream aStream, String[] expected) throws IOException
	{
		Token aToken = aStream.next();
		
		int index = 0;
		while(aToken != null)
		{
			Assert.assertTrue(aToken.term().trim().equals(expected[index]));
			
			aToken = aStream.next();
			index++;
		}
	}
	
	private void checkResults(List<String> results, String[] expected) throws IOException
	{
		for(int index=0; index < results.size(); index++)
		{
			Assert.assertTrue(results.get(index).trim().equals(expected[index]));
		}
	}

}
