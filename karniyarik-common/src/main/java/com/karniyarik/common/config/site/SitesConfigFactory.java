package com.karniyarik.common.config.site;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.config.base.XMLFileFilter;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.CategoryPropertyConfig;
import com.karniyarik.common.util.ConfigurationURLUtil;

public class SitesConfigFactory
{

	private static final int	DEFAULT_CRAWL_DELAY			= 500;
	private static final String	DEFAULT_CAT					= "Urun";
	private static final String	DEFAULT_CRAWLER_CRONJOB		= "daily=3";
	private static final String	DEFAULT_DATAFEED_CRONJOB	= "cron=0 0 1 * * ? *";

	public SitesConfig create(CategorizerConfig categorizerConfig)
	{
		SitesConfig sitesConfig = new SitesConfig();
		Map<String, SiteConfig> sitesMap = new HashMap<String, SiteConfig>();

		File siteConfigDir = ConfigurationURLUtil.loadSiteConfDir();
		SiteConfig siteConfig;
		for (File file : siteConfigDir.listFiles(new XMLFileFilter()))
		{
			siteConfig = createSiteConfig(file, categorizerConfig);
			if (siteConfig != null)
			{
				sitesMap.put(siteConfig.getSiteName(), siteConfig);
			}
		}
		sitesConfig.setSitesMap(sitesMap);

		return sitesConfig;
	}

	public SiteConfig createSiteConfig(File siteConfigFile, CategorizerConfig categorizerConfig)
	{
		try
		{
			FileInputStream stream = new FileInputStream(siteConfigFile);
			SiteConfig createSiteConfig = createSiteConfig(stream, categorizerConfig);
			stream.close();
			return createSiteConfig;
		}
		catch (Throwable e)
		{
			getLogger().error("Can not create site config from " + siteConfigFile.getAbsolutePath(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public SiteConfig createSiteConfig(InputStream siteConfigStream, CategorizerConfig categorizerConfig)
	{
		SiteConfig siteConfig = new SiteConfig();

		try
		{
			XMLConfiguration conf = new XMLConfiguration();
			conf.load(siteConfigStream);
			XMLConfiguration ecommerceConfig = null;
			XMLConfiguration datafeedConfig = null;
			CategoryConfig categoryConfig = null;

			siteConfig.setSiteName(conf.getString("[@name]", ""));

			siteConfig.setEcommerceConfig(conf.getString("[@ecommerce]", ""));
			siteConfig.setDatafeedConfig(conf.getString("datafeed[@name]", ""));

			if (StringUtils.isNotBlank(siteConfig.getEcommerceConfig()))
			{
				ecommerceConfig = new XMLConfiguration(ConfigurationURLUtil.loadECommerceConfig(siteConfig.getEcommerceConfig()));
			}

			if (StringUtils.isNotBlank(siteConfig.getDatafeedConfig()))
			{
				datafeedConfig = new XMLConfiguration(ConfigurationURLUtil.loadDatafeedConfig(siteConfig.getDatafeedConfig()));
			}

			siteConfig.setAffiliateClass(conf.getString("affiliate", ""));

			siteConfig.setBrandXQuery(conf.getString("parser.xquery.brand", ""));
			if (StringUtils.isBlank(siteConfig.getBrandXQuery()) && ecommerceConfig != null)
			{
				siteConfig.setBrandXQuery(ecommerceConfig.getString("parser.xquery.brand", ""));
			}

			siteConfig.setModelXQuery(conf.getString("parser.xquery.model", ""));
			if (StringUtils.isBlank(siteConfig.getModelXQuery()) && ecommerceConfig != null)
			{
				siteConfig.setModelXQuery(ecommerceConfig.getString("parser.xquery.model", ""));
			}
			
			siteConfig.setBreadcrumbXQuery(conf.getString("parser.xquery.breadcrumb", ""));
			if (StringUtils.isBlank(siteConfig.getBreadcrumbXQuery()) && ecommerceConfig != null)
			{
				siteConfig.setBreadcrumbXQuery(ecommerceConfig.getString("parser.xquery.breadcrumb", ""));
			}

			siteConfig.setCategory(conf.getString("info.category", DEFAULT_CAT));
			categoryConfig = categorizerConfig.getCategoryConfig(siteConfig.getCategory());

			siteConfig.setCrawlDelay(conf.getInt("crawler.delay", DEFAULT_CRAWL_DELAY));

			List<String> datafeedUrlList = conf.getList("datafeed.datafeedurl");
			if (datafeedUrlList != null)
			{
				ArrayList list = new ArrayList<String>();
				list.addAll(datafeedUrlList);
				siteConfig.setDatafeedUrlList(list);
			}

			siteConfig.setExecutingip(conf.getString("datafeed.executingip", ""));
			
			// set datafeed url list before this if else to check if site has a datafeed
			siteConfig.setSpliturl(conf.getBoolean("datafeed.spliturl", true));
			
			siteConfig.setCron(conf.getString("datafeed.cron", ""));
			if(StringUtils.isBlank(siteConfig.getCron()))
			{
				if (siteConfig.hasDatafeed())
				{
					siteConfig.setCron(conf.getString("cronjob", DEFAULT_DATAFEED_CRONJOB));
				}
				else
				{
					siteConfig.setCron(conf.getString("cronjob", DEFAULT_CRAWLER_CRONJOB));
				}				
			}

			siteConfig.setDatafeedProductsXPath(conf.getString("datafeed.products", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedProductsXPath()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedProductsXPath(datafeedConfig.getString("products", ""));
			}
			
			siteConfig.setDatafeedIsNamespaceAware(conf.getBoolean("datafeed.namespaceaware", false));
			

			siteConfig.setDatafeedPricePattern(conf.getString("datafeed.pricepattern.pattern", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedPricePattern()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedPricePattern(datafeedConfig.getString("pricepattern.pattern", ""));
			}
			siteConfig.setKdvValue(conf.getInt("datafeed.kdvValue",0));
			siteConfig.setDatafeedPriceDecimalSeparator(conf.getString("datafeed.pricepattern.decimalseparator", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedPriceDecimalSeparator()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedPriceDecimalSeparator(datafeedConfig.getString("pricepattern.decimalseparator", ""));
			}

			siteConfig.setDatafeedPriceThousandSeparator(conf.getString("datafeed.pricepattern.thousandseparator", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedPriceThousandSeparator()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedPriceThousandSeparator(datafeedConfig.getString("pricepattern.thousandseparator", ""));
			}

			siteConfig.setDatafeedBrandXPath(conf.getString("datafeed.brand", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedBrandXPath()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedBrandXPath(datafeedConfig.getString("brand", ""));
			}

			siteConfig.setDatafeedModelXPath(conf.getString("datafeed.model", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedModelXPath()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedModelXPath(datafeedConfig.getString("model", ""));
			}

			siteConfig.setDatafeedBreadcrumbXPath(conf.getString("datafeed.breadcrumb", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedBreadcrumbXPath()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedBreadcrumbXPath(datafeedConfig.getString("breadcrumb", ""));
			}

			siteConfig.setDatafeedCurrencyXPath(conf.getString("datafeed.currency", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedCurrencyXPath()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedCurrencyXPath(datafeedConfig.getString("currency", ""));
			}

			siteConfig.setDatafeedImageElementName(conf.getString("datafeed.image", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedImageElementName()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedImageElementName(datafeedConfig.getString("image", ""));
			}

			siteConfig.setDatafeedNameXPath(conf.getString("datafeed.name", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedNameXPath()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedNameXPath(datafeedConfig.getString("name", ""));
			}

			siteConfig.setDatafeedPriceXPath(conf.getString("datafeed.price", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedPriceXPath()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedPriceXPath(datafeedConfig.getString("price", ""));
			}

			siteConfig.setDatafeedProductUrlXPath(conf.getString("datafeed.producturl", ""));
			if (StringUtils.isBlank(siteConfig.getDatafeedProductUrlXPath()) && datafeedConfig != null)
			{
				siteConfig.setDatafeedProductUrlXPath(datafeedConfig.getString("producturl", ""));
			}

			HashMap<String, String> datafeedPropertyXPathMap = new HashMap<String, String>();
			String datafeedPropertyXPath = "";
			for (CategoryPropertyConfig propertyConfig : categoryConfig.getPropertyMap().values())
			{
				datafeedPropertyXPath = conf.getString("datafeed.properties." + propertyConfig.getName(), "");
				if (StringUtils.isBlank(datafeedPropertyXPath) && datafeedConfig != null)
				{
					datafeedPropertyXPath = datafeedConfig.getString("properties." + propertyConfig.getName(), "");
				}
				datafeedPropertyXPathMap.put(propertyConfig.getName(), datafeedPropertyXPath);
			}
			siteConfig.setDatafeedPropertyXPathMap(datafeedPropertyXPathMap);

			siteConfig.setDatafeedType(conf.getString("datafeed[@type]", SiteConfig.XML_DATAFEED));
			siteConfig.setUrl(conf.getString("info.url", ""));

			try
			{
				URL url = new URL(siteConfig.getUrl());
				String domainName = url.getHost();
				domainName = domainName.replace("www.", "");
				siteConfig.setDomainName(domainName);
			}
			catch (Throwable e)
			{
				siteConfig.setDomainName(siteConfig.getSiteName());
			}

			TreeSet<String> ignoreSet = new TreeSet<String>();

			List<String> siteIgnoreRuleList = conf.getList("crawler.rule.ignorerules.rule");
			List<String> siteExcludedIgnoreRuleList = conf.getList("crawler.rule.ignorerules.excludedrule");
			ignoreSet.addAll(siteIgnoreRuleList);
			ignoreSet.removeAll(siteExcludedIgnoreRuleList);

			if (ecommerceConfig != null)
			{
				List<String> ecommerceIgnoreRuleList = ecommerceConfig.getList("crawler.rule.ignorerules.rule");
				List<String> ecommerceExcludedIgnoreRuleList = ecommerceConfig.getList("crawler.rule.ignorerules.excludedrule");
				ignoreSet.addAll(ecommerceIgnoreRuleList);
				ignoreSet.removeAll(ecommerceExcludedIgnoreRuleList);
			}

			siteConfig.setIgnoreList(ignoreSet);

			siteConfig.setImage(conf.getString("info.image", ""));

			siteConfig.setImageXQuery(conf.getString("parser.xquery.image", ""));
			if (StringUtils.isBlank(siteConfig.getImageXQuery()) && ecommerceConfig != null)
			{
				siteConfig.setImageXQuery(ecommerceConfig.getString("parser.xquery.image", ""));
			}

			siteConfig.setNameXQuery(conf.getString("parser.xquery.name", ""));
			if (StringUtils.isBlank(siteConfig.getNameXQuery()) && ecommerceConfig != null)
			{
				siteConfig.setNameXQuery(ecommerceConfig.getString("parser.xquery.name", ""));
			}

			siteConfig.setPagingRule(conf.getString("crawler.rule.paging", ""));

			siteConfig.setPriceXQuery(conf.getString("parser.xquery.price", ""));
			if (StringUtils.isBlank(siteConfig.getPriceXQuery()) && ecommerceConfig != null)
			{
				siteConfig.setPriceXQuery(ecommerceConfig.getString("parser.xquery.price", ""));
			}

			HashMap<String, String> propertyXQueryMap = new HashMap<String, String>();
			String propertyXQuery = "";
			for (CategoryPropertyConfig propertyConfig : categoryConfig.getPropertyMap().values())
			{
				propertyXQuery = conf.getString("parser.xquery.properties." + propertyConfig.getName(), "");
				if (StringUtils.isBlank(propertyXQuery) && ecommerceConfig != null)
				{
					propertyXQuery = ecommerceConfig.getString("parser.xquery.properties." + propertyConfig.getName(), "");
				}
				
				if(StringUtils.isNotBlank(propertyXQuery))
				{
					propertyXQueryMap.put(propertyConfig.getName(), propertyXQuery);	
				}				
			}
			siteConfig.setPropertyXQueryMap(propertyXQueryMap);

			siteConfig.setRankHelper(conf.getString("rankhelper", ""));
			siteConfig.setRuleClassName(conf.getString("crawler.rule.class", ""));
			siteConfig.setSingleBrand(conf.getString("parser.rule.singleBrand", ""));
			siteConfig.setSiteEncoding(conf.getString("info.encoding", ""));
			siteConfig.setSiteRank(conf.getInt("info.siterank", 50));
		}
		catch (Throwable e)
		{
			siteConfig = null;
			throw new RuntimeException(e);
		}

		return siteConfig;
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
