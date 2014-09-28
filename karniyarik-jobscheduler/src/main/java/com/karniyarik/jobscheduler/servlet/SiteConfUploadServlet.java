package com.karniyarik.jobscheduler.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.base.ConfigurationBundle;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfigFactory;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.util.ConfigurationURLUtil;
import com.karniyarik.common.util.JerseyUtil;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.jobscheduler.JobSchedulerAdmin;

public class SiteConfUploadServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String tempDir = StreamUtil.getTempDir();
		File file = new File(tempDir + "/uploads");
		if(!file.exists())
		{
			file.mkdir();
		}
		
		DiskFileItemFactory factory = newDiskFileItemFactory(getServletContext(), file);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items;
		try
		{
			items = upload.parseRequest(req);
		}
		catch (FileUploadException e)
		{
			throw new RuntimeException(e);
		}
		

		ConfigurationBundle configurationBundle = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle();
		CategorizerConfig categorizerConfig = configurationBundle.getCategorizerConfig();
		
		List<String> files=new ArrayList<String>();
		for(FileItem item: items)
		{
			//System.out.println(item.getName());
			files.add(item.getName());
			File siteConfigDir = ConfigurationURLUtil.loadSiteConfDir();
			File output = new File(siteConfigDir.getAbsolutePath() + "/" + item.getName());
			String content = item.getString(StringUtil.DEFAULT_ENCODING);

			//this will validate the config
			InputStream stream = new BufferedInputStream(new ByteArrayInputStream(content.getBytes()));
			SiteConfig config = new SitesConfigFactory().createSiteConfig(stream, categorizerConfig);
			stream.close();
			
			//write the config to file
			FileUtils.writeStringToFile(output, content, StringUtil.DEFAULT_ENCODING);
			
			configurationBundle.getSitesConfig().updateSiteConfig(config);
			
			JobSchedulerAdmin.getInstance().refreshStatisticsMap();
			
			JobSchedulerAdmin.getInstance().scheduleSiteQuartzJob(config);
			
			DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
			String webEndPoint = deploymentConfig.getMasterWebUrl() + "/rest/event/siteconf/add";
			JerseyUtil.sendJSONPut(webEndPoint, content, true, 10000, MediaType.TEXT_PLAIN + ";charset=UTF-8");
		}
		
		String filesStr = StringUtils.join(files, ", ");
		resp.sendRedirect(req.getContextPath() + "/uploadxml.jsp?result=success&files=" + filesStr);
	}
	
	 public static DiskFileItemFactory newDiskFileItemFactory(ServletContext context, File repository) {
		 FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(context);
		 DiskFileItemFactory factory = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository);
		 factory.setFileCleaningTracker(fileCleaningTracker);
		 return factory;
	 }
}