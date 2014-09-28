<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><?xml version="1.0" encoding="UTF-8"?>
<%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@page import="java.util.List"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%@page import="java.util.List"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="com.karniyarik.web.json.SearchStatisticsManager"
%><%@page import="java.util.Calendar"
%><%@page import="com.karniyarik.common.KarniyarikRepository"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="com.karniyarik.web.util.QueryStringHelper"
%><%@page import="com.karniyarik.common.config.system.SearchConfig"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.site.SiteInfoProvider"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.json.ProductResult"
%><%@page import="com.karniyarik.web.category.BaseCategoryUtil"
%><%@page import="com.karniyarik.web.servlet.image.ImageServlet"
%><%@page import="com.karniyarik.web.category.ProductUtil"
%><%@page import="java.util.ArrayList"
%><%@page import="org.apache.commons.lang.StringEscapeUtils"
%><%@page import="com.karniyarik.web.category.UtilProvider"
%><%@page import="com.karniyarik.web.mobile.MobileUtil"
%><%
HttpCacheUtil.setStaticResponseCacheAttributes(response, request);%>
<wng:document>
	<wng:head>
		<wng:title>Hakkımızda</wng:title>
		<%@ include file="head.jsp" %>
	</wng:head>
    <wng:body>
    <%@ include file="nav.jsp" %>
    <jsp:include page="top.jsp"></jsp:include>
    <wng:title css_ref="h">Hakkımızda</wng:title>
	<wng:text_block>
		<wng:text>
			karniyarik.com gerek mağazalar gerekse internet sitelerinden alışveriş yapan tüketicilere fiyat-ürün çeşitliliği açılarından 
			daha bilinçli olmaları yönünde kolaylık sağlamak amacıyla servis veren bir sistemdir. 
		</wng:text>
		<wng:br/>
		<wng:text>
			karniyarik.com üzerinden herhangi bir ürün satılmamaktadır. Ürünler hakkındaki güncel bilgiler, satış yapan mağazaların internet 
			sitelerinde mevcuttur.
		</wng:text>
		<wng:br/>
		<wng:text>
			karniyarik.com üzerinden araba ilanı verilmemektedir. İlanlar hakkındaki güncel bilgiler, ilanların verildiği 
			internet sitelerinde mevcuttur.
		</wng:text>
	</wng:text_block>    
	<jsp:include page="footer.jsp"></jsp:include>
	</wng:body>
</wng:document>