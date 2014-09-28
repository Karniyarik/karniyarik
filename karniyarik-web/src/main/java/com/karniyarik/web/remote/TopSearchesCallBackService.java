package com.karniyarik.web.remote;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.karniyarik.common.statistics.vo.TopSearches;
import com.karniyarik.web.json.LinkedLabel;
import com.karniyarik.web.json.SearchStatisticsManager;
import com.karniyarik.web.util.SearchLogUtil;

@Path("/topSearches")
public class TopSearchesCallBackService
{
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response saveSiteStat(TopSearches topSearches)
	{
		SearchLogUtil util = new SearchLogUtil();
		List<LinkedLabel> topSearchesLabels = util.getTopSearches(topSearches);
		SearchStatisticsManager.getInstance().setTopSearches(topSearches.getCategory(), topSearchesLabels);
		return Response.ok().build();
	}

	@PUT
	@Path("monthly/update")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response saveMonthlyTopSearches(TopSearches topSearches)
	{
		
		return Response.ok().build();
	}

}
