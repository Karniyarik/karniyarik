package com.karniyarik.common.group;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StreamUtil;

public class GroupMemberFactory {
	public static final int NONE = 0;
	public static final int SCHEDULER = 1;
	public static final int EXECUTOR = 2;
	
	public static String ADMIN_GROUP_NAME = "CrawlerGroup";
	

	private static String getThisName(String ip, boolean seleniumCapable, boolean jobClassCapable)
	{
		String result = "";
		
		try
		{
			File file = getExecutorFile("executor.name.properties");
			//development environmentta multiple server ile duzgun calissin diye eklendi
			
			String propertyName = getServerNameHash() + "uuid"; 
			Properties prop = null;
			
			if(file.exists())
			{
				prop = new Properties();
				FileInputStream inStream = new FileInputStream(file);
				prop.load(inStream);
				inStream.close();
				result = prop.getProperty(propertyName);
			}
			else {
				file.createNewFile();
			}
			
			if(StringUtils.isBlank(result))
			{
				long time = Calendar.getInstance().getTimeInMillis();
				String randomStr = time + ip + seleniumCapable;
				result = UUID.nameUUIDFromBytes(randomStr.getBytes()).toString();
				
				FileOutputStream stream = new FileOutputStream(file);
				if(prop == null)
				{
					prop = new Properties();	
				}
				
				prop.put(propertyName, result);
				prop.store(stream, "Stores the unique id of the executor. Please do not delete.");
				stream.close();
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		return result;
	}
	
	public static void saveThisMemberCapability(GroupMember member)
	{
		try {
			Properties prop = loadExecutorProperties();
			String serverHash = getServerNameHash();
			String seleniumPropertyName = serverHash + "selenium";
			String jobClassPropertyName = serverHash + "jobclass";
			String gelirortaklariFeed = serverHash + "go";
			prop.put(seleniumPropertyName, Boolean.toString(member.isSeleniumCapable()));
			prop.put(jobClassPropertyName, Boolean.toString(member.isJobsWithClassCapable()));
			prop.put(gelirortaklariFeed, Boolean.toString(false));
			writeExecutorPropertyFile(prop);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static GroupMember getThisMember()
	{
		try
		{
			String ip = getIp();
			
			String uuid = ""; 
					
			boolean seleniumCapable = false;
			boolean jobsWithClassCapable = false;
			boolean gelirortaklariCapable = false;

			try
			{
				Properties prop = loadExecutorProperties();
				String serverHash = getServerNameHash();
				String seleniumPropertyName = serverHash + "selenium";
				String jobClassPropertyName = serverHash + "jobclass";
				String gelirortaklariFeedName = serverHash + "go";				
				String selenium = prop.getProperty(seleniumPropertyName);
				String classCapable = prop.getProperty(jobClassPropertyName);
				String gelirortaklariCapableStr = prop.getProperty(gelirortaklariFeedName);
				
				boolean propChanged = false;
				
				System.out.println("Selenium: " + selenium + " - Class: " + classCapable);
				
				if(StringUtils.isNotBlank(selenium))
				{
					seleniumCapable = Boolean.valueOf(selenium);
					//System.out.println("Selenium: " + seleniumCapable);
				}
				else
				{
					prop.put(seleniumPropertyName, "false");
					propChanged = true;
				}
				
				if(StringUtils.isNotBlank(classCapable))
				{
					jobsWithClassCapable = Boolean.valueOf(classCapable);
					//System.out.println("Job class: " + jobsWithClassCapable);
				}
				else
				{
					prop.put(jobClassPropertyName, "false");
					propChanged = true;
				}
				
				if(StringUtils.isNotBlank(gelirortaklariCapableStr))
				{
					gelirortaklariCapable = Boolean.valueOf(gelirortaklariCapableStr);
				}
				else
				{
					prop.put(gelirortaklariFeedName, "false");
					propChanged = true;
				}
				
				if(propChanged)
				{
					writeExecutorPropertyFile(prop);					
				}
				
				uuid = getThisName(ip, seleniumCapable, jobsWithClassCapable);
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}

			GroupMember member = new GroupMember();
			member = new GroupMember();
			member.setUuid(uuid);
			member.setIp(ip);
			member.setSeleniumCapable(seleniumCapable);
			member.setJobsWithClassCapable(jobsWithClassCapable);
			member.setGelirotaklariCapable(gelirortaklariCapable);
			return member;
		}
		catch (UnknownHostException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static String getIp() throws UnknownHostException
	{
		String ip = null; 
		
		try {
			Socket s = new Socket("8.8.4.4", 53);
			ip = s.getLocalAddress().getHostAddress();
			s.close();
		} catch (Throwable e1) {
			InetAddress addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
		}
		return ip;
	}

	private static void writeExecutorPropertyFile(Properties prop)
			throws FileNotFoundException, IOException {
		File file = getExecutorFile("executor.properties");
		FileOutputStream stream = new FileOutputStream(file);
		prop.store(stream, "Stores the capabilities of executor. Please do not delete. You can update the boolean values.");
		stream.close();
	}
	
	private static String getServerNameHash()
	{
		String localIdentifier = System.getProperty("catalina.base");
		String hash = "";
		if(StringUtils.isNotBlank(localIdentifier))
		{
			hash = Integer.toString(localIdentifier.hashCode());
		}
		
		return hash;
	}

	private static Properties loadExecutorProperties() throws IOException, FileNotFoundException {
		
		File file = getExecutorFile("executor.properties");
		if(!file.exists())
		{
			file.createNewFile();
		}
		else
		{
			System.out.println("Executor file exists");

		}
		
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream(file);
		prop.load(inStream);
		inStream.close();
		return prop;
	}
	
	public static File getExecutorFile(String filename)
	{
		String tempDir = StreamUtil.getTempDir();
		File file = new File(tempDir + "/" + filename);
		return file;
	}
	
	public static String getMemberTypeStr(int type)
	{
		String result = "";
		switch(type)
		{
			case EXECUTOR: result = "Executor"; break;
			case SCHEDULER: result = "Scheduler"; break;
			default: result = "Unknown";break;
		}
		
		return result;
	}
}
