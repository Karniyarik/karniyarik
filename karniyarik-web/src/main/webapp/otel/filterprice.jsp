<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.json.SearchResult"%>
<%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="com.karniyarik.web.util.RequestWrapper"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.web.category.ProductUtil"%>
<%@page import="com.karniyarik.common.util.StringUtil"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.karniyarik.web.category.OtelUtil"%>
<%@page import="com.karniyarik.web.category.UtilProvider"%>
<%@page import="com.karniyarik.common.config.system.CategorizerConfig"%>
<%SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
OtelUtil otelUtil = (OtelUtil) UtilProvider.getDomainUtil(CategorizerConfig.HOTEL_TYPE);
%>
<div class="feas otel price">
	<div class="lhead" title="Sadece belli bir fiyat aralığındaki otelleri göster">
	  <a class="sh act" href="javascript:"></a>
	  Fiyat
	</div>
   <div class="llist act" id="price_list">
	<%
		List<LinkedLabel> priceClusters = otelUtil.getPrice(searchResult);
		if(!otelUtil.isPriceFilterApplied(request))
		{    	 
		for(LinkedLabel linkedLabel: priceClusters)
		{%>
	<div class="pb">
		<a rel="nofollow" class="price" title="Sadece fiyatı <%=linkedLabel.getLabel()%> olan sonuçları göster" href='<%=linkedLabel.getLink()%>' rel="nofollow"><%=linkedLabel.getLabel()%></a>
	</div>
	<%}} %>
  <div class="pb">
   <%String price1 = StringUtil.getValue(request.getParameter("price1"));
	String price2 = StringUtil.getValue(request.getParameter("price2"));%>
    <input name="price1" type="text" id="textfield" size="5" value="<%=price1%>" class="numeric rotc"/>
    -
    <input name="price2" type="text" id="textfield2" size="5" value="<%=price2%>" class="numeric rotc"/>
    TL
    <input type="image" src="<%=request.getContextPath()%>/images/ara.png" class="bu"/>
  </div>
</div>
  <div class="lbottom c1 act">
  <a href="javascript:" onclick="cleanAndSubmit('city');"><img src="<%=request.getContextPath()%>/images/remove.png" title="Temizle" alt="Temizle"/></a>
  <a href="javascript:" onclick="$('#frmSearch').submit();"><img src="<%=request.getContextPath()%>/images/apply.png" title="Uygula" alt="Uygula"/></a>
  </div>  

</div>