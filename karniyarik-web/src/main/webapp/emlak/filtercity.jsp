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
List<LinkedLabel> list = estateUtil.getCity(searchResult);
String[] values = estateUtil.getFilterData(list, "city", request);
String data = values[0];
String value = values[1];
%>
<div class="lhead" title="Sadece belli bir şehirdeki emlak ilanlarını göster">
  <a class="sh act" href="javascript:"></a>
  Şehir </div>
<div class="llist act c1" id="city_list">
  <input type="hidden" id="city" name="city" value="<%=value%>" class="rotc"/>
  <%
  String follow = list.size() > 1 ? "" : "rel='nofollow'"; 
  for(LinkedLabel linkedLabel: list){%>
  <div class="pb">
    <label>
      <input name="" id="<%=linkedLabel.getLabel()%>_chkbox" type="checkbox" value=""/>
      <span><a title="Sadece <%=linkedLabel.getLabel()%> şehirindeki emlak ilanlarını göster" href="<%=linkedLabel.getLink()%>" <%=follow%>><%=linkedLabel.getLabel()%></a></span>
      <span class="f1" title="Bu özelliğe sahip toplam <%=linkedLabel.getCount()%> sonuç var">(<%=linkedLabel.getCount()%>)</span>
    </label>
  </div>
  <%} %> 
  <script type="text/javascript">
  setFilterValues("#city_list", "#city", [<%=data%>]);
  hookFilter("#city_list", "#city");
  </script>
</div>
<div class="lbottom c1 act">
  <a href="javascript:" onclick="cleanAndSubmit('city');">
  	<img class="sprite filter-cl" src="<%=request.getContextPath()%>/images/transparent.gif"  width="16" height="16" title="Temizle" alt="Temizle"/>
  </a>
  <a href="javascript:" onclick="$('#frmSearch').submit();">
  	<img class="sprite filter-ap" src="<%=request.getContextPath()%>/images/transparent.gif"  width="16" height="16" title="Uygula" alt="Uygula"/>
  </a>
</div>