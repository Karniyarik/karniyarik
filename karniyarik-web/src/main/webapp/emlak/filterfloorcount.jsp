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
List<String> values = estateUtil.getRangeFilterVariables("floorcount", searchResult, request);
String price1 = values.get(0);
String price2 = values.get(1); 
String min = values.get(4); 
String max = values.get(5);
String priceMin = values.get(2); 
String priceMax  = values.get(3);
%>
<div class="lhead" title="Sadece belli bir katta bulunan emlak ilanlarını göster">
  <a class="sh act" href="javascript:"></a>
  Kat</div>
<div class="llist act b c1">
	<div class="pb price">
		<div class="fst">
			<input name="floorcount1" type="text" id="floorcount1" size="7" value="<%=price1%>" class="numeric rotc fbox" />
		</div>
		<div class="scd">
			<input name="floorcount2" type="text" id="floorcount2" size="7" value="<%=price2%>" class="numeric rotc fbox"/>
		</div>
	</div>
	<div id="floorcount-range" class="rangefilter"></div>
	<script type="text/javascript">
	$(function() {
		$( "#floorcount-range" ).slider({
			range: true,
			min: <%=priceMin%>,
			max: <%=priceMax%>,
			step: 5,
			values: [ <%=min%>, <%=max%> ],			
			slide: function( event, ui ) {
				resetnumeric($("#floorcount1"),$( "#floorcount-range" ).slider( "values", 0 ));
				resetnumeric($("#floorcount2"),$( "#floorcount-range" ).slider( "values", 1 ));		
			}
		});
	});
	</script>
</div>
<div class="lbottom c1 act">
  <a href="javascript:" onclick="cleanAndSubmit('floorcount1,floorcount2');">
  	<img class="sprite filter-cl" src="<%=request.getContextPath()%>/images/transparent.gif"  width="16" height="16" title="Temizle" alt="Temizle"/>
  </a>
  <a href="javascript:" onclick="$('#frmSearch').submit();">
  	<img class="sprite filter-ap" src="<%=request.getContextPath()%>/images/transparent.gif"  width="16" height="16" title="Uygula" alt="Uygula"/>
  </a>
  <input name="floorcountmax" type="hidden" size="5" value="<%=priceMax%>" class="rotc"/>
  <input name="floorcountmin" type="hidden" size="5" value="<%=priceMin%>" class="rotc"/>
</div>   