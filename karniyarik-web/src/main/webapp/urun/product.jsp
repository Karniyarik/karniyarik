<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.util.RequestWrapper"%>
<%@page import="com.karniyarik.common.util.StringUtil"%>
<%@page import="com.karniyarik.common.config.system.CategorizerConfig"%>
<%
int categoryType =  RequestWrapper.getInstance(request).getCategoryType();
%>
<html lang="tr" version="XHTML+RDFa 1.0" xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<%	String subcontext = categoryType==CategorizerConfig.CAR_TYPE ? "araba" : "%C3%BCr%C3%BCn";
	
		String url = request.getContextPath() + "/" + subcontext + "/search.jsp?" + request.getQueryString(); 
		response.setStatus(301);
		response.setHeader( "Location", url);
		response.setHeader( "Connection", "close" );%> 
</body>
</html>