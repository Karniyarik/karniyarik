package com.karniyarik.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.config.system.WebConfig;

public class SiteConfUpdateUtil
{

	private final String	ARCHIVE_NAME_PARAMETER	= "a";
	private final String	OPERATION_PARAMETER = "o";
	private final String 	REFRESH_OPERATION = "refresh";
	private final String 	DELETE_OPERATION = "delete";

	public void callSiteConfRefreshServlets(String archiveName)
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		WebConfig webConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig();
		
		callSiteConfRefreshServlet(deploymentConfig.getMasterWebUrl(), webConfig.getSiteConfRefreshServlet(), archiveName, REFRESH_OPERATION);
		//callSiteConfRefreshServlet(deploymentConfig.getSlaveWebUrl(), webConfig.getSiteConfRefreshServlet(), archiveName, REFRESH_OPERATION);
	}
	
	public void callDeleteSiteConfArchiveServlet(String archiveName) {
		WebConfig webConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig();
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		callSiteConfRefreshServlet(deploymentConfig.getMasterWebUrl(), webConfig.getSiteConfRefreshServlet(), archiveName, DELETE_OPERATION);
	}

	private void callSiteConfRefreshServlet(String serverUrl, String servlet, String archiveName, String operation)
	{
		URL servletURL = null;
		URLConnection connection = null;
		InputStream is = null;

		try
		{
			String tail = servlet + "?" + ARCHIVE_NAME_PARAMETER + "=" + archiveName + "&" + OPERATION_PARAMETER + "=" + operation;

			if (StringUtils.isNotBlank(serverUrl))
			{
				if(!tail.startsWith("/") && !serverUrl.endsWith("/"))
				{
					serverUrl += "/";
				}
				
				serverUrl += tail;

				servletURL = new URL(serverUrl);
				connection = servletURL.openConnection();

				connection.connect();
				is = connection.getInputStream();
				String response = IOUtils.toString(is);
				is.close();

				if (!Boolean.parseBoolean(response))
				{
					getLogger().error("site conf refresh servlet failed on server " + serverUrl + " operation: " + operation);
				}
			}

			servletURL = null;
			connection = null;
			is = null;
			tail = null;
		}
		catch (Throwable e)
		{
			getLogger().error("Could not sync site confs with server " + serverUrl + " operation: " + operation, e);
		}
	}

	public File createSiteConfArchive()
	{
		File siteConfDir = ConfigurationURLUtil.loadSiteConfDir();
		File logosDir = ConfigurationURLUtil.loadLogosDir();

		File zipFile = null;

		try
		{
			zipFile = getZipFile();
			
			if (!zipFile.getParentFile().exists()) {
				FileUtils.forceMkdir(zipFile.getParentFile());
			}
			
			if (zipFile.exists())
			{
				FileUtils.forceDelete(zipFile);
			}

			zipFile.createNewFile();
			final OutputStream outStream = new FileOutputStream(zipFile);
			ArchiveOutputStream out = new ArchiveStreamFactory().createArchiveOutputStream("zip", outStream);

			for (File file : siteConfDir.listFiles())
			{
				out.putArchiveEntry(new ZipArchiveEntry(file.getName()));
				IOUtils.copy(new FileInputStream(file), out);
				out.closeArchiveEntry();
			}

			for (File file : logosDir.listFiles())
			{
				out.putArchiveEntry(new ZipArchiveEntry(file.getName()));
				IOUtils.copy(new FileInputStream(file), out);
				out.closeArchiveEntry();
			}

			outStream.flush();
			out.finish();
			outStream.close();
			
		}
		catch (Throwable e)
		{
			getLogger().error("Can not create site conf archive", e);
		}

		return zipFile;
	}

	public void extractSiteConfArchive(String archiveName)
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String publishPath = deploymentConfig.getSftpPublishDirectory();

		if (!publishPath.endsWith(File.separator))
		{
			publishPath += File.separator;
		}

		publishPath += archiveName;

		OutputStream out = null;
		ArchiveInputStream in = null;
		ZipArchiveEntry entry = null;
		InputStream is = null;
		File archive = null;
		File siteConfDir = null;
		File siteImagesDir = null;

		try
		{

			archive = new File(StreamUtil.getURL(publishPath).toURI());

			if (archive.exists())
			{
				siteConfDir = ConfigurationURLUtil.loadSiteConfDir();
				siteImagesDir = ConfigurationURLUtil.loadSiteImagesDir();

				is = new FileInputStream(archive);
				in = new ArchiveStreamFactory().createArchiveInputStream("zip", is);

				entry = (ZipArchiveEntry) in.getNextEntry();

				while (entry != null)
				{
					if (entry.getName().endsWith("xml"))
					{
						out = new FileOutputStream(new File(siteConfDir, entry.getName()));
					}
					else
					{
						out = new FileOutputStream(new File(siteImagesDir, entry.getName()));
					}

					IOUtils.copy(in, out);
					out.close();
					entry = (ZipArchiveEntry) in.getNextEntry();
				}
			}
			else
			{
				getLogger().error("Can not extract site conf archive " + archive.getAbsolutePath() + " does not exist!");
			}

		}
		catch (Throwable e)
		{
			getLogger().error("Can not extract site conf archive " + archiveName, e);
			throw new RuntimeException("Can not extract site conf archive " + archiveName, e);
		}
		finally
		{
			try
			{
				if(in != null) {in.close();};
			}
			catch (Throwable e)
			{
				getLogger().error("Can not close site conf archive input stream" + archiveName, e);
			}
			out = null;
			in = null;
			entry = null;
			is = null;
			archive = null;
			publishPath = null;
			siteConfDir = null;
			siteImagesDir = null;
		}
	}

	public boolean deleteArchive(String archiveName)
	{
		boolean deleted = true;
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String publishPath = deploymentConfig.getSftpPublishDirectory();

		if (!publishPath.endsWith(File.separator))
		{
			publishPath += File.separator;
		}

		publishPath += archiveName;

		File archive = null;
		try
		{
			archive = new File(StreamUtil.getURL(publishPath).toURI());
			if (archive.exists())
			{
				FileUtils.forceDelete(archive);
			}
			else
			{
				getLogger().error("Can not delete site conf archive " + archive.getAbsolutePath() + " does not exist!");
				deleted = false;
			}
		}
		catch (Throwable e)
		{
			getLogger().error("Can not delete site conf archive " + archive.getAbsolutePath(), e);
			deleted = false;
		}

		return deleted;
	}

	private File getZipFile() throws URISyntaxException
	{
		String zipPath = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getIndexDirectory();
		
		zipPath += File.separator + "siteconf" + "_" + UUID.randomUUID().toString().replaceAll("-", "") + ".zip";

		URI zipURI = StreamUtil.getURL(zipPath).toURI();
		return new File(zipURI);
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

	public static void main(String[] args)
	{
		new SiteConfUpdateUtil().createSiteConfArchive();
	}

}
