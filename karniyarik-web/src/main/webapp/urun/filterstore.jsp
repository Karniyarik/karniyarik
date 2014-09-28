<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="java.util.List"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="com.karniyarik.site.SiteInfoProvider"
%><%@page import="com.karniyarik.site.SiteInfoConfig"
%><%@page import="org.apache.commons.lang.StringEscapeUtils"
%><%@page import="com.karniyarik.web.category.ProductUtil"
%><%@page import="com.karniyarik.web.category.UtilProvider"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
ProductUtil productUtil = (ProductUtil) UtilProvider.getDomainUtil(CategorizerConfig.PRODUCT_TYPE);
SiteInfoProvider infoProvider = SiteInfoProvider.getInstance();
List<LinkedLabel> list = productUtil.getStore(searchResult);
String[] values = productUtil.getFilterData(list, "source", request);
String data = values[0];
String value = values[1];
	%><div class="lhead" title="Sadece belli bir alışveriş sitesindeki ürünleri göster">
		<a class="sh act" href="javascript:"></a>
		Mağaza
	</div>
	<div class="llist act c1" id="store_list">
	  <input type="hidden" id="store" name="source" value="<%=value%>" class="rotc"/><%
	  LinkedLabel rankLinkedLabel;
	  for(LinkedLabel linkedLabel: list){
		%><div class="pb">
		    <label>
		      <input name="" type="checkbox" id="<%=linkedLabel.getLabel()%>_chkbox" value="" /><%
		      SiteInfoConfig siteInfo = infoProvider.getSiteInfo(linkedLabel.getLabel());
		      String label = linkedLabel.getLabel();
		      if(siteInfo != null && label.length() > 9) { label=label.substring(0,7) + "...";}
		      else if(label.length() > 18) { label=label.substring(0,18) + "...";} 
		      %><span style="display: none;"><%=linkedLabel.getLabel()%></span>
		      <span><a title="Sadece <%=linkedLabel.getLabel()%> alışveriş sitesindeki ürünleri göster" href="<%=linkedLabel.getLink()%>" rel="nofollow"><%=label%></a></span>
		      <span class="f1" title="Bu özelliğe sahip toplam <%=linkedLabel.getCount()%> sonuç var">(<%=linkedLabel.getCount()%>)</span>
		    </label><%
		    if(siteInfo != null){ 
		    %><a class="pba" rel="nofollow" title="NeredenAldin.com'da <%=siteInfo.getRank()%> sırada." href='<%=siteInfo.getSiteReviewURL()%>'>
		   		<img class="sprite stars-s-<%=siteInfo.getCalculatedRank()%>" alt="" width="49" height="11" src="<%=request.getContextPath()%>/images/transparent.gif"/>
		   	</a><%
		   	}
		%></div><%
		} 
		%><script type="text/javascript">setFilterValues("#store_list", "#store", [<%=data%>]);hookFilter("#store_list", "#store");</script>  
	</div>
	<div class="lbottom c1 act">
	  <a href="javascript:" onclick="cleanAndSubmit('source');">
	  	<img class="sprite filter-cl" src="<%=request.getContextPath()%>/images/transparent.gif"  width="16" height="16" title="Temizle" alt="Temizle"/>
	  </a>
	  <a href="javascript:" onclick="$('#frmSearch').submit();">
	  	<img class="sprite filter-ap" src="<%=request.getContextPath()%>/images/transparent.gif"  width="16" height="16" title="Uygula" alt="Uygula"/>
	  </a>
	</div>