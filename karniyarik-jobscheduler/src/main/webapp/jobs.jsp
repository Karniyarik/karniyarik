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
<%@page import="java.util.ArrayList"%>
<%@page import="com.karniyarik.common.group.GroupMember"%><html>
<head>
<title>Jobs</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb">
	    <div class="mt10"></div>
	    <h1>All Jobs</h1>
	    <hr/>
	    <div class="cl"></div>
	    <div class="mb10"></div>
    
    	<div class="span-24 last" style="margin-bottom:20px;">	
    		<script type="text/javascript">
    		$(function() {
    			$( "#refresh" ).button({
    				text: true,
    				icons: {primary: "ui-icon-arrow-refresh-1-w"}
    			});
    			/*$( "#schedulall" ).button({
    				text: true,
    				icons: {primary: "ui-icon-circle-triangle-e"}
    			});*/
    			$( "#pauseall" ).button({
    				text: true,
    				icons: {primary: "ui-icon-pause"}
    			});
    			$( "#filter" ).buttonset();
    			$( "#filter1" ).buttonset();
    			$( "#autorefresh" ).button({
    				text: true,
    				icons: {primary: "ui-icon-arrow-refresh-1-w"}
    			});
    		});
    		</script>
    		<%
    			String refreshStr = "";
    			String state = request.getParameter("state");
    			String checked = "";
	    		if(StringUtils.isNotBlank(state))
				{
	    			JobAdminStateFilter stateFilter = JobAdminStateFilter.valueOf(state);
	    			refreshStr = "?state=" + stateFilter;
					if(stateFilter == JobAdminStateFilter.EXECUTING)
					{
						checked = "checked='checked'"; 
					}
				}
    		%>
	    	<span class="toolbar ui-widget-header ui-corner-all">
				<a id="refresh" href="jobs.jsp<%=refreshStr%>">Refresh</a>
				<a id="pauseall" href="jobs.jsp?<%=JobRequestHandler.OPERATION_PARAMETER%>=<%=JobRequestHandler.PAUSE_ALL_OPERATION%>">Pause All</a>
			</span>
			<span class="toolbar ui-widget-header ui-corner-all">
				<span id="filter">
					<a id="all" href="jobs.jsp?state=<%=JobAdminStateFilter.ALL%>">All</a>
					<a id="executing" href="jobs.jsp?state=<%=JobAdminStateFilter.EXECUTING%>">Executing</a>
					<a id="paused" href="jobs.jsp?state=<%=JobAdminStateFilter.PAUSED%>">Paused</a>
					<a id="failed" href="jobs.jsp?state=<%=JobAdminStateFilter.FAILED%>">Failed</a>
					<a id="brokendown" href="jobs.jsp?state=<%=JobAdminStateFilter.BROKEN%>">Broken</a>
					<a id="datafeed" href="jobs.jsp?state=<%=JobAdminStateFilter.DATAFEED%>">Dtfd</a>
					<a id="selenium" href="jobs.jsp?state=<%=JobAdminStateFilter.SELENIUM%>">Slnm</a>
					<a id="crawler" href="jobs.jsp?state=<%=JobAdminStateFilter.CRAWLER%>">Crwlr</a>
				</span>
			</span>
			<span class="toolbar ui-widget-header ui-corner-all">
				<input type="checkbox" <%=checked%> id="autorefresh" name="autorefresh" title="Automaticall refreshes the page in every 20 seconds"/><label for="autorefresh">Auto refresh</label>
			</span>		
			<div class="cl" style="margin-top:20px;"></div>
			<span class="toolbar ui-widget-header ui-corner-all">
				<span id="filter1">
					<a id="ideasoft" href="jobs.jsp?state=<%=JobAdminStateFilter.IDEASOFT%>">Ideasoft</a>
					<a id="ideasoft" href="jobs.jsp?state=<%=JobAdminStateFilter.KOBIMASTER%>">Kobimaster</a>
					<a id="ideasoft" href="jobs.jsp?state=<%=JobAdminStateFilter.PROJE%>">Proj-e</a>
					<a id="ideasoft" href="jobs.jsp?state=<%=JobAdminStateFilter.HEMENAL%>">Hemenal</a>
					<a id="ideasoft" href="jobs.jsp?state=<%=JobAdminStateFilter.PRESTASHOP%>">Prestashop</a>
					<a id="ideasoft" href="jobs.jsp?state=<%=JobAdminStateFilter.NETICARET%>">Neticaret</a>
				</span>
			</span>				
		</div>
    	<div class="cl"></div>
    	<div class="mt10">
	    	<table id="jobstable">
	    		<thead>
	    			<tr>
					<th>Site</th>
	    			<th>Health</th>
	    			<th><span title="Datafeed">Dtfd</span></th>
	    			<th><span title="Ecommerce">Ecmmrc</span></th>
	    			<th>Schedule Date</th>
	    			<th>Start</th>
	    			<th>End</th>
	    			<th>Server</th>
	    			<th>State</th>
	    			<th>Product Count</th>
	    			<th>Actions</th>
	    			</tr>	    		
	    		</thead>
	    		<tbody>
	    		<tr>
	    		</tr>
	    		<%
	    			List<JobAdminStateFilter> filters = new ArrayList<JobAdminStateFilter>();
	    			if(StringUtils.isNotBlank(state))
	    			{
	    				filters.add(JobAdminStateFilter.valueOf(state));
	    			}
	    			else
	    			{
	    				filters.add(JobAdminStateFilter.ALL);
	    			}
	    			
	    			List<JobExecutionStat> statisticsList = JobSchedulerAdmin.getInstance().getStatistics(filters);
	    			
	    			SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
	    			JobExecutionStat statistics;
	    			String beginDate;
	    			String endDate;
	    			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm"); 
	    			
	    			for(int i = 0; i < statisticsList.size(); i++) {
	    				
	    				statistics = statisticsList.get(i);
	    				JobStatisticsSummary summ = JobSchedulerAdmin.getInstance().getStatisticsSummary(statistics.getSiteName());
	    				SiteConfig siteConfig = sitesConfig.getSiteConfig(statistics.getSiteName());
	    				
	    				boolean isDataFeed = false;
	    				boolean isSelenium = false;
	    				String ecommerceType = "";
	    				if(siteConfig != null)
	    				{
	    					isDataFeed = siteConfig.hasDatafeed();
	    					isSelenium = siteConfig.isSelenium();
	    					ecommerceType = siteConfig.getECommerceName();
	    				}
	    				
	    				if (statistics.getStartDate() > 0) {
		    				beginDate = dateFormat.format(new Date(statistics.getStartDate()));
	    				} else {
	    					beginDate = "Not started";
	    				}
	    				
	    				if (statistics.getEndDate() > 0) {
		    				endDate = dateFormat.format(new Date(statistics.getEndDate()));
	    				} else {
							endDate = "Not ended";	    					
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
    					
    					String schedulDate = "";
    					if(statistics.getNextExecutionDate() != 0)
    					{
    						Date nextExecutionDate = new Date(statistics.getNextExecutionDate());
    						schedulDate = dateFormat.format(nextExecutionDate);
    					}
	    			%>
	    			<tr class="<%=statistics.getStatusMessage().toString().toLowerCase()%>">
	    				<td>
	    				<a class="iframe" href='jobdetail_min.jsp?sn=<%=statistics.getSiteName()%>'><img src="<%=request.getContextPath()%>/images/icons/16/zoom_in.png"/></a>
	    				<a href='jobdetail.jsp?sn=<%=statistics.getSiteName()%>'><%=statistics.getSiteName()%></a>
	    				</td>
	    				<td><span class="" title=""><%=format.format(summ.getOverallHealth())%></span></td>
	    				<td>
	    					<%if(isSelenium){%><img src="<%=request.getContextPath()%>/images/icons/24/green_button.png" title="Selenium"/><%} else {%>
	    					<img src="<%=request.getContextPath()%>/images/icons/24/<%=isDataFeed ? "green_flag.png" : "red_flag.png"%>"/>
	    					<%} %>
	    				</td>
	    				<td><%=ecommerceType%></td>
	    				<td><span title="schedule date"><%=schedulDate%></span></td>
	    				<td><span title="begin date"><%=beginDate%></span></td>
	    				<td><span title="end date"><%=endDate%></span></td>
						<td>
						<%if(member != null){ %>
						<a href="<%=request.getContextPath()%>/server.jsp?id=<%=serverId%>"><%=serverName%></a>
						<%} else {%> <%=serverName%> <%} %>
						</td>
						<td><%=statistics.getStatus().toString()%></td>
	    				<td><span title="product count"><%=statistics.getProductCount()%></span></td>
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
	    			</tbody>
	    			<tfoot>
	    				<tr><td colspan="12">Total size: <%=statisticsList.size()%></td></tr>
	    			</tfoot>
	    	</table>
	    	
    	</div>
    <%@ include file="footer.jsp" %>    	
    </div>
   </div>
</body>
</html>