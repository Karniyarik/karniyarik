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
%><%@page import="com.karniyarik.web.category.CarUtil"
%><%@page import="com.karniyarik.web.category.UtilProvider"
%><%
HttpCacheUtil.setSearchResponseCacheAttributes(response, request);
request.setAttribute(RequestWrapper.CATEGORY, CategorizerConfig.CAR);
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
request.setAttribute(RequestWrapper.SEARCH_RESULT, searchResult);
int rSort = 0;
int pSort = 0;
int kSort = 0;
int ySort = 0;
int hSort = 0;
switch(searchResult.getSort()){
case 1: rSort = 1; break;
case 2: pSort = 1; break;
case 3: pSort = 2; break;
case 4: kSort = 1; break;
case 5: kSort = 2; break;
case 6: ySort = 1; break;
case 7: ySort = 2; break;
case 8: hSort = 1; break;
case 9: hSort = 2;break;
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
String action=request.getContextPath() + "/m/araba/search.jsp";
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
		<wng:title css_ref="h"><%=searchResult.getPageHeader()%></wng:title>
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
					<%if (!StringUtils.isEmpty(result.getProperty("city"))) { %><%=result.getProperty("city") %><% } %>,
					<%if (!StringUtils.isEmpty(result.getProperty("modelyear"))) { %><%=result.getProperty("modelyear")%><%}%> 
				</wng:text>
				<wng:br/>
				<wng:text> 
					<%if (!StringUtils.isEmpty(result.getProperty("km"))) { %><%=result.getProperty("km")+ "km" %><%}%>,
					<%if (!StringUtils.isEmpty(result.getProperty("enginepower"))) { %><%=result.getProperty("enginepower") + "hp" %><%}%> 
				</wng:text>
				<wng:br/>
				<wng:text><%=result.getPrice()%> TL</wng:text>
				<wng:br/> 
				<wng:link href="<%=result.getLink()%>" text="Git"/>
			</wng:right_part> 
		</wng:illustrated_item_no_wrap> 	
		<%} %>	
		<jsp:include page="../pager.jsp"></jsp:include>

		<wng:title css_ref="flt">Filtrele</wng:title>
		<%
			CarUtil productUtil = (CarUtil) UtilProvider.getDomainUtil(CategorizerConfig.CAR_TYPE);
			List<String> values = productUtil.getRangeFilterVariables(SearchConstants.PRICE, searchResult, request);
			String price1 = values.get(0);
			String price2 = values.get(1);
			values = productUtil.getRangeFilterVariables(SearchConstants.KM, searchResult, request);
			String km1 = values.get(0);
			String km2 = values.get(1);
			values = productUtil.getRangeFilterVariables(SearchConstants.YEAR, searchResult, request);
			String modelyear1 = values.get(0);
			String modelyear2 = values.get(1);
			values = productUtil.getRangeFilterVariables("enginepower", searchResult, request);
			String enginepower1 = values.get(0);
			String enginepower2 = values.get(1);
			values = productUtil.getRangeFilterVariables("enginevolume", searchResult, request);
			String enginevolume1 = values.get(0);
			String enginevolume2 = values.get(1);
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
				<%if(searchResult.getSort()==3){%>
				<wng:option value="3" selected="selected">Fiyat - Azalan</wng:option>
				<%} else { %>
				<wng:option value="3">Fiyat - Azalan</wng:option>
				<%}%>
				<%if(searchResult.getSort()==4){%>
				<wng:option value="4" selected="selected">KM - Artan</wng:option>
				<%} else { %>
				<wng:option value="4">KM - Artan</wng:option>
				<%}%>
				<%if(searchResult.getSort()==5){%>
				<wng:option value="5" selected="selected">KM - Azalan</wng:option>
				<%} else { %>
				<wng:option value="5">KM - Azalan</wng:option>
				<%}%>
				<%if(searchResult.getSort()==6){%>
				<wng:option value="6" selected="selected">Model Yılı - Artan</wng:option>
				<%} else { %>
				<wng:option value="6">Model Yılı - Artan</wng:option>
				<%}%>
				<%if(searchResult.getSort()==7){%>
				<wng:option value="7" selected="selected">Model Yılı - Azalan</wng:option>
				<%} else { %>
				<wng:option value="7">Model Yılı - Azalan</wng:option>
				<%}%>
			</wng:select>			
				
			<wng:br/>
			<wng:text css_ref="fltt">Fiyat</wng:text>
			<wng:input type="text" name="price1" value="<%=price1%>" size="4"/><wng:text>-</wng:text><wng:input type="text" name="price2" value="<%=price2%>" size="4"/>
			<wng:br/>
			<wng:text css_ref="fltt">KM</wng:text>
			<wng:input type="text" name="km1" value="<%=km1%>" size="4"/><wng:text>-</wng:text><wng:input type="text" name="km2" value="<%=km2%>" size="4"/>
			<wng:br/>
			<wng:text css_ref="fltt">Yılı</wng:text>
			<wng:input type="text" name="modelyear1" value="<%=modelyear1%>" size="4"/><wng:text>-</wng:text><wng:input type="text" name="modelyear2" value="<%=modelyear2%>" size="4"/>
			<wng:br/>
			<wng:text css_ref="fltt">Motor Gücü</wng:text>
			<wng:input type="text" name="enginepower1" value="<%=enginepower1%>" size="4"/><wng:text>-</wng:text><wng:input type="text" name="enginepower2" value="<%=enginepower2%>" size="4"/>
			<wng:br/>
			<wng:text css_ref="fltt">Motor Hacmi</wng:text>
			<wng:input type="text" name="enginevolume1" value="<%=enginevolume1%>" size="4"/><wng:text>-</wng:text><wng:input type="text" name="enginevolume2" value="<%=enginevolume2%>" size="4"/>
			<wng:br/>
			<wng:input type="hidden" name="query" value="<%=searchResult.getEscapedQuery()%>"/>
			<wng:input id="srchbtn" type="submit" value="Uygula"/>
		</wng:form>
		<wng:rack_menu background_color1="#F2F2F2" background_color2="#F2F2F2" css_ref="fltb">
			<%String uri = MobileUtil.replaceServlet(request, "search.jsp","f.jsp");
			uri = MobileUtil.appendParam(uri,"t","brand");%>
			<wng:link href="<%=uri%>" text="Marka Seç&gt;&gt;" css_ref="rackl"/>
			<%uri = MobileUtil.replaceServlet(request, "search.jsp","f.jsp");
			uri = MobileUtil.appendParam(uri,"t","city");%>
			<wng:link href="<%=uri%>" text="Şehir Seç&gt;&gt;" css_ref="rackl"/>
			<%uri = MobileUtil.replaceServlet(request, "search.jsp","f.jsp");
			uri = MobileUtil.appendParam(uri,"t","color");%>
			<wng:link href="<%=uri%>" text="Renk Seç&gt;&gt;" css_ref="rackl"/>
			<%uri = MobileUtil.replaceServlet(request, "search.jsp","f.jsp");
			uri = MobileUtil.appendParam(uri,"t","fuel");%>
			<wng:link href="<%=uri%>" text="Yakıt Tipi&gt;&gt;" css_ref="rackl"/>
			<%uri = MobileUtil.replaceServlet(request, "search.jsp","f.jsp");
			uri = MobileUtil.appendParam(uri,"t","gear");%>
			<wng:link href="<%=uri%>" text="Vites Tipi Seç&gt;&gt;" css_ref="rackl"/>
		 </wng:rack_menu>
		<jsp:include page="../footer.jsp"></jsp:include>
    </wng:body>
</wng:document>