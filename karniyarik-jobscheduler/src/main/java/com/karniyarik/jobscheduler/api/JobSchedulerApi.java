package com.karniyarik.jobscheduler.api;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.karniyarik.common.exception.ExceptionVO;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.externalrank.alexa.AlexaRankRegistry;
import com.karniyarik.externalrank.alexa.AlexaSiteInfo;
import com.karniyarik.externalrank.alexa.AlexaSiteInfoList;
import com.karniyarik.jobscheduler.JobSchedulerAdmin;
import com.karniyarik.jobscheduler.warning.WarningMessageController;

@Path("/")
public class JobSchedulerApi
{
	@Context
	HttpServletRequest	servletRequest;
	
	@Context
	HttpServletResponse servletResponse;
	
	@Context
	ServletContext servletContext;

	@GET
	@Path("getstathistory")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public JobExecutionStatList getStatHistory(
			@QueryParam("s") @DefaultValue("") String sitename)
	{
		JobExecutionStatList result = new JobExecutionStatList();
		
		List<JobExecutionStat> statHistory = JobSchedulerAdmin.getInstance().getStatHistory(sitename);
		if(statHistory != null)
		{
			result.getStats().addAll(statHistory);	
		}
		
		return result;
	}
	
	@GET
	@Path("getalexainfo")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public AlexaSiteInfo getAlexaInfo(@QueryParam("s") @DefaultValue("") String sitename)
	{
		AlexaSiteInfo siteInfo = AlexaRankRegistry.getInstance().getSiteInfo(sitename);
		return siteInfo;
	}

	@GET
	@Path("getalexainfolist")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public AlexaSiteInfoList getAlexaInfoList()
	{
		return AlexaRankRegistry.getInstance().getSiteInfoList();
	}

	@PUT
	@Path("exception/add")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response exceptionOccured(ExceptionVO exceptionVO)
	{
		new WarningMessageController().warningOccured(exceptionVO);
		return Response.ok().build();
	}
}
