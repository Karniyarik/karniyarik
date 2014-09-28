<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%
Integer catType = RequestWrapper.getInstance(request).getCategoryType();
%>
<wng:navigation_bar separator=" | " css_ref="nav">
   <wng:link href="${kcontextpath}/m/?cat=urun" text="Ürün" css_ref="cbtn"/>
   <wng:link href="${kcontextpath}/m/?cat=araba" text="Araba" css_ref="cbtn"/>
   <wng:link href="${kcontextpath}/m/sehir-firsati" text="Şehir Fırsatı" css_ref="cbtn"/>
</wng:navigation_bar>			