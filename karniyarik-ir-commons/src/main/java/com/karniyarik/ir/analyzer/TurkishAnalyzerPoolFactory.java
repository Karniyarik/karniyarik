package com.karniyarik.ir.analyzer;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;

public class TurkishAnalyzerPoolFactory extends BaseKeyedPoolableObjectFactory
{

	@Override
	public Object makeObject(Object key) throws Exception
	{
		return createStrictAnalyzer((AnalyzerType) key);
	}

	public NewTurkishAnalyzer createStrictAnalyzer(AnalyzerType type)
	{
		ZemberekFactory factory = new ZemberekFactory();
		NewTurkishAnalyzer analyzer;
		switch (type)
		{
		case INDEX_ANALYZER:
			analyzer = new NewTurkishAnalyzer(factory.createZemberek(), true, false);
			break;
		case SEARCH_ANALYZER:
			analyzer = new NewTurkishAnalyzer(factory.createZemberek(), true, true);
			break;
		default:
			analyzer = null;
			break;
		}
		return analyzer;
	}

}
