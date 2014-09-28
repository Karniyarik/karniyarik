package com.karniyarik.recognizer.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import com.karniyarik.common.util.StreamUtil;

public class NoiseFilter extends TokenFilter
{
	private Set<String>				noises				= new HashSet<String>();
	private Pattern[] 				noisePatterns = new Pattern[]{
			Pattern.compile("\\d+GB"),
			Pattern.compile("[\\d\\.]+[\"\']+")
		};
	
	public NoiseFilter(TokenStream in)
	{
		super(in);
		InputStream stream = StreamUtil.getStream("noisewords.txt");
		List<String> lines;
		try
		{
			lines = (List<String>) IOUtils.readLines(stream);
			stream.close();
			for (String line : lines)
			{
				line = line.trim().toLowerCase();
				String[] terms = line.split("\\s");
				for(String term: terms)
				{
					term  = term.trim();
					if (StringUtils.isNotBlank(term) && term.length() > 2)
					{
						noises.add(term.toLowerCase(Locale.ENGLISH));
					}					
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public Token next() throws IOException
	{
		Token token = null; 
		while(true)
		{
			token = super.next();
			boolean isNoise = false;
			if(noises.contains(token.term()) || token.term().length() < 2)
			{
				isNoise = true;
			}
			else
			{
				for(Pattern pat: noisePatterns)
				{
					Matcher matcher = pat.matcher(token.term());
					if(matcher.matches())
					{
						isNoise = true;
						break;
					}
				}
			}
			
			if(!isNoise)
			{
				break;
			}
			else
			{
				token = null;
			}
		}
		
		return token;
	}
}
