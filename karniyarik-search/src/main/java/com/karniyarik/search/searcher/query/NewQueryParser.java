package com.karniyarik.search.searcher.query;

import java.util.regex.Pattern;

public class NewQueryParser {

	private static final String LUCENE_ESCAPE_CHARS = "[\\\\+\\-\\!\\(\\)\\:\\^\\]\\[\\{\\}\\~\\*\\?\\\"\\\\\\&\\|]";
	private static final Pattern LUCENE_PATTERN = Pattern.compile(LUCENE_ESCAPE_CHARS);
	private static final String REPLACEMENT_STRING = "\\\\$0";

	public static String LUCENE_AND = "\\bAND\\b";
	public static String LUCENE_OR = "\\bOR\\b";
	public static String LUCENE_NOT = "\\bNOT\\b";

	private int wordCount = 0;

	public NewQueryParser() {
	}

	public String parse(String queryString){

		queryString = queryString.trim();
		queryString = queryString.replaceAll("\\s+", " ");
		queryString = escapeLuceneChars(queryString);

		queryString = removeAndORs(queryString);
		queryString = queryString.replaceAll("\\s+", " ");
		queryString = queryString.trim();

		wordCount = queryString.split(" ").length;
		
		return queryString;
	}

	private String removeAndORs(String aQuery) {

		aQuery = aQuery.replaceAll(LUCENE_AND, " and ");
		aQuery = aQuery.replaceAll(LUCENE_OR, " or ");
		aQuery = aQuery.replaceAll(LUCENE_NOT, " not ");

		return aQuery;
	}

	public int getWordCount() {
		return wordCount;
	}

	private String escapeLuceneChars(String aQuery) {

		aQuery = LUCENE_PATTERN.matcher(aQuery).replaceAll(REPLACEMENT_STRING);

		return aQuery;
	}
}
