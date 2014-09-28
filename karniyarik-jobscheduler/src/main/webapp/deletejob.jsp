<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.jobscheduler.request.JobRequestHandler"%>
<%@page import="com.karniyarik.jobscheduler.JobSchedulerAdmin"%>
<%JobRequestHandler.handleCrawlerRequest(request, response);%>
<%@page import="com.karniyarik.common.KarniyarikRepository"%>
<%@page import="com.karniyarik.common.config.site.SitesConfig"%>
<%@page import="com.karniyarik.common.config.site.SiteConfig"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
<title>Delete Job</title>
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
    	<div class="mt10">
	    	<table id="jobstable">
	    		<tr>
	    			<th>Site</th>
	    			<th>Actions</th>
	    		</tr>
	    		<%
	    			SitesConfig siteConfigList = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
	    		
	    			for(SiteConfig siteConfig: siteConfigList.getSiteConfigList()) {

	    			%>
	    			<tr>
	    				<td>
	    				<%=siteConfig.getSiteName()%>
	    				</td>
	    				<td>
	    					<a href="jobs.jsp?<%=JobRequestHandler.OPERATION_PARAMETER%>=<%=JobRequestHandler.DELETE_SITE_OPERATION%>&<%=JobRequestHandler.SITE_NAME_PARAMETER%>=<%=siteConfig.getSiteName()%>">
				    			<img src="images/icons/16/delete.png" alt="Delete" title="Delete"/>
				    		</a>
	    				</td>
	    			</tr>	
	    			<%}%>
	    	</table>
    	</div>
    <%@ include file="footer.jsp" %>    	
    </div>
   </div>
</body>
</html>