<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ taglib uri="/WEB-INF/tld/wng.tld" prefix="wng"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@page import="com.karniyarik.web.mobile.MobileUtil"
%><%@page import="java.util.Calendar"
%><%@page import="java.text.DateFormat"
%><%@page import="java.util.Locale"
%><%@page import="net.sourceforge.wurfl.core.Device"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%
MobileUtil.setDispensers(request);
request.setAttribute(MobileUtil.CONTEXT, request.getContextPath());
%>
<wng:meta httpEquiv="Content-Language" content="tr"/>
<wng:meta httpEquiv="Content-type" content="text/html;charset=UTF-8" />
<wng:meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<%Object cacheControl = request.getAttribute("k-cache-control");
if(cacheControl != null) {
	String cacheControlStr = cacheControl.toString();
	String gmtDate = request.getAttribute("k-cache-expires").toString();%>
<wng:meta httpEquiv="expires" content="<%=gmtDate%>" />
<wng:meta httpEquiv="cache-control" content="<%=cacheControlStr%>" /><%}%>
<wng:css_style>
	<wng:css selector="html">
    	<wng:css_property name="height" value="100%" />
    	<wng:css_property name="width" value="100%" />
    	<wng:css_property name="font-family" value="arial, sans-serif" />
    	<wng:css_property name="font-size" value="small" />
  	</wng:css>
	<wng:css selector="body">
		<wng:css_property name="margin" value="0" />
		<wng:css_property name="padding" value="0" />
		<wng:css_property name="color" value="#444" />
		<wng:css_property name="border" value="none" />
	</wng:css>
	<wng:css selector="input">
		<wng:css_property name="color" value="#444" />
	</wng:css>
	<wng:css selector="h1,h2">
		<wng:css_property name="padding" value="0" />
		<wng:css_property name="margin" value="2" />
	</wng:css>
	<wng:css selector="fieldset">
		<wng:css_property name="border" value="none" />
		<wng:css_property name="margin" value="0" />
		<wng:css_property name="padding" value="0" />
	</wng:css>
	<wng:css selector="img">
		<wng:css_property name="border" value="none" />
	</wng:css>
	<wng:css selector="a">
		<wng:css_property name="color" value="#5D9300" />
		<wng:css_property name="font-weight" value="bold" />
		<wng:css_property name="text-decoration" value="none" />
	</wng:css>
	<wng:css selector=".h">
		<wng:css_property name="padding" value="2px " />
		<wng:css_property name="background-color" value="#DFC3D6"/>
		<wng:css_property name="border-top" value="1px solid #8F3275"/>
		<wng:css_property name="color" value="#444"/>
		<wng:css_property name="text-align" value="left"/>
	</wng:css>
	<wng:css selector=".hst">
		<wng:css_property name="width" value="90%"/>
	</wng:css>
	<wng:css selector=".st">
		<wng:css_property name="width" value="93px" applies_to="${capabilities.max_image_width lt 241}"/>
		<wng:css_property name="width" value="150px" applies_to="${capabilities.max_image_width lt 321}"/>
		<wng:css_property name="width" value="160px" applies_to="${capabilities.max_image_width lt 361}"/>
		<wng:css_property name="width" value="240px" applies_to="${capabilities.max_image_width gt 360}"/>
	</wng:css>
	<wng:css selector=".sb">
		<wng:css_property name="padding" value="0px"/>
	</wng:css>
	<wng:css selector=".sl">
		<wng:css_property name="width" value="93px" applies_to="${capabilities.max_image_width lt 241}"/>
		<wng:css_property name="width" value="150px" applies_to="${capabilities.max_image_width lt 321}"/>
		<wng:css_property name="width" value="160px" applies_to="${capabilities.max_image_width lt 361}"/>
		<wng:css_property name="width" value="240px" applies_to="${capabilities.max_image_width gt 360}"/>
	</wng:css>
	<wng:css selector=".flt">
		<wng:css_property name="padding" value="2px" />
		<wng:css_property name="background-color" value="#DDDDDD"/>
		<wng:css_property name="border-top" value="1px solid #B2B2B2"/>
		<wng:css_property name="text-align" value="left"/>
	</wng:css>
	<wng:css selector=".fltb">
		<wng:css_property name="padding" value="2px" />
		<wng:css_property name="background-color" value="#DDDDDD"/>
		<wng:css_property name="text-align" value="left"/>
		<wng:css_property name="width" value="%100"/>
	</wng:css>
	<wng:css selector=".fltt">
		<wng:css_property name="text-align" value="left"/>
		<wng:css_property name="margin-right" value="4px;"/>
	</wng:css>
	<wng:css selector=".rackl">
		<wng:css_property name="text-align" value="left"/>
	</wng:css>
	<wng:css selector=".t">
		<wng:css_property name="margin-top" value="4px;" />
		<wng:css_property name="color" value="#89266D" />
	</wng:css>
	<wng:css selector=".appr">
		<wng:css_property name="margin" value="auto" />
		<wng:css_property name="text-align" value="left" />
	</wng:css>	
	<wng:css selector=".cbtn">
		<wng:css_property name="text-align" value="left"/>
	</wng:css>
	<wng:css selector=".nav">
	    <wng:css_property name="padding" value="4px" />
	    <wng:css_property name="text-align" value="left" />
	    <wng:css_property name="border" value="1px solid #5D9300;" />
	    <wng:css_property name="background-color" value="#DDD;" />
	    <wng:css_property name="margin-bottom" value="4px;" />
	</wng:css>
</wng:css_style>
<wng:script type="text/javascript">
	var _gaq = _gaq || [];
	_gaq.push(['_setAccount', 'UA-3933507-1']);
	_gaq.push(['_trackPageview']);

	(function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();
	  
	  <%if((Boolean)request.getAttribute(MobileUtil.TOUCHSCREEN)) {%>
	(function() {
	    var style = document.createElement('link'); style.type = 'text/css'; style.rel = 'stylesheet'; 
	    style.href = "<%=request.getContextPath()%>/m/css/touch.css";
	    var s = document.getElementsByTagName('script')[0]; 
	    s.parentNode.insertBefore(style, s);
	  })();
	  <%} %>
	  
	(function() {
	    var style = document.createElement('link'); style.media = 'screen and (resolution: 163dpi)'; style.rel = 'apple-touch-icon'; 
	    style.href = "<%=request.getContextPath()%>/m/images/icons/57.png";
	    var s = document.getElementsByTagName('script')[0]; 
	    s.parentNode.insertBefore(style, s);
	    
	    style = document.createElement('link'); style.media = 'screen and (resolution: 326dpi)'; style.rel = 'apple-touch-icon'; 
	    style.href = "<%=request.getContextPath()%>/m/images/icons/114.png";
	    var s = document.getElementsByTagName('script')[0]; 
	    s.parentNode.insertBefore(style, s);

	    style = document.createElement('link'); style.rel = 'shortcut icon'; 
	    style.href = "<%=request.getContextPath()%>/m/images/icons/57.png";
	    var s = document.getElementsByTagName('script')[0]; 
	    s.parentNode.insertBefore(style, s);
	  })();

</wng:script>
