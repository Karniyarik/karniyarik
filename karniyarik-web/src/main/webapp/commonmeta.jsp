<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="java.util.Calendar"
%><%@page import="java.text.DateFormat"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="com.karniyarik.web.json.SearchResult"%>
		<meta http-equiv="Content-Language" content="tr"/>
		<meta http-equiv="Content-type" content="text/html;charset=UTF-8" />
		<meta name="location" content="türkiye, tr, turkey" />
		<link rel="search" type="application/opensearchdescription+xml" title="Karnıyarık" href="<%=request.getContextPath()%>/opensearch.xml"/>
		<%boolean robotsIndex = true;
		Object srObj = request.getAttribute(RequestWrapper.SEARCH_RESULT);
		if(srObj != null){
			SearchResult searchResult = (SearchResult) srObj;
			if(searchResult.getTotalHits() < 1 && searchResult.getSiteCount() < 1){
				robotsIndex = false;
			}
		}		
		if(robotsIndex){%><meta name="robots" content="index,follow,noodp" /><%} 
			else {%><meta name="robots" content="noarchive,nosnippet,noodp" /><%}%>
		<link rel="meta" type="application/rdf+xml" title="RDF/XML data for Karniyarik LLC" href="http://www.karniyarik.com/semanticweb.rdf" />
		<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico"/>
		<meta name="verify-v1" content="1soHu32NTG0PeVBYV5YPcPYhUHQO7zXtALd8Nln0I1c=" />
		<meta name="google-site-verification" content="R9lmefqe-nfV4gZJQxdflJEUQ1QrWCld1NhSwlf6uuM" />
		<meta name="y_key" content="634e3ab80f4f342d"/>
		<%Object cacheControl = request.getAttribute("k-cache-control");
		if(cacheControl != null) {
			String cacheControlStr = cacheControl.toString();
			String gmtDate = request.getAttribute("k-cache-expires").toString();%>
		<meta name="expires" http-equiv="expires" content="<%=gmtDate%>" />
		<meta name="cache-control" http-equiv="cache-control" content="<%=cacheControlStr%>" />
		<meta property="og:site_name" content="Karniyarik"/>
		<meta property="fb:admins" content="90334878760"/>
		<meta property="og:region" content="Ankara"/>
		<meta property="og:country-name" content="Turkey"/><%}%>