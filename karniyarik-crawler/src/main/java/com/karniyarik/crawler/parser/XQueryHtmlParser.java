package com.karniyarik.crawler.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.trans.XPathException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;

import com.karniyarik.common.util.URLUtil;

public class XQueryHtmlParser implements HTMLParser
{

	private final HtmlCleaner			cleaner;
	private final Configuration			xqueryConfiguration;
	private final StaticQueryContext	staticContext;
	private XQueryExpression			hrefQuery;
	private XQueryExpression			baseQuery;
	private final String				HREF_QUERY	= "data(//@href)";
	private final String				BASE_QUERY	= "data(//base/@href)";
	private final URLUtil				urlUtil;

	public XQueryHtmlParser()
	{
		urlUtil = new URLUtil();
		cleaner = new HtmlCleaner();
		xqueryConfiguration = new Configuration();
		staticContext = new StaticQueryContext(xqueryConfiguration);

		try
		{
			hrefQuery = staticContext.compileQuery(HREF_QUERY);
			baseQuery = staticContext.compileQuery(BASE_QUERY);
		}
		catch (XPathException e)
		{
			// this exception will not occur
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getLinks(String content, String currentURL)
	{
		Set<String> links = new TreeSet<String>();
		String tempURI = null;
		DynamicQueryContext dynamicContext = null;
		List resultList = null;
		String baseUrl = "";
		
		try
		{
			dynamicContext = initializeDynamicQueryContex(content);
			resultList = hrefQuery.evaluate(dynamicContext);
			baseUrl = parseBaseElement(dynamicContext);
			if (StringUtils.isBlank(baseUrl))
			{
				baseUrl = currentURL;
			}
			
			for (Object resultObject : resultList)
			{
				if (resultObject != null)
				{
					tempURI = StringUtils.trim(resultObject.toString());
				}

				if (StringUtils.isNotBlank(tempURI))
				{
					if (StringUtils.containsIgnoreCase(tempURI, "javascript")) {
						links.add(tempURI);
					}
					else {
						try {
							tempURI = urlUtil.createUrl(baseUrl, tempURI);

							links.add(tempURI);
						} catch (Throwable e){
							// Catch malformed url exceptions and ignore them.
							// this catch will prevent the for loop from terminating.
						}
					}
				}
			}
		}
		catch (Throwable e)
		{
			// do nothing
		}
		finally
		{
			tempURI = null;
			dynamicContext = null;
			resultList = null;
		}

		return links;
	}

	private String parseBaseElement(DynamicQueryContext dynamicContext) throws XPathException
	{
		String url = "";
		Object obj = baseQuery.evaluateSingle(dynamicContext);

		if (obj != null)
		{
			url = obj.toString().trim();
		}

		return url;
	}
	
	private DynamicQueryContext initializeDynamicQueryContex(String content)
			throws IOException, XPathException, ParserConfigurationException
	{
		DynamicQueryContext dynamicContext = new DynamicQueryContext(
				xqueryConfiguration);
		dynamicContext.setContextItem(xqueryConfiguration
				.buildDocument(new DOMSource(new DomSerializer(cleaner
						.getProperties(), true).createDOM(cleaner
						.clean(content)))));
		return dynamicContext;
	}
	
	public static void main(String[] args) throws Throwable
	{
		XQueryHtmlParser parser = new XQueryHtmlParser();
		String content = FileUtils.readFileToString(new File("C:/dev/test.htm"), "WINDOWS-1254");
		Set<String> links = parser.getLinks(content, "http://www.netsiparis.com");
		for (String link : links)
		{
			System.out.println(link);
		}
	}

}
