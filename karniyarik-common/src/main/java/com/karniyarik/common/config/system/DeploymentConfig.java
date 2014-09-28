package com.karniyarik.common.config.system;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;

/**
 * Configuration object for the database attributes.
 * 
 * @author siyamed
 * 
 */
@SuppressWarnings("serial")
public class DeploymentConfig extends ConfigurationBase
{
	private final String			BASE_DIR						= "C:/basedir";
	private final String			PUBLISH_DIR						= BASE_DIR + "/index_publish";
	private static final String		SFTP_HOST						= "karniyarik.com";
	private static final String		SFTP_USER						= "sshadmin";
	private static final String		SFTP_PASSWORD					= "";
	private static final Integer	SFTP_PORT						= 22;
	
	public DeploymentConfig() throws Exception
	{
		super(ConfigurationURLUtil.getDeploymentConfig());
	}

	public String getBaseDir()
	{
		return getString("deployment.basedir", SFTP_HOST);
	}

	public String getSftpHost()
	{
		return getString("deployment.sftp.host", SFTP_HOST);
	}

	public Integer getSftpPort()
	{
		return getInteger("deployment.sftp.port", SFTP_PORT);
	}

	public String getSftpUser()
	{
		return getString("deployment.sftp.user", SFTP_USER);
	}

	public String getSftpPassword()
	{
		return getString("deployment.sftp.password", SFTP_PASSWORD);
	}

	/**
	 * The directory path to publish index archives from crawler machines to web
	 * server. Do not append anything to this path. Use it directly on both
	 * publishing and reading sides.
	 * 
	 * @return
	 */
	public String getSftpPublishDirectory()
	{
		return getString("deployment.sftp.publishDirectory", PUBLISH_DIR);
	}
	
	public String getJobSchedulerDeploymentURL()
	{
		return getString("deployment.jobscheduler");
	}
	

	public String getEnterpriseDeploymentURL()
	{
		return getString("deployment.enterprise", "");
	}

	public String getStatisticsDeploymentURL()
	{
		return getString("deployment.statistics", "");
	}
	
	public String getMasterWebUrl() {
		return getString("deployment.web.master", "");
	}
	
	public String getSlaveWebUrl() {
		return getString("deployment.web.slave", "");
	}

	public String getSolr() {
		return getString("deployment.solr", "");
	}

}