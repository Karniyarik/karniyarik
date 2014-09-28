<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@page import="java.util.Calendar"%>
<wng:hr></wng:hr>
<jsp:include page="deviceapp.jsp"></jsp:include>
<wng:navigation_bar separator=" | " id="fc">
   <wng:css>
    <wng:css_property name="margin-left" value="4px" />
    <wng:css_property name="margin-right" value="4px" />
    <wng:css_property name="padding-top" value="4px" />
    <wng:css_property name="text-align" value="center" />
   </wng:css>
   <wng:text>Mobil</wng:text>
   <wng:link href="${contextPath}/?m=false" text="Klasik" css_ref="cbtn"/>
</wng:navigation_bar>

<wng:navigation_bar separator=" | " >
   <wng:css>
    <wng:css_property name="margin-left" value="4px" />
    <wng:css_property name="margin-right" value="4px" />
    <wng:css_property name="padding-top" value="4px" />
    <wng:css_property name="text-align" value="center" />
   </wng:css>
   <wng:text>Karnıyarık Ltd. © <%=Calendar.getInstance().get(Calendar.YEAR)%></wng:text>
   <wng:link href="${contextPath}/m/aboutus.jsp" text="Hakkımızda" />
</wng:navigation_bar>
