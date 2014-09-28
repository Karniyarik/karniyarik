<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%@page import="java.util.List"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="com.karniyarik.web.json.SearchStatisticsManager"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="com.karniyarik.web.util.QueryStringHelper"
%><%@page import="net.sourceforge.wurfl.core.Device"
%><%@page import="com.karniyarik.common.config.system.SearchConfig"
%><%
HttpCacheUtil.setIndexResponseCacheAttributes(response, request);
int categoryType =  RequestWrapper.getInstance(request).getCategoryType();
String cat = RequestWrapper.getInstance(request).getCategory();
if(StringUtils.isBlank(cat))
{
	cat = "urun";
}
String action=request.getContextPath() + "/m/urun/search.jsp";
if(categoryType == CategorizerConfig.CAR_TYPE){
	action=request.getContextPath()+"/m/araba/search.jsp";
}
String desc = "Fiyatı olan her şey Karniyarik.com'da";
String keywords = "alışveriş, ürün, araba, ikinci el, en ucuz, ne kadar, nerede satılıyor, fiyat, karniyarik";
String title = "Karnıyarık.com";

request.setAttribute("formaction",action);
request.setAttribute("title",title);
%>

<wng:document>
	<wng:head>
	  <wng:title text="${title}" />
		<%@ include file="head.jsp" %>
	</wng:head>

	<wng:body id="hm">
		<wng:css>
			<wng:css_property name="text-align" value="center" />
		</wng:css>

		<%@ include file="nav.jsp"%>
		<wng:header>
			<wng:css>
				<wng:css_property name="color" value="#006699" />
				<wng:css_property name="width" value="100%" />
				<wng:css_property name="margin-bottom" value="4px;" />
			</wng:css>
			<wng:image imageRetriever="${logo}">
				<wng:link href="/m/" />
			</wng:image>
		</wng:header>
		<wng:form action="${formaction}" method="get">
			<wng:input type="text" name="query" css_ref="hst" />
			<wng:br />
			<wng:input id="srchbtn" type="submit" value="Ara" />
		</wng:form>
		<jsp:include page="footer.jsp"></jsp:include>
	</wng:body>
</wng:document>