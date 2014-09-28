package com.karniyarik.search.searcher.query;


public class QueryParserFactory {

	public NewQueryParser createQueryParser() {

//		QueryParser qp = new QueryParser(fields[0], searchAnalyzer);
//		QueryParser qp = new MultiFieldQueryParser(fields, searchAnalyzer, boosts);
//		if (isConvertSpacesToOr) {
//			qp.setDefaultOperator(Operator.OR);
//		}
//		else {
//			qp.setDefaultOperator(Operator.AND);
//		}

		return new NewQueryParser();
	}
}
