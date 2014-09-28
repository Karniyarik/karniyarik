<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
if(searchResult.isShowSuggestion()) {%>
			<div class="prop">
			  <div class="c1">
			  Yaptığınız <b><%=searchResult.getQuery()%></b> sorgusu için az miktarda ya da hiç sonuç bulunamadı.<br/>
			  &nbsp;<br/>
			  Aşağıda verilen sorgunuza benzer sorgu önerilerini kullanabilirsiniz:
			    <div class="props didyoumean" >
			      <div class="cl"></div>
			        <%for(int index=0; index<searchResult.getSuggestedWords().size(); index++) {
			        %><a href="<%=searchResult.getSuggestedWords().get(index).getLink()%>"><%=searchResult.getSuggestedWords().get(index).getLabel()%></a><%
			        }%><div class="cl"></div>
			    </div>
			  </div>
			</div><%}%>