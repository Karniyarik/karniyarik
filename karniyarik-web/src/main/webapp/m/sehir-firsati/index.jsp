<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%@page import="com.karniyarik.web.category.BaseCategoryUtil"
%><%@page import="org.apache.commons.lang.StringEscapeUtils"
%><%@page import="java.util.List"
%><%@page import="java.util.ArrayList"
%><%@page import="com.karniyarik.web.servlet.image.ImageServlet"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="com.karniyarik.ir.SearchConstants"
%><%@page import="com.karniyarik.web.citydeals.CityDealConverter"
%><%@page import="com.karniyarik.web.citydeals.CityResult"
%><%@page import="com.karniyarik.web.citydeals.CityDealResult"
%><%
HttpCacheUtil.setNoCacheAttributes(response, request);
request.setAttribute(RequestWrapper.CATEGORY, CategorizerConfig.CITY_DEAL);
CityDealConverter result = new CityDealConverter(request);
List<CityResult> cities =  result.getCities();
List<CityResult> activeCities =  result.getActiveCities();
List<CityDealResult> results = result.getCityDeals();
CityResult currentCity = result.getSelectedCity();
CityDealResult sharedDeal = result.getSharedCityDeal();
BaseCategoryUtil.setBreadCrumb(request, result.getBreadcrumb());
String title = CityDealConverter.getTitle(sharedDeal, currentCity.getName(), result.getSource());
String action=request.getContextPath() + "/m/sehir-firsati";
request.setAttribute("citydealaction",action);
String sortStr = request.getParameter("sort");
int sort = CityDealConverter.SORT_DEFAULT;
if(StringUtils.isNotBlank(sortStr))
{
	try{sort = Integer.parseInt(sortStr);}catch(Exception e){};
}
%>
<wng:document>
	<wng:head>
		<wng:title text="<%=title%>" />
		<%@ include file="../head.jsp" %>
	</wng:head>
    <wng:body>
    	<%@ include file="../nav.jsp" %>
		<jsp:include page="../top.jsp"></jsp:include>
		<wng:title css_ref="h"><%=currentCity.getName()%> Şehir Fırsatı Listesi</wng:title>
		<%if(results.size() < 1){
			%><wng:text><%=currentCity.getName()%> şehirinde kayıtlı bir şehir fırsatı şu an bulunmamaktadır.</wng:text><%} 
		else {
			for(int dealIndex = 0; dealIndex < results.size(); dealIndex++){
	        	 CityDealResult deal = results.get(dealIndex);
	        	 String imgUrl = ImageServlet.getImageRszUrl(request, deal.getImage(), "", 50, 50);%>
		<wng:illustrated_item_no_wrap> 
			<wng:left_part> 
				<wng:image src="<%=StringEscapeUtils.escapeHtml(imgUrl)%>"/> 
			</wng:left_part> 
			<wng:right_part> 
				<wng:link href="<%=StringEscapeUtils.escapeHtml(deal.getProductURL())%>" text="<%=LinkedLabel.getShortenedLabel(deal.getTitle(), 60)%>"/>
				<wng:br/> 
				<wng:text> 
					<%=deal.getDiscountPrice()%> TL 
				</wng:text>
				<wng:text>
					(<%=deal.getPrice()%>)
				</wng:text>
				<wng:br/> 
				<wng:text><%=LinkedLabel.getShortenedLabel(deal.getDescription(),100)%></wng:text>
				<wng:link href="<%=StringEscapeUtils.escapeHtml(deal.getProductURL())%>" text="Git"/>
			</wng:right_part> 
		</wng:illustrated_item_no_wrap>	        	 
		<%}
		}%>
		
		<wng:title css_ref="flt">Filtrele</wng:title>
		<wng:form action="${citydealaction}" method="get" css_ref="fltb">
			<%if(result.getPages().size()>0){ %>
			<wng:text css_ref="fltt">Sayfa Seç</wng:text>
			<wng:select name="page">
				<%for(LinkedLabel linkedLabel: result.getPages()){
					if(linkedLabel.getCssClass().equals("act")){%>
				<wng:option selected="selected" value="<%=Integer.toString(linkedLabel.getCount())%>"><%=linkedLabel.getCount()%></wng:option>
				<%} else {%>
				<wng:option value="<%=Integer.toString(linkedLabel.getCount())%>"><%=linkedLabel.getCount()%></wng:option>
				<%} %>
				<%}%>
			</wng:select>
			<wng:br/>
			<%}%>		
			
			<wng:text css_ref="fltt">Sırala</wng:text>
			<wng:select name="sort">
				<%if(sort == CityDealConverter.SORT_DPERCENTAGE){%>
				<wng:option value="<%=Integer.toString(CityDealConverter.SORT_DPERCENTAGE)%>" selected="selected">İndirim Yüzdesi</wng:option>
				<%} else {%>
				<wng:option value="<%=Integer.toString(CityDealConverter.SORT_DPERCENTAGE)%>">İndirim Yüzdesi</wng:option>
				<%} %>
				<%if(sort == CityDealConverter.SORT_PRICE){%>
				<wng:option value="<%=Integer.toString(CityDealConverter.SORT_PRICE)%>" selected="selected">Fiyat - Artan</wng:option>
				<%} else {%>
				<wng:option value="<%=Integer.toString(CityDealConverter.SORT_PRICE)%>">Fiyat - Artan</wng:option>
				<%} %>
				<%if(sort == CityDealConverter.SORT_DATE){%>
				<wng:option value="<%=Integer.toString(CityDealConverter.SORT_DATE)%>" selected="selected">Tarih</wng:option>
				<%} else {%>
				<wng:option value="<%=Integer.toString(CityDealConverter.SORT_DATE)%>">Tarih</wng:option>
				<%} %>
				<%if(sort == CityDealConverter.SORT_DPERCENTAGE){%>
				<wng:option value="<%=Integer.toString(CityDealConverter.SORT_DPERCENTAGE)%>" selected="selected">İndirim Miktarı</wng:option>
				<%} else {%>
				<wng:option value="<%=Integer.toString(CityDealConverter.SORT_DPERCENTAGE)%>">İndirim Miktarı</wng:option>
				<%} %>
				<%if(sort == CityDealConverter.SORT_DEFAULT){%>
				<wng:option value="<%=Integer.toString(CityDealConverter.SORT_DEFAULT)%>" selected="selected">Varsayılan</wng:option>
				<%} else {%>
				<wng:option value="<%=Integer.toString(CityDealConverter.SORT_DEFAULT)%>">Varsayılan</wng:option>
				<%} %>
			</wng:select>
			<wng:br/>
			<wng:text css_ref="fltt">Şehir Seç</wng:text>
			<wng:select name="city">
				
				<%for(CityResult city : activeCities){
					if(currentCity.getValue().equals(city.getValue())){%>
				<wng:option value="<%=city.getValue()%>" selected="selected"><%=city.getName()%> (<%=city.getDealCount()%>)</wng:option>
				<%} else {%>
				<wng:option value="<%=city.getValue()%>"><%=city.getName()%> (<%=city.getDealCount()%>)</wng:option>
				<%} %>
				<%}%>
			</wng:select>
			<wng:br/>
			<wng:input id="srchbtn" type="submit" value="Uygula"/>
		</wng:form>
		<wng:br/>
		<jsp:include page="../footer.jsp"></jsp:include>
    </wng:body>
</wng:document>