package com.karniyarik.common.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class ConfigurationURLUtil
{

	private static final String	DB_CONFIG						= "db.config.xml";
	private static final String	CATEGORIZER_CONFIG				= "categorizer.config.xml";
	private static final String	CRAWLER_CONFIG					= "crawler.config.xml";
	private static final String	PARSER_CONFIG					= "parser.config.xml";
	private static final String	SEARCH_CONFIG					= "search.config.xml";
	private static final String	STATISTICS_CONFIG				= "statistics.config.xml";
	private static final String	WEB_CONFIG						= "web.config.xml";
	private static final String	SCHEDULER_CONFIG				= "scheduler.config.xml";
	private static final String	PARSER_TEST_ASSISTANT_CONFIG	= "parsertest.sites.xml";
	private static final String	LOG_CONF						= "log4j.properties";
	private static final String	MAIL_CONF						= "mail.config.xml";
	private static final String	APIKEY_CONFIG					= "apikey.xml";
	private static final String	NEWS_CONFIG						= "news.xml";
	private static final String	CACHE_CONFIG					= "ehcache.xml";
	private static final String	ROBOTS_LIST_CONFIG				= "robotlist.txt";
	private static final String	DEPLOYMENT_CONFIG				= "deployment.config.xml";
	
	private static final String	SITE_CONF_DIR					= "siteconf";
	private static final String	LOGOS_DIR						= "logos";
	private static final String	ECOMMERCE_CONF_DIR				= "ecommerce";
	private static final String	SYS_CONF_DIR					= "system";
	private static final String	DICTIONARY_CONF_DIR				= "dictionary";
	private static final String	DATAFEED_CONF_DIR				= "datafeed";

	private static final String	SITE_IMAGES_DIR					= "../../images/sites/100x30";

	private static URL loadURL(String urlStr)
	{
		URL aURL = StreamUtil.getURL(urlStr);

		InputStream aStream = null;

		try
		{
			aStream = StreamUtil.getStream(aURL);
		}
		catch (Throwable e)
		{
		}

		if (aStream == null)
		{
			aURL = StreamUtil.getURL("conf/" + urlStr);
		}
		else
		{
			try
			{
				aStream.close();
			}
			catch (Throwable e)
			{
			}
		}

		return aURL;
	}

	private static File loadFile(URL aURL)
	{
		try
		{
			return new File(aURL.toURI());
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Cannot load config file " + aURL.toExternalForm());
		}
	}

	public static File loadSiteConfDir()
	{
		return loadFile(loadURL(SITE_CONF_DIR));
	}

	public static File loadSiteImagesDir()
	{
		return loadFile(loadURL(SITE_IMAGES_DIR));
	}

	public static File loadLogosDir()
	{
		return loadFile(loadURL(LOGOS_DIR));
	}

	public static File loadECommerceConfDir()
	{
		return loadFile(loadURL(ECOMMERCE_CONF_DIR));
	}

	public static File loadDatafeedConfDir()
	{
		return loadFile(loadURL(DATAFEED_CONF_DIR));
	}

	public static URL loadSiteConfig(String siteName)
	{
		return loadURL(SITE_CONF_DIR + File.separator + siteName + ".xml");
	}

	public static File loadSiteConfigFile(String siteName)
	{
		return loadFile(loadSiteConfig(siteName));
	}

	public static URL loadECommerceConfig(String ecommerceName)
	{
		return loadURL(ECOMMERCE_CONF_DIR + File.separator + ecommerceName + ".xml");
	}

	public static URL loadDatafeedConfig(String datafeedName)
	{
		return loadURL(DATAFEED_CONF_DIR + File.separator + datafeedName + ".xml");
	}

	public static URL getDBConfig()
	{
		return loadSystemConf(DB_CONFIG);
	}

	public static URL getDeploymentConfig()
	{
		return loadSystemConf(DEPLOYMENT_CONFIG);
	}

	public static URL getCrawlerConfig()
	{
		return loadSystemConf(CRAWLER_CONFIG);
	}

	public static URL getCacheConfig()
	{
		return loadSystemConf(CACHE_CONFIG);
	}

	public static URL getParserConfig()
	{
		return loadSystemConf(PARSER_CONFIG);
	}

	public static URL getSearchConfig()
	{
		return loadSystemConf(SEARCH_CONFIG);
	}
	
	public static URL getStatisticsConfig()
	{
		return loadSystemConf(STATISTICS_CONFIG);
	}

	public static URL getWebConfig()
	{
		return loadSystemConf(WEB_CONFIG);
	}

	public static URL getCategorizerConfig()
	{
		return loadSystemConf(CATEGORIZER_CONFIG);
	}

	public static URL getAPIKeyConfig()
	{
		return loadURL("api/" + APIKEY_CONFIG);
	}

	public static URL getNewsConfig()
	{
		return loadURL("news/" + NEWS_CONFIG);
	}

	public static URL getLogConf()
	{
		return loadSystemConf(LOG_CONF);
	}

	public static URL getSchedulerConf()
	{
		return loadSystemConf(SCHEDULER_CONFIG);
	}

	public static URL getParserTestAssistantConf()
	{
		return loadSystemConf(PARSER_TEST_ASSISTANT_CONFIG);
	}

	public static URL getRobotsListConfig()
	{
		return loadURL(DICTIONARY_CONF_DIR + File.separator + ROBOTS_LIST_CONFIG);
	}

	public static URL getMailConfig()
	{
		return loadSystemConf(MAIL_CONF);
	}

	private static URL loadSystemConf(String name)
	{
		return loadURL(SYS_CONF_DIR + File.separator + name);
	}

}