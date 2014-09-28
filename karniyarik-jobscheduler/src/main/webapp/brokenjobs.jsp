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
    <h1>Broken Jobs</h1>
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
    			$( "#filter1" ).buttonset();
    		});
    		</script>
	    	<span class="toolbar ui-widget-header ui-corner-all">
				<a id="refresh" href="brokenjobs.jsp">Refresh</a>
			</span>
			<span class="toolbar ui-widget-header ui-corner-all">
				<span id="filter">
					<a id="all" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.ALL%>">All</a>
					<a id="executing" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.EXECUTING%>">Executing</a>
					<a id="paused" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.PAUSED%>">Paused</a>
					<a id="failed" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.FAILED%>">Failed</a>
					<a id="datafeed" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.DATAFEED%>">Dtfd</a>
					<a id="datafeed" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.SELENIUM%>">Slnm</a>
					<a id="datafeed" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.CRAWLER%>">Crwlr</a>
				</span>
			</span>
			<div class="cl" style="margin-top:20px;"></div>
			<span class="toolbar ui-widget-header ui-corner-all">
				<span id="filter1">
					<a id="ideasoft" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.IDEASOFT%>">Ideasoft</a>
					<a id="ideasoft" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.KOBIMASTER%>">Kobimaster</a>
					<a id="ideasoft" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.PROJE%>">Proj-e</a>
					<a id="ideasoft" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.HEMENAL%>">Hemenal</a>
					<a id="ideasoft" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.PRESTASHOP%>">Prestashop</a>
					<a id="ideasoft" href="brokenjobs.jsp?state=<%=JobAdminStateFilter.NETICARET%>">Neticaret</a>
				</span>
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
				String state = request.getParameter("state");
				
				List<JobAdminStateFilter> filters = new ArrayList<JobAdminStateFilter>();
				filters.add(JobAdminStateFilter.BROKEN);
				
				if(StringUtils.isNotBlank(state))
				{
					filters.add(JobAdminStateFilter.valueOf(state));
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
	   				<td><span class="progress" title=""><%=format.format(summ.getRatioProductAccepted())%></span></td>
	   				<td><span class="progress" title=""><%=format.format(summ.getRatioParsedBrand())%></span></td>
	   				<td><span class="progress" title=""><%=format.format(summ.getRatioParsedImage())%></span></td>
	   				<td><span class="progress" title=""><%=format.format(summ.getFetchTimePercentage())%></span></td>
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