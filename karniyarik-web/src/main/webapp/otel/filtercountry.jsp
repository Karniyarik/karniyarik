<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.json.SearchResult"%>
<%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.web.category.OtelUtil"%>
<%@page import="com.karniyarik.web.category.UtilProvider"%>
<%@page import="com.karniyarik.common.config.system.CategorizerConfig"%>
<%SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
OtelUtil otelUtil = (OtelUtil) UtilProvider.getDomainUtil(CategorizerConfig.HOTEL_TYPE);
List<LinkedLabel> list = otelUtil.getCountry(searchResult);
String[] values = otelUtil.getFilterData(list, "country", request);
String data = values[0];
String value = values[1];
%>
<div class="feas otel">
  <div class="lhead" title="Sadece belli bir ülkedeki otelleri göster">
    <a class="sh act" href="javascript:"></a>
    Ülke</div>
  <div class="llist act" id="country_list">
  <!-- div class="remove"><img src="<%=request.getContextPath()%>/images/kaldir.png" width="61" height="17" alt="Kaldır" /></div -->
  <input type="hidden" id="country" name="country" value="<%=value%>" class="rotc"/>
  <%for(LinkedLabel linkedLabel: list){%>
    <div class="pb">
      <label>
  	    <input name="" type="checkbox" value="" id="<%=linkedLabel.getLabel()%>_chkbox"/>
	    <span><a rel="nofollow" title="Sadece <%=linkedLabel.getLabel()%> ülkesindeki otelleri göster" href="<%=linkedLabel.getLink()%>"><%=linkedLabel.getLabel()%></a></span>
	    <span class="f1" title="Bu özelliğe sahip toplam <%=linkedLabel.getCount()%> sonuç var">(<%=linkedLabel.getCount()%>)</span>
      </label>
     </div>
 <%}%>    
  </div>
  <script type="text/javascript">
  setFilterValues("#country_list", "#country", [<%=data%>]);
  hookFilter("#country_list", "#country");
  </script>
  <div class="lbottom c1 act">
  <a href="javascript:" onclick="cleanAndSubmit('country');"><img src="<%=request.getContextPath()%>/images/remove.png" title="Temizle" alt="Temizle"/></a>
  <a href="javascript:" onclick="$('#frmSearch').submit();"><img src="<%=request.getContextPath()%>/images/apply.png" title="Uygula" alt="Uygula"/></a>
  </div>  
</div>