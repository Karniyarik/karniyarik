package com.karniyarik.categorizer.lucene;

import java.util.regex.Pattern;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;

public class CatQueryParser {

	private static final String LUCENE_ESCAPE_CHARS = "[\\\\+\\-\\!\\(\\)\\:\\^\\]\\[\\{\\}\\~\\*\\?\\\"\\\\\\&\\|]";
	private static final Pattern LUCENE_PATTERN = Pattern.compile(LUCENE_ESCAPE_CHARS);
	private static final String REPLACEMENT_STRING = "\\\\$0";

	public static String LUCENE_AND = "\\s+AND\\s+";
	public static String LUCENE_OR = "\\s+OR\\s+";

	private final QueryParser queryParser;

	private int wordCount = 0;

	public CatQueryParser(QueryParser queryParser) {
		this.queryParser = queryParser;
	}

	public org.apache.lucene.search.Query parse(String queryString) throws ParseException {

		org.apache.lucene.search.Query result = null;

		queryString = queryString.trim();
		queryString = queryString.replaceAll("\\s+", " ");
		queryString = escapeLuceneChars(queryString);
		queryString = queryString + " "; 
		queryString = removeAndORs(queryString);
		queryString = queryString.replaceAll("\\s+", " ");
		queryString = queryString.trim();
		
		wordCount = queryString.split(" ").length;
		
		try
		{
			result = queryParser.parse(queryString);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	private String removeAndORs(String aQuery) {

		aQuery = aQuery.replaceAll(LUCENE_AND, " ");
		aQuery = aQuery.replaceAll(LUCENE_OR, " ");

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
