<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
		%><div class="navh"><%
			int index = searchResult.getPageNumber();
			boolean nofollow = searchResult.getPageNumber() > 1;
			for(LinkedLabel linkedLabel: searchResult.getPagerResults()){
				index++;
			%><a <%=(index > 4 || nofollow)  ? "rel='nofollow'" : ""%> <%=StringUtils.isNotBlank(linkedLabel.getCssClass()) ? "class='" + linkedLabel.getCssClass() + "'" : ""%> title="<%=linkedLabel.getTooltip()%>" href="<%=linkedLabel.getLink()%>"><%=linkedLabel.getLabel()%></a><%
			}
		%></div>

