package com.karniyarik.crawler.parser;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;

import com.karniyarik.common.util.URLUtil;

/**
 * $Id: URLFinder.java,v 1.9 2003/04/10 16:19:17 vanrogu Exp $
 */
public class URLFinder
{

	public final String		basePattern	= "<base href=";
	private URLUtil			mURLUtil = null;

	public URLFinder()
	{
		mURLUtil = new URLUtil();
	}


	public final String[]	patterns	= { "href=" };

	@SuppressWarnings("unchecked")
	public Set<String> findURLs(String aContent, String aCurrentURLString)
			throws Exception
	{
		Set<String> aResult = new TreeSet<String>();
		List<String> aLineList = IOUtils.readLines(new StringReader(aContent));

		URL aCurrentURL = new URL(aCurrentURLString);

		for (String aLine : aLineList)
		{
			findURLsInLine(aLine, aResult, aCurrentURL);
		}

		return aResult;
	}

	private void findURLsInLine(String line, Set<String> urlSet,
			URL aCurrentURL) throws MalformedURLException
	{
		// URL aBaseURL = findBase(line, basePattern);

		for (int i = 0; i < patterns.length; i++)
		{
			String pattern = patterns[i];
			findURLs(line, pattern, urlSet, aCurrentURL);
		}
	}

	protected URL findBase(String line, String pattern)
			throws MalformedURLException
	{
		String lineLowerCase = line.toLowerCase(Locale.ENGLISH);
		int pos = lineLowerCase.indexOf(pattern);
		if (pos != -1)
		{
			String url = "";
			url = extractURL(line, pos + pattern.length());
			String baseURL = mURLUtil.normalize(url);
			return new URL(baseURL);
		}

		return null;
	}

	protected void findURLs(String line, String pattern, Set<String> urlSet,
			URL aCurrentURL) throws MalformedURLException
	{
		String lineLowerCase = line.toLowerCase(Locale.ENGLISH);

		//this line is commented out so do not try to find any url
		if (line.startsWith("<!--") || line.endsWith("-->")) {

			return;
		}

		int pos = lineLowerCase.indexOf(pattern);
		String foundURL = null;

		while (pos != -1)
		{
			try
			{
				String uri = "";
				uri = extractURL(line, pos + pattern.length());
				if (!mURLUtil.isFileSpecified(aCurrentURL))
				{
					// Force a slash in case of a folder (to avoid buggy
					// relative refs)
					aCurrentURL = new URL(aCurrentURL.toString() + "/");
				}
				if (uri != null && uri.startsWith("javascript"))
				{
					foundURL = uri;
				}
				else
				{
					foundURL = mURLUtil.normalize(new URL(aCurrentURL, uri)
							.toExternalForm());
				}
				urlSet.add(foundURL);
			}
			catch (Throwable e)
			{
				// do nothing
			}

			pos = lineLowerCase.indexOf(pattern, pos + pattern.length());
		}
	}

	protected String extractURL(String string, int pos)
	{
		//code added by km
		boolean found = false;
		char hrefQoute = '"'; //default
		//end

		char c = string.charAt(pos);
		String ret = "";
		if (c == '\'' || c == '"')
		{
			//code added by km
			found = true;
			hrefQoute = c;
			//end
			string = string.substring(pos + 1);
		}
		else
		{
			string = string.substring(pos);
		}
		if (string.length() > 0)
		{
			c = string.charAt(0);

			//this code is added for malformed urls given in idefix
			if (c == '\\' && !found) {
				ret = "";
			}
			//ends here
			
			else if (c == '\'' || c == '\"' || c == '>')
			{
				ret = "";
			}
			else
			{
				if (string.startsWith("javascript:"))
				{
					ret = string.substring(0, string.indexOf(hrefQoute));
				}
				else
				{
					StringTokenizer st = null;
					//code added by km
					if (found) {
						st = new StringTokenizer(string, " \"\'");
					}
					//end
					else {
						st = new StringTokenizer(string, " \"\'>");
					}
					ret = st.nextToken();
				}
			}
		}
		int p = ret.indexOf('#');
		if (p > -1)
		{
			return ret.substring(0, p);
		}
		else
		{
			return ret;
		}
	}

}
