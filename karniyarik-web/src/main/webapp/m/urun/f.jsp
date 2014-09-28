<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><?xml version="1.0" encoding="UTF-8"?>
<%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@page import="java.util.List"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%@page import="java.util.List"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="com.karniyarik.web.util.QueryStringHelper"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.category.ProductUtil"
%><%@page import="java.util.ArrayList"
%><%@page import="org.apache.commons.lang.StringEscapeUtils"
%><%@page import="com.karniyarik.ir.SearchConstants"
%><%@page import="com.karniyarik.web.category.UtilProvider"
%><%@page import="com.karniyarik.web.mobile.MobileUtil"
%><%
HttpCacheUtil.setSearchResponseCacheAttributes(response, request);
request.setAttribute(RequestWrapper.CATEGORY, CategorizerConfig.PRODUCT);
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
request.setAttribute(RequestWrapper.SEARCH_RESULT, searchResult);
ProductUtil productUtil = (ProductUtil) UtilProvider.getDomainUtil(CategorizerConfig.PRODUCT_TYPE);

String type = request.getParameter("t");
if(type == null) {type = "";};
List<LinkedLabel> list = new ArrayList<LinkedLabel>();
String title = "";
if(type.trim().equals(SearchConstants.STORE)){
	list = productUtil.getStore(searchResult);
	title = "Mağaza Filtrele";
} else if(type.trim().equals(SearchConstants.BRAND)){
	list = productUtil.getBrand(searchResult);
	title = "Marka Filtrele";
} 
String[] values = productUtil.getFilterData(list, type, request);
String data = values[0];
String value = values[1];
%>

<wng:document>
	<wng:head>
		<wng:title><%=title%> - <%=searchResult.getQuery()%></wng:title>
		<%@ include file="../head.jsp" %>
	</wng:head>
    <wng:body id="fp">
    <%@ include file="../nav.jsp" %>
	<wng:header css_ref="sl">
		<wng:image imageRetriever="${slogo}" >
			<wng:link href="/m/"/>
		</wng:image>
	</wng:header>
	    
    <wng:title css_ref="h"><%=title%> - <%=searchResult.getQuery()%></wng:title>

  	<%for(LinkedLabel linkedLabel: list){
  		String link = linkedLabel.getLink();
  		link = link.replace("f.jsp","search.jsp");
  		link = MobileUtil.removeParam(link, "t",type);
  		String text = linkedLabel.getLabel() + "("+linkedLabel.getCount()+")";
  		%><wng:list_item id="fbd"> 
  			<wng:link href="<%=link%>" text="<%=text%>" css_ref="cbtn"/>
		</wng:list_item>
	<%}%>
	
	<wng:br></wng:br>
	<%
    String uri = MobileUtil.replaceServlet(request, "f.jsp","search.jsp");
    uri = MobileUtil.removeParam(uri, "t",type);
    %>
	<wng:link css_ref="cbtn" href="<%=uri%>" text="Sonuçlara dön"/>
  	
	
	<jsp:include page="../footer.jsp"></jsp:include>
	</wng:body>
</wng:document>
