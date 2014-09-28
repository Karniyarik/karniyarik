package com.karniyarik.search.logger;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.karniyarik.search.searcher.logger.SpamController;

public class SpamTest
{
	@Test
	public void testTheFileIsLoadedCorrectly()
	{
		Assert.assertTrue(SpamController.getInstance().getSpamWordList() != null);
		Assert.assertTrue(SpamController.getInstance().getSpamWordList().size() > 0);
	}
	
	@Test
	public void testSampleQueries()
	{
		Map<String, Boolean> sampleQueries = new HashMap<String, Boolean>();
		sampleQueries.put("yarrak ", true);
		sampleQueries.put("am", true);
		sampleQueries.put("sik", true);
		sampleQueries.put("sikacak", true);
		sampleQueries.put("sIkacak", true);
		sampleQueries.put("kaam cıkları", true);
		sampleQueries.put("a.m.c.i.k", true);
		sampleQueries.put("fahi$e", true);
		sampleQueries.put("absolut", false);
		sampleQueries.put("yar aklin nerde", true);
		sampleQueries.put("göt", true);
		sampleQueries.put("gÖt", true);
		sampleQueries.put("SİKİCİ", true);
		
		for(String query: sampleQueries.keySet())
		{
			Assert.assertEquals(query + " is expected to be " +  sampleQueries.get(query), 
					sampleQueries.get(query), Boolean.valueOf(SpamController.getInstance().isSpam(query)));
		}
	}
}
