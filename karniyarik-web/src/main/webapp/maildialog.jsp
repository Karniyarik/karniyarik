<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.citydeals.CityResult"
%><%@page import="com.karniyarik.web.util.CityUtil"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%
Object catObj = request.getAttribute(RequestWrapper.CATEGORY);
String cat = "";
if(catObj != null)
{
	cat = catObj.toString();
}%>
		<div id="email_dialog" title="Yeniliklerden Haberdar Olmak İçin Kaydol" style="display:none">
			<div>
				<img class="sprite logo-sq" src="<%=request.getContextPath()%>/images/transparent.gif" width="86" height="86" style="float:left;margin-right:8px;"/>
			</div>
			<p>Karniyarik.com'daki yeniliklerden haberdar olmak için e-posta adresiniz ve bulunduğunuz şehri seçebilirsiniz.</p>
			<div class="cl">&nbsp;</div>
			<form id="cityDealEmailForm" style="margin-top:10px;">
				<label for="email" class="label">E-posta</label>
				<input type="text" name="email" id="email" value="" class="input" />
				<br/>
				<label for="city" class="label">Şehir</label>
				<select id="city" class="select">
				<%for(CityResult city : CityUtil.getCitiesInTurkey()){
				%><option title="<%=city.getName()%>" value="<%=city.getValue()%>" <%=city.isSelected() ? "selected='selected'" : ""%>><%=city.getName()%></option><%
				}
				%></select>
				<input type="hidden" id="cat" name="cat" value="<%=cat%>"/>
			</form>
		</div>
		<div id="email_check_dialog" title="Lütfen Mail Adresinizi Kontrol Ediniz" style="display:none">
			<p>Listeye üyelik için aktivasyon epostanız gönderilmiştir. Lütfen epostanızı kontrol edip kaydınızı onaylayınız.</p>
		</div>