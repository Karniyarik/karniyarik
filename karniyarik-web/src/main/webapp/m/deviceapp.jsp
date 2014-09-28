<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@page import="com.karniyarik.web.mobile.MobileUtil"
%><%
String deviceID = (String) request.getAttribute(MobileUtil.DEVICEID);
if(MobileUtil.isIPodOrDerivative(deviceID)){
%>
<wng:illustrated_item_no_wrap css_ref="appr">
	<wng:left_part>
		<wng:image src="${kcontextpath}/m/images/icons/48.png">
			<wng:link href="http://itunes.apple.com/tr/app/id382242506" text="Karnıyarık iPhone Uygulamasını indir"/>
		</wng:image> 
	</wng:left_part> 
	<wng:right_part> 
		<wng:link href="http://itunes.apple.com/tr/app/id382242506" text="Karnıyarık iPhone Uygulamasını indir"/>
		<wng:br/> 
		<wng:text> 
			Karnıyarık iPhone uygulamasını denediniz mi?
		</wng:text>
		<wng:br/>
		<wng:link href="http://itunes.apple.com/tr/app/id382242506" text="İndir"/>
	</wng:right_part> 
</wng:illustrated_item_no_wrap>

<%}%>
