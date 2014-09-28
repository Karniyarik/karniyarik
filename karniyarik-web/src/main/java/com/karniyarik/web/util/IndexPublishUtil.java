package com.karniyarik.web.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.common.util.StreamUtil;

public class IndexPublishUtil
{

	public final void createPublishDirectory() {
		
		SearchConfig searchConf = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig();
		
		if (searchConf.getPublishIndex()) {
			DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
			String dir = deploymentConfig.getSftpPublishDirectory();
			
			try
			{
				File file = new File(StreamUtil.getURL(dir).toURI());
				if (!file.exists()) {
					FileUtils.forceMkdir(file);
				}
			}
			catch (Throwable e)
			{
				getLogger().error("Can not generate index publish directory", e);
			}
		}
	}
	
	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}
	
}
