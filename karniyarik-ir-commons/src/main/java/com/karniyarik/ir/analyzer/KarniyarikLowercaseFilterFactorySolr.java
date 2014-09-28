package com.karniyarik.ir.analyzer;

import org.apache.lucene.analysis.TokenStream;
import org.apache.solr.analysis.BaseTokenFilterFactory;

public class KarniyarikLowercaseFilterFactorySolr extends BaseTokenFilterFactory {

	@Override
	public KarniyarikLowercaseFilterSolr create(TokenStream input) {
		return new KarniyarikLowercaseFilterSolr(input);
	}

}
