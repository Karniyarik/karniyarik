package com.karniyarik.web.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.base.ConfigurationBundle;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.config.site.SitesConfigFactory;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.util.ConfigurationURLUtil;
import com.karniyarik.common.util.SiteConfUpdateUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.search.solr.SOLRIndexerPool;
import com.karniyarik.search.solr.SOLRIndexerProxy;
import com.karniyarik.web.util.WebApplicationDataHolder;

@SuppressWarnings("serial")
public class SiteConfRefreshServlet extends HttpServlet
{

	private final String	ARCHIVE_NAME_PARAMETER	= "a";
	private final String	OPERATION_PARAMETER		= "o";
	private final String	REFRESH_OPERATION		= "refresh";
	private final String	DELETE_OPERATION		= "delete";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String archiveName = request.getParameter(ARCHIVE_NAME_PARAMETER);
		String operation = request.getParameter(OPERATION_PARAMETER);
		if (StringUtils.isNotBlank(archiveName) && StringUtils.isNotBlank(operation))
		{
			if (operation.equals(REFRESH_OPERATION))
			{
				try
				{
					new SiteConfUpdateUtil().extractSiteConfArchive(archiveName);
					KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().refreshSitesConfig();
					WebApplicationDataHolder.getInstance().refreshSiteData();					
					response.getWriter().print(true);
				}
				catch (Throwable e)
				{
					getLogger().error("Can not refresh site confs", e);
					response.getWriter().print(false);
				}
			}
			else if (operation.equals(DELETE_OPERATION))
			{
				boolean deleted = new SiteConfUpdateUtil().deleteArchive(archiveName);
				response.getWriter().print(deleted);
			}
		}
	}
	
	public static void createSiteConfig(String configStr, ServletContext servletContext)
	{
		try {
			ConfigurationBundle configurationBundle = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle();
			CategorizerConfig categorizerConfig = configurationBundle.getCategorizerConfig();
			InputStream stream = new BufferedInputStream(new ByteArrayInputStream(configStr.getBytes()));
			SiteConfig config = new SitesConfigFactory().createSiteConfig(stream, categorizerConfig);
			stream.close();
			
			File siteConfigDir = ConfigurationURLUtil.loadSiteConfDir();
			File output = new File(siteConfigDir.getAbsolutePath() + "/" + config.getSiteName() + ".xml");
			FileUtils.writeStringToFile(output, configStr, StringUtil.DEFAULT_ENCODING);
			
			configurationBundle.getSitesConfig().updateSiteConfig(config);
			
			WebApplicationDataHolder.getInstance().refreshSiteData();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void deleteSiteConfig(String sitename, ServletContext servletContext)
	{
		ConfigurationBundle configurationBundle = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle();
		SitesConfig sitesConfig = configurationBundle.getSitesConfig();
		
		SiteConfig siteConfig = sitesConfig.getSiteConfig(sitename);
		if(siteConfig != null)
		{
			File siteConfigDir = ConfigurationURLUtil.loadSiteConfDir();
			File file = new File(siteConfigDir.getAbsolutePath() + "/" + sitename + ".xml");
			file.delete();
			configurationBundle.getSitesConfig().deleteSiteConfig(sitename);
			
			SOLRIndexerProxy indexer = SOLRIndexerPool.getInstance().getIndexer(siteConfig.getCategory());
			try {
				
				indexer.delete(sitename);
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				SOLRIndexerPool.getInstance().returnIndexer(indexer);	
			}
			
			WebApplicationDataHolder.getInstance().refreshSiteData();
		}
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
