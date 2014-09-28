<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="java.util.Properties"
%><%@page import="net.tanesha.recaptcha.ReCaptchaFactory"
%><%@page import="net.tanesha.recaptcha.ReCaptcha"
%><%// create recaptcha without <noscript> tags
       ReCaptcha captcha = ReCaptchaFactory.newReCaptcha("6LeYPrsSAAAAAMgfFTWdbmJ0G_1P5q087H6bnuNu", 
    		   "6LeYPrsSAAAAAFixxc-DgpeWHJn2oezrhIso6Af7", true);
      Properties prop = new Properties();
       prop.put("lang", "tr");
       prop.put("theme", "clean");
       String captchaScript = captcha.createRecaptchaHtml("Robot süzgecinde hata oluştu", prop);
       out.print(captchaScript);%>
<style>
	#recaptcha_widget_div {width:914px;}
</style>