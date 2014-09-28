package com.karniyarik.statistics.remote;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.quartz.JobExecutionException;

import com.karniyarik.common.statistics.vo.ProductClickLog;
import com.karniyarik.common.statistics.vo.SearchLog;
import com.karniyarik.common.statistics.vo.SiteStat;
import com.karniyarik.statistics.SaveStatisticsJob;
import com.karniyarik.statistics.SiteClickStatDB;
import com.karniyarik.statistics.StatisticsCollector;
import com.karniyarik.statistics.dao.SiteStatDAO;

@Path("/api")
public class StatisticsService
{
	@PUT
	@Path("sitestat")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response saveSiteStat(SiteStat stat)
	{
		new SiteStatDAO().saveSiteStat(stat);

		return Response.ok().build();
	}

	@PUT
	@Path("hit")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response productClicked(ProductClickLog clickLog)
	{
		StatisticsCollector.getInstance().collectProductClick(clickLog);
		return Response.ok().build();
	}

	@PUT
	@Path("searchlog")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response searchLog(SearchLog searchLog)
	{
		StatisticsCollector.getInstance().collectSearchLog(searchLog);

		return Response.ok().build();
	}

	@GET
	@Path("savestatstest")
	public Response saveStatsForTest()
	{
		try
		{
			new SaveStatisticsJob().execute(null);
		}
		catch (JobExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	
	@GET
	@Path("reimport")
	public Response reimportSearchLogs()
	{
		StatisticsCollector.getInstance().importLogs();
		return Response.ok().build();
	}
	
	@GET
	@Path("gecir")
	public Response moveOldLogs()
	{
		new SiteClickStatDB().run();
		return Response.ok().build();
	}

}