<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.json.SearchResult"%>
<%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.web.category.CarUtil"%>
<%@page import="com.karniyarik.web.category.UtilProvider"%>
<%@page import="com.karniyarik.common.config.system.CategorizerConfig"%>
<%
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
CarUtil carUtil = (CarUtil) UtilProvider.getDomainUtil(CategorizerConfig.CAR_TYPE);
List<LinkedLabel> list = carUtil.getFuel(searchResult);
String[] values = carUtil.getFilterData(list, "fuel", request);
String data = values[0];
String value = values[1];
%>
<div class="feas">
  <div class="lhead" title="Sadece belli bir yakıt tipindeki arabaları göster">
    <a class="sh act" href="javascript:"></a>
    Yakıt Tipi</div>
  <div class="llist act" id="fuel_list">
  <!-- div class="remove"><img src="<%=request.getContextPath()%>/images/kaldir.png" width="61" height="17" alt="Kaldır" /></div -->
  <input type="hidden" id="fuel" name="fuel" value="<%=value%>" class="rotc"/>
  <%for(LinkedLabel linkedLabel: list){%>
    <div class="pb">
      <label>
  	    <input name="" type="checkbox" value="" id="<%=linkedLabel.getLabel()%>_chkbox"/>
	    <span><a title="Sadece <%=linkedLabel.getLabel()%> yakıt tipindeki araçları göster" href="<%=linkedLabel.getLink()%>" rel="nofollow"><%=linkedLabel.getLabel()%></a></span>
	    <span class="f1" title="Bu özelliğe sahip toplam <%=linkedLabel.getCount()%> sonuç var">(<%=linkedLabel.getCount()%>)</span>
      </label>
     </div>
 <%}%>    
  </div>
  <script type="text/javascript">
  setFilterValues("#fuel_list", "#fuel", [<%=data%>]);
  hookFilter("#fuel_list", "#fuel");
  </script>
  <div class="lbottom c1 act">
  <a href="javascript:" onclick="cleanAndSubmit('fuel');">
  	<img class="sprite filter-cl" src="<%=request.getContextPath()%>/images/transparent.gif"  width="16" height="16" title="Temizle" alt="Temizle"/>
  </a>
  <a href="javascript:" onclick="$('#frmSearch').submit();">
  	<img class="sprite filter-ap" src="<%=request.getContextPath()%>/images/transparent.gif"  width="16" height="16" title="Uygula" alt="Uygula"/>
  </a>
  </div>
</div>