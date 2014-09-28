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
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.karniyarik.jobscheduler.JobAdminStateFilter"%>
<%@page import="java.util.ArrayList"%><html>
<head>
<title>Karniyarik JobScheduler Jobs Health</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb">
	    <div class="mt10"></div>
	    <h1>Job Healths</h1>
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
    			$( "#filter" ).buttonset();
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
				<a id="refresh" href="jobs_health.jsp<%=refreshStr%>">Refresh</a>
			</span>
			<span class="toolbar ui-widget-header ui-corner-all">
				<span id="filter">
					<a id="all" href="jobs_health.jsp?state=<%=JobAdminStateFilter.ALL%>">All</a>
					<a id="executing" href="jobs_health.jsp?state=<%=JobAdminStateFilter.EXECUTING%>">Executing</a>
					<a id="failed" href="jobs_health.jsp?state=<%=JobAdminStateFilter.FAILED%>">Failed</a>
					<a id="paused" href="jobs_health.jsp?state=<%=JobAdminStateFilter.PAUSED%>">Paused</a>
					<a id="broken" href="jobs_health.jsp?state=<%=JobAdminStateFilter.BROKEN%>">Broken</a>
					<a id="datafeed" href="jobs_health.jsp?state=<%=JobAdminStateFilter.DATAFEED%>">Dtfd</a>
					<a id="selenium" href="jobs_health.jsp?state=<%=JobAdminStateFilter.SELENIUM%>">Slnm</a>
					<a id="crawler" href="jobs_health.jsp?state=<%=JobAdminStateFilter.CRAWLER%>">Crwlr</a>
				</span>
			</span>
			<span class="toolbar ui-widget-header ui-corner-all">
				<input type="checkbox" <%=checked%> id="autorefresh" name="autorefresh"/><label for="autorefresh">Auto refresh</label>
			</span>
		</div>
		<div class="cl"></div>
    	<div class="mt10">
		    
	   	<table id="jobsTable">
	   		<tr>
	   			<th>Site</th>
	   			<th>Datafeed</th>
	   			<th>Product</th>
	   			<th>Brand</th>
	   			<th>Image</th>
	   			<th>Speed</th>
	   			<th>Overall</th>
	   		</tr>
	   		<%
				state = request.getParameter("state");
				
				List<JobAdminStateFilter> filters = new ArrayList<JobAdminStateFilter>();
				if(StringUtils.isNotBlank(state))
				{
					filters.add(JobAdminStateFilter.valueOf(state));
				}
				else
				{
					filters.add(JobAdminStateFilter.ALL);
				}
	
				List<JobStatisticsSummary> summList = JobSchedulerAdmin.getInstance().getStatisticsSummary(filters);
				
	    		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
	  			
	  			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); 
	  			for(JobStatisticsSummary summ: summList) {
	   				SiteConfig siteConfig = sitesConfig.getSiteConfig(summ.getSiteName());
	   				boolean isDataFeed = false;
	   				if(siteConfig != null)
	   				{
	   					isDataFeed = siteConfig.hasDatafeed();
	   				}
	   				DecimalFormat format = new DecimalFormat("#0");
	   			%>
	   			<tr>
	   				<td>
	   				<a class="iframe" href='jobdetail_min.jsp?sn=<%=summ.getSiteName()%>'><img src="<%=request.getContextPath()%>/images/icons/16/zoom_in.png"/></a>
	   				<a href='jobdetail.jsp?sn=<%=summ.getSiteName()%>'><%=summ.getSiteName()%></a>
	   				</td>
	   				<td><img src="<%=request.getContextPath()%>/images/icons/16/<%=isDataFeed ? "green_flag.png" : "red_flag.png"%>"/></td>
	   				<td><span class="progress" title="Product"><%=format.format(summ.getRatioProductAccepted())%></span></td>
	   				<td><span class="progress" title="Brand"><%=format.format(summ.getRatioParsedBrand())%></span></td>
	   				<td><span class="progress" title="Image"><%=format.format(summ.getRatioParsedImage())%></span></td>
	   				<td><span class="progress" title="Time"><%=format.format(summ.getFetchTimePercentage())%></span></td>
	   				<td><span class="progress" title=""><%=format.format(summ.getOverallHealth())%></span></td>
	   			</tr>	
	   			<%}%>
	   	</table>
	   	<div class="span-24">Total size: <%=summList.size()%></div>
    </div>
    <%@ include file="footer.jsp" %>
   </div>
   </div>
</body>

</html>