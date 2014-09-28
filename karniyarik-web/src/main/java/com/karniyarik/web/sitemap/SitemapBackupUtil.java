package com.karniyarik.web.sitemap;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.WebConfig;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.notifier.ExceptionNotifier;

public class SitemapBackupUtil 
{
	Logger log = Logger.getLogger(SitemapBackupUtil.class);
	
	private static boolean backupMailSent = false;
	
	private String rootPath = "";
	
	public SitemapBackupUtil(String rootPath)
	{
		this.rootPath = rootPath;
	}
	
	public void backupSiteMap()
	{
		try {
			WebConfig webConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig();
			if(webConfig.isBackupEnabled())
			{
				String indexDirectory = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getIndexDirectory();
				
				File file = new File(indexDirectory);
				File parentFile = file.getParentFile();
				String parentPath = parentFile.getAbsolutePath();
				
				String sourceDir = rootPath; 
				String backupDir = parentFile + "/sitemap_backup/";
				
				ExecutorService executor = Executors.newSingleThreadExecutor();
				executor.execute(new SystemCopy(sourceDir, backupDir));
				executor.shutdown();
				if(!backupMailSent)
				{
					//new MailNotifier().sendTextMail("Backup Successfull", "Backup executed\n");
					backupMailSent = true;
				}
			}
		} catch (Exception e)
		{
			KarniyarikLogger.logException("Cannot backup sitemap", e, log);
			ExceptionNotifier.sendException("sitemap-backup-failed", "Cannot backup sitemap", "", e);
			//new MailNotifier().sendTextMail("Sitemap backup failed", "Cannot backup sitemap\n" + ExceptionUtils.getStackTrace(e));
		}
	}
	
	class SystemCopy implements Runnable {
		String sourceDir;
		String backupDir;
		
		public SystemCopy(String sourceDir, String backupDir) {
			this.sourceDir = sourceDir;
			this.backupDir = backupDir;
		}
		
		@Override
		public void run() {
			executeCopyProcess(sourceDir, backupDir);
		}
		
		private void executeCopyProcess(String sourceDir, String backupDir)
		{
			try 
			{
				File sourceDirFile = new File(sourceDir);
				File backupDirFile = new File(backupDir);
				if(backupDirFile.exists())
				{
					FileUtils.deleteDirectory(backupDirFile);
				}
				
				backupDirFile.mkdir();
				
				FileUtils.copyDirectory(sourceDirFile, backupDirFile);
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}	
	
	public static void main(String[] args) {
		new SitemapBackupUtil("H:/1").backupSiteMap();
	}
}
