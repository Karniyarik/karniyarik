package com.karniyarik.crawler.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.util.ClassUtil;
import com.karniyarik.common.util.URLUtil;

public class SiteLinkAnalyzer
{
	private SiteRule			mSiteRuleObject				= null;
	private final String		homeURL;
	private final Set<String>	siteIgnoreRules;
	private final String		pagingRule;
	private final String		ruleClassName;
	private final List<String>	commonIgnoreRules;
	public static final String	pagingUrlDelimeter			= "_ppp_";
	private final Logger		siteLogger;
	private final List<String>	robotsDisallowedUrlList;
	private List<Pattern>		ignorePattern				= null;
	private Pattern				pagingPattern				= null;
	private Pattern				constructedPagingPattern	= null;

	public SiteLinkAnalyzer(String homeURL, Set<String> siteIgnoreRules,
			String pagingRule, String ruleClassName,
			List<String> commonIgnoreRules,
			List<String> robotsDisallowedUrlList, Logger siteLogger)
	{
		this.homeURL = homeURL;
		this.siteIgnoreRules = siteIgnoreRules;
		this.pagingRule = pagingRule;
		this.commonIgnoreRules = commonIgnoreRules;
		this.ruleClassName = ruleClassName;
		this.siteLogger = siteLogger;
		this.robotsDisallowedUrlList = robotsDisallowedUrlList;

		setIgnoreRules();
		setPagingRules();
		loadRuleClass();
	}

	private void setPagingRules()
	{

		String regex = getRegEx(pagingRule);

		if (StringUtils.isNotBlank(regex))
		{
			pagingPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE
					| Pattern.UNICODE_CASE);
			constructedPagingPattern = Pattern.compile(".*"
					+ pagingUrlDelimeter + regex, Pattern.CASE_INSENSITIVE
					| Pattern.UNICODE_CASE);
		}

	}

	private void setIgnoreRules()
	{
		String regex;
		ignorePattern = new ArrayList<Pattern>();

		for (String ignoreRule : siteIgnoreRules)
		{
			regex = getRegEx(ignoreRule);

			if (StringUtils.isNotBlank(regex))
			{
				ignorePattern.add(Pattern.compile(regex));
			}
		}

		for (String ignoreRule : commonIgnoreRules)
		{
			regex = getRegEx(ignoreRule);

			if (StringUtils.isNotBlank(regex))
			{
				ignorePattern.add(Pattern.compile(regex));
			}
		}
	}

	private String getRegEx(String param)
	{
		if (param.startsWith("#regex:"))
		{
			return param.replaceFirst("#regex:", "");
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private void loadRuleClass()
	{
		try
		{
			if (StringUtils.isNotBlank(ruleClassName))
			{

				Class aClass = ClassUtil.loadClass(ruleClassName);

				if (aClass == null)
				{
					throw new RuntimeException(
							"Cannot load Class");
				}

				if (SiteRule.class.isAssignableFrom(aClass))
				{
					mSiteRuleObject = (SiteRule) aClass.newInstance();
					mSiteRuleObject.setSiteLinkAnalyzer(this);
				}
				else
				{
					throw new RuntimeException(
							"The given rule class " + ruleClassName
									+ " shall implement "
									+ SiteRule.class.getName());
				}
			}
		}
		catch (Throwable e)
		{
			siteLogger.error("Cannot load rule class " + ruleClassName, e);
			throw new RuntimeException("Cannot load rule class "
					+ ruleClassName, e);
		}
	}

	public final boolean checkIgnoreList(String aURL)
	{
		boolean result = Boolean.FALSE;

		for (String rule : robotsDisallowedUrlList)
		{

			if (StringUtils.containsIgnoreCase(aURL, rule))
			{
				result = Boolean.TRUE;
				break;
			}
		}

		if (!result)
		{
			URLUtil util = new URLUtil();
			for (String aString : commonIgnoreRules)
			{
				if (StringUtils.endsWithIgnoreCase(util.getFileName(aURL), aString))
				{
					result = Boolean.TRUE;
					break;
				}
			}
		}

		if (!result)
		{
			for (String aString : siteIgnoreRules)
			{
				if (StringUtils.containsIgnoreCase(aURL, aString))
				{
					result = Boolean.TRUE;
					break;
				}
			}
		}

		if (!result)
		{
			aURL = removeHostName(aURL);

			for (Pattern pattern : ignorePattern)
			{
				if (pattern.matcher(aURL).find())
				{
					result = Boolean.TRUE;
					break;
				}
			}
		}

		return result;
	}

	public boolean isIgnored(String aURL)
	{
		boolean result = false;

		if (StringUtils.isNotBlank(aURL))
		{
			result = checkIgnoreList(aURL);
			// no need to check the special ignorance rules
			// if the url is already ignored by general site rules
			if (result != true && mSiteRuleObject != null)
			{
				result = mSiteRuleObject.isIgnored(aURL, result);
			}
		}

		return result;
	}

	public String correctURL(String aURL, String aParentURL)
	{

		if (mSiteRuleObject != null)
		{
			return mSiteRuleObject.correctURL(aURL, aParentURL);
		}

		return aURL;
	}

	private String removeHostName(String aURL)
	{
		String homeURL = getHomeURL();

		int anIndex = homeURL.indexOf("/", 7);
		if (anIndex != -1)
		{
			homeURL = homeURL.substring(0, anIndex);
		}

		aURL = aURL.replace(homeURL, "");
		if (aURL.startsWith("/"))
		{
			aURL = aURL.substring(1);
		}
		return aURL;
	}


	public String getHomeURL()
	{
		return homeURL;
	}

	public List<String> getStartingURLs()
	{
		List<String> startUrlList = null;
		if (mSiteRuleObject != null)
		{
			startUrlList = mSiteRuleObject.populateStartingURLs();
		}
		else
		{
			startUrlList = new ArrayList<String>();
		}

		return startUrlList;
	}

	public boolean isPagingUrl(String url)
	{

		return pagingPattern.matcher(url).find();
	}

	public boolean isConstructedPagingUrl(String url)
	{

		return constructedPagingPattern.matcher(url).find();
	}

	public String extractPagingUrlParent(String url)
	{

		return url.split(pagingUrlDelimeter)[0];
	}

	public String extractPagingUrl(String url)
	{

		return url.split(pagingUrlDelimeter)[1];
	}

	public String generateSpecialPagingUrl(String parentURL, String url)
	{

		return parentURL + pagingUrlDelimeter + url;
	}

	public static void main(String[] args)
	{
		String aURL = "http://www.ispanak.com.tr/TR/Urun/Urun.aspx?F6E10F8892433CFFAAF6AA849816B2EFC6029A481C6EADF5";
		String homeURL = "http://www.ispanak.com.tr/TR/Genel/Default.aspx?F6E10F8892433CFFAAF6AA849816B2EF4376734BED947CDE";
		String regex = ".*Urun.aspx\\?(.*)";

		if (homeURL.indexOf("/", 7) != -1)
		{
			homeURL = homeURL.substring(0, homeURL.indexOf("/", 7));
		}

		aURL = aURL.replace(homeURL, "");
		if (aURL.startsWith("/"))
		{
			aURL = aURL.substring(1);
		}
		System.out.println(aURL);

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(aURL);
		boolean result = matcher.matches();
		System.out.println(result);
		System.out.println(matcher.group(1));
	}

	public Set<String> generateUrls(String aURL) {

		Set<String> result = new TreeSet<String>();

		if (mSiteRuleObject != null) {
			result = mSiteRuleObject.generateUrls(aURL);
		}

		return result;
	}
}