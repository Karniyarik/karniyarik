<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="java.util.List"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="com.karniyarik.web.category.BaseCategoryUtil"
%><%
	String stab = request.getParameter("stabi");
	int stabint = 1;
	if(StringUtils.isNotBlank(stab)){stabint = Integer.parseInt(stab);}
	String query = request.getParameter("queryi");
	if(StringUtils.isBlank(query)) query = "";
		%><div class="search search2">
		    <div class="search2l">
		      <div class="search2r">
		        <div class="stab <%=stabint == 1 ? "act" : ""%>">
		          <a rel="nofollow" class="ri" href="#"><span class="sprite p-tab-2"></span><span class="txt">Ürün</span></a>
		        </div>
		        <div class="stab <%=stabint == 2 ? "act " : ""%> tho">
		          <a rel="nofollow" class="ri" href="#"><span class="sprite c-tab-2"></span><span class="txt">Araç</span></a>
		        </div><%
		        	if(stabint == 3){
		        %><div class="stab <%=stabint == 3 ? "act " : ""%> tho1">
					<a rel="nofollow" class="ri" href="#"><span class="sprite e-tab-2"></span><span class="txt">Emlak</span></a>
        		</div><%}%>		        
		        <div class="fform2">
		          <input id="words" name="query" type="text" class="trans inp mhide" value="<%=query%>" />
		          <input class="trans sub" name="" type="submit" value="Ara" />
		          <input id="stab" name="stab" type="hidden" value="<%=stab%>" />
		        </div>
		      </div>
		    </div>
		  </div>
		<jsp:include page="commontop_inf.jsp"></jsp:include>