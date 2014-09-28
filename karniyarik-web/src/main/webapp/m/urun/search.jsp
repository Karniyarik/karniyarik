<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><?xml version="1.0" encoding="UTF-8"?>
<%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%@page import="com.karniyarik.site.SiteInfoProvider"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.category.BaseCategoryUtil"
%><%@page import="org.apache.commons.lang.StringEscapeUtils"
%><%@page import="java.util.List"
%><%@page import="java.util.ArrayList"
%><%@page import="com.karniyarik.web.servlet.image.ImageServlet"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="com.karniyarik.web.json.ProductResult"
%><%@page import="com.karniyarik.ir.SearchConstants"
%><%@page import="com.karniyarik.web.category.ProductUtil"
%><%@page import="com.karniyarik.web.category.UtilProvider"
%><%
HttpCacheUtil.setSearchResponseCacheAttributes(response, request);
request.setAttribute(RequestWrapper.CATEGORY, CategorizerConfig.PRODUCT);
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
request.setAttribute(RequestWrapper.SEARCH_RESULT, searchResult);
SiteInfoProvider infoProvider = SiteInfoProvider.getInstance();
int rSort = 0;
int pSort = 0;
switch(searchResult.getSort()){
case 1: rSort = 1; break;
case 2: pSort = 1; break;
case 3: pSort = 2; break;
}
if(searchResult.isShowEmptySearchError()) {
	response.sendRedirect("http://www.karniyarik.com/m");
}
BaseCategoryUtil.setBreadCrumb(request, searchResult.getBreadcrumb());

String queryForTrack = searchResult.getQuery();
queryForTrack = StringEscapeUtils.unescapeHtml(queryForTrack);
queryForTrack = StringEscapeUtils.escapeJavaScript(queryForTrack);

List<ProductResult> results = new ArrayList<ProductResult>();
results.addAll(searchResult.getSponsoredProducts());
results.addAll(searchResult.getResults());
String action=request.getContextPath() + "/m/urun/search.jsp";
request.setAttribute("formaction",action);
%>
<wng:document>
	<wng:head>
		<wng:title text="<%=searchResult.getPageTitle()%>" />
		<%@ include file="../head.jsp" %>
	</wng:head>
    <wng:body>
    	<%@ include file="../nav.jsp" %>
		<jsp:include page="../top.jsp"></jsp:include>
		<wng:title css_ref="h">
			<%=searchResult.getPageHeader()%>
		</wng:title>
		<%for(ProductResult result : searchResult.getResults()) {
				String imgUrl = ImageServlet.getImageRszUrl(request, result.getImageURL(), "", 40, 40);
		%><wng:illustrated_item_no_wrap> 
			<wng:left_part>
				<wng:image src="<%=StringEscapeUtils.escapeHtml(imgUrl)%>"/> 
			</wng:left_part> 
			<wng:right_part> 
				<wng:link href="<%=result.getLink()%>" text="<%=LinkedLabel.getShortenedLabel(result.getProductName(), 60)%>"/>
				<wng:br/> 
				<wng:text> 
					<%=LinkedLabel.getShortenedLabel(result.getSourceName(),53)%> 
				</wng:text>
				<wng:br/>
				<wng:text> 
					<%=result.getPrice()%> TL 
				</wng:text>
				<wng:br/> 
				<wng:link href="<%=result.getLink()%>" text="Git"/>
			</wng:right_part> 
		</wng:illustrated_item_no_wrap>
		<%} %>	
		<jsp:include page="../pager.jsp"></jsp:include>

		<wng:title css_ref="flt">Filtrele</wng:title>
		<%
			ProductUtil productUtil = (ProductUtil) UtilProvider.getDomainUtil(CategorizerConfig.PRODUCT_TYPE);
			List<String> values = productUtil.getRangeFilterVariables(SearchConstants.PRICE, searchResult, request);
			String price1 = values.get(0);
			String price2 = values.get(1);
		%>
		<wng:form action="${formaction}" method="get" css_ref="fltb">
			<%if(searchResult.getPagerResults().size()>0){ %>
			<wng:text css_ref="fltt">Sayfa Seç</wng:text>
			<wng:select name="page">
				<%for(LinkedLabel linkedLabel: searchResult.getPagerResults()){
					if(linkedLabel.getCssClass().equals("act")){%>
				<wng:option value="<%=Integer.toString(linkedLabel.getCount())%>" selected="selected"><%=linkedLabel.getCount()%></wng:option>
				<%} else {%>
				<wng:option value="<%=Integer.toString(linkedLabel.getCount())%>"><%=linkedLabel.getCount()%></wng:option>
				<%} %>
				<%}%>
			</wng:select>
			<wng:br/>
			<%}%>		
			
			<wng:text css_ref="fltt">Sırala</wng:text>
			<wng:select name="sort">
				<%if(searchResult.getSort()==1){%>
				<wng:option value="1" selected="selected">Benzerlik</wng:option>
				<%} else { %>
				<wng:option value="1">Benzerlik</wng:option>
				<%}%>
				<%if(searchResult.getSort()==2){%>
				<wng:option value="2" selected="selected">Fiyat - Artan</wng:option>
				<%} else { %>
				<wng:option value="2">Fiyat - Artan</wng:option>
				<%}%>
				<%if(searchResult.getSort()==2){%>
				<wng:option value="3" selected="selected">Fiyat - Azalan</wng:option>
				<%} else { %>
				<wng:option value="3">Fiyat - Azalan</wng:option>
				<%}%>
			</wng:select>
			<wng:br/>
			<wng:text css_ref="fltt">Fiyat</wng:text>
			<wng:input type="text" name="price1" value="<%=price1%>" size="4"/>
			<wng:text>-</wng:text>
			<wng:input type="text" name="price2" value="<%=price2%>" size="4"/>
			<wng:br/>
			<wng:input type="hidden" name="query" value="<%=searchResult.getEscapedQuery()%>"/>
			<wng:input id="srchbtn" type="submit" value="Uygula"/>
		</wng:form>
		<wng:rack_menu background_color1="#DDDDD" background_color2="#DDDDD" css_ref="fltb" id="fltbt">
			<%String uri = MobileUtil.replaceServlet(request, "search.jsp","f.jsp");
			uri = MobileUtil.appendParam(uri,"t","brand");%>
			<wng:link href="<%=uri%>" text="Marka Seç&gt;&gt;" css_ref="rackl"/>
			<%uri = MobileUtil.replaceServlet(request, "search.jsp","f.jsp");
			uri = MobileUtil.appendParam(uri,"t","source");%>
			<wng:link href="<%=uri%>" text="Mağaza Seç&gt;&gt;" css_ref="rackl"/>
		 </wng:rack_menu>
		<jsp:include page="../footer.jsp"></jsp:include>
    </wng:body>
</wng:document>