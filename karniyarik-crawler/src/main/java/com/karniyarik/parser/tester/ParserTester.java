package com.karniyarik.parser.tester;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.util.URLUtil;
import com.karniyarik.parser.base.XQueryParser;
import com.karniyarik.parser.logger.ParserBrandsLogger;
import com.karniyarik.parser.pojo.Product;

public class ParserTester
{

	public UrlTestResult testUrl(String url)
	{
		UrlTestResult testResult = new UrlTestResult();
		SiteConfig siteConfig = getSiteConfig(url);

		if (siteConfig != null)
		{
			ParserBrandsLogger logger = new ParserBrandsLogger(siteConfig.getSiteName());
			XQueryParser parser = new XQueryParser(siteConfig, logger, new URLUtil());
			testResult = testUrl(parser, url, siteConfig.getSiteEncoding());
		}
		else
		{
			// prevent null pointers
			testResult.setProduct(new Product());
		}

		return testResult;
	}

	private UrlTestResult testUrl(XQueryParser parser, String url, String encoding)
	{
		UrlTestResult testResult = new UrlTestResult();
		testResult.setTestClass(parser.getClass().getSimpleName());
		if (parser != null)
		{
			testResult.setUrl(url);

			String content = getUrlContent(url, encoding);
			if (content != null)
			{
				Product product = parser.testParse(url, content);
				if (product != null)
				{
					testResult.setState(UrlTestState.SUCCESS);
				}
				else
				{
					testResult.setState(UrlTestState.FAIL);
				}
				testResult.setProduct(product);
			}
			else
			{
				testResult.setState(UrlTestState.COULD_NOT_FETCH_CONTENT);
			}
		}
		else
		{
			// prevent null pointers
			testResult.setProduct(new Product());
			testResult.setState(UrlTestState.SITE_NOT_SUPPORTED);
		}

		return testResult;
	}

	private SiteConfig getSiteConfig(String url)
	{
		SiteConfig config = null;

		Collection<SiteConfig> configList = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfigList();

		for (SiteConfig siteConfig : configList)
		{
			if (new URLUtil().hostsAreEqual(url, siteConfig.getUrl()))
			{
				config = siteConfig;
				break;
			}
		}

		return config;
	}

	private String getUrlContent(String url, String encoding)
	{
		String content = null;

		try
		{
			if ((url != null) && (encoding != null))
			{
				URL aURL = new URL(url);
				URLConnection aURLConnection = aURL.openConnection();

				aURLConnection.setConnectTimeout(20000);
				aURLConnection.setReadTimeout(20000);
				aURLConnection.setDefaultUseCaches(false);
				aURLConnection.setUseCaches(false);
				aURLConnection.setRequestProperty("User-agent", "Firefox3b01");
				aURLConnection.setRequestProperty("Cache-Control", "max-age=0,no-cache");
				aURLConnection.setRequestProperty("Pragma", "no-cache");

				InputStream aStream = aURLConnection.getInputStream();

				content = IOUtils.toString(aStream, encoding);
				aStream.close();
			}
		}
		catch (Throwable e)
		{
			getLogger().error("Can not fetch test url content", e);
		}

		return content;
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
