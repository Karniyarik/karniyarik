<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="org.apache.commons.lang.StringEscapeUtils"
%><%
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
if(searchResult.isShowNotFound()) { %>
<%@page import="com.karniyarik.search.solr.SOLRSearcherImpl"%><div class="prop">
			  <div class="c1 text">
			    Yaptığınız <b><%=searchResult.getQuery()%></b> sorgusu ya da filtrelere uygun sonuç bulunamadı<br/>
			    Öneriler: <%if(searchResult.getQuery() == null || searchResult.getQuery().length() <= SOLRSearcherImpl.MIN_QUERY_LENGTH) {%>
				<ul>
			    	<li>Arama yapmak için en az <%=SOLRSearcherImpl.MIN_QUERY_LENGTH+1%> karakterli bir sorgu girmeniz gerekmektedir. .</li>
			    </ul>			    
			    <%} else {  
			    %><ul>
			    	<li>Tüm sözcükleri doğru yazdığınızdan emin olun.</li>
			    	<li>Başka sorgu kelimeleri deneyin.</li>
			    	<li>Daha az kelime deneyin.</li>
			    	<li>Filtrelerinizi yeniden düzenleyin.</li>
			    </ul><%} %>
			  </div>
			</div><%
} else if(searchResult.isShowURLNotFoundError()) {%>
			<div class="prop">
			  <div class="c1 text">
		    	Aranan ürün sistemden kaldırılmıştır.			    
			  </div>
			</div>
<%} %>

