package com.karniyarik.search.searcher;

import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.TokenGroup;

public class HTMLFormatter implements Formatter
{
	String preTag;
	String postTag;
	

	public HTMLFormatter(String preTag, String postTag)
	{
		this.preTag = preTag;
		this.postTag = postTag;
	}

	/**
	 * Default constructor uses HTML: &lt;B&gt; tags to markup terms
	 * 
	 **/
	public HTMLFormatter()
	{
		this.preTag = "<b>";
		this.postTag = "</b>";
	}

	/* (non-Javadoc)
	 * @see org.apache.lucene.search.highlight.Formatter#highlightTerm(java.lang.String, org.apache.lucene.search.highlight.TokenGroup)
	 */
	public String highlightTerm(String originalText, TokenGroup tokenGroup)
	{
		StringBuffer returnBuffer;
		if(tokenGroup.getTotalScore()>0)
		{
			returnBuffer=new StringBuffer();
			returnBuffer.append(preTag);
			returnBuffer.append(originalText);
			returnBuffer.append(postTag);
			return returnBuffer.toString();
		}
		return originalText;
	}}
