package com.karniyarik.parser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CrawlerConfig;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.common.vo.CurrencyType;
import com.karniyarik.crawler.HttpClientContentFetcher;
import com.karniyarik.crawler.IContentFetcher;

public class CurrencyManager
{

	private static CurrencyManager		instance				= null;
	private Map<CurrencyType, Float>	currencyMap;
	private float						DEFAULT_EXCHANGE_RATE	= 1f;
	private ReentrantReadWriteLock		lock;

	private CurrencyManager()
	{
		currencyMap = new HashMap<CurrencyType, Float>();
		lock = new ReentrantReadWriteLock();
		refreshCurrencyMap();
	}

	public static CurrencyManager getInstance()
	{

		if (instance == null)
		{
			instance = new CurrencyManager();
		}

		return instance;
	}

	// should be refactored later inorder to return real exchange rates
	public Float getExchangeRate(CurrencyType currency)
	{
		Float rate = DEFAULT_EXCHANGE_RATE;
		try
		{
			lock.readLock().lock();
			rate = currencyMap.get(currency);
		}
		catch (Throwable e)
		{
			Logger.getLogger(this.getClass()).error("Can not get currency rate for " + currency.getCode(), e);
		}
		finally
		{
			lock.readLock().unlock();
		}
		return rate;
	}

	public void refreshCurrencyMap()
	{
		try
		{
			lock.writeLock().lock();
			Document doc = fetchCurrencyDocument();

			for (CurrencyType currencyType : CurrencyType.values())
			{
				if (currencyType != CurrencyType.TL)
				{
					currencyMap.put(currencyType, getCurrencyExchangeRate(currencyType, doc));
				}
			}

		}
		catch (Throwable e)
		{
			Logger.getLogger(this.getClass()).error("Can not refresh currency data", e);
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}

	private Float getCurrencyExchangeRate(CurrencyType currencyType, Document doc)
	{
		Float rate = DEFAULT_EXCHANGE_RATE;

		if (doc != null)
		{
			String xpath = "//Currency[@CurrencyCode=\"" + currencyType.getCode() + "\"]/ForexSelling";
			Element element = (Element) doc.getRootElement().selectSingleNode(xpath);
			if (element != null && StringUtils.isNotBlank(element.getTextTrim()))
			{
				rate = Float.parseFloat(element.getTextTrim());
			}
		}

		return rate;
	}

	private Document fetchCurrencyDocument()
	{
		CrawlerConfig crawlerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig();
		IContentFetcher contentFetcher = new HttpClientContentFetcher(crawlerConfig.getBotName(), crawlerConfig.getURLFetchTimeout(), "ISO-8859-9");

		Document doc = null;
		SAXReader reader = new SAXReader();

		String content = contentFetcher.getContent("http://www.tcmb.gov.tr/kurlar/today.xml");

		if (StringUtils.isNotBlank(content))
		{
			try
			{
				doc = reader.read(new StringInputStream(content, StringUtil.DEFAULT_ENCODING));
			}
			catch (Throwable e)
			{
				Logger.getLogger(this.getClass()).error("Can not fetch currency rates from central bank of turkey", e);
				doc = null;
			}
		}
		return doc;
	}

	public static void main(String[] args)
	{
		CurrencyManager.getInstance();
	}

}
