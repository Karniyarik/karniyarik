package com.karniyarik.recognizer.model;

import java.io.IOException;
import java.util.Locale;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class ModelLowerCaseFilter extends TokenFilter
{
	public ModelLowerCaseFilter(TokenStream in)
	{
		super(in);
	}
	
	@Override
	public Token next() throws IOException
	{
		Token token = super.next(); 
		token.setTermBuffer(token.term().toLowerCase(Locale.ENGLISH));
		return token;
	}
}
