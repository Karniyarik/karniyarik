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

<%@page import="com.karniyarik.jobscheduler.util.JobStatisticsSummary"%>
<%@page import="com.karniyarik.jobscheduler.util.JobStatisticsSummaryCalculator"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="org.quartz.Trigger"%>
<%@page import="com.karniyarik.jobscheduler.util.DateFormatter"%><html>
<head>
<title></title>
<jsp:include page="common_include.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$("#tabs").tabs();
	});
</script>

</head>
<body class="statsm">
    <div id="main">
    	<div id="tableDiv" class="statsm">
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
    			
    			JobStatisticsSummary summ = JobStatisticsSummaryCalculator.calculateSummary(stat, 350, 100);
    			DecimalFormat format = new DecimalFormat("#0");
    			
    			Trigger trigger = JobSchedulerAdmin.getInstance().getScheduleInformation(stat.getSiteName());
    			String lastExecutionTime = DateFormatter.getDurationStr(stat.getStartDate(), stat.getEndDate());
    			String nextStartDate = DateFormatter.format(trigger.getNextFireTime());
    			String duration = DateFormatter.getDurationStr(new Date(), trigger.getNextFireTime());
    			
    			boolean isDataFeed = false;
				boolean isSelenium = false;
				if(siteConfig != null)
				{
					isDataFeed = siteConfig.hasDatafeed();
					isSelenium = siteConfig.isSelenium();
				}
    			%>
    			<h1><%=siteConfig.getDomainName()%></h1>
    			<hr/>
    			<div id="tabs">
			    	<ul>
						<li><a href="#tabs-1">Info</a></li>
						<li><a href="#tabs-2">Detail</a></li>
						<li><a href="#tabs-3">History</a></li>
					</ul>
					<div id="tabs-1">
						<div id="sitestatimg">
		    				<div>Overall Health: <span class="progress"><%=format.format(summ.getOverallHealth())%></span></div><br/>
			   				<img src="<%=summ.getImageProductRatio()%>"/>
			    			<img src="<%=summ.getImageFetchTime()%>"/>
			    			<img src="<%=summ.getImageRatios()%>"/>
			    			
			    			<table class="right_t" style="width:300px;margin-top:10px;">
								<tbody>
					    			<tr>
				    					<th>Products Remaining</th><td><%=stat.getProductsRemaining()%></td>
					    			</tr>
					    			<tr>
				    					<th>Products Indexed</th><td><%=stat.getProductsIndexed()%></td>
					    			</tr>
					    			<tr>
				    					<th>Exception Count</th><td><%=stat.getTotalExceptionCount()%></td>
					    			</tr>
					    			<tr>
				    					<th>Products To Index</th><td><%=stat.getTotalProductsToIndex()%></td>
				    				</tr>
			    				</tbody>
		    				</table>	
		    			</div>
						<div id="sitestat_t">
							<table class="right_t">
								<tbody>
									<tr><th>Config</th><td><a href="<%=request.getContextPath()%>/sitexmlconfig.jsp?s=<%=siteConfig.getSiteName()%>">View</a><br/></td></tr>
				    				<tr>
					    				<th>Running Server</th><td><%=stat.getRunningServer()%></td>
					    			</tr>
					    			<tr><td>URL</td><td><a href="<%=siteConfig.getUrl()%>"><%=siteConfig.getUrl()%></a></td></tr>
					    			<tr>
				    					<th>Status</th><td><%=stat.getStatus()%></td>
					    			</tr>
					    			<tr>
				    					<th>Status Message</th><td><%=stat.getStatusMessage()%></td>
					    			</tr>
					    			<tr>
				    					<th>Datafeed</th><td><%=isDataFeed%></td>
					    			</tr>
					    			<tr>
				    					<th>Selenium</th><td><%=isSelenium%></td>
					    			</tr>
					    			<tr>
				    					<th>ECommerce Type</th><td><%=siteConfig.getECommerceName()%></td>
					    			</tr>
					    			<tr>
				    					<th>Start Date</th><td><%=startDate%></td>
					    			</tr>
					    			<tr>
				    					<th>End Date</th><td><%=endDate%></td>
					    			</tr>
									<tr><th>Last Duration</th><td><%=lastExecutionTime%></td></tr>
				    				<tr><th>Next Start</th><td><%=nextStartDate%></td></tr>
				    				<tr><th>Time Left</th><td><%=duration%></td></tr>	    			
					    			<tr>
				    					<th>Total Fetch Time</th><td><%=stat.getTotalFetchTime()%></td>
					    			</tr>
					    			<tr>
				    					<th>Indexing Time</th><td><%=stat.getIndexingTime()%></td>
					    			</tr>
			    				</tbody>
		    				</table>				
						</div>   
						<div class="cl"></div> 					    
					</div>    
					<div id="tabs-2">
						<table class="bottom_t">
		    				<tr>
		    					<th>DB Cache Hit</th><td><%=stat.getTotalDBCacheHitCount()%></td>
		    					<th>Link Count</th><td><%=stat.getTotalLinkCount()%></td>
		    					<th>Visited</th><td><%=stat.getTotalVisitedLinks()%></td>
		    				</tr>
		    				<tr>
			    				<th>DB Cache Miss</th><td><%=stat.getTotalDBCacheMissCount()%></td>
			    				<th>Link Cache Size</th><td><%=stat.getVisitedLinkCacheSize()%></td>
			    				<th>Visited Flush</th><td><%=stat.getTotalVisitedLinkFlush()%></td>
		    				</tr>
		    				<tr>
			    				<th>Total DB Ops.</th><td><%=stat.getTotalDBOperationCount()%></td>
			    				<th>Links to Visit</th><td><%=stat.getTotalLinksToVisit()%></td>
			    				<th>Visited Read</th><td><%=stat.getTotalVisitedLinkRead()%></td>
		    				</tr>    				    					
		    				<tr>
		    					<th>Memory Cache Hit</th><td><%=stat.getTotalMemoryCacheHitCount()%></td>
		    					<th>Links to Visit Flush</th><td><%=stat.getTotalLinksToVisitFlush()%></td>
		    					<th></th><td></td>
		    				</tr>    					
		    				<tr>
		    					<th>Memory Cache Miss</th><td><%=stat.getTotalMemoryCacheMissCount()%></td>
		    					<th>Links to Visit Read</th><td><%=stat.getTotalLinksToVisitRead()%></td>
		    					<th></th><td></td>
		    				</tr>    					
		    			</table>
		    			<div class="cl"></div>					    
					</div>    
					<div id="tabs-3">
						<jsp:include page="stathistory.jsp">
							<jsp:param name="sitename" value="<%=siteName%>"/>
						</jsp:include>
					</div>	
		    	</div>
    	   <%}
    	}
    	%>
    	</div>
    </div>
</body>
</html>