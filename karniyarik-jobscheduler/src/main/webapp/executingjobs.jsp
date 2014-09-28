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
<title>Jobs</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb">
	    <div class="mt10"></div>
	    <h1>Executing Jobs</h1>
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
    			$( "#pauseall" ).button({
    				text: true,
    				icons: {primary: "ui-icon-pause"}
    			});
    			$( "#autorefresh" ).button({
    				text: true,
    				icons: {primary: "ui-icon-arrow-refresh-1-w"}
    			});
    			$( "#filter" ).buttonset();
    		});
    		</script>
	    	<span id="toolbar" class="ui-widget-header ui-corner-all">
				<a id="refresh" href="executingjobs.jsp">Refresh</a>
				<a id="pauseall" href="jobs.jsp?<%=JobRequestHandler.OPERATION_PARAMETER%>=<%=JobRequestHandler.PAUSE_ALL_OPERATION%>">Pause All</a>
				<!-- span id="repeat">
					<input type="radio" id="all" checked="checked"/><label for="all">All</label>
					<input type="radio" id="executing"/><label for="executing">Executing</label>
					<input type="radio" id="dangerous"/><label for="dangerous">Dangerous</label>
					<input type="radio" id="failed"/><label for="failed">Failed</label>
					<input type="radio" id="inactive" /><label for="inactive">In-Active</label>
				</span -->
			</span>
			<span class="toolbar ui-widget-header ui-corner-all">
				<span id="filter">
					<a id="brokendown" href="executingjobs.jsp?state=<%=JobAdminStateFilter.BROKEN%>">Broken</a>
					<a id="datafeed" href="executingjobs.jsp?state=<%=JobAdminStateFilter.DATAFEED%>">Dtfd</a>
					<a id="selenium" href="executingjobs.jsp?state=<%=JobAdminStateFilter.SELENIUM%>">Slnm</a>
					<a id="crawler" href="executingjobs.jsp?state=<%=JobAdminStateFilter.CRAWLER%>">Crwlr</a>
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
	    			<th>Datafeed</th>
	    			<th>Start</th>
	    			<th>Server</th>
	    			<th>State</th>
	    			<th>Visited</th>
	    			<th>Product Count</th>
	    			<th>Duplicate</th>
	    			<th>To Visit</th>
	    			<th>Exception</th>
	    			<th>Indexed</th>
	    			<th>Actions</th>
	    		</tr>
	    		<%
		    		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
	    			
	    			String state = request.getParameter("state");
	    			
		    		List<JobAdminStateFilter> filters = new ArrayList<JobAdminStateFilter>();
					if(StringUtils.isNotBlank(state))
					{
						filters.add(JobAdminStateFilter.valueOf(state));
					}
					filters.add(JobAdminStateFilter.EXECUTING);
	    			 
	    			List<JobExecutionStat> statisticsList = JobSchedulerAdmin.getInstance().getStatistics(filters);
	    			
	    			JobExecutionStat statistics;
	    			String beginDate;
	    			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); 
	    			for(int i = 0; i < statisticsList.size(); i++) {
	    				
	    				statistics = statisticsList.get(i);
	    				JobStatisticsSummary summ = JobSchedulerAdmin.getInstance().getStatisticsSummary(statistics.getSiteName());
	    				SiteConfig siteConfig = sitesConfig.getSiteConfig(statistics.getSiteName());
	    				
	    				boolean isDataFeed = false;
	    				if(siteConfig != null)
	    				{
	    					isDataFeed = siteConfig.hasDatafeed();
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
	    			%>
	    			<tr class="<%=statistics.getStatusMessage().toString().toLowerCase()%>">
	    				<td>
	    				<a class="iframe" href='jobdetail_min.jsp?sn=<%=statistics.getSiteName()%>'><img src="<%=request.getContextPath()%>/images/icons/16/zoom_in.png"/></a>
	    				<a href='jobdetail.jsp?sn=<%=statistics.getSiteName()%>'><%=statistics.getSiteName()%></a>
	    				</td>
	    				<td><span class="progress" title=""><%=format.format(summ.getOverallHealth())%></span></td>
	    				<td><img src="<%=request.getContextPath()%>/images/icons/24/<%=isDataFeed ? "green_flag.png" : "red_flag.png"%>"/></td>
	    				<td><%=beginDate%></td>
						<td>
						<%if(member != null){ %>
						<a href="<%=request.getContextPath()%>/server.jsp?id=<%=serverId%>"><%=serverName%></a>
						<%} else {%> <%=serverName%> <%} %>
						</td>
						<td><%=statistics.getStatus().toString()%></td>
	    				<td><%=statistics.getTotalVisitedLinks()%></td>
	    				<td><%=statistics.getProductCount()%></td>
	    				<td><%=statistics.getDuplicateProductCount()%></td>
	    				<td><%=statistics.getTotalLinksToVisit()%></td>
	    				<td><%=statistics.getTotalExceptionCount()%></td>
	    				<td><%=statistics.getProductsIndexed()%></td>	   
	    				<td>
	    					<a href="jobs.jsp?<%=JobRequestHandler.OPERATION_PARAMETER%>=<%=JobRequestHandler.SCHEDULE_SITE_OPERATION%>&<%=JobRequestHandler.SITE_NAME_PARAMETER%>=<%=statistics.getSiteName()%>">
				    			<img src="images/icons/16/play.png" alt="Crawl" title="Crawl"/>
				    		</a>
				    		<a href="jobs.jsp?<%=JobRequestHandler.OPERATION_PARAMETER%>=<%=JobRequestHandler.PAUSE_SITE_OPERATION%>&<%=JobRequestHandler.SITE_NAME_PARAMETER%>=<%=statistics.getSiteName()%>">
				    			<img src="images/icons/16/pause.png" alt="Pause" title="Pause"/>
				    		</a>
				    		<a href="jobs.jsp?<%=JobRequestHandler.OPERATION_PARAMETER%>=<%=JobRequestHandler.END_SITE_OPERATION%>&<%=JobRequestHandler.SITE_NAME_PARAMETER%>=<%=statistics.getSiteName()%>">
				    			<img src="images/icons/16/stop.png" alt="End" title="End"/>
				    		</a>
	    				</td>
	    			</tr>	
	    			<%}%>
	    	</table>
	    	<div class="span-24">Total size: <%=statisticsList.size()%></div>
    	</div>
    <%@ include file="footer.jsp" %>    	
    </div>
   </div>
</body>
</html>