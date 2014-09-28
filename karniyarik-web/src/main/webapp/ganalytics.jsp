<%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="org.apache.commons.lang.StringEscapeUtils"
%>		<script type="text/javascript">
			var _gaq = _gaq || [];
			<%
			String queryForTrack = "";
			Integer typeVal = null;
			
			Object searchResultObj = request.getAttribute(RequestWrapper.SEARCH_RESULT);
			if(searchResultObj != null)
			{
				SearchResult searchResult = (SearchResult) searchResultObj;
				queryForTrack = searchResult.getQuery();
				queryForTrack = StringEscapeUtils.unescapeHtml(queryForTrack);
				queryForTrack = StringEscapeUtils.escapeJavaScript(queryForTrack);				
			}
			
			Object typeValObj = request.getAttribute(RequestWrapper.GA_TRCK_TYPE_VALUE);
			if(typeValObj != null)
			{
				typeVal = (Integer) typeValObj;
			}
			%>
			_gaq.push(['_setAccount', 'UA-3933507-1']<%
					if(typeVal != null){%>,
					['_setCustomVar', 1, 'view-type', getViewEventName(<%=typeVal.toString()%>)]<%
					}
					if(StringUtils.isNotBlank(queryForTrack)){%>,
					['_setCustomVar', 2, 'query', '<%=queryForTrack%>']<%}%>,
					['_trackPageview']);
		
			(function() {
			    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
			    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
			    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
			  })();
		
			  /*__compete_code = 'a9c00ca1e3c6ad3a80e4e69757beb9fa';*/
		</script>