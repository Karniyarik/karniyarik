package com.karniyarik.ir.analyzer;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;

public class TurkishAnalyzerPool
{
	private static TurkishAnalyzerPool		INSTANCE	= null;
	//private ginal GenericKeyedObjectPool	pool;
	private Map<AnalyzerType,NewTurkishAnalyzer> pool = new HashMap<AnalyzerType, NewTurkishAnalyzer>(); 
	private TurkishAnalyzerPool()
	{
//		pool = new GenericKeyedObjectPool(new TurkishAnalyzerPoolFactory());
//		pool.setWhenExhaustedAction(GenericKeyedObjectPool.WHEN_EXHAUSTED_GROW);
//		
//		try
//		{
//			for (int i = 0; i < 50; i++)
//			{
//				pool.addObject(AnalyzerType.INDEX_ANALYZER);
//			}
//
//			for (int i = 0; i < 50; i++)
//			{
//				pool.addObject(AnalyzerType.SEARCH_ANALYZER);
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
		
		TurkishAnalyzerPoolFactory factory = new TurkishAnalyzerPoolFactory();
		pool.put(AnalyzerType.INDEX_ANALYZER, factory.createStrictAnalyzer(AnalyzerType.INDEX_ANALYZER));
		pool.put(AnalyzerType.SEARCH_ANALYZER, factory.createStrictAnalyzer(AnalyzerType.SEARCH_ANALYZER));
	}

	public static TurkishAnalyzerPool getInstance()
	{
		if (INSTANCE == null)
		{
			synchronized (TurkishAnalyzerPool.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = new TurkishAnalyzerPool();
				}
			}
		}
		return INSTANCE;
	}

	public NewTurkishAnalyzer borrowIndexAnalyzer()
	{
		return borrowAnalyzer(AnalyzerType.INDEX_ANALYZER);
	}

	public void returnIndexAnalyzer(Analyzer analyzer)
	{
		returnAnalyzer(AnalyzerType.INDEX_ANALYZER, analyzer);
	}

	public NewTurkishAnalyzer borrowSearchAnalyzer()
	{
		return borrowAnalyzer(AnalyzerType.SEARCH_ANALYZER);
	}

	public void returnSearchAnalyzer(Analyzer analyzer)
	{
		returnAnalyzer(AnalyzerType.SEARCH_ANALYZER, analyzer);
	}

	private NewTurkishAnalyzer borrowAnalyzer(AnalyzerType type)
	{
		NewTurkishAnalyzer analyzer = null;

		try
		{
			//analyzer = (NewTurkishAnalyzer) pool.borrowObject(type);
			analyzer = (NewTurkishAnalyzer) pool.get(type);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return analyzer;
	}

	private void returnAnalyzer(AnalyzerType type, Analyzer analyzer)
	{
		try
		{
			//pool.returnObject(type, analyzer);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
