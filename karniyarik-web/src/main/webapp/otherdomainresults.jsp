<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="org.apache.commons.lang.StringEscapeUtils"
%><%@page import="com.karniyarik.web.json.CrossDomainSearchResult"
%><%@page import="com.karniyarik.web.json.ProductResult"
%><%@page import="com.karniyarik.web.servlet.image.ImageServlet"
%><%
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();%>
<%if(searchResult.getOtherVerticalResults().size() > 0){ %>
	<div class="prop c1 oc_wrap" style="margin-top:20px;">
	  Bunun yanında diğer kategorilerde sonuçlar mevcut: 
	  <div class="oc_list">
	  	<%for(String domainName: searchResult.getOtherVerticalResults().keySet()) {
	  		CrossDomainSearchResult result =  searchResult.getOtherVerticalResults().get(domainName);%>
	  		<div class="oc_c props full">
	  			<div class="oc_h" style="display:block;"> 
	  				<a href="<%=result.getUrl()%>"><%=result.getCategoryName()%> Kategorisi (<%=result.getTotalHits()%> sonuç) - Git</a>
	  			</div>
	  			<div class="oc_il"><%
	  			for(ProductResult pro: result.getResults()) {
	  				String imgUrl = ImageServlet.getImageRszUrl(request, pro.getImageURL(), pro.getProductName(), 100, 100);%>
		  			<div class="oc_i">
		  				<div class="i">
		  					<img src="<%=imgUrl%>" height="100px" width="100px;" />
		  				</div>
		  				<span class="n"><%=LinkedLabel.getShortenedLabel(pro.getProductName(), 10)%></span>
		  				<span class="p"><%=pro.getPrice()%> <%=pro.getPriceCurrency()%></span>
		  			</div><%
	  			}%>
	  			</div> 
	  		</div><%
	  	} 
	  %></div>
	</div><%
}%>