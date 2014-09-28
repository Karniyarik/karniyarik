<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.karniyarik.common.statistics.vo.JobExecutionStat"%>
<%@page import="com.karniyarik.jobscheduler.request.JobRequestHandler"%>
<%@page import="com.karniyarik.jobscheduler.JobSchedulerAdmin"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%JobRequestHandler.handleCrawlerRequest(request, response);%>
<%@page import="com.karniyarik.common.KarniyarikRepository"%>
<%@page import="com.karniyarik.common.config.site.SitesConfig"%>
<%@page import="com.karniyarik.common.config.site.SiteConfig"%>
<%@page import="com.karniyarik.jobscheduler.util.JobStatisticsSummary"%>
<%@page import="com.karniyarik.jobscheduler.util.JobStatisticsSummaryCalculator"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.karniyarik.jobscheduler.JobAdminStateFilter"%>
<%@page import="com.karniyarik.common.group.GroupMember"%>
<%@page import="java.util.ArrayList"%><html>
<head>
<title>Jobs Detailed Values</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb">
	    <div class="mt10"></div>
	    <h1>Jobs Detailed Values</h1>
	    <hr/>
	    <div class="cl"></div>
	    <div class="mb10"></div>
    
    	<div class="span-24 last">	
    		<script type="text/javascript">
    		$(function() {
    			$( "#refresh" ).button({
    				text: true,
    				icons: {primary: "ui-icon-arrow-refresh-1-w"}
    			});
    			$( "#autorefresh" ).button({
    				text: true,
    				icons: {primary: "ui-icon-arrow-refresh-1-w"}
    			});
    			$( "#filter" ).buttonset();
    			/*$( "#repeat" ).buttonset();*/
    		});
    		</script>
	    	<span id="toolbar" class="ui-widget-header ui-corner-all">
				<a id="refresh" href="alljobsdetails.jsp">Refresh</a>
			</span>
			<span class="toolbar ui-widget-header ui-corner-all">
				<span id="filter">
					<a id="all" href="alljobsdetails.jsp?state=<%=JobAdminStateFilter.ALL%>">All</a>
					<a id="executing" href="alljobsdetails.jsp?state=<%=JobAdminStateFilter.EXECUTING%>">Executing</a>
					<a id="paused" href="alljobsdetails.jsp?state=<%=JobAdminStateFilter.PAUSED%>">Paused</a>
					<a id="failed" href="alljobsdetails.jsp?state=<%=JobAdminStateFilter.FAILED%>">Failed</a>
					<a id="brokendown" href="alljobsdetails.jsp?state=<%=JobAdminStateFilter.BROKEN%>">Broken</a>
					<a id="datafeed" href="alljobsdetails.jsp?state=<%=JobAdminStateFilter.DATAFEED%>">Dtfd</a>
					<a id="datafeed" href="alljobsdetails.jsp?state=<%=JobAdminStateFilter.SELENIUM%>">Slnm</a>
					<a id="datafeed" href="alljobsdetails.jsp?state=<%=JobAdminStateFilter.CRAWLER%>">Crwlr</a>
				</span>
			</span>			
			<span class="toolbar ui-widget-header ui-corner-all">
				<input type="checkbox" checked="checked" id="autorefresh" name="autorefresh"/><label for="autorefresh">Auto refresh</label>
			</span>
		</div>
    	<div class="cl"></div>
    	<div class="mt10">
	    	<table id="jobstable">
	    		<tr>
	    			<th>Site</th>
	    			<th>Health</th>
	    			<th><span title="Datafeed">Dtfd</span></th>
	    			<th><span title="Selenium">Slnm</span></th>
	    			<th>State</th>
	    			<th><span title="Visited">Vstd</span></th>
	    			<th><span title="Product Count">Prdct #</span></th>
	    			<th><span title="Duplicate Count">Dup #</span></th>
	    			<th><span title="To Visit">To Vst #</span></th>
	    			<th><span title="Exception">Exc #</span></th>
	    			<th><span title="Indexed">Idxd #</span></th>
	    			<th><span title="Images Missed">Img Mss #</span></th>
	    			<th><span title="Brands Missed">Bnd Mss #</span></th>
	    			<th><span title="Breadcrumbs Missed">Brc Mss #</span></th>
	    			<th><span title="Parsed Page Count">Par. Pg #</span></th>
	    			<th><span title="Product Miss Count">Pro. Mss #</span></th>
	    		</tr>
	    		<%
		    		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
	    			
		    		String state = request.getParameter("state");
	    			
		    		List<JobAdminStateFilter> filters = new ArrayList<JobAdminStateFilter>();
					if(StringUtils.isNotBlank(state))
					{
						filters.add(JobAdminStateFilter.valueOf(state));
					}
	    			
	    			List<JobExecutionStat> statisticsList = JobSchedulerAdmin.getInstance().getStatistics(filters);
	    			
	    			JobExecutionStat statistics;
	    			String beginDate;
	    			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); 
	    			for(int i = 0; i < statisticsList.size(); i++) {
	    				
	    				statistics = statisticsList.get(i);
	    				JobStatisticsSummary summ = JobSchedulerAdmin.getInstance().getStatisticsSummary(statistics.getSiteName());
	    				SiteConfig siteConfig = sitesConfig.getSiteConfig(statistics.getSiteName());
	    				
	    				boolean isDataFeed = false;
	    				boolean isSelenium = false;
	    				if(siteConfig != null)
	    				{
	    					isDataFeed = siteConfig.hasDatafeed();
	    					isSelenium = siteConfig.isSelenium();
	    				}
	    				
	    				if (statistics.getStartDate() > 0) {
		    				beginDate = dateFormat.format(new Date(statistics.getStartDate()));
	    				} else {
	    					beginDate = "Not started";
	    				}
	    				
	    				DecimalFormat format = new DecimalFormat("#0");
	    				
	    				String serverId = statistics.getRunningServer();
	    				String serverName = statistics.getRunningServer();
	    				GroupMember member = null;
    					if (StringUtils.isNotBlank(serverId)) {
    						member = JobSchedulerAdmin.getInstance().getMember(serverId);
    						if(member != null)
    						{
    							serverName = member.getAddress().toString();
    						}
    					} else if (JobSchedulerAdmin.getInstance().isWaiting(statistics.getSiteName())) {
    						serverId = null;
    						serverName = "Waiting";
    					} else {
    						serverId = null;
    						serverName = "-";
    					}
    					String stateicon = "green_button";
    					if(statistics.getStatus().hasFailed())
    					{
    						stateicon = "red_button";
    					}
    					else if(statistics.getStatus().hasPaused())
    					{
    						stateicon = "orange_button";
    					}
	    			%>
	    			<tr class="<%=statistics.getStatusMessage().toString().toLowerCase()%>">
	    				<td>
	    				<a class="iframe" href='jobdetail_min.jsp?sn=<%=statistics.getSiteName()%>'><img src="<%=request.getContextPath()%>/images/icons/16/zoom_in.png"/></a>
	    				<a href='jobdetail.jsp?sn=<%=statistics.getSiteName()%>'><%=statistics.getSiteName()%></a>
	    				</td>
	    				<td><span class="progress" title=""><%=format.format(summ.getOverallHealth())%></span></td>
	    				<td><img src="<%=request.getContextPath()%>/images/icons/24/<%=isDataFeed ? "green_flag.png" : "red_flag.png"%>"/></td>
	    				<td><%if(isSelenium){%><img src="<%=request.getContextPath()%>/images/icons/24/green_button.png"/><%}%></td>
	    				<td>
	    					<span title="<%=statistics.getStatus().toString() + " " + statistics.getStatusMessage()%>">
	    						<img src="<%=request.getContextPath()%>/images/icons/24/<%=stateicon%>.png"/>
	    					</span>
	    				</td>
	    				<td><span title="Visited"><%=statistics.getTotalVisitedLinks()%></span></td>
	    				<td><span title="Product Count"><%=statistics.getProductCount()%></span></td>
	    				<td><span title="Duplicate Count"><%=statistics.getDuplicateProductCount()%></span></td>
	    				<td><span title="To Visit"><%=statistics.getTotalLinksToVisit()%></span></td>
	    				<td><span title="Exception"><%=statistics.getTotalExceptionCount()%></span></td>
	    				<td><span title="Indexed"><%=statistics.getProductsIndexed()%></span></td>
	    				<td><span title="Images Missed"><%=statistics.getImagesMissedCount()%></span></td>	   
	    				<td><span title="Brands Missed"><%=statistics.getBrandsMissedCount()%></span></td>
	    				<td><span title="Breadcrumbs Missed"><%=statistics.getBreadcrumbsMissedCount()%></span></td>
	    				<td><span title="Parsed Page Count"><%=statistics.getParsedPageCount()%></span></td>
	    				<td><span title="Product Miss Count"><%=statistics.getProductMissCount()%></span></td>
	    			</tr>	
	    			<%}%>
	    	</table>
    	</div>
    <%@ include file="footer.jsp" %>    	
    </div>
   </div>
</body>
</html>