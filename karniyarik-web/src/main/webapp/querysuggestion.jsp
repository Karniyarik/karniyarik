<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
if(searchResult.getTotalHits() > 100 && searchResult.getSuggestedQueries().size() > 0){%>
	<div class="prop">
	  <div class="c1"> Sorgunuz yüksek miktarda sonuç döndü. 
	    İsterseniz aşağıdaki sorguları kullanarak ya da "Sonuçları daralt" kısmındaki filtreleri kullanarak sorgu sonuçlarınızı daraltabilirsiniz.
	    <div class="props" >
	      <div class="cl lower"></div>
	      <%for(LinkedLabel suggestion: searchResult.getSuggestedQueries()) {
	      %><h2><a href="<%=suggestion.getLink()%>" title="<%=suggestion.getLabel()%>"><%=suggestion.getShortenedLabel(30)%></a></h2><%
	      }
	      %><div class="cl"></div>
	    </div>
	  </div>
	</div><%
}%>