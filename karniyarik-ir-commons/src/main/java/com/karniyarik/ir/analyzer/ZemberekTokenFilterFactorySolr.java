package com.karniyarik.ir.analyzer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;

public class ZemberekTokenFilterFactorySolr extends BaseTokenFilterFactory {

	@Override
	public ZemberekTokenFilterSolr create(TokenStream input) {
		return new ZemberekTokenFilterSolr(input);
	}

}
