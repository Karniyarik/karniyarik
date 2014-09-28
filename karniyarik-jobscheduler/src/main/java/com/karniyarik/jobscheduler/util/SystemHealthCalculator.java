package com.karniyarik.jobscheduler.util;

import java.util.List;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.jobscheduler.JobAdminStateFilter;
import com.karniyarik.jobscheduler.JobSchedulerAdmin;

public class SystemHealthCalculator
{
	int totalSize = 0;
	int executing = 0;
	int failed = 0;
	int broken = 0;
	int datafeed = 0;
	int selenium;
	int crawler = 0;
	int sponsoredCount = 0;
	int featuredCount = 0;
	int siteRegCount = 0;
	int hemenal = 0;
	int ideasoft = 0;
	int kobimaster = 0;
	int proje= 0;
	int neticaret= 0;
	int prestashop = 0;
	int noecommerce = 0;
	String statstmage = ""; 
	String ecommerceimage = "";
	String datacollectionimage="";
	String brokenTotalImage = "";
	
	public SystemHealthCalculator()
	{
	}
	
	public void calculate()
	{
		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
		
		List<JobExecutionStat> allStatistics = JobSchedulerAdmin.getInstance().getStatistics();
		List<JobExecutionStat> executingList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.EXECUTING}, allStatistics);
		List<JobExecutionStat> failedList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.FAILED}, allStatistics);
		List<JobExecutionStat> brokenList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.BROKEN}, allStatistics);
		List<JobExecutionStat> datafeedList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.DATAFEED}, allStatistics);
		List<JobExecutionStat> crawlerList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.CRAWLER}, allStatistics);
		List<JobExecutionStat> ideasoftList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.IDEASOFT}, allStatistics);
		List<JobExecutionStat> hemenalList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.HEMENAL}, allStatistics);
		List<JobExecutionStat> kobimasterList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.KOBIMASTER}, allStatistics);
		List<JobExecutionStat> prestashopList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.PRESTASHOP}, allStatistics);
		List<JobExecutionStat> projeList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.PROJE}, allStatistics);
		List<JobExecutionStat> neticaretList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.NETICARET}, allStatistics);
		List<JobExecutionStat> seleniumList = JobSchedulerAdmin.getInstance().getStatistics(new JobAdminStateFilter[]{JobAdminStateFilter.SELENIUM}, allStatistics);
		
		totalSize = sitesConfig.getSiteConfigList().size();
		executing = executingList.size();
		failed = failedList.size();
		broken = brokenList.size();
		datafeed = datafeedList.size();
		selenium = seleniumList.size();
		crawler = crawlerList.size();
		ideasoft = ideasoftList.size();
		hemenal = hemenalList.size();		
		kobimaster = kobimasterList.size();
		prestashop = prestashopList.size();
		proje = projeList.size();
		neticaret = neticaretList.size();
		noecommerce = totalSize - (kobimaster+ideasoft+hemenal+prestashop+proje+neticaret);
		
		statstmage = "http://chart.apis.google.com/chart?chxt=y&chbh=a&chs=300x160&cht=bvg&" +
				"chco=EFEF25,FF9900,FF0000,A2C180,990066,3072F3&" +
				"chtt=Health Params" +
				"&chd=t:" + totalSize + "|" +  broken + "|" +  failed + "|"+  datafeed + "|" + selenium + "|" + crawler +
				"&chdl=Total|Broken|Failed|Datafeed|Selenium|Crawler" + 
				"&chm=" + 
				"t" + totalSize + ",FF0000,0,-1,11|" +
				"t" + broken + ",FF0000,0,-1,11|" +
				"t" + failed + ",FF0000,0,-1,11|" +
				"t" + datafeed + ",FF0000,0,-1,11|" +
				"t" + selenium + ",FF0000,0,-1,11|" +
				"t" + crawler + ",FF0000,0,-1,11|";
		
		ecommerceimage = "http://chart.apis.google.com/chart?chs=200x150&cht=p&" +
				"chco=EFEF25,FF9900,FF0000,A2C180,990066,3072F3&" +
				"chtt=ECommerce Distribution&" +
				"chd=t:" + ideasoft + "," + hemenal + "," + kobimaster + "," + prestashop + "," + proje + "," + neticaret + "," + noecommerce +
				"&chdl=ideasoft|hemenal|kobimaster|prestashop|proje|neticaret|noecommerce";
		
		datacollectionimage = "http://chart.apis.google.com/chart?chs=250x150&cht=p&" +
		"chco=A2C180,FF0000,FF9900&" +
		"chtt=Data Collection Method&" +
		"chd=t:" + datafeed + "," + selenium + "," + crawler+
		"&chdl=Datafeed|Selenium|Crawler";		
		
		brokenTotalImage = "http://chart.apis.google.com/chart?chs=200x150&cht=p&" +
		"chco=A2C180,FF0000&" +
		"chtt=Broken/Healthy&" +
		"chd=t:" + (totalSize - broken) + "," + broken + 
		"&chdl=Healthy|Broken";
	}
	
	public int getBroken()
	{
		return broken;
	}
	
	public int getDatafeed()
	{
		return datafeed;
	}
	
	public int getExecuting()
	{
		return executing;
	}
	
	public int getFailed()
	{
		return failed;
	}
	
	public int getTotalSize()
	{
		return totalSize;
	}
	
	public int getSelenium()
	{
		return selenium;
	}
	
	public int getIdeasoft()
	{
		return ideasoft;
	}
	
	public int getHemenal()
	{
		return hemenal;
	}
	
	public int getKobimaster()
	{
		return kobimaster;
	}
	
	public int getPrestashop()
	{
		return prestashop;
	}
	
	public int getProje()
	{
		return proje;
	}
	
	public int getCrawler()
	{
		return crawler;
	}
	
	public int getNoecommerce()
	{
		return noecommerce;
	}
	
	public String getStatstmage()
	{
		return statstmage;
	}
	
	public String getEcommerceimage()
	{
		return ecommerceimage;
	}
	
	public String getDatacollectionimage()
	{
		return datacollectionimage;
	}
	
	public String getBrokenTotalImage()
	{
		return brokenTotalImage;
	}
	
	public int getNeticaret() {
		return neticaret;
	}
}	
