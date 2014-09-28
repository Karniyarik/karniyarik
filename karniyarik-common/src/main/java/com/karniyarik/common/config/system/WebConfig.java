package com.karniyarik.common.config.system;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;

@SuppressWarnings("serial")
public class WebConfig extends ConfigurationBase {
	private static final String SYSTEM_REFRESHSITEMAP = "/system/refreshsitemap";
	private static final String SYSTEM_MERGEINDEX = "/system/mergeindex";
	private static final String SYSTEM_REFRESHINDEX = "/system/refreshindex";
	private static final String SYSTEM_SITECONFREFRESH = "/system/siteconfrefresh";
	private static final String MONEY_FORMAT = "############.00";
	private static final String RESULT_TIME_FORMAT = "#0.00";
	private static final String CLUSTER_FORMAT = "#0";

	public WebConfig() throws Exception {
		super(ConfigurationURLUtil.getWebConfig());
	}

	public String getMoneyFormat() {
		return getString("web.format[@money]", MONEY_FORMAT);
	}

	public String getResultTimeFormat() {
		return getString("web.format[@resulttime]", RESULT_TIME_FORMAT);
	}

	public String getClusterFormat() {
		return getString("web.format[@pricecluster]", CLUSTER_FORMAT);
	}

	public String getRefreshIndexServlet() {
		return getString("web.refreshindexservlet", SYSTEM_REFRESHINDEX);
	}

	public String getMergeIndexServlet() {
		return getString("web.mergeindexservlet", SYSTEM_MERGEINDEX);
	}

	public String getRefreshSitemapServlet() {
		return getString("web.refreshsitemapservlet", SYSTEM_REFRESHSITEMAP);
	}
	
	public String getSiteConfRefreshServlet() {
		return getString("web.siteconfrefreshservlet", SYSTEM_SITECONFREFRESH);
	}

	public String getTwitterUsername() {
		return getString("web.twitter.username", "");
	}

	public String getTwitterPassword() {
		return getString("web.twitter.password", "");
	}

	public String getFacebookUserName() {
		return getString("web.facebook.username", "");
	}

	public String getFacebookKey() {
		return getString("web.facebook.apikey", "");
	}

	public String getFacebookSecret() {
		return getString("web.facebook.secret", "");
	}

	public String getFacebookSessionCode() {
		return getString("web.facebook.code", "");
	}

	public String getFacebookPassword() {
		return getString("web.facebook.password", "");
	}
	
	public Boolean getStatusUpdatesEnabled() {
		return getBoolean("web.statusupdatesenabled", Boolean.FALSE);
	}
	
	public String getBitlyLogin() {
		return getString("web.bitly.login", "");
	}
	
	public String getBitlyPassword() {
		return getString("web.bitly.password", "");
	}
	
	public String getBitlyApiKey() {
		return getString("web.bitly.apikey", "");
	}

	public String getSitemapRootPath() {
		return getString("web.sitemappath", "/www/karniyarik");
	}

	public String getDownloadRootPath() {
		return getString("web.downloadpath", "/www/karniyarik");
	}

	public boolean isBackupEnabled()
	{
		return getBoolean("web.backup", true);
	}
	
}
