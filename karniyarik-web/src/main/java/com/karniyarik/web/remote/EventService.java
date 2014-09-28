package com.karniyarik.web.remote;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.site.SiteRegistry;
import com.karniyarik.common.statistics.vo.ProductClickLog;
import com.karniyarik.common.util.StatisticsWebServiceUtil;
import com.karniyarik.search.EngineRepository;
import com.karniyarik.search.searcher.autocomplete.AutoCompleteEngine;
import com.karniyarik.site.SiteInfoProvider;
import com.karniyarik.web.job.CityDealJob;
import com.karniyarik.web.job.SOLRIndexCommitJob;
import com.karniyarik.web.json.SiteLinkPrefixUpdater;
import com.karniyarik.web.servlet.SiteConfRefreshServlet;
import com.karniyarik.web.sitemap.FirstTimeGenerator;
import com.karniyarik.web.sitemap.InsightGenerator;
import com.karniyarik.web.sitemap.SearchLogGenerator;
import com.karniyarik.web.sitemap.SitemapDateUpdater;
import com.karniyarik.web.statusmsg.StatusUpdateMessenger;
import com.karniyarik.web.statusmsg.TwitterMessages;

@Path("/event")
public class EventService
{
	@Context
	HttpServletRequest	servletRequest;
	
	@Context
	HttpServletResponse servletResponse;
	
	@Context
	ServletContext servletContext;

	@POST
	@Path("/hit")
	@Produces(MediaType.TEXT_PLAIN)
	public Response hitOccured(
			@FormParam("url") @DefaultValue("") String url,
			@FormParam("site") @DefaultValue("") String siteName,
			@FormParam("name") @DefaultValue("") String pName,
			@FormParam("query") @DefaultValue("") String query,
			@FormParam("type") @DefaultValue("0") int type)
	{
		
		if (StringUtils.isNotBlank(siteName) && StringUtils.isNotBlank(url)) 
		{
			ProductClickLog log = new ProductClickLog();
			log.setName(pName);
			log.setSiteName(siteName);
			log.setUrl(url);
			log.setQuery(query);
			if (type == 1) {
				log.setSponsor(true);
			} else {
				log.setSponsor(false);
			}
			log.setIp(servletRequest.getRemoteAddr());

			StatisticsWebServiceUtil.sendProductHit(log);
		}

		return Response.ok().build();
	}

	@GET
	@Path("/entsynch")
	public Response synchEnterprise()
	{
		SiteRegistry.getInstance().sendSiteInfo();
		SiteRegistry.getInstance().refreshSiteCache();
		SiteRegistry.getInstance().refreshFeaturedCache();
		SiteRegistry.getInstance().refreshAlexaCache();
		return Response.ok().build();
	}

	@PUT
	@Path("/siteconf/add")
	@Consumes(MediaType.TEXT_PLAIN + ";charset=UTF-8")
	public Response addNewSiteConfig(String configStr)
	{
		SiteConfRefreshServlet.createSiteConfig(configStr, servletContext);
		return Response.ok().build();
	}

	@GET
	@Path("/siteconf/delete")
	public Response deleteSiteConfig(@QueryParam("sitename") @DefaultValue("")  String sitename)
	{
		SiteConfRefreshServlet.deleteSiteConfig(sitename, servletContext);
		return Response.ok().build();
	}

	@GET
	@Path("/statsynch")
	public Response synchStat()
	{
		EngineRepository.getInstance().refreshSponsoredLinksData();
		return Response.ok().build();
	}

	@GET
	@Path("/generatesitemap")
	@Produces(MediaType.TEXT_PLAIN)
	public Response refreshSitemap()
	{
		FirstTimeGenerator generator = new FirstTimeGenerator();
		generator.generate();
		return Response.ok().build();
	}

	@GET
	@Path("/createsearchlogsitemap")
	@Produces(MediaType.TEXT_PLAIN)
	public Response createSearchlogSitemap()
	{
		SearchLogGenerator generator = new SearchLogGenerator(SearchLogGenerator.WEEKLY);
		generator.generate();

		return Response.ok().build();
	}

	@GET
	@Path("/createinsightsitemap")
	@Produces(MediaType.TEXT_PLAIN)
	public Response createInsightSitemap(@QueryParam("f") @DefaultValue("")  String filename)
	{
		InsightGenerator generator = new InsightGenerator(filename);
		generator.generate();

		return Response.ok().build();
	}
	
	@GET
	@Path("/refreshsitemapdates")
	@Produces(MediaType.TEXT_PLAIN)
	public Response refreshSitemapDates()
	{
		SitemapDateUpdater updater = new SitemapDateUpdater();
		updater.update();

		return Response.ok().build();
	}

	@GET
	@Path("/refreshqueryindex")
	@Produces(MediaType.TEXT_PLAIN)
	public Response refreshQueryIndex()
	{
		AutoCompleteEngine.getInstance().refresh();
		return Response.ok().build();
	}

	@GET
	@Path("/deletesite")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteSite(@QueryParam("n") @DefaultValue("") String name)
	{
		
		return Response.ok().build();
	}

	@GET
	@Path("/refreshsitereviews")
	@Produces(MediaType.TEXT_PLAIN)
	public Response refreshSiteReviewRank()
	{
		SiteInfoProvider.getInstance().refresh();
		return Response.ok().build();
	}
	
	@GET
	@Path("/refreshtwittermsg")
	@Produces(MediaType.TEXT_PLAIN)
	public Response refreshTwitterMessages()
	{
		TwitterMessages.getInstance().update();
		return Response.ok().build();
	}

	@GET
	@Path("/sendtwitterstatus")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendTwitterStatus()
	{
		new StatusUpdateMessenger().update();
		return Response.ok().build();
	}

	
	
	@GET
	@Path("/otel")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getOtelPrice()
	{
		AutoCompleteEngine.getInstance().refresh();
		return Response.ok().build();
	}
	
	@GET
	@Path("/refreshcitydeals")
	@Produces(MediaType.TEXT_PLAIN)
	public Response refreshCityDeals()
	{
		CityDealJob job = new CityDealJob();
		job.update();
		return Response.ok().build();
	}

	@GET
	@Path("/solrcommit")
	@Produces(MediaType.TEXT_PLAIN)
	public Response commitSolr(@QueryParam("c") @DefaultValue("") String cat)
	{
		SOLRIndexCommitJob job = null;
		if(StringUtils.isNotBlank(cat))
		{
			job = new SOLRIndexCommitJob(cat);
		}
		else
		{
			job = new SOLRIndexCommitJob();
		}
		
		job.execute();
		return Response.ok().build();		
	}

	@PUT
	@Path("/updatesiteprefix")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response productClicked(String content)
	{
		SiteLinkPrefixUpdater.getInstance().updateSources(content);
		return Response.ok().build();
	}
}