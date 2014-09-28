<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="java.util.List"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="org.apache.commons.lang.StringEscapeUtils"
%><%@page import="com.karniyarik.web.category.UtilProvider"
%><%@page import="com.karniyarik.web.category.EstateUtil"
%><%@page import="com.karniyarik.ir.SearchConstants"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
EstateUtil estateUtil = (EstateUtil) UtilProvider.getDomainUtil(CategorizerConfig.ESTATE_TYPE);

List<String> values = estateUtil.getRangeFilterVariables(SearchConstants.PRICE, searchResult, request);
String price1 = values.get(0);
String price2 = values.get(1); 
String min = values.get(4); 
String max = values.get(5);
String priceMin = values.get(2); 
String priceMax  = values.get(3);

%>

<div class="lhead" title="Sadece belli bir fiyat aralığındaki ürünleri göster">
  <a class="sh act" href="javascript:"></a>
  Fiyat</div>
<div class="llist act b c1">
	<div class="pb price">
		<div class="fst">
			<input name="price1" type="text" id="price1" size="7" value="<%=price1%>" class="numeric rotc fbox" />TL
		</div>
		<div class="scd">
			<input name="price2" type="text" id="price2" size="7" value="<%=price2%>" class="numeric rotc fbox"/>TL
		</div>
	</div>
	<div id="price-range" class="rangefilter"></div>
	<script type="text/javascript">
	$(function() {
		$( "#price-range" ).slider({
			range: true,
			min: <%=priceMin%>,
			max: <%=priceMax%>,
			step: 5,
			values: [ <%=min%>, <%=max%> ],			
			slide: function( event, ui ) {
				resetnumeric($("#price1"),$( "#price-range" ).slider( "values", 0 ));
				resetnumeric($("#price2"),$( "#price-range" ).slider( "values", 1 ));		
			}
		});
	});
	</script>
</div>
<div class="lbottom c1 act">
  <a href="javascript:" onclick="cleanAndSubmit('price1,price2');">
  	<img class="sprite filter-cl" src="<%=request.getContextPath()%>/images/transparent.gif"  width="16" height="16" title="Temizle" alt="Temizle"/>
  </a>
  <a href="javascript:" onclick="$('#frmSearch').submit();">
  	<img class="sprite filter-ap" src="<%=request.getContextPath()%>/images/transparent.gif"  width="16" height="16" title="Uygula" alt="Uygula"/>
  </a>
  <input name="pricemax" type="hidden" size="5" value="<%=priceMax%>" class="rotc"/>
  <input name="pricemin" type="hidden" size="5" value="<%=priceMin%>" class="rotc"/>
</div>   