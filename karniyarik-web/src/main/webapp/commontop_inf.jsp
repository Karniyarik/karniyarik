<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.util.WebApplicationDataHolder"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="java.util.List"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="com.karniyarik.web.category.BaseCategoryUtil"
%><%@page import="com.karniyarik.web.util.Formatter"%>
		
<%@page import="org.apache.commons.lang.StringUtils"%><div class="inf">
		    <!-- div class="soli"><img src="<%=request.getContextPath()%>/images/+.png" width="16" height="16" alt="+" /><span class="c1 b udot">Alışveriş Listem</span> (2)
		    </div -->
			<font class="onlyprint" style="font-size:24px;">Karniyarik.com</font>    
		    <div class="breadcrumb"><%
		    List<LinkedLabel> breadcrumb = BaseCategoryUtil.getBreadCrumb(request);
		    if(breadcrumb != null && breadcrumb.size() > 0)
		    {
		    	int crumbIndex = 0;
		    	boolean internal = false;
		    	for(LinkedLabel crumb: breadcrumb){
		    		crumbIndex++;internal = false;%>
		    	<span typeof="v:Breadcrumb">
		    		<%if(crumbIndex < breadcrumb.size()){internal = true;}
		    		if(internal){ %><a rel="nofollow v:url" property="v:title" href="<%=crumb.getLink()%>"><%}%><%=crumb.getLabel()%><%if(internal){%></a><%}%>
		    	</span><%
		    		if(internal){%>&nbsp;&gt;&nbsp;<%}%><%
		    	}
		    }%>&nbsp;
		    </div>
		    <div class="sitecount"><%
		    	WebApplicationDataHolder aHolder = WebApplicationDataHolder.getInstance();%>
				<span class="b">
			    	<a rel="nofollow" target="_blank" title="Bilgi topladığımız internet sitelerini görmek için tıklayın" href="<%=request.getContextPath()%>/sites.jsp">
			    	<%=Formatter.formatInteger(aHolder.getSiteCount())%> site
			    	</a> 
			    	<%=Formatter.formatInteger(aHolder.getProductCount())%> ürün
				</span>
		    </div>
		</div><%
		String rootPath = "";
		if(StringUtils.isBlank(request.getContextPath())){
			rootPath ="/";
		}
		else
		{
			rootPath = request.getContextPath() + "/";
		}
		%><a title="Alışveriş" rel="home" href="<%=rootPath%>" class="logo2">
			<img alt="" src="<%=request.getContextPath()%>/images/transparent.gif" height="69" width="303" class="sprite logo-s-img"/>
		</a>
		<div class="cl"></div>