<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.json.SearchResult"%>
<%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="com.karniyarik.web.util.RequestWrapper"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.common.util.StringUtil"%>
<%@page import="com.karniyarik.web.category.CarUtil"%>
<%@page import="com.karniyarik.web.category.UtilProvider"%>
<%@page import="com.karniyarik.common.config.system.CategorizerConfig"%>
<%@page import="com.karniyarik.ir.SearchConstants"%>
<%
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
CarUtil carUtil = (CarUtil) UtilProvider.getDomainUtil(CategorizerConfig.CAR_TYPE);
%>
<div class="fla"> Fiyatı :
	<%
	List<String> values = carUtil.getRangeFilterVariables(SearchConstants.PRICE, searchResult, request);
	String val1 = values.get(0);
	String val2 = values.get(1); 
	String min = values.get(4); 
	String max = values.get(5);
	String valMin = values.get(2); 
	String valMax  = values.get(3);
	%>
	<input name="price1" type="text" id="price1" size="5" value="<%=val1%>" class="inpu numeric rotc fbox" /> - 
	<input name="price2" type="text" id="price2" size="5" value="<%=val2%>" class="inpu numeric rotc fbox"/>TL
	<input name="pricemin" type="hidden" size="5" value="<%=valMin%>"/>
  	<input name="pricemax" type="hidden" size="5" value="<%=valMax%>"/>  	
	<div id="price-range" class="rangefilter"></div>
	<script>
	$(function() {
		$( "#price-range" ).slider({
			range: true,
			min: <%=valMin%>,
			max: <%=valMax%>,
			step: 500,
			values: [ <%=min%>, <%=max%> ],			
			slide: function( event, ui ) {
				resetnumeric($("#price1"),$( "#price-range" ).slider( "values", 0 ));
				resetnumeric($("#price2"),$( "#price-range" ).slider( "values", 1 ));
			}
		});
	});
	</script>
</div>
<div class="fla"> Kilometresi :
	<%
	values = carUtil.getRangeFilterVariables(SearchConstants.KM, searchResult, request);
	val1 = values.get(0);
	val2 = values.get(1); 
	min = values.get(4); 
	max = values.get(5);
	valMin = values.get(2); 
	valMax  = values.get(3);
	%>

  <input class="inpu numeric rotc fbox" type="text" name="km1" id="km1" value="<%=val1%>"/> -
  <input class="inpu numeric rotc fbox" type="text" name="km2" id="km2" value="<%=val2%>"/>
  <input name="kmmin" type="hidden" value="<%=valMin%>"/>
  <input name="kmmax" type="hidden" value="<%=valMax%>"/>
  
  <div id="km-range" class="rangefilter"></div>
	<script>
	$(function() {
		$( "#km-range" ).slider({
			range: true,
			min: <%=valMin%>,
			max: <%=valMax%>,
			step: 1000,
			values: [ <%=min%>, <%=max%> ],			
			slide: function( event, ui ) {
				resetnumeric($("#km1"),$( "#km-range" ).slider( "values", 0 ));
				resetnumeric($("#km2"),$( "#km-range" ).slider( "values", 1 ));
			}
		});
	});
	</script>
</div>
<%List<LinkedLabel> list = carUtil.getYear(searchResult, request);%>
<div class="fla"> Yılı :
  <input class="inpu numeric rotc fbox" type="text" name="modelyear1" id="modelyear1" value="<%=list.get(2).getCount()%>"/> -
  <input class="inpu numeric rotc fbox" type="text" name="modelyear2" id="modelyear2" value="<%=list.get(3).getCount()%>"/>
  <div id="modelyear-range" class="rangefilter"></div>
	<script>
	$(function() {
		$( "#modelyear-range" ).slider({
			range: true,
			min: <%=list.get(0).getCount()%>,
			max: <%=list.get(1).getCount()%>,
			step: 1,
			values: [ <%=list.get(2).getCount()%>, <%=list.get(3).getCount()%> ],			
			slide: function( event, ui ) {
				resetnumeric($("#modelyear1"),$( "#modelyear-range" ).slider( "values", 0 ));
				resetnumeric($("#modelyear2"),$( "#modelyear-range" ).slider( "values", 1 ));
			}
		});
	});
	</script>
</div>
<%list = carUtil.getEngineVolume(searchResult, request);%>
<div class="fla"> Motor Hacmi :
 <input class="inpu numeric rotc fbox" type="text" name="enginevolume1" id="enginevolume1" value="<%=list.get(2).getCount()%>"/>-
  <input class="inpu numeric rotc fbox" type="text" name="enginevolume2" id="enginevolume2" value="<%=list.get(3).getCount()%>"/> cc
  <div id="enginevolume-range" class="rangefilter"></div>
	<script>
	$(function() {
		$( "#enginevolume-range" ).slider({
			range: true,
			min: <%=list.get(0).getCount()%>,
			max: <%=list.get(1).getCount()%>,
			step: 250,			
			values: [ <%=list.get(2).getCount()%>, <%=list.get(3).getCount()%> ],			
			slide: function( event, ui ) {
				resetnumeric($("#enginevolume1"),$( "#enginevolume-range" ).slider( "values", 0 ));
				resetnumeric($("#enginevolume2"),$( "#enginevolume-range" ).slider( "values", 1 ));
			}
		});
	});
	</script>  
</div>
<%list = carUtil.getEnginePower(searchResult, request);%>
<div class="fla"> Motor Gücü :
  <input class="inpu numeric rotc fbox" type="text" name="enginepower1" id="enginepower1" value="<%=list.get(2).getCount()%>"/>-
  <input class="inpu numeric rotc fbox" type="text" name="enginepower2" id="enginepower2" value="<%=list.get(3).getCount()%>"/>hp
  <div id="enginepower-range" class="rangefilter"></div>
	<script>
	$(function() {
		$( "#enginepower-range" ).slider({
			range: true,
			min: <%=list.get(0).getCount()%>,
			max: <%=list.get(1).getCount()%>,
			step: 25,
			values: [ <%=list.get(2).getCount()%>, <%=list.get(3).getCount()%> ],			
			slide: function( event, ui ) {
				resetnumeric($("#enginepower1"),$( "#enginepower-range" ).slider( "values", 0 ));
				resetnumeric($("#enginepower2"),$( "#enginepower-range" ).slider( "values", 1 ));
			}
		});
	});
	</script>  
</div>
<div class="fla buts">
  <input type="image" class="sprite filter-cl-b" src="<%=request.getContextPath()%>/images/transparent.gif" onclick="cleanAndSubmit('price1,price2,km1,km2,modelyear1,modelyear2,enginepower1,enginepower2,enginevolume1,enginevolume2');"/>
  <span class="c5"> l </span>
  <input type="image" class="sprite filter-ap-b" src="<%=request.getContextPath()%>/images/transparent.gif"/>
</div>