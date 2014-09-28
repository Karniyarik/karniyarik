package com.karniyarik.externalrank.alexa;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.util.StreamUtil;

public class AlexaRankRegistry 
{	
	private AlexaSiteInfoList sites = null;
	private Map<String, AlexaSiteInfo> infoMap = new HashMap<String, AlexaSiteInfo>();
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private static AlexaRankRegistry instance = null;
	
	private AlexaRankRegistry() {
		load();
	}
	
	public static AlexaRankRegistry getInstance() {
		if(instance == null)
		{
			instance = new AlexaRankRegistry();
		}
		return instance;
	}
	
	public AlexaSiteInfo getSiteInfo(String sitename)
	{
		lock.readLock().lock();
		AlexaSiteInfo result = infoMap.get(sitename);
		lock.readLock().unlock();
		
		return result;
	}
	
	public AlexaSiteInfoList getSiteInfoList() {
		return sites;
	}

	public void update()
	{
		Date date = new Date();
		Date lastUpdateDate = sites.getLastFetchDate();
		
		if((date.getTime()-lastUpdateDate.getTime()) < 15*24*60*60*1000) 
		{
			AlexaSiteInfoList newSites = loadFromAlexa();
			lock.writeLock().lock();
			constructMap(newSites);
			sites = newSites;
			lock.writeLock().unlock();
		}
	}
	
	private void load() {
		lock.writeLock().lock();
		sites = loadFromFile();
		if(sites == null)
		{
			AlexaSiteInfoList newSites = loadFromAlexa();
			sites = newSites;
		}
		constructMap(sites);
		lock.writeLock().unlock();
	}
	
	private void constructMap(AlexaSiteInfoList newSites)
	{
		infoMap.clear();
		
		for(AlexaSiteInfo siteInfo: newSites.getSites())
		{
			infoMap.put(siteInfo.getSitename(), siteInfo);
		}
	}
		
	private AlexaSiteInfoList loadFromAlexa()
	{
		AlexaSiteInfoList newSites = new AlexaSiteInfoList();
		
		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
		AlexaWebService service = new AlexaWebService();
		
		for(SiteConfig siteConfig: sitesConfig.getSiteConfigList())
		{
			AlexaSiteInfo siteInfo = service.getSiteInfo(siteConfig.getSiteName(), siteConfig.getUrl());
			newSites.getSites().add(siteInfo);
		}
		
		newSites.setLastFetchDate(new Date());
		
		writeToFile(newSites);		
		
		return newSites;
	}

	private AlexaSiteInfoList loadFromFile() 
	{
		try {
			File file = getFile();
			JAXBContext context = JAXBContext.newInstance(AlexaSiteInfoList.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			//note: setting schema to null will turn validator off
			unmarshaller.setSchema(null);
			AlexaSiteInfoList result = AlexaSiteInfoList.class.cast(unmarshaller.unmarshal(file));
			return result;
		} catch (Throwable e) {
			return null;
			//throw new RuntimeException("Cannot read alexa data from file");
		}
	}
	
	private void writeToFile(AlexaSiteInfoList list) 
	{
		try {
			File file = getFile();
			FileOutputStream stream = new FileOutputStream(file);
			
			JAXBContext context = JAXBContext.newInstance(AlexaSiteInfoList.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(list, stream); 
			stream.close(); 
		} catch (Throwable e) {
			throw new RuntimeException("Cannot read alexa data from file");
		}
	}

	private File getFile() {
		String tempDir = StreamUtil.getTempDir();
		File file = new File(tempDir + "/alexa.xml");
		return file;
	}

}
