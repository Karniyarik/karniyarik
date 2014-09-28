<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="tr" version="XHTML+RDFa 1.0" xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<%
		String url = request.getContextPath() + "/urun/search.jsp?" + request.getQueryString(); 
		response.setStatus(301);
		response.setHeader( "Location", url);
		response.setHeader( "Connection", "close" );%> 
</body>
</html>