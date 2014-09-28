package com.karniyarik.statistics.remote;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.apikey.APIKey;
import com.karniyarik.common.config.apikey.APIKeyConfig;
import com.karniyarik.common.statistics.vo.DailyStats;
import com.karniyarik.common.statistics.vo.LastSearches;
import com.karniyarik.common.statistics.vo.ProductStatSortOptions;
import com.karniyarik.common.statistics.vo.ProductStats;
import com.karniyarik.common.statistics.vo.SiteSponsorStat;
import com.karniyarik.common.statistics.vo.SiteStat;
import com.karniyarik.common.statistics.vo.SponsorStats;
import com.karniyarik.common.statistics.vo.TopSearches;
import com.karniyarik.common.util.TableNameUtil;
import com.karniyarik.statistics.cache.DailyCache;
import com.karniyarik.statistics.dao.ProductStatisticsDAO;
import com.karniyarik.statistics.dao.SearchLogDAO;
import com.karniyarik.statistics.dao.SiteClickStatDAO;
import com.karniyarik.statistics.dao.SiteStatDAO;

@Path("/report")
public class ReportService
{

	@GET
	@Path("/siteStat")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public SiteStat getCurrentSiteStat(@QueryParam("s") @DefaultValue("") String siteName)
	{
		return new SiteStatDAO().getCurrentSiteStat(siteName);
	}

	@GET
	@Path("/sponsorStat")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public SponsorStats sponsorStats(@QueryParam("s") @DefaultValue("") String siteList, @QueryParam("d") long date)
	{

		SponsorStats sponsorStats = new SponsorStats();
		List<SiteSponsorStat> siteSponsorStatList = new ArrayList<SiteSponsorStat>();
		sponsorStats.setSiteSponsorStatList(siteSponsorStatList);

		if (StringUtils.isNotBlank(siteList))
		{
			String[] siteNameList = siteList.split(",");
			ProductStatisticsDAO dao;

			for (String siteName : siteNameList)
			{
				dao = new ProductStatisticsDAO(siteName, TableNameUtil.createProductStatTableName(siteName));
				SiteSponsorStat siteSponsorStatsFromDate = dao.getSiteSponsorStatsFromDate(date);
				DailyCache.getInstance().resetSiteSponsorStat(siteName, siteSponsorStatsFromDate);
				siteSponsorStatList.add(siteSponsorStatsFromDate);
			}
		}

		return sponsorStats;
	}

	@GET
	@Path("/lastSearches")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public LastSearches lastSearches(@QueryParam("c") @DefaultValue("0") int category, @QueryParam("l") @DefaultValue("20") int maxLastSearches)
	{
		return SearchLogDAO.getLastSearches(category, maxLastSearches);
	}

	@GET
	@Path("/topSearches")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public TopSearches topSearches(@QueryParam("c") @DefaultValue("0") int category, @QueryParam("l") @DefaultValue("20") int maxTopSearches)
	{
		return SearchLogDAO.getDailyTopSearches(category, maxTopSearches);
	}

	@GET
	@Path("/weeklyTopSearches")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public TopSearches weeklyTopSearches(@QueryParam("c") @DefaultValue("0") int category, @QueryParam("l") @DefaultValue("20") int maxTopSearches, @QueryParam("d") long date)
	{
		return SearchLogDAO.getWeeklyTopSearches(category, maxTopSearches, date);
	}

	@GET
	@Path("/monthlyTopSearches")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public TopSearches monthlyTopSearches(@QueryParam("c") @DefaultValue("0") int category, @QueryParam("l") @DefaultValue("20") int maxTopSearches, @QueryParam("d") long date)
	{
		return SearchLogDAO.getMonthlyTopSearches(category, maxTopSearches, date);
	}

	@GET
	@Path("/dailyStat")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public DailyStats dailyStats(@QueryParam("s") @DefaultValue("") String siteName, @QueryParam("startDate") long startDate, @QueryParam("endDate") long endDate)
	{
		APIKeyConfig apiKeyConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getApiKeyConfig();
		APIKey apiKey = apiKeyConfig.getAPIKey(siteName);

		SiteClickStatDAO dao = new SiteClickStatDAO();
		DailyStats result = null;

		if (apiKey != null)
		{
			result = dao.getDailyStats(apiKey.getValue(), startDate, endDate);
		}
		else
		{
			result = dao.getDailyStats(siteName, startDate, endDate);
		}

		return result;
	}

	@GET
	@Path("/dailyapistat")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public DailyStats dailyAPIStats(@QueryParam("s") @DefaultValue("") String apiKey, @QueryParam("startDate") long startDate, @QueryParam("endDate") long endDate)
	{
		SiteClickStatDAO dao = new SiteClickStatDAO();
		return dao.getDailyStats(apiKey, startDate, endDate);
	}

	@GET
	@Path("/urlDailyStat")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public DailyStats urlDailyStats(@QueryParam("s") @DefaultValue("") String siteName, @QueryParam("u") @DefaultValue("") String url, @QueryParam("startDate") long startDate,
			@QueryParam("endDate") long endDate)
	{
		String productStatTable = TableNameUtil.createProductStatTableName(siteName);
		ProductStatisticsDAO dao = new ProductStatisticsDAO(siteName, productStatTable);
		return dao.getDailyStatsByUrl(url, startDate, endDate);
	}

	@GET
	@Path("/productStat")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public ProductStats productStats(@QueryParam("s") @DefaultValue("") String siteName, @QueryParam("startDate") long startDate, @QueryParam("endDate") long endDate,
			@QueryParam("sortColumn") int sortColumn, @QueryParam("sortType") int sortType, @QueryParam("page") int page, @QueryParam("pageSize") int pageSize)
	{
		String productStatTable = TableNameUtil.createProductStatTableName(siteName);
		ProductStatisticsDAO dao = new ProductStatisticsDAO(siteName, productStatTable);
		ProductStatSortOptions options = new ProductStatSortOptions(sortColumn, sortType);
		return dao.getProductStats(startDate, endDate, options, page, pageSize);
	}
}
