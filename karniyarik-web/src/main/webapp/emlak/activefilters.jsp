<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.json.SearchResult"%>
<%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="com.karniyarik.web.util.RequestWrapper"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.karniyarik.web.category.UtilProvider"%>
<%@page import="com.karniyarik.web.category.EstateUtil"%>
<%@page import="com.karniyarik.common.config.system.CategorizerConfig"%>
<%SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
EstateUtil estateUtil = (EstateUtil) UtilProvider.getDomainUtil(CategorizerConfig.ESTATE_TYPE);
List<LinkedLabel> list = estateUtil.getActiveFilters(request);
if(list.size() > 0)
{%>
<div class="lhead" title="Uygulanmış filtreler">
  <a class="sh act" href="javascript:"></a>
  Uygulanmış filtreler </div>
<div class="llist act c1">
  <%for(LinkedLabel linkedLabel: list){%>
  <div class="pb">
	<input class="buttonFilterClean" name="cleanfilter" type="button"  
                	value="" title="Filtreyi kaldır" onclick="cleanAndSubmit('<%=linkedLabel.getCssClass()%>')"/>  
    <label>
      <span><%=StringEscapeUtils.unescapeHtml(linkedLabel.getLabel())%>: <%=linkedLabel.getLink()%></span>
    </label>
  </div>
  <%} %> 
</div>
<%} %>