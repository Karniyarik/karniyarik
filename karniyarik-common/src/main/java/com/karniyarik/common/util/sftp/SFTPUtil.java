package com.karniyarik.common.util.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.exception.ExceptionVO;
import com.karniyarik.common.notifier.ExceptionNotifier;
import com.karniyarik.common.notifier.MailNotifier;
import com.karniyarik.common.util.StreamUtil;

public class SFTPUtil
{
	private static final String	SFTP	= "sftp";
	
	public static final void publishToWebServer(File file) throws RuntimeException
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		publishToServer(deploymentConfig.getSftpPublishDirectory(), file);
	}

	private static final void publishToServer(String publishPath, File file) throws RuntimeException
	{
		if (file != null)
		{
			Session session = null;
			try
			{
				session = FTPConnectionPool.getInstance().getSession();
				Channel channel = session.openChannel(SFTP);
				channel.connect();
				
				ChannelSftp c = (ChannelSftp) channel;

				InputStream inputStream = new FileInputStream(file);

				if (!publishPath.endsWith("/"))
				{
					publishPath += "/";
				}
				publishPath += file.getName();
				int mode = ChannelSftp.OVERWRITE;

				c.put(inputStream, publishPath, mode);
				
				inputStream.close();
				c.disconnect();

				c = null;
				inputStream = null;
				publishPath = null;
				channel = null;
				file = null;
			}
			catch (Throwable e)
			{
				ExceptionNotifier.sendException("ftp-publish-failed", "FTP Publish for " + file.getName() + " failed.", "", e);
				//new MailNotifier().sendTextMail("SFTP Publish Failed", "FTP publish failed for " + file.getName());
				throw new RuntimeException("Can not publish " + file.getAbsolutePath() + " to web server", e);
			}
			finally
			{
				if(session != null)
				{
					FTPConnectionPool.getInstance().returnSession(session);
				}
			}
		}
	}

	
	public static void main(String[] args)
	{
		File file = StreamUtil.getFile("conf/group/udp.xml");
		publishToWebServer(file);
	}
}
