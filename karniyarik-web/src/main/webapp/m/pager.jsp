<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();%>
<wng:navigation_bar separator="|"><%
for(LinkedLabel linkedLabel: searchResult.getPagerResults()){
	if(linkedLabel.getLabel().equalsIgnoreCase("&lt;")){%>
	<wng:link href="<%=linkedLabel.getLink()%>" text="Önceki Sayfa"/><%
	}
	if(linkedLabel.getLabel().equalsIgnoreCase("&gt;")){%>
	<wng:link href="<%=linkedLabel.getLink()%>" text="Sonraki Sayfa"/><%
	}
}
%>
</wng:navigation_bar>

<br/>
	<label for="page">Sayfaya Git:</label>
	<select name="page"><%
	for(LinkedLabel linkedLabel: searchResult.getPagerResults()){%>
		<option value="<%=linkedLabel.getCount()%>" <%=linkedLabel.getCssClass().equals("act") ? "selected='selected'" : ""%>><%=linkedLabel.getCount()%></option>
	<%}%>
	</select>
<input name="" type="submit" value="Git"/>
