<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%@page import="java.util.Locale"%>
<%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="java.text.NumberFormat"
%><%
int categoryType =  RequestWrapper.getInstance(request).getCategoryType();

String action=request.getContextPath() + "/m/urun/search.jsp";
if(categoryType == CategorizerConfig.CAR_TYPE){
	action=request.getContextPath()+"/m/araba/search.jsp";
}
request.setAttribute("formaction",action);

SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
String query = "";
if(searchResult != null) {
	query = searchResult.getEscapedQuery();
}
%>
<wng:header css_ref="sl">
	<wng:image imageRetriever="${slogo}" >
		<wng:link href="/m/"/>
	</wng:image>
</wng:header>
<wng:form action="${formaction}" method="get">
	<wng:input type="text" name="query" value="<%=query%>" size="5" css_ref="st"/>
	<wng:input id="srchbtn" type="submit" value="Ara" css_ref="sb"/>
</wng:form>
<wng:text>
	<wng:css>
		<wng:css_property name="font-size" value="x-small;" />
	</wng:css>
<%if(searchResult != null && searchResult.isShowProducts() && !searchResult.isSimilar()) {
	%><%=searchResult.getTimeTaken()%> sn, <%
	if(searchResult.getSiteCount() != 0) {
	%><%=NumberFormat.getInstance(Locale.ENGLISH).format(searchResult.getSiteCount())%> site, <%}
	%><%=NumberFormat.getInstance(Locale.ENGLISH).format(searchResult.getTotalHits())%> sonuç <%
}%>
</wng:text>
