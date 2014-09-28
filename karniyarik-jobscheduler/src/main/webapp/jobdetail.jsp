<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.karniyarik.common.statistics.vo.JobExecutionStat"%>

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.karniyarik.common.KarniyarikRepository"%>
<%@page import="com.karniyarik.common.config.site.SitesConfig"%>
<%@page import="com.karniyarik.common.config.site.SiteConfig"%>
<%@page import="com.karniyarik.jobscheduler.JobSchedulerAdmin"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.karniyarik.common.util.StringUtil"%>
<%@page import="com.karniyarik.jobscheduler.util.JobStatisticsSummary"%>
<%@page import="com.karniyarik.jobscheduler.util.JobStatisticsSummaryCalculator"%>
<%@page import="org.quartz.Trigger"%>
<%@page import="com.karniyarik.jobscheduler.util.DateFormatter"%><html>
<head>
<title></title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb jobdetail">
    	<%
    	String siteName = request.getParameter("sn");
    	if(StringUtils.isNotBlank(siteName))
    	{
    		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
    		SiteConfig siteConfig = sitesConfig.getSiteConfig(siteName);
    		if(siteConfig != null)
    		{
    			JobExecutionStat stat = JobSchedulerAdmin.getInstance().getSiteStatistics(siteName);
    			
    			Date start = new Date(stat.getStartDate());
    			String startDate = stat.getStartDate() != 0 ? start.toString() : "";
    			
    			Date end = new Date(stat.getEndDate());
    			String endDate = stat.getEndDate() != 0 ? end.toString() : "";
    			DecimalFormat format = new DecimalFormat("#0");
    			
    			Trigger trigger = JobSchedulerAdmin.getInstance().getScheduleInformation(stat.getSiteName());
    			String lastExecutionTime = DateFormatter.getDurationStr(stat.getStartDate(), stat.getEndDate());
    			String nextStartDate = DateFormatter.format(trigger.getNextFireTime());
    			String duration = DateFormatter.getDurationStr(new Date(), trigger.getNextFireTime());
    			%>
    			<img src="http://www.karniyarik.com/kurumsal/images/sitelogo/<%=siteConfig.getSiteName()%>.png"/><h1><%=siteConfig.getDomainName()%></h1>
    			<hr/>
				<div>
	    			<%JobStatisticsSummary summ = JobStatisticsSummaryCalculator.calculateSummary(stat, 300, 100);%>
	    			<div>Overall Health: <span class="progress"><%=format.format(summ.getOverallHealth())%></span></div><br/>
	    			<img src="<%=summ.getImageProductRatio()%>"/>
	    			<img src="<%=summ.getImageFetchTime()%>"/>
	    			<img src="<%=summ.getImageRatios()%>"/>
	    			<div class="cl"></div>
				</div>    			
    			<div style="float:left;margin-right:10px;width: 320px;">
    			<h4>Site Config</h4>
    			<table width="100%">
					<tr><td>Config</td><td><a href="<%=request.getContextPath()%>/sitexmlconfig.jsp?s=<%=siteConfig.getSiteName()%>">View</a><br/></td></tr>
    				<tr><td>Site Name</td><td><%=siteConfig.getSiteName()%></td></tr>
    				<tr><td>URL</td><td><a href="<%=siteConfig.getUrl()%>"><%=siteConfig.getUrl()%></a></td></tr>
    				<tr><td>Cron</td><td><%=siteConfig.getCron()%></td></tr>
    				<tr><td>Site Rank</td><td><%=siteConfig.getSiteRank()%></td></tr>
    				<tr><td>Site Encoding</td><td><%=siteConfig.getSiteEncoding()%></td></tr>
    				<tr><td>Crawl Delay</td><td><%=siteConfig.getCrawlDelay()%></td></tr>
    				<tr><td>Selenium</td><td><%=siteConfig.isSelenium()%></td></tr>
    				<tr><td>Datafeed</td><td><%=siteConfig.isDatafeed()%></td></tr>
	    			<tr><td>ECommerce Type</td><td><%=siteConfig.getECommerceName()%></td></tr>
    			</table>
    			</div>
    			<div style="float:left;margin-right:10px;width: 380px;">
    			<h4>Execution</h4>
    			<table width="100%">
    				<tr><th>Running Server</th><td><%=stat.getRunningServer()%></td></tr>
    				<tr><th>Status</th><td><%=stat.getStatus()%></td></tr>
    				<tr><th>Status Message</th><td><%=stat.getStatusMessage()%></td></tr>
    				<tr><th>Start Date</th><td><%=startDate%></td></tr>
    				<tr><th>End Date</th><td><%=endDate%></td></tr>
    				<tr><th>Last Duration</th><td><%=lastExecutionTime%></td></tr>
    				<tr><th>Next Start</th><td><%=nextStartDate%></td></tr>
    				<tr><th>Time Left</th><td><%=duration%></td></tr>
    			</table>
    			</div>
    			<div style="float:left;margin-right:10px;width: 220px;">
    			<h4>Summary</h4>
    			<table width="100%">
    				<tr><th>Product Count</th><td><%=stat.getProductCount()%></td></tr>
    				<tr><th>Duplicate Prods.</th><td><%=stat.getDuplicateProductCount()%></td></tr>
    				<tr><th>Product Miss</th><td><%=stat.getProductMissCount()%></td></tr>
    				<tr><th>Parsed Page Count</th><td><%=stat.getParsedPageCount()%></td></tr>
    				<tr><th>Exception Count</th><td><%=stat.getTotalExceptionCount()%></td></tr>
    			</table>
    			</div>
    			<div class="cl"></div>
    			<div>
    				<img src="<%=summ.getImageImageRatio()%>"/>
	    			<img src="<%=summ.getImageBrandRatio()%>"/>
	    			<img src="<%=summ.getImageBreadcrumbRatio()%>"/>	    			
    				<div class="cl"></div>
	    			<h4>Details</h4>
	    			<div class="cl"></div>
	    			<table style="float:left;margin-right:10px;width: 220px;">
	    			    <tr><th>Products Remaining</th><td><%=stat.getProductsRemaining()%></td></tr>
	    				<tr><th>Products Indexed</th><td><%=stat.getProductsIndexed()%></td></tr>
						<tr><th>Products To Index</th><td><%=stat.getTotalProductsToIndex()%></td></tr>
	    				<tr><th>Avg Fetch Time</th><td><%=stat.getWindowedAvgFetchTime()%></td></tr>
						<tr><th>Total Fetch Time</th><td><%=stat.getTotalFetchTime()%></td></tr>    				
	    				<tr><th>Indexing Time</th><td><%=stat.getIndexingTime()%></td></tr>
	    				<tr><th>Fetch Time Count</th><td><%=stat.getFetchTimeCount()%></td></tr>
	    				<tr><th>&nbsp;</th><td>&nbsp;</td></tr>    			    			
	    			</table>
	    			<table style="float:left;margin-right:10px;width: 230px;">
	    				<tr><th>Images Missed</th><td><%=stat.getImagesMissedCount()%></td></tr>
	    				<tr><th>Images Parsed</th><td><%=stat.getImagesParsedCount()%></td></tr>
	    				<tr><th>Brands Missed</th><td><%=stat.getBrandsMissedCount()%></td></tr>
	    				<tr><th>Brands Parsed</th><td><%=stat.getBrandsParsedCount()%></td></tr>
	    				<tr><th>Breadcrumbs Missed</th><td><%=stat.getBreadcrumbsMissedCount()%></td></tr>
	    				<tr><th>Breadcrumbs Parsed</th><td><%=stat.getBreadcrumbsParsedCount()%></td></tr>
	    				<tr><th>&nbsp;</th><td>&nbsp;</td></tr>
	    				<tr><th>&nbsp;</th><td>&nbsp;</td></tr>
	    			</table>
	    			<table style="float:left;margin-right:10px;width: 230px;">
	    				<tr><th>DB Cache Hit</th><td><%=stat.getTotalDBCacheHitCount()%></td></tr>
	    				<tr><th>DB Cache Miss</th><td><%=stat.getTotalDBCacheMissCount()%></td></tr>
	    				<tr><th>Total DB Ops.</th><td><%=stat.getTotalDBOperationCount()%></td></tr>
	    				<tr><th>Memory Cache Hit</th><td><%=stat.getTotalMemoryCacheHitCount()%></td></tr>
	    				<tr><th>Memory Cache Miss</th><td><%=stat.getTotalMemoryCacheMissCount()%></td></tr>
	    				<tr><th>&nbsp;</th><td>&nbsp;</td></tr>
	    				<tr><th>&nbsp;</th><td>&nbsp;</td></tr>
	    				<tr><th>&nbsp;</th><td>&nbsp;</td></tr>
	    			</table>
	    			<table style="float:left;margin-right:10px;width: 230px;">
	    				<tr><th>Link Count</th><td><%=stat.getTotalLinkCount()%></td></tr>
	    				<tr><th>Links to Visit</th><td><%=stat.getTotalLinksToVisit()%></td></tr>
	    				<tr><th>Links to Visit Flush</th><td><%=stat.getTotalLinksToVisitFlush()%></td></tr>
	    				<tr><th>Links to Visit Read</th><td><%=stat.getTotalLinksToVisitRead()%></td></tr>
	    				<tr><th>Visited</th><td><%=stat.getTotalVisitedLinks()%></td></tr>
	    				<tr><th>Visited Flush</th><td><%=stat.getTotalVisitedLinkFlush()%></td></tr>
	    				<tr><th>Visited Read</th><td><%=stat.getTotalVisitedLinkRead()%></td></tr>
	    				<tr><th>Link Cache Size</th><td><%=stat.getVisitedLinkCacheSize()%></td></tr>
	    			</table>    	
    			</div>			   		
				<div class="cl"></div>
				<div class="span-24">
					<jsp:include page="stathistory.jsp">
						<jsp:param name="sitename" value="<%=siteName%>"/>
					</jsp:include>
				</div>  
    	   <%}
    	}
    	%>
    	<%@ include file="footer.jsp" %>
    	</div>
    </div>
</body>
</html>