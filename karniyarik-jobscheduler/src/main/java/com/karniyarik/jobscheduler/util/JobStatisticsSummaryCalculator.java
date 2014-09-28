package com.karniyarik.jobscheduler.util;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;

import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.jobscheduler.JobSchedulerAdmin;

public class JobStatisticsSummaryCalculator
{
	private static String IMG_ROOT = "http://chart.apis.google.com/chart?";
	public static JobStatisticsSummary calculateSummary(JobExecutionStat stat, int width, int height)
	{
		JobStatisticsSummary summ = new JobStatisticsSummary();
		summ.setSiteName(stat.getSiteName());
		
		DecimalFormat format = new DecimalFormat("#0");
		
		int total = stat.getParsedPageCount();
		int missed = stat.getProductMissCount();
		int duplicate = stat.getDuplicateProductCount();
		int accepted = stat.getProductCount();
		
		double missRatio = 0;
		double duplicateRatio = 0; 
		double acceptedRatio = 0;
		
		if(total != 0)
		{
			missRatio = missed*100.0d/total;
			duplicateRatio = duplicate*100.0d/total;
			acceptedRatio = accepted*100.0d/total;
		}
		String data = "t:" + duplicate  + "," + missed + "," + accepted;
		String labels = encode("Duplicate("+format.format(duplicateRatio)+")|Miss("+format.format(missRatio)+")|Accepted("+format.format(acceptedRatio)+")");
		String legend = encode(duplicate + "|"  +  missed + "|" + accepted);
		String productImage = IMG_ROOT + "chs=" + width + "x" +height+ "&cht=p3&chco=990066&chma=|0,5&chtt=Veri+Toplama&chts=676767,10.5&chd=" + data + "&chdl=" + legend + "&chl=" + labels;
		summ.setRatioProductMiss(missRatio);
		summ.setRatioProductDuplicate(duplicateRatio);
		summ.setRatioProductAccepted(acceptedRatio);
		summ.setImageProductRatio(productImage);
		
		long time = stat.getWindowedAvgFetchTime();
		int timePercentage = 0;
		if(time != 0)
		{
			time = time > 10000 ? 10000 : time; 
			timePercentage = 100 - new Double(time*100d/10000d).intValue();  			
		}
		String fetchTimeImage = IMG_ROOT + "chs=" + width + "x" +height+ "&cht=gm&chts=676767,10.5&chco=FF9900,990066|FFFF88|80C65A&chtt=Ortalama+Sayfa+Indirme&chd=t:" + timePercentage;
		summ.setFetchTimePercentage(timePercentage);
		summ.setImageFetchTime(fetchTimeImage);

		Object[] ratioChartStr = prepareRatioChart("Resim", stat.getImagesParsedCount(), stat.getImagesMissedCount(), format, width, height);
		summ.setRatioParsedImage((Double)ratioChartStr[0]);
		summ.setRatioMissedImage((Double)ratioChartStr[1]);
		summ.setImageImageRatio((String)ratioChartStr[2]);
		
		ratioChartStr = prepareRatioChart("Marka", stat.getBrandsParsedCount(), stat.getBrandsMissedCount(), format, width, height);
		summ.setRatioParsedBrand((Double)ratioChartStr[0]);
		summ.setRatioMissedBrand((Double)ratioChartStr[1]);
		summ.setImageBrandRatio((String)ratioChartStr[2]);
		
		ratioChartStr = prepareRatioChart("Breadcrumb", stat.getBreadcrumbsParsedCount(), stat.getBreadcrumbsMissedCount(), format, width, height);
		summ.setRatioParsedBreadcrumb((Double)ratioChartStr[0]);
		summ.setRatioMissedBreadcrumb((Double)ratioChartStr[1]);
		summ.setImageBreadcrumbRatio((String)ratioChartStr[2]);
		
		String parsedMissedRatiosImage = IMG_ROOT + "chs=" + width + "x" +height+ "&chxt=y&chbh=a,20,20&cht=bvg&chco=EBCCE1,C266A3,990066&chg=0,25&chma=0,0,6,4&chtt=Parsed+Content&chts=676767,10.5&chm=tMarka,000000,0,-1,11|tResim,000000,1,-1,11|tBreadcrumb,000000,2,-1,11&chd=t:" + 
			summ.getRatioParsedBrand() + "|" + summ.getRatioParsedImage() + "|"+summ.getRatioParsedBreadcrumb();
		
		summ.setImageRatios(parsedMissedRatiosImage);
		
		//calculate overall health
		double totalHealth = summ.getRatioProductAccepted() * 7 + summ.getRatioParsedBrand()*1 + summ.getRatioParsedImage()*1 + timePercentage;
		
		double overallHealth = totalHealth / 10;
		
		List<JobExecutionStat> history = JobSchedulerAdmin.getInstance().getStatHistory(stat.getSiteName(), 3);
		
		int failedcount = 0;;		
		for(JobExecutionStat statHistoryItem: history)
		{
			if(statHistoryItem.getStatus().hasFailed())
			{
				failedcount++;
			}
		}
		if(failedcount > 2)
		{
			overallHealth = 5;
		}
		
		summ.setOverallHealth(overallHealth);
		return summ;
	}
	
	public static Object[] prepareRatioChart(String title, int parsed, int missed, DecimalFormat format, int width, int height)
	{
		int total = parsed + missed;
		Double parsedRatio = 0d;
		Double missedRatio = 0d;
		if(total!= 0)
		{
			parsedRatio = parsed*100d/total;
			missedRatio = missed*100d/total;
		}
		String data = "t:" + parsed+ "," + missed;
		String labels = encode("Başarılı("+format.format(parsedRatio)+")|Hatalı("+format.format(missedRatio)+")");
		String legends = encode(parsed+"|"+missed);
		
		String img = IMG_ROOT + "chs=" + width + "x" +height+ "&cht=p3&chco=990066&chma=|0,5&chtt=" + title + "&chts=676767,10.5&chd=" + data + "&chdl=" + legends + "&chl=" + labels;
		Object[] result = new Object[3];
		result[0] = parsedRatio;
		result[1] = missedRatio;
		result[2] = img;
		
		return result;
	}
	
	public static String encode(String str)
	{
		String result = str;
		try{
			result = URLEncoder.encode(str, StringUtil.DEFAULT_ENCODING);
		}catch(Throwable t){}
		return result;
	}
}
