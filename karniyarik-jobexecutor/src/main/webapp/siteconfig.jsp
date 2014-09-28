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
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.karniyarik.jobexecutor.JobExecutorAdmin"%>
<%@page import="com.karniyarik.jobexecutor.JobExecutionTask"%><html>
<head>
<title>Site Config</title>
<jsp:include page="common_include.jsp"></jsp:include>
<style>
	.lbl {font-weight: bold;float:left;}
</style>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb">
	    <div class="mt10"></div>
	    <h1>Site Config</h1>
	    <hr/>
	    <div class="span-23 last cl"></div>
	    <div class="mb10"></div>
    	<div class="span-23 last cl"></div>
    	<div class="mt10">
    	<div class="span-23 last cl"></div>
    	<div class="span-23 last mt10 mb10">
    	<%
    	String sitename = request.getParameter("s");
    	JobExecutionTask task = JobExecutorAdmin.getInstance().getJobExecutionTask(sitename);
    	if(task == null)
    	{
    		
    	}
    	
    	SiteConfig siteConfig = task.getSiteConfig();
    	%>
	    	<div class="span-12">
		    	<h2>General</h2>
		    	<hr/>
		    	<span class="span-5 lbl">Site name</span><span class="span-5"><%=siteConfig.getSiteName()%></span><br/>
		    	<span class="span-5 lbl">Domain name</span><span class="span-5"><%=siteConfig.getDomainName()%></span><br/>
			   	<span class="span-5 lbl">Ecommerce</span><span class="span-5"><%=siteConfig.getEcommerceConfig()%></span><br/>
		    	<span class="span-5 lbl">Category</span><span class="span-5"><%=siteConfig.getCategory()%></span><br/>
		    	<span class="span-5 lbl">URL</span><span class="span-5"><%=siteConfig.getUrl()%></span><br/>
		    	<span class="span-5 lbl">Encoding</span><span class="span-5"><%=siteConfig.getSiteEncoding()%></span><br/>
		    	<span class="span-5 lbl">Rank</span><span class="span-5"><%=siteConfig.getSiteRank()%></span><br/>
				<span class="span-5 lbl">Single brand</span><span class="span-5"><%=siteConfig.getSingleBrand()%></span><br/>
		    	<span class="span-5 lbl">Crawl delay</span><span class="span-5"><%=siteConfig.getCrawlDelay()%></span>
		    	<span class="span-5 lbl">Cron</span><span class="span-5"><%=siteConfig.getCron()%></span><br/>	    	
	    	</div>
	    	<div class="span-11 last">
		    	<h2>Classes</h2>
		    	<hr/>	    	
				<span class="span-5 lbl">Rank helper</span><span class="span-5"><%=siteConfig.getRankHelper()%></span><br/>
				<span class="span-5 lbl">Crawl Rule class</span><span class="span-5"><%=siteConfig.getRuleClassName()%></span><br/>
				<span class="span-5 lbl">Paging rule</span><span class="span-5"><%=siteConfig.getPagingRule()%></span><br/>
				<span class="span-5 lbl">Affliate class</span><span class="span-5"><%=siteConfig.getAffiliateClass()%></span><br/>
		    </div>
			<div class="span-12">
				<h2>XQuery</h2>
		    	<hr/>
		    	<span class="span-5 lbl">Name</span><span class="span-5"><%=siteConfig.getNameXQuery()%></span><br/>
		    	<span class="span-5 lbl">Product Url</span><span class="span-5"><%=siteConfig.getDatafeedProductUrlXPath()%></span><br/>
		    	<span class="span-5 lbl">Price</span><span class="span-5"><%=siteConfig.getPriceXQuery()%></span><br/>
		    	<span class="span-5 lbl">Brand</span><span class="span-5"><%=siteConfig.getBrandXQuery()%></span><br/>
		    	<span class="span-5 lbl">Breadcrumb</span><span class="span-5"><%=siteConfig.getBreadcrumbXQuery()%></span><br/>
		    	<%for(String propertyStr: siteConfig.getPropertyXQueryMap().keySet()) {
		    		String value = siteConfig.getPropertyXQuery(propertyStr);%>	
		    		<span class="span-5 lbl"><%=propertyStr%></span><span class="span-5"><%=value%></span><br/>
		    	<%} %>
			</div>    	
			<div class="span-11 last">
				<h2>Datafeed</h2>
		    	<hr/>
		    	<span class="span-5 lbl">Datafeed Type</span><span class="span-5"><%=siteConfig.getDatafeedType()%></span><br/>
		    	<span class="span-5 lbl">Datafeed Config</span><span class="span-5"><%=siteConfig.getDatafeedConfig()%></span><br/>
		    	<h4>URLs</h4>
		    	<%for(String datafeedUrl: siteConfig.getDatafeedUrlList()) {%>
		    		<span class="span-5 lbl">&nbsp;</span><span class="span-5"><%=datafeedUrl%></span><br/>
		    	<%}%>
				<span class="span-5 lbl">Products</span><span class="span-5"><%=siteConfig.getDatafeedProductsXPath()%></span><br/>
		    	<span class="span-5 lbl">Name</span><span class="span-5"><%=siteConfig.getDatafeedNameXPath()%></span><br/>
		    	<span class="span-5 lbl">Brand</span><span class="span-5"><%=siteConfig.getDatafeedBrandXPath()%></span><br/>
		    	<span class="span-5 lbl">Breadcrumb</span><span class="span-5"><%=siteConfig.getDatafeedBreadcrumbXPath()%></span><br/>
		    	<span class="span-5 lbl">Currency</span><span class="span-5"><%=siteConfig.getDatafeedCurrencyXPath()%></span><br/>
		    	<span class="span-5 lbl">Price</span><span class="span-5"><%=siteConfig.getDatafeedPriceXPath()%></span><br/>
		    	<span class="span-5 lbl">Price Pattern</span><span class="span-5"><%=siteConfig.getDatafeedPricePattern()%></span><br/>
		    	<span class="span-5 lbl">Price Decimal</span><span class="span-5"><%=siteConfig.getDatafeedPriceDecimalSeparator()%></span><br/>
		    	<span class="span-5 lbl">Price Thousand</span><span class="span-5"><%=siteConfig.getDatafeedPriceThousandSeparator()%></span><br/> 	
			</div>
			<div class="span-12">
				<h2>Rules</h2>
		    	<hr/>
		    	<%for(String rule: siteConfig.getIgnoreList()){ %>
		    	<span class="span-5 lbl"></span><span class="span-5"><%=rule%></span><br/>
		    	<%} %>			
			</div>
    	</div>
    	<div class="span-23 last cl"></div>
    	</div>
    <%@ include file="footer.jsp" %>    	
    </div>
   </div>
</body>
</html>