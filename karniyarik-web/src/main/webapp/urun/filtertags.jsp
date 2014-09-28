<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.json.SearchResult"%>
<%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="com.karniyarik.web.util.RequestWrapper"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.karniyarik.web.category.ProductUtil"%>
<%@page import="com.karniyarik.web.category.UtilProvider"%>
<%@page import="com.karniyarik.common.config.system.CategorizerConfig"%>
<%SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
ProductUtil productUtil = (ProductUtil) UtilProvider.getDomainUtil(CategorizerConfig.PRODUCT_TYPE);
List<LinkedLabel> list = productUtil.getTags(searchResult);
String[] values = productUtil.getFilterData(list, "tags", request);
String data = values[0];
String value = values[1];%>
<div class="lhead" title="Sadece belli bir etİkete ait ürünleri göster">
  <a class="sh act" href="javascript:"></a>
  Etiket </div>
<div class="llist act c1" id="tags_list">
  <input type="hidden" id="tags" name="tags" value="<%=value%>" class="rotc"/>
  <%
  String follow = list.size() > 1 ? "" : "rel='nofollow'"; 
  for(LinkedLabel linkedLabel: list){%>
  <div class="pb">
    <label>
      <input name="" id="<%=linkedLabel.getLabel()%>_chkbox" type="checkbox" value=""/>
      <span><a title="Sadece <%=linkedLabel.getLabel()%> etiketine ait ürünleri göster" href="<%=linkedLabel.getLink()%>" <%=follow%>><%=linkedLabel.getLabel()%></a></span>
      <span class="f1" title="Bu özelliğe sahip toplam <%=linkedLabel.getCount()%> sonuç var">(<%=linkedLabel.getCount()%>)</span>
    </label>
  </div>
  <%} %> 
  <script type="text/javascript">
  setFilterValues("#tags_list", "#tags", [<%=data%>]);
  hookFilter("#tags_list", "#tags");
  </script>
</div>
<div class="lbottom c1 act">
  <a href="javascript:" onclick="cleanAndSubmit('tags');"><img src="<%=request.getContextPath()%>/images/remove.png" title="Temizle" alt="Temizle"/></a>
  <a href="javascript:" onclick="$('#frmSearch').submit();"><img src="<%=request.getContextPath()%>/images/apply.png" title="Uygula" alt="Uygula"/></a>
</div>