<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.jobscheduler.util.SystemHealthCalculator"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.karniyarik.jobscheduler.JobAdminStateFilter"%>

<script type="text/javascript">
	$(function() {
		$("#overallhealth").tabs();
	});
</script>

<%
   	SystemHealthCalculator calc = new SystemHealthCalculator();
   	calc.calculate();
   	DecimalFormat format = new DecimalFormat("#0");
   	int totalSize = calc.getTotalSize();
   	if(totalSize == 0) 
   	{
   		totalSize = 1;
   	}
   	double overall = 100 - (calc.getBroken() * 1d / totalSize * 100);  
   	%>
   	
<div id="overallhealth">
   	<h1>Overall Health</h1>
   	<ul>
		<li><a href="#htabs-1">Images</a></li>
		<li><a href="#htabs-2">Data</a></li>
	</ul>
	<div id="htabs-1">
		<div style="margin-left:18px;">
	    	<label for="progress" style="margin-right:10px;margin-bottom:5px;">Overall Health</label><span id="progress" class="progress" title=""><%=format.format(overall)%></span><br/>
		</div>    
		<div class="span-6">
   			<img src="<%=calc.getDatacollectionimage()%>"/>
   		</div>
   		<div class="span-5">
   			<img src="<%=calc.getBrokenTotalImage()%>"/>
   		</div>
   		<div class="cl"></div>
	</div>    
	<div id="htabs-2">
		<div class="span-5">
	   		<table>
	   			<tbody>
		    		<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Total Site</th>
				    	<td><%=calc.getTotalSize()%></td>
				    	<td><a href="<%=request.getContextPath()%>/jobs.jsp">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Broken Site</th>
				    	<td><%=calc.getBroken()%></td>
				    	<td><a href="<%=request.getContextPath()%>/brokenjobs.jsp">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Failed Site</th>
				    	<td><%=calc.getFailed()%></td>
				    	<td><a href="<%=request.getContextPath()%>/failedjobs.jsp">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Executing Site</th>
				    	<td><%=calc.getExecuting()%></td>
				    	<td><a href="<%=request.getContextPath()%>/executingjobs.jsp">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Datafeed Site</th>
				    	<td><%=calc.getDatafeed()%></td>
				    	<td><a href="<%=request.getContextPath()%>/jobs.jsp?state=<%=JobAdminStateFilter.DATAFEED%>">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Selenium Site</th>
				    	<td><%=calc.getSelenium()%></td>
				    	<td><a href="<%=request.getContextPath()%>/jobs.jsp?state=<%=JobAdminStateFilter.SELENIUM%>">Go</a></td>
			    	</tr>
			    	<tr>
						<th class="spec" scope="row" style="font-weight: bold;">Crawler Site</th>
						<td><%=calc.getCrawler()%></td>
						<td><a href="<%=request.getContextPath()%>/jobs.jsp?state=<%=JobAdminStateFilter.CRAWLER%>">Go</a></td>
			    	</tr>
		    	</tbody>
	   		</table>
	   	</div>
	   	<div class="span-5">
	   		<table>
	   			<tbody>
		    		<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Ideasoft</th>
				    	<td><%=calc.getIdeasoft()%></td>
				    	<td><a href="<%=request.getContextPath()%>/jobs.jsp?state=<%=JobAdminStateFilter.IDEASOFT%>">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Hemenal</th>
				    	<td><%=calc.getHemenal()%></td>
				    	<td><a href="<%=request.getContextPath()%>/jobs.jsp?state=<%=JobAdminStateFilter.HEMENAL%>">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Kobimaster</th>
				    	<td><%=calc.getKobimaster()%></td>
				    	<td><a href="<%=request.getContextPath()%>/jobs.jsp?state=<%=JobAdminStateFilter.KOBIMASTER%>">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Prestashop</th>
				    	<td><%=calc.getPrestashop()%></td>
				    	<td><a href="<%=request.getContextPath()%>/jobs.jsp?state=<%=JobAdminStateFilter.PRESTASHOP%>">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Proj-e</th>
				    	<td><%=calc.getProje()%></td>
				    	<td><a href="<%=request.getContextPath()%>/jobs.jsp?state=<%=JobAdminStateFilter.PROJE%>">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">Neticaret</th>
				    	<td><%=calc.getNeticaret()%></td>
				    	<td><a href="<%=request.getContextPath()%>/jobs.jsp?state=<%=JobAdminStateFilter.NETICARET%>">Go</a></td>
			    	</tr>
			    	<tr>
				    	<th class="spec" scope="row" style="font-weight: bold;">None</th>
				    	<td><%=calc.getNoecommerce()%></td>
				    	<td></td>
			    	</tr>
		    	</tbody>
	   		</table>
	   	</div>	    
	   	<div class="cl"></div>
	</div>    	
</div>