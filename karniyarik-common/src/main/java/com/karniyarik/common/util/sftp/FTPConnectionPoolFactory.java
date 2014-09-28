package com.karniyarik.common.util.sftp;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.tools.ant.taskdefs.optional.ssh.SSHUserInfo;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;

public class FTPConnectionPoolFactory extends BasePoolableObjectFactory
{
	private JSch jsch = null;
	
	public FTPConnectionPoolFactory()
	{
		jsch = new JSch();
	}
	
	@Override
	public Object makeObject() throws Exception
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		
		String hostname = deploymentConfig.getSftpHost();
		Session session = jsch.getSession(deploymentConfig.getSftpUser(), hostname, deploymentConfig.getSftpPort());
		session.setUserInfo(new SSHUserInfo(deploymentConfig.getSftpPassword(), Boolean.TRUE));
		
		return session;
	}
	
	@Override
	public void passivateObject(Object obj) throws Exception
	{
		Session session = (Session) obj;
		session.disconnect();
	}
	
	@Override
	public boolean validateObject(Object obj)
	{
		Session session = (Session) obj;
		if(session.isConnected())
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void activateObject(Object obj) throws Exception
	{
		Session session = (Session) obj;
		if(!session.isConnected())
		{
			session.connect();
		}
	}
	
	@Override
	public void destroyObject(Object obj) throws Exception
	{
		try
		{
			Session session = (Session) obj;
			if(session.isConnected())
			{
				session.disconnect();
			}
		}
		catch (Exception e)
		{
			//do nothing
		}
	}
}
