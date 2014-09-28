package com.karniyarik.jobexecutor.api;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionException;

import com.karniyarik.common.statistics.vo.JobExecutionContext;
import com.karniyarik.common.statistics.vo.JobExecutionStats;
import com.karniyarik.jobexecutor.JobExecutorAdmin;
import com.karniyarik.jobexecutor.init.HepsiburadaXMLJob;
import com.karniyarik.jobexecutor.init.ModelTrainerJob;

@Path("/")
public class JobExecutorApi
{
	@Context
	HttpServletRequest	servletRequest;
	
	@Context
	HttpServletResponse servletResponse;
	
	@Context
	ServletContext servletContext;

	@PUT
	@Path("start")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response start(JobExecutionContext context)
	{
		if (context != null)
		{
			JobExecutorAdmin.getInstance().start(context.getSiteConfig(), context.getJobExecutionStat());
		}

		return Response.ok().build();
	}

	@POST
	@Path("pause/{siteName}")
	public Response pause(@PathParam("siteName") @DefaultValue("") String siteName)
	{
		if (StringUtils.isNotBlank(siteName))
		{
			JobExecutorAdmin.getInstance().pause(siteName);
		}
		return Response.ok().build();
	}

	@POST
	@Path("end/{siteName}")
	public Response end(@PathParam("siteName") @DefaultValue("") String siteName)
	{
		if (StringUtils.isNotBlank(siteName))
		{
			JobExecutorAdmin.getInstance().end(siteName);
		}
		return Response.ok().build();
	}

	@POST
	@Path("pauseAll")
	public Response pause()
	{
		JobExecutorAdmin.getInstance().pauseAll();
		return Response.ok().build();
	}

	@GET
	@Path("runningStats")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public JobExecutionStats getRunningStats()
	{
		JobExecutionStats stats = new JobExecutionStats();
		stats.setJobExecutionStatList(JobExecutorAdmin.getInstance().getAllStatistics());
		return stats;
	}

	@GET
	@Path("executemodeltrainer")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response executeModelTrainer()
	{
		System.out.println("Model trainer executing");
		try
		{
			new ModelTrainerJob().execute(null);
		}
		catch (JobExecutionException e)
		{
			e.printStackTrace();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("executehepsiburada")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response executeHepsiburada()
	{
		try
		{
			new HepsiburadaXMLJob().execute(null);
		}
		catch (JobExecutionException e)
		{
			e.printStackTrace();
		}
		return Response.ok().build();
	}
}
